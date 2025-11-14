package app.apotek;

import com.sun.net.httpserver.HttpServer;

import app.apotek.dao.MedicineDao;
import app.apotek.dao.PatientDao;
import app.apotek.util.HttpUtil;
import app.apotek.web.MedicineHandler;
import app.apotek.web.PatientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {
        MedicineDao medicineDao = new MedicineDao();
        PatientDao patientDao = new PatientDao();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", ex -> {
            if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) {
                HttpUtil.addCors(ex.getResponseHeaders());
                ex.sendResponseHeaders(204, -1);
                ex.close();
                return;
            }
            if ("/".equals(ex.getRequestURI().getPath())) {
                HttpUtil.sendText(ex, 200, "Inventory Native Java (PostgreSQL) running. See /medicines or /patients");
            } else {
                ex.sendResponseHeaders(404, -1);
            }
            ex.close();
        });

        server.createContext("/medicines", new MedicineHandler(medicineDao));
        server.createContext("/patients", new PatientHandler(patientDao));
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        System.out.println("Server started at http://localhost:8080");
        server.start();

    }
}