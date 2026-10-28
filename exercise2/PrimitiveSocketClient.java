package exercise2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PrimitiveSocketClient {
    static final String PROXY_HOST = "localhost";
    static final int PROXY_PORT = 3128;

    public static void main(String[] args) {
        // Beliebiger Server per Argument, z.B. im FH-Wedel-Intranet: <host> <port> <pfad>
        if (args.length == 3) {
            System.out.println("=== 1(a) " + args[0] + " ===");
            fetch(args[0], Integer.parseInt(args[1]), args[2]);
            return;
        }

        System.out.println("=== 1(a) Internet ===");
        fetch("neverssl.com", 80, "/");

        System.out.println("=== 1(c) mit Proxy ===");
        fetchViaProxy("neverssl.com", 80, "/", PROXY_HOST, PROXY_PORT);

        System.out.println("=== 2(c) eigener SocketServer ===");
        fetch("localhost", SocketServer.PORT, "/index.html");
    }

    // 1(a) Direkte Verbindung zum Server, in der Request-Zeile steht nur der Pfad
    static void fetch(String host, int port, String path) {
        send(host, port, host, port, path);
    }

    // 1(c) Verbindung zum Proxy statt zum Server, in der Request-Zeile steht die vollständige URL
    static void fetchViaProxy(String host, int port, String path, String proxyHost, int proxyPort) {
        send(proxyHost, proxyPort, host, port, "http://" + host + ":" + port + path);
    }

    private static void send(String connectHost, int connectPort, String host, int port, String target) {
        try (Socket socket = new Socket(connectHost, connectPort)) {
            System.out.println("Socket connected \n" + socket.getInetAddress());

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.print("GET " + target + " HTTP/1.1\r\n");
            out.print("Host: " + host + (port == 80 ? "" : ":" + port) + "\r\n");
            out.print("User-Agent: SocketClient\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");
            out.flush();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
