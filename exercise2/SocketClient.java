package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "stud.fh-wedel.de";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 80;
        String path = args.length > 2 ? args[2] : "/index.html";

        try (Socket socket = new Socket(host, port)) {
            // send request
            String request = "GET " + path + " HTTP/1.1\r\n"
                    + "Host: " + host + "\r\n"
                    + "Connection: close\r\n"
                    + "\r\n";
            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes());
            out.flush();

            // read response until the server closes the connection
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
