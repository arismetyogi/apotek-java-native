package app.apotek.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Medicine {
    public long id;
    public String name;
    public String unit;
    public BigDecimal price;
    public int stock;
    public int minStock;
    public long expiryDate;
    public Status status;
    public long createdAt;
    public long updatedAt;

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
        med.put("updatedAt", updatedAt);
        return med;
    }

}
