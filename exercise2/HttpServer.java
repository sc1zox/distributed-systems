package exercise2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;

        // the server socket waits for connection on this port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            // accept() blocks until a client connects
            Socket socket = serverSocket.accept();
            System.out.println("New connection from: " + socket.getInetAddress());

            // one new thread for each incoming request
            HttpRequestHandler handler = new HttpRequestHandler(socket);

            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}
