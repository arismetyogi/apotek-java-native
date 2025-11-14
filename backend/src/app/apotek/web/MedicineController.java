package app.apotek.web;

import app.apotek.dao.MedicineDao;
import app.apotek.model.Medicine;
import app.apotek.model.Status;
import app.apotek.util.HttpUtil;
import app.apotek.util.Json;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.sql.Date;

public class MedicineController implements HttpHandler {

    private final MedicineDao dao;

    public MedicineController(MedicineDao dao) {
        this.dao = dao;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        try {
            if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) {
                HttpUtil.addCors(ex.getResponseHeaders());
                ex.sendResponseHeaders(204, -1);
                return;
            }
            String method = ex.getRequestMethod();
            String path = ex.getRequestURI().getPath(); // /Medicines, /Medicines/{id}, /Medicines/{id}/adjust
            String[] parts = path.split("/");

            if (parts.length == 2) {
                switch (method) {
                    case "GET"  -> handleList(ex);
                    case "POST" -> handleCreate(ex);
                    default     -> ex.sendResponseHeaders(405, -1);
                }
                return;
            }

            if (parts.length == 3) {
                Long id = HttpUtil.parseLong(parts[2]);
                if (id == null) { HttpUtil.sendJson(ex, 400, Map.of("error","BAD_REQUEST","message","ID tidak valid")); return; }
                switch (method) {
                    case "GET"    -> handleGet(ex, id);
                    case "PUT"    -> handleUpdate(ex, id);
                    case "DELETE" -> handleDelete(ex, id);
                    default       -> ex.sendResponseHeaders(405, -1);
                }
                return;
            }

            if (parts.length == 4 && "adjust".equalsIgnoreCase(parts[3])) {
                if (!"POST".equals(method)) { ex.sendResponseHeaders(405, -1); return; }
                Long id = HttpUtil.parseLong(parts[2]);
                if (id == null) { HttpUtil.sendJson(ex, 400, Map.of("error","BAD_REQUEST","message","ID tidak valid")); return; }
                handleAdjust(ex, id);
                return;
            }

            ex.sendResponseHeaders(404, -1);

        } catch (IllegalArgumentException iae) {
            HttpUtil.sendJson(ex, 400, Map.of("error", "BAD_REQUEST", "message", iae.getMessage()));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            HttpUtil.sendJson(ex, 500, Map.of("error","SQL_ERROR","message", sqle.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            HttpUtil.sendJson(ex, 500, Map.of("error", "INTERNAL_ERROR", "message", "Terjadi kesalahan server"));
        } finally {
            ex.close();
        }
    }

    private void handleList(HttpExchange ex) throws Exception {
        var q = HttpUtil.query(ex.getRequestURI());
        String search = Optional.ofNullable(q.get("q")).orElse("").trim();
        int page = HttpUtil.parseInt(q.get("page"), 0);
        int size = Math.max(1, HttpUtil.parseInt(q.get("size"), 10));

        MedicineDao.PageResult<Medicine> pr = dao.search(search, page, size);
        List<Map<String,Object>> rows = new ArrayList<>();
        for (Medicine it : pr.rows) rows.add(it.toMap());

        Map<String,Object> resp = new LinkedHashMap<>();
        resp.put("content", rows);
        resp.put("page", page);
        resp.put("size", size);
        resp.put("totalElements", pr.total);
        resp.put("totalPages", (int)Math.ceil(pr.total / (double)size));
        HttpUtil.sendJson(ex, 200, resp);
    }

    private void handleCreate(HttpExchange ex) throws Exception {
        var data = Json.parseFlatObject(HttpUtil.body(ex));
        String err = validateMedicineInput(data.get("name"), data.get("unit"), data.get("price"), 
                                       data.get("stock"), data.get("minStock"), data.get("expiryDate"), data.get("status"));
        if (err != null) { HttpUtil.sendJson(ex, 400, Map.of("error","VALIDATION","message",err)); return; }

        Medicine it = new Medicine();
        it.name = data.get("name").trim();
        it.unit = data.get("unit").trim();
        it.price = new BigDecimal(data.get("price"));
        it.stock = Integer.parseInt(data.get("stock"));
        it.minStock = Integer.parseInt(data.get("minStock"));
        it.expiryDate = Date.valueOf(data.get("expiryDate"));
        it.status = Status.valueOf(data.get("status").trim().toString().toUpperCase());

        dao.create(it);
        HttpUtil.sendJson(ex, 201, it.toMap());
    }

    private void handleGet(HttpExchange ex, long id) throws Exception {
        Medicine it = dao.findById(id);
        if (it == null) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }
        HttpUtil.sendJson(ex, 200, it.toMap());
    }

