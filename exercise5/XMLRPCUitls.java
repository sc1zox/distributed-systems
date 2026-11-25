package exercise5;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class XMLRPCUitls {

    // Util for writing XML RPC

    enum FaultCode {
        INVALID_ARGUMENT(1), // addRecord mit negativem Index
        NOT_FOUND(2); // getRecord auf nicht existierenden Record

        private final int code;

        FaultCode(int code) {
            this.code = code;
        }

        int code() {
            return code;
        }
    }

    static String resultXml(String value) {
        return "<?xml version=\"1.0\"?>\n"
                + "<methodResponse>\n"
                + "  <params>\n"
                + "    <param><value>" + value + "</value></param>\n"
                + "  </params>\n"
                + "</methodResponse>\n";
    }

    static String faultXml(FaultCode code, String message) {
        return "<?xml version=\"1.0\"?>\n"
                + "<methodResponse>\n"
                + "  <fault>\n"
                + "    <value>\n"
                + "      <struct>\n"
                + "        <member><name>faultCode</name><value><int>" + code.code() + "</int></value></member>\n"
                + "        <member><name>faultString</name><value><string>" + escape(message)
                + "</string></value></member>\n"
                + "      </struct>\n"
                + "    </value>\n"
                + "  </fault>\n"
                + "</methodResponse>\n";
    }

    static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    static void sendRequest(OutputStream out, String host, int port, String xml) throws IOException {
        byte[] body = xml.getBytes(StandardCharsets.UTF_8);
        String header = "POST /RPC2 HTTP/1.1\r\n"
                + "Host: " + host + ":" + port + "\r\n"
                + "User-Agent: SocketClient\r\n"
                + "Content-Type: text/xml\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";
        out.write(header.getBytes(StandardCharsets.US_ASCII));
        out.write(body);
        out.flush();
    }

    static void sendResponse(OutputStream out, String xml) throws IOException {
        byte[] body = xml.getBytes(StandardCharsets.UTF_8);
        String header = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/xml\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";
        out.write(header.getBytes(StandardCharsets.US_ASCII));
        out.write(body);
        out.flush();
    }

    // Util for reading XML RPC

    static byte[] readHttpMessage(InputStream in) throws IOException {
        String startLine = readLine(in);
        if (startLine == null) {
            return null;
        }

        int contentLength = -1;
        String line;
        while ((line = readLine(in)) != null && !line.isEmpty()) {
            int colon = line.indexOf(':');
            if (colon > 0 && line.substring(0, colon).trim().equalsIgnoreCase("Content-Length")) {
                contentLength = Integer.parseInt(line.substring(colon + 1).trim());
            }
        }
        if (contentLength < 0) {
            throw new IOException("Missing Content-Length");
        }

        // genau Content-Length Bytes, nicht bis zum Verbindungsende lesen
        byte[] body = in.readNBytes(contentLength);
        if (body.length < contentLength) {
            throw new IOException("Connection closed in the middle of the body");
        }
        return body;
    }

    private static String readLine(InputStream in) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\n') {
                break;
            }
            if (b != '\r') {
                buf.write(b);
            }
        }
        if (b == -1 && buf.size() == 0) {
            return null; // Stream zu Ende
        }
        return buf.toString(StandardCharsets.US_ASCII);
    }
}
