package exercise2;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class HttpRequestHandler implements Runnable {
    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    // run() is executed in the new thread
    @Override
    public void run() {
        try {
            handleRequest();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // nothing more to do
            }
        }
    }

    private void handleRequest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();

        // first line looks like: GET /index.html HTTP/1.1
        String requestLine = in.readLine();
        if (requestLine == null) {
            return;
        }
        System.out.println("Request: " + requestLine);

        // read the remaining header lines until the empty line
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            // headers are ignored in this simple server
        }

        // split the request line into its parts
        String[] parts = requestLine.split(" ");
        if (parts.length < 2 || !parts[0].equals("GET")) {
            sendText(out, "400 Bad Request", "Only GET is supported");
            return;
        }
        String path = parts[1];

        // "/" means index.html
        if (path.equals("/")) {
            path = "/index.html";
        }

        // do not allow ".." (would leave the www folder)
        if (path.contains("..")) {
            sendText(out, "403 Forbidden", "Not allowed");
            return;
        }

        // files are served from the folder "www"
        File file = new File("www" + path);
        if (file.isFile()) {
            byte[] content = Files.readAllBytes(file.toPath());
            sendResponse(out, "200 OK", contentType(path), content);
        } else {
            sendText(out, "404 Not Found", "File not found: " + path);
        }
    }

    // choose the content type from the file ending
    private String contentType(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".txt")) return "text/plain";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg")) return "image/jpeg";
        return "application/octet-stream";
    }

    private void sendText(OutputStream out, String status, String text) throws IOException {
        sendResponse(out, status, "text/plain", text.getBytes());
    }

    // status line, headers, empty line, then the body
    private void sendResponse(OutputStream out, String status, String type, byte[] body) throws IOException {
        String header = "HTTP/1.1 " + status + "\r\n"
                + "Content-Type: " + type + "\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";
        out.write(header.getBytes());
        out.write(body);
        out.flush();
    }
}