    private void handleUpdate(HttpExchange ex, long id) throws Exception {
        Medicine existing = dao.findById(id);
        if (existing == null) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }

        var data = Json.parseFlatObject(HttpUtil.body(ex));
        String err = validateMedicineInput(data.get("name"), data.get("unit"), data.get("price"),
                                       data.get("stock"), data.get("minStock"), data.get("expiryDate"), data.get("status"));
        if (err != null) { HttpUtil.sendJson(ex, 400, Map.of("error","VALIDATION","message",err)); return; }

        Medicine newVal = new Medicine();
        newVal.name = data.get("name").trim();
        newVal.unit = data.get("unit").trim();
        newVal.price = new BigDecimal(data.get("price"));
        newVal.stock = Integer.parseInt(data.get("stock"));
        newVal.minStock = Integer.parseInt(data.get("minStock"));
        newVal.expiryDate = Date.valueOf(data.get("expiryDate"));
        newVal.status = Status.valueOf(data.get("status").trim().toString().toUpperCase());

        Medicine updated = dao.update(id, newVal);
        if (updated == null) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }
        HttpUtil.sendJson(ex, 200, updated.toMap());
    }

    private void handleDelete(HttpExchange ex, long id) throws Exception {
        boolean ok = dao.delete(id);
        if (!ok) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }
        HttpUtil.sendJson(ex, 200, Map.of("status","DELETED"));
    }

    private void handleAdjust(HttpExchange ex, long id) throws Exception {
        Medicine existing = dao.findById(id);
        if (existing == null) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }

        var data = Json.parseFlatObject(HttpUtil.body(ex));
        String deltaStr = data.get("delta");
        if (HttpUtil.isBlank(deltaStr)) { HttpUtil.sendJson(ex, 400, Map.of("error","VALIDATION","message","delta wajib diisi")); return; }

        int delta;
        try { delta = Integer.parseInt(deltaStr); }
        catch (Exception e) { HttpUtil.sendJson(ex, 400, Map.of("error","VALIDATION","message","delta harus integer")); return; }

        try {
            Medicine adjusted = dao.adjust(id, delta);
            if (adjusted == null) { HttpUtil.sendJson(ex, 404, Map.of("error","NOT_FOUND","message","Medicine tidak ditemukan")); return; }
            HttpUtil.sendJson(ex, 200, adjusted.toMap());
        } catch (IllegalArgumentException iae) {
            HttpUtil.sendJson(ex, 400, Map.of("error","BAD_REQUEST","message", iae.getMessage()));
        }
    }

    private String validateMedicineInput(String name, String unit, String price, String stock, String minStock, String expiryDate, String status) {
        if (HttpUtil.isBlank(name)) return "Nama obat tidak boleh kosong";
        if (HttpUtil.isBlank(unit)) return "Satuan tidak boleh kosong";
        if (HttpUtil.isBlank(stock)) return "Stock tidak boleh kosong";
        if (HttpUtil.isBlank(minStock)) return "Minimun stock tidak boleh kosong";
        if (HttpUtil.isBlank(expiryDate)) return "Tanggal Expired tidak boleh kosong";
        int q;
        try { q = Integer.parseInt(stock); } catch (Exception e) { return "Stock harus integer >= 0"; }
        if (q < 0) return "Stock harus >= 0";
        try { q = Integer.parseInt(minStock); } catch (Exception e) { return "Minimum Stock harus integer >= 0"; }
        if (q < 0) return "Minimum Stock harus >= 0";
        try {
            var up = new BigDecimal(price);
            if (up.signum() < 0) return "price harus >= 0";
        } catch (Exception e) { return "price harus numerik"; }
        return null;
    }
}
