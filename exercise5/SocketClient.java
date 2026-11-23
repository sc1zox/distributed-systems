package exercise5;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SocketClient {

    private static final Map<Integer, String> RECORDS_TO_ADD = new LinkedHashMap<>();
    static {
        RECORDS_TO_ADD.put(4101, "Appen");
        RECORDS_TO_ADD.put(4102, "Ahrensburg");
        RECORDS_TO_ADD.put(4103, "Wedel");
        RECORDS_TO_ADD.put(4104, "Aumuhle");
        RECORDS_TO_ADD.put(4105, "Seevetal");
        RECORDS_TO_ADD.put(4106, "Quickborn");
    }

    private static String host = "localhost";
    private static int port = 8080;

    // Util for client RPC

    private static String addRecordXml(int index, String record) {
        return methodCallXml("addRecord",
                "<int>" + index + "</int>",
                "<string>" + XMLRPCUitls.escape(record) + "</string>");
    }

    private static String getRecordXml(int index) {
        return methodCallXml("getRecord",
                "<int>" + index + "</int>");
    }

    private static String getSizeXml() {
        return methodCallXml("getSize");
    }

    private static void line(StringBuilder xml, int depth, String content) {
        xml.append("  ".repeat(depth)).append(content).append('\n');
    }

    private static String methodCallXml(String methodName, String... values) {
        StringBuilder xml = new StringBuilder();
        line(xml, 0, "<?xml version=\"1.0\"?>");
        line(xml, 0, "<methodCall>");
        line(xml, 1, "<methodName>" + methodName + "</methodName>");
        if (values.length == 0) {
            line(xml, 1, "<params/>");
        } else {
            line(xml, 1, "<params>");
            for (String value : values) {
                line(xml, 2, "<param><value>" + value + "</value></param>");
            }
            line(xml, 1, "</params>");
        }
        line(xml, 0, "</methodCall>");
        return xml.toString();
    }

    private static Document sendAndParse(DocumentBuilder db, OutputStream out, String requestXml, InputStream in)
            throws IOException, SAXException {
        XMLRPCUitls.sendRequest(out, host, port, requestXml);
        byte[] body = XMLRPCUitls.readHttpMessage(in);
        if (body == null) {
            throw new IOException("Server closed the connection");
        }
        return db.parse(new ByteArrayInputStream(body));
    }

    private static String readResponse(Document response) {
        if (response.getElementsByTagName("fault").getLength() > 0) {
            NodeList values = response.getElementsByTagName("value");

            String code = values.item(1).getTextContent().trim();
            String message = values.item(2).getTextContent();
            return "Fault " + code + ": " + message;
        }

        return response.getElementsByTagName("value").item(0).getTextContent();
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        try (Socket socket = new Socket(host, port)) {
            // Add Record Queries
            for (Map.Entry<Integer, String> entry : RECORDS_TO_ADD.entrySet()) {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                String xmlRequest = addRecordXml(entry.getKey(), entry.getValue());

                Document response = sendAndParse(db, out, xmlRequest, in);

                System.out.println(readResponse(response));
            }

            // Get Record Querys
            for (int index : new int[] { 4103, 4107 }) {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                String xmlRequest = getRecordXml(index);

                Document response = sendAndParse(db, out, xmlRequest, in);
                System.out.println(readResponse(response));
            }

            // Get Size Query
            {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                String xmlRequest = getSizeXml();

                Document response = sendAndParse(db, out, xmlRequest, in);

                System.out.println(readResponse(response));
            }
        }
    }
}
