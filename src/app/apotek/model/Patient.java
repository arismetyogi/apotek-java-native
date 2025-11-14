package app.apotek.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Patient {
    protected long id;
    protected String fullName;
    protected String phone;
    protected String address;
    protected long createdAt;
    
    public Map<String, Object> toMap(){
        Map<String, Object> pat = new LinkedHashMap<>();

        pat.put("id", id);
        pat.put("fullName", fullName);
        pat.put("phone", phone);
        pat.put("address", address);
        pat.put("createdAt", createdAt);
        return pat;
    }
}

// id SERIAL PRIMARY KEY,
// full_name VARCHAR(100) NOT NULL,
// phone VARCHAR(20),
// address TEXT,
// created_at TIMESTAMP DEFAULT CURRENT_TIMESTAM