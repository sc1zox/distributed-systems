package exercise2;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlClient {
    static final String TARGET_URL = "http://neverssl.com";
    static final String OWN_SERVER_URL = "http://localhost:" + SocketServer.PORT;
    static final String PROXY_HOST = "localhost";
    static final int PROXY_PORT = 3128;

    public static void main(String[] args) {
        try {
            URL url = URI.create(TARGET_URL).toURL();
            URL ownSocket = URI.create(OWN_SERVER_URL).toURL();

            System.out.println("=== (b) ohne Proxy ===");
            fetch(url);
            fetch(ownSocket);

            System.out.println("=== (c) mit Proxy ===");
            fetchViaProxy(url, PROXY_HOST, PROXY_PORT);
            fetchViaProxy(ownSocket, PROXY_HOST, PROXY_PORT);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // (b) Direkte Verbindung zum Server
    static void fetch(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        printResponse(connection);
    }

    // (c) Verbindung über einen HTTP-Proxy
    static void fetchViaProxy(URL url, String proxyHost, int proxyPort) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
        printResponse(connection);
    }

    private static void printResponse(HttpURLConnection connection) throws IOException {
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "TestClient");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: ");

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            )) {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } finally {
            connection.disconnect();
        }
    }
}
