package exercise3.aufgabe1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class LogClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 7000;

        try (Socket socket = new Socket(host, port)) {
            OutputStream out = socket.getOutputStream();

            Person person = Person.newBuilder()
                    .setName("Max Mustermann")
                    .setOrt("Wedel")
                    .build();

            send(out, Severity.INFO, "Client started", null);
            send(out, Severity.WARN, "Disk almost full", person);
            send(out, Severity.ERROR, "Something went wrong", person);
        }
    }

    private static void send(OutputStream out, Severity severity, String text, Person person) throws IOException {
        LogMessage.Builder builder = LogMessage.newBuilder()
                .setCreatedAt(System.currentTimeMillis())
                .setSeverity(severity)
                .setMessage(text);
        if (person != null) {
            builder.setPerson(person);
        }

        // writes the size first, so the server knows where one message ends
        builder.build().writeDelimitedTo(out);
        out.flush();
    }
}
