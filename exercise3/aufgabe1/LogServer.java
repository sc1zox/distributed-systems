package exercise3.aufgabe1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;

public class LogServer {
    private static Writer logFile;

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 7000;

        // true = append to the file instead of overwriting it
        logFile = new FileWriter("server.log", true);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Log server started on port " + port);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New connection from: " + socket.getInetAddress());

            // one new thread for each client
            new Thread(() -> handleClient(socket)).start();
        }
    }

    private static void handleClient(Socket socket) {
        try (socket) {
            InputStream in = socket.getInputStream();

            // returns null when the client closes the connection
            LogMessage msg;
            while ((msg = LogMessage.parseDelimitedFrom(in)) != null) {
                writeLine(format(msg));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // e.g. 2026-10-29T14:23:00.123Z WARN [Max Mustermann, Wedel] Disk almost full
    private static String format(LogMessage msg) {
        String line = Instant.ofEpochMilli(msg.getCreatedAt()) + " " + msg.getSeverity();
        if (msg.hasPerson()) {
            line += " [" + msg.getPerson().getName() + ", " + msg.getPerson().getOrt() + "]";
        }
        // escape line breaks, so every entry stays on one line
        return line + " " + msg.getMessage().replace("\n", "\\n");
    }

    // synchronized, so lines of different clients do not get mixed up
    private static synchronized void writeLine(String line) throws IOException {
        logFile.write(line + System.lineSeparator());
        logFile.flush();
        System.out.println(line);
    }
}
