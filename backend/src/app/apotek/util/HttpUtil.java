package app.apotek.util;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpUtil {
    public static void sendJson(HttpExchange ex, int status, Object body) throws IOException {
        byte[] resp = Json.stringify(body).getBytes(StandardCharsets.UTF_8);
        Headers h = ex.getResponseHeaders();
        h.add("Content-Type", "application/json; charset=utf-8");
        addCors(h);
        ex.sendResponseHeaders(status, resp.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(resp); }
    }
    public static void sendText(HttpExchange ex, int status, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        Headers h = ex.getResponseHeaders();
        h.add("Content-Type", "text/plain; charset=utf-8");
        addCors(h);
        ex.sendResponseHeaders(status, resp.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(resp); }
    }
    public static String body(HttpExchange ex) throws IOException {
        try (InputStream is = ex.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    public static Map<String,String> query(URI uri) {
        Map<String,String> q = new HashMap<>();
        String raw = uri.getRawQuery();
        if (raw == null || raw.isEmpty()) return q;
        for (String pair : raw.split("&")) {
            String[] kv = pair.split("=", 2);
            String k = decode(kv[0]);
            String v = kv.length>1 ? decode(kv[1]) : "";
            q.put(k, v);
        }
        return q;
    }
    public static void addCors(Headers h) {
        h.add("Access-Control-Allow-Origin", "*");
        h.add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        h.add("Access-Control-Allow-Headers", "Content-Type");
    }
    public static String decode(String s) {
        try { return URLDecoder.decode(s, StandardCharsets.UTF_8); }
        catch (Exception e) { return s; }
    }
    public static boolean isBlank(String s){ return s==null || s.trim().isEmpty(); }
    public static Integer parseInt(String s, Integer def) {
        try { return (s==null)?def:Integer.parseInt(s); } catch(Exception e){ return def; }
    }
    public static Long parseLong(String s) {
        try { return Long.parseLong(s); } catch (Exception e) { return null; }
    }
}
