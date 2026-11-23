package exercise5;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class HttpRequestHandler implements Runnable {
    // the records are shared by all connections, so the map has to be thread safe
    private static final Map<Integer, String> RECORDS = new ConcurrentHashMap<>();

    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    // run() is executed in the new thread
    @Override
    public void run() {
        try {
            handleRequests();
        } catch (IOException | ParserConfigurationException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // nothing more to do
            }
        }
    }

    private void handleRequests() throws IOException, ParserConfigurationException {

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        while (true) {
            byte[] body = XMLRPCUitls.readHttpMessage(in);
            if (body == null) {
                return; // Client hat die Verbindung geschlossen
            }

            String responseXml;
            try {
                Document request = db.parse(new ByteArrayInputStream(body));
                responseXml = handleRequest(request);
            } catch (SAXException e) {
                responseXml = XMLRPCUitls.faultXml(XMLRPCUitls.FaultCode.INVALID_ARGUMENT,
                        "Malformed XML: " + e.getMessage());
            }

            XMLRPCUitls.sendResponse(out, responseXml);
        }
    }

    private static String handleRequest(Document request) {
        String methodName = request.getElementsByTagName("methodName").item(0).getTextContent().trim();
        NodeList values = request.getElementsByTagName("value");

        switch (methodName) {
            case "addRecord":
                return addRecord(Integer.parseInt(values.item(0).getTextContent().trim()),
                        values.item(1).getTextContent());
            case "getRecord":
                return getRecord(Integer.parseInt(values.item(0).getTextContent().trim()));
            case "getSize":
                return getSize();
            default:
                return XMLRPCUitls.faultXml(XMLRPCUitls.FaultCode.INVALID_ARGUMENT,
                        "Unknown method: " + methodName);
        }
    }

    private static String getRecord(int index) {
        System.out.println("getRecord(" + index + ")");

        String record = RECORDS.get(index);
        if (record == null) {
            return XMLRPCUitls.faultXml(XMLRPCUitls.FaultCode.NOT_FOUND, "No record at index " + index);
        }
        return XMLRPCUitls.resultXml("<string>" + XMLRPCUitls.escape(record) + "</string>");
    }

    private static String addRecord(int index, String record) {
        System.out.println("addRecord(" + index + ", \"" + record + "\")");

        if (index < 0) {
            return XMLRPCUitls.faultXml(XMLRPCUitls.FaultCode.INVALID_ARGUMENT,
                    "Index must not be negative: " + index);
        }
        RECORDS.put(index, record);
        return XMLRPCUitls.resultXml("<int>" + index + "</int>");
    }

    private static String getSize() {
        int size = RECORDS.size();
        System.out.println("getSize() -> " + size);
        return XMLRPCUitls.resultXml("<int>" + size + "</int>");
    }
}
