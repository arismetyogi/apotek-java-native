package app.apotek.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Patient {
    public long id;
    public String fullName;
    public String phone;
    public String address;
    public long createdAt;
    public long updatedAt;
    
    public Map<String, Object> toMap(){
        Map<String, Object> pat = new LinkedHashMap<>();

        pat.put("id", id);
        pat.put("fullName", fullName);
        pat.put("phone", phone);
        pat.put("address", address);
        pat.put("createdAt", createdAt);
        pat.put("updatedAt", updatedAt);
        return pat;
    }
}