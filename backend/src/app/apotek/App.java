package app.apotek;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", ex -> {
            if ("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())) {
                // HttpUtil.addCors
            }
        });
    }
}