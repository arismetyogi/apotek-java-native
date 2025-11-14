package app.apotek.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.apotek.model.Medicine;
import app.apotek.model.Status;
import app.apotek.db.Db;

public class MedicineDao {

    public static class PageResult<T> {
        public final List<T> rows;
        public final long total;

        public PageResult(List<T> rows, long total) {
            this.rows = rows;
            this.total = total;
        }
    }

    public Medicine create(Medicine med) throws SQLException {
        String qString = """
                INSERT INTO medicines (name, unit, price, stock, min_stock, expiry_date, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                RETURNING id,name,unit,price,stock,min_stock,expiry_date,status,
                        EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                        EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                """;

        try (
                Connection conn = Db.getConnection();
                PreparedStatement ps = conn.prepareStatement(qString)) {
            ps.setString(1, med.name);
            ps.setString(2, med.unit);
            ps.setBigDecimal(3, med.price);
            ps.setInt(4, med.stock);
            ps.setInt(5, med.minStock);
            ps.setDate(6, med.expiryDate);
            ps.setString(7, med.status.toString());

            try (ResultSet rSet = ps.executeQuery()) {
                if (rSet.next()) {
                    med.id = rSet.getLong("id");
                    med.createdAt = rSet.getLong("created_ms");
                    return med;
                }
            }
        }
        throw new SQLException("Failed to create new medicine");
    }

    public Medicine findById(long id) throws SQLException {
        String qString = """
                SELECT id, name, unit, price, stock, min_stock, expiry_date, status,
                    EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                    EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                FROM medicines WHERE id = ?
                """;

        try (Connection conn = Db.getConnection(); PreparedStatement ps = conn.prepareStatement(qString)) {
            ps.setLong(1, id);
            try (ResultSet rSet = ps.executeQuery()) {
                if (rSet.next())
                    return map(rSet);
            }
        }
        return null;
    }

    public Medicine update(long id, Medicine updated) throws SQLException {
        // versi sederhana: update by id, increment version
        String sql = """
                   UPDATE medicines
                      SET name=?, unit=?, price=?, stock=?, min_stock=?,
                          expiry_date=?, status=?, updated_at=NOW()
                    WHERE id=?
                RETURNING id, name, unit, price, stock, min_stock, expiry_date, status,
                          EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                          EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                   """;
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, updated.name);
            ps.setString(2, updated.unit);
            ps.setBigDecimal(3, updated.price);
            ps.setInt(4, updated.stock);
            ps.setInt(5, updated.minStock);
            ps.setDate(6, updated.expiryDate);
            ps.setString(7, updated.status.toString());
            ps.setLong(8, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    public boolean delete(long id) throws SQLException {
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement("DELETE FROM medicines WHERE id=?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean existsMedicine(String name, Long excludeId) throws SQLException {
        String sql = "SELECT 1 FROM medicines WHERE LOWER(name)=LOWER(?)" +
                (excludeId != null ? " AND id<>?" : "") + " LIMIT 1";
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            if (excludeId != null)
                ps.setLong(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public PageResult<Medicine> lowStock(int page, int size) throws SQLException {
        StringBuilder where = new StringBuilder(" WHERE 1=1 AND stock < min_stock ");
        List<Object> params = new ArrayList<>();

        long total;
        String sqlCount = "SELECT COUNT(*) FROM medicines " + where;
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sqlCount)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                total = rs.getLong(1);
            }
        }

        String sql = """
                SELECT id, name, unit, price, stock, min_stock, expiry_date, status,
                       EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                       EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                  FROM medicines
                """ + where + " ORDER BY stock ASC LIMIT ? OFFSET ?";

        List<Medicine> rows = new ArrayList<>();
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            List<Object> p2 = new ArrayList<>(params);
            p2.add(size);
            p2.add(page * (long) size);
            bind(ps, p2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    rows.add(map(rs));
            }
        }
        return new PageResult<>(rows, total);
    }

