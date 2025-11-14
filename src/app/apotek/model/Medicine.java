package app.apotek.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

public class Medicine {
    protected long id;
    protected String name;
    protected String unit;
    protected BigDecimal price;
    protected int stock;
    protected int minStock;
    protected Timestamp expiryDate;
    protected Status status;
    protected long createdAt;

    public Map<String, Object> toMap() {
        Map<String, Object> med = new LinkedHashMap<>();

        med.put("id", id);
        med.put("name", name);
        med.put("unit", unit);
        med.put("price", price);
        med.put("stock", stock);
        med.put("minStock", minStock);
        med.put("expiryDate", expiryDate);
        med.put("status", status);
        med.put("createdAt", createdAt);
        return med;
    }

}
