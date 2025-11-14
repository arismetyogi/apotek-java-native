package app.apotek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import app.apotek.model.Medicine;
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
                INSERT INTO MEDICINES (name, unit, price, stock, min_stock, expiry_date, status)
                VALUE (?, ?, ?, ?, ?, ?, ?)
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
            ps.setInt(5, med.stock);
            ps.setInt(6, med.minStock);
            ps.setLong(7, med.expiryDate);
            ps.setString(8, med.status.toString());

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
                SELECT id, name, unit, price, stock, min_stock, expiry_data, status, EXTRACT(EPOCH FROM created_at)*1000 AS created_ms,
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
            ps.setLong(6, updated.expiryDate);
            ps.setString(7, updated.status.toString());
            ps.setLong(8, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    private static Medicine map(ResultSet rs) throws SQLException {
        Medicine it = new Medicine();
        it.id = rs.getLong("id");
        it.name = rs.getString("name");
        it.unit = rs.getString("unit");
        it.price = rs.getBigDecimal("price");
        it.stock = rs.getInt("stock");
        it.minStock = rs.getInt("min_stock");
        it.expiryDate = rs.getLong("expiry_date");
        it.createdAt = rs.getLong("created_ms");
        return it;
    }
}