    public PageResult<Medicine> nearExpiry(int page, int size) throws SQLException {

        StringBuilder where = new StringBuilder(" WHERE 1=1 AND expiry_date < CURRENT_DATE + INTERVAL '30 days' ");
        List<Object> params = new ArrayList<>();

        long total;
        String sqlCount = "SELECT COUNT(*) FROM medicines " + where;
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sqlCount)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                total = rs.getLong(1);
            }
        }

        String sql = """
                SELECT id, name, unit, price, stock, min_stock, expiry_date, status,
                       EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                       EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                  FROM medicines
                """ + where + " ORDER BY expiry_date ASC LIMIT ? OFFSET ?";

        List<Medicine> rows = new ArrayList<>();
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            List<Object> p2 = new ArrayList<>(params);
            p2.add(size);
            p2.add(page * (long) size);
            bind(ps, p2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    rows.add(map(rs));
            }
        }
        return new PageResult<>(rows, total);
    }

    // ---- Adjust stok (transaksional) ----
    public Medicine adjust(long id, int delta) throws SQLException {
        try (Connection c = Db.getConnection()) {
            c.setAutoCommit(false);
            try {
                // Ambil current Stock
                int curStock = 0;
                try (PreparedStatement ps = c.prepareStatement("SELECT stock FROM medicines WHERE id=? FOR UPDATE")) {
                    ps.setLong(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            c.rollback();
                            return null;
                        }
                        curStock = rs.getInt(1);
                    }
                }
                int newStock = curStock + delta;
                if (newStock < 0) {
                    c.rollback();
                    throw new IllegalArgumentException("Stok tidak boleh negatif");
                }
                // Update
                try (PreparedStatement ps = c.prepareStatement("""
                           UPDATE medicines SET stock=?, updated_at=NOW()
                            WHERE id=?
                        RETURNING id, name, unit, price, stock, min_stock, status,
                                  EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                                  EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                           """)) {
                    ps.setInt(1, newStock);
                    ps.setLong(2, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Medicine out = map(rs);
                            c.commit();
                            return out;
                        }
                    }
                }
                c.rollback();
                return null;
            } catch (RuntimeException | SQLException ex) {
                c.rollback();
                throw ex;
            } finally {
                c.setAutoCommit(true);
            }
        }
    }

    // ---- List + Search + Pagination ----
    public PageResult<Medicine> search(String q, int page, int size) throws SQLException {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (q != null && !q.isBlank()) {
            where.append(" AND LOWER(name) LIKE LOWER(?) ");
            String like = "%" + q.trim() + "%";
            params.add(like);
        }

        long total;
        String sqlCount = "SELECT COUNT(*) FROM medicines " + where;
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sqlCount)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                total = rs.getLong(1);
            }
        }

        String sql = """
                SELECT id, name, unit, price, stock, min_stock, expiry_date, status,
                       EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                       EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                  FROM medicines
                """ + where + " ORDER BY id ASC LIMIT ? OFFSET ?";

        List<Medicine> rows = new ArrayList<>();
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            List<Object> p2 = new ArrayList<>(params);
            p2.add(size);
            p2.add(page * (long) size);
            bind(ps, p2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    rows.add(map(rs));
            }
        }
        return new PageResult<>(rows, total);
    }

    // ---- helpers ----
    private static void bind(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object v = params.get(i);
            if (v instanceof String s)
                ps.setString(i + 1, s);
            else if (v instanceof Integer n)
                ps.setInt(i + 1, n);
            else if (v instanceof Long l)
                ps.setLong(i + 1, l);
            else if (v instanceof BigDecimal b)
                ps.setBigDecimal(i + 1, b);
            else
                ps.setObject(i + 1, v);
        }
    }

    private static Medicine map(ResultSet rs) throws SQLException {
        Medicine it = new Medicine();
        it.id = rs.getLong("id");
        it.name = rs.getString("name");
        it.unit = rs.getString("unit");
        it.price = rs.getBigDecimal("price");
        it.stock = rs.getInt("stock");
        it.minStock = rs.getInt("min_stock");
        it.expiryDate = rs.getDate("expiry_date");
        it.status = Status.valueOf(rs.getString("status"));
        it.createdAt = rs.getLong("created_ms");
        it.updatedAt = rs.getLong("updated_ms");
        return it;
    }
}
