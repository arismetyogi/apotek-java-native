package app.inventory.util;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class Json {
    public static String stringify(Object o) {
        if (o == null) return "null";
        if (o instanceof String s) return "\"" + escape(s) + "\"";
        if (o instanceof Number || o instanceof Boolean) return o.toString();
        if (o instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (var e : map.entrySet()) {
                if (!first) sb.append(",");
                first = false;
                sb.append(stringify(String.valueOf(e.getKey()))).append(":").append(stringify(e.getValue()));
            }
            sb.append("}");
            return sb.toString();
        }
        if (o instanceof Iterable<?> it) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object el : it) {
                if (!first) sb.append(",");
                first = false;
                sb.append(stringify(el));
            }
            sb.append("]");
            return sb.toString();
        }
        return "\"" + escape(String.valueOf(o)) + "\"";
    }
    private static String escape(String s) {
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r").replace("\t","\\t");
    }

    public static Map<String, String> parseFlatObject(String json) {
        Map<String,String> out = new HashMap<>();
        if (json == null) return out;
        json = json.trim();
        if (!(json.startsWith("{") && json.endsWith("}"))) return out;
        json = json.substring(1, json.length() - 1).trim();
        if (json.isEmpty()) return out;

        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inStr = false;
        for (int i=0;i<json.length();i++){
            char c = json.charAt(i);
            if (c=='"' && (i==0 || json.charAt(i-1)!='\\')) inStr = !inStr;
            if (c==',' && !inStr) { parts.add(cur.toString()); cur.setLength(0); }
            else cur.append(c);
        }
        parts.add(cur.toString());

        for (String p : parts) {
            int idx = p.indexOf(':');
            if (idx<0) continue;
            String k = stripQuotes(p.substring(0, idx).trim());
            String v = stripQuotes(p.substring(idx+1).trim());
            out.put(k, v);
        }
        return out;
    }
    private static String stripQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length()-1)
                 .replace("\\\"", "\"").replace("\\\\","\\")
                 .replace("\\n","\n").replace("\\r","\r").replace("\\t","\t");
        }
        return s;
    }
    public static byte[] bytes(String s){ return s.getBytes(StandardCharsets.UTF_8); }
}
