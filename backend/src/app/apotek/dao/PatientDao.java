package app.apotek.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.apotek.db.Db;
import app.apotek.model.Patient;

public class PatientDao {

    public static class PageResult<T> {
        public final List<T> rows;
        public final long total;

        public PageResult(List<T> rows, long total) {
            this.rows = rows;
            this.total = total;
        }
    }

    public Patient create(Patient pat) throws SQLException {
        String qString = """
                INSERT INTO patients (full_name, phone, address)
                VALUES (?, ?, ?)
                RETURNING id,full_name, phone, address,
                        EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                        EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                """;

        try (
                Connection conn = Db.getConnection();
                PreparedStatement ps = conn.prepareStatement(qString)) {
            ps.setString(1, pat.fullName);
            ps.setString(2, pat.phone);
            ps.setString(3, pat.address);

            try (ResultSet rSet = ps.executeQuery()) {
                if (rSet.next()) {
                    pat.id = rSet.getLong("id");
                    pat.createdAt = rSet.getLong("created_ms");
                    return pat;
                }
            }
        }
        throw new SQLException("Failed to create new patient");
    }

    public Patient findById(long id) throws SQLException {
        String qString = """
                SELECT id, full_name, phone, address, 
                    EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                    EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                FROM patients WHERE id = ?
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

    public Patient update(long id, Patient updated) throws SQLException {
        // versi sederhana: update by id, increment version
        String sql = """
                   UPDATE patients
                      SET full_name=?, phone=?, address=?, updated_at=NOW()
                    WHERE id=?
                RETURNING id, full_name, phone, address,
                          EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                          EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
                   """;
        try (Connection c = Db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, updated.fullName);
            ps.setString(2, updated.phone);
            ps.setString(3, updated.address);
            ps.setLong(4, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    public boolean delete(long id) throws SQLException {
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM patients WHERE id=?")) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // ---- List + Search + Pagination ----
    public PageResult<Patient> search(String q, int page, int size) throws SQLException {
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (q != null && !q.isBlank()) {
            where.append(" AND (LOWER(full_name) LIKE LOWER(?) OR LOWER(phone) LIKE LOWER(?)) ");
            String like = "%" + q.trim() + "%";
            params.add(like);
        }

        long total;
        String sqlCount = "SELECT COUNT(*) FROM patients " + where;
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlCount)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next(); total = rs.getLong(1);
            }
        }

        String sql = """
            SELECT id, full_name, phone, address,
                   EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
                   EXTRACT(EPOCH FROM updated_at)*1000 AS updated_ms
              FROM patients
            """ + where + " ORDER BY id ASC LIMIT ? OFFSET ?";

        List<Patient> rows = new ArrayList<>();
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            List<Object> p2 = new ArrayList<>(params);
            p2.add(size);
            p2.add(page * (long)size);
            bind(ps, p2);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) rows.add(map(rs));
            }
        }
        return new PageResult<>(rows, total);
    }

    // ---- helpers ----
    private static void bind(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i=0;i<params.size();i++) {
            Object v = params.get(i);
            if (v instanceof String s) ps.setString(i+1, s);
            else if (v instanceof Integer n) ps.setInt(i+1, n);
            else if (v instanceof Long l) ps.setLong(i+1, l);
            else if (v instanceof BigDecimal b) ps.setBigDecimal(i+1, b);
            else ps.setObject(i+1, v);
        }
    }

    private static Patient map(ResultSet rs) throws SQLException {
        Patient pat = new Patient();
        pat.id = rs.getLong("id");
        pat.fullName = rs.getString("full_name");
        pat.phone = rs.getString("phone");
        pat.createdAt = rs.getLong("created_ms");
        pat.updatedAt = rs.getLong("updated_ms");
        return pat;
    }
}
