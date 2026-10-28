package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class SocketServer {
    static final int PORT = 8089;
    static final Path WEB_ROOT = Path.of("exercise2/www").toAbsolutePath().normalize();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server läuft auf http://localhost:"+PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                // Für jede eingehende Verbindung ein eigener Thread
                new Thread(() -> handle(socket)).start();
            }
        }
    }

    static void handle(Socket socket) {
        try (socket) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );
            OutputStream out = socket.getOutputStream();

            // Erste Zeile, z.B. "GET /index.html HTTP/1.1" -> der Pfad ist das zweite Wort
            String requestLine = in.readLine();
            if (requestLine == null) {
                return;
            }
            System.out.println(Thread.currentThread().getName() + ": " + requestLine);

            // Restliche Header bis zur Leerzeile lesen, sonst bricht die Verbindung beim Schließen ab
            String header;
            while ((header = in.readLine()) != null && !header.isEmpty()) {
            }

            String path = requestLine.split(" ")[1];
            if (path.equals("/")) {
                path = "/index.html";
            }
            Path file = WEB_ROOT.resolve(path.substring(1)).normalize();

            // Nur Dateien innerhalb von www ausliefern, damit "/../" nicht aus dem Ordner herausführt
            if (file.startsWith(WEB_ROOT) && Files.isRegularFile(file)) {
                send(out, "200 OK", Files.readAllBytes(file));
            } else {
                send(out, "404 Not Found", "404 Not Found".getBytes(StandardCharsets.UTF_8));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void send(OutputStream out, String status, byte[] body) throws IOException {
        String head = "HTTP/1.1 " + status + "\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        out.write(head.getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }
}
