package app.apotek.web;

import app.apotek.dao.PatientDao;
import app.apotek.model.Patient;
import app.apotek.util.HttpUtil;
import app.apotek.util.Json;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class PatientHandler implements HttpHandler {

    private final PatientDao dao;

    public PatientHandler(PatientDao dao) {
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
            String path = ex.getRequestURI().getPath(); // /Pasiens, /Pasiens/{id}, /Pasiens/{id}/adjust
            String[] parts = path.split("/");

            if (parts.length == 2) {
                switch (method) {
                    case "GET" -> handleList(ex);
                    case "POST" -> handleCreate(ex);
                    default -> ex.sendResponseHeaders(405, -1);
                }
                return;
            }

            if (parts.length == 3) {
                Long id = HttpUtil.parseLong(parts[2]);
                if (id == null) {
                    HttpUtil.sendJson(ex, 400, Map.of("error", "BAD_REQUEST", "message", "ID tidak valid"));
                    return;
                }
                switch (method) {
                    case "GET" -> handleGet(ex, id);
                    case "PUT" -> handleUpdate(ex, id);
                    case "DELETE" -> handleDelete(ex, id);
                    default -> ex.sendResponseHeaders(405, -1);
                }
                return;
            }

            ex.sendResponseHeaders(404, -1);

        } catch (IllegalArgumentException iae) {
            HttpUtil.sendJson(ex, 400, Map.of("error", "BAD_REQUEST", "message", iae.getMessage()));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            HttpUtil.sendJson(ex, 500, Map.of("error", "SQL_ERROR", "message", sqle.getMessage()));
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

        PatientDao.PageResult<Patient> pr = dao.search(search, page, size);
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Patient it : pr.rows)
            rows.add(it.toMap());

        Map<String, Object> resp = new LinkedHashMap<>();
        resp.put("content", rows);
        resp.put("page", page);
        resp.put("size", size);
        resp.put("totalElements", pr.total);
        resp.put("totalPages", (int) Math.ceil(pr.total / (double) size));
        HttpUtil.sendJson(ex, 200, resp);
    }

    private void handleCreate(HttpExchange ex) throws Exception {
        var data = Json.parseFlatObject(HttpUtil.body(ex));
        String err = validatePatientInput(data.get("fullName"), data.get("phone"), data.get("address"));
        if (err != null) {
            HttpUtil.sendJson(ex, 400, Map.of("error", "VALIDATION", "message", err));
            return;
        }

        Patient it = new Patient();
        it.fullName = data.get("fullName").trim();
        it.phone = data.get("phone").trim();
        it.address = data.get("address").trim();

        dao.create(it);
        HttpUtil.sendJson(ex, 201, it.toMap());
    }

    private void handleGet(HttpExchange ex, long id) throws Exception {
        Patient it = dao.findById(id);
        if (it == null) {
            HttpUtil.sendJson(ex, 404, Map.of("error", "NOT_FOUND", "message", "Pasien tidak ditemukan"));
            return;
        }
        HttpUtil.sendJson(ex, 200, it.toMap());
    }

    private void handleUpdate(HttpExchange ex, long id) throws Exception {
        Patient existing = dao.findById(id);
        if (existing == null) {
            HttpUtil.sendJson(ex, 404, Map.of("error", "NOT_FOUND", "message", "Pasien tidak ditemukan"));
            return;
        }

        var data = Json.parseFlatObject(HttpUtil.body(ex));
        String err = validatePatientInput(data.get("fullName"), data.get("phone"), data.get("address"));
        if (err != null) {
            HttpUtil.sendJson(ex, 400, Map.of("error", "VALIDATION", "message", err));
            return;
        }

        Patient newVal = new Patient();
        newVal.fullName = data.get("full_name").trim();
        newVal.phone = data.get("phone").trim();
        newVal.address = data.get("address").trim();

        Patient updated = dao.update(id, newVal);
        if (updated == null) {
            HttpUtil.sendJson(ex, 404, Map.of("error", "NOT_FOUND", "message", "Pasien tidak ditemukan"));
            return;
        }
        HttpUtil.sendJson(ex, 200, updated.toMap());
    }

    private void handleDelete(HttpExchange ex, long id) throws Exception {
        boolean ok = dao.delete(id);
        if (!ok) {
            HttpUtil.sendJson(ex, 404, Map.of("error", "NOT_FOUND", "message", "Pasien tidak ditemukan"));
            return;
        }
        HttpUtil.sendJson(ex, 200, Map.of("status", "DELETED"));
    }

    private String validatePatientInput(String fullName, String phone, String address) {
        if (HttpUtil.isBlank(fullName))
            return "Nama pasien tidak boleh kosong";
        if (HttpUtil.isBlank(phone))
            return "No Telp pasien tidak boleh kosong";
        if (HttpUtil.isBlank(address))
            return "Alamat pasien tidak boleh kosong";
        return null;
    }
}
