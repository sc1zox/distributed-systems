package exercise3.aufgabe2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Protobuf direkt über Socket vs. in HTTP verpackt:
 *
 * Direkt: Protobuf-Bytes gehen roh über TCP. Framing muss selbst gemacht werden
 * (writeDelimitedTo/parseDelimitedFrom mit Längenpräfix). Minimaler Overhead, Verbindung
 * bleibt offen, aber eigenes Protokoll: Status, Fehler, Auth, TLS selbst bauen, Firewalls/
 * Proxies verstehen es nicht.
 *
 * HTTP: Protobuf-Nachricht im Body (z.B. POST).
 * Framing über Content-Length, dazu Statuscodes, Header, TLS, Proxies, Load-Balancer und
 * Port 80/443 geschenkt. Operation kann in der URL stehen. Dafür mehr Overhead durch Header
 * (Beispiel: gRPC über HTTP/2).
 */
public class DatabaseServer {
    private static final Map<Integer, String> records = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 7001;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Database server started on port " + port);

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
            OutputStream out = socket.getOutputStream();

            RpcRequest req;
            while (((req = RpcRequest.parseDelimitedFrom(in)) != null)) {
                RpcResponse.Builder responseBuilder = RpcResponse.newBuilder();

                Operation op = req.getOp();

                if (req.hasOp()) {
                    switch (op) {
                        case OPERATION_GET_RECORD: {
                            if (req.hasGetRecordArgs()) {
                                int id = req.getGetRecordArgs().getIndex();
                                String content = records.get(id);

                                GetRecordRes.Builder res = GetRecordRes.newBuilder().setIndex(id);
                                if (content == null) {
                                    res.setSuccess(false);
                                } else {
                                    res.setContent(content).setSuccess(true);
                                }
                                responseBuilder.setGetRecordRes(res);
                            } else {
                                GetRecordRes res = GetRecordRes.newBuilder().setSuccess(false).build();
                                responseBuilder.setGetRecordRes(res);
                            }
                        }
                        break;
                        case OPERATION_ADD_RECORD: {
                            if (req.hasAddRecordArgs()) {
                                String content = req.getAddRecordArgs().getContent();
                                int id = req.getAddRecordArgs().getIndex();
                                records.put(id, content);
                                AddRecordRes res = AddRecordRes.newBuilder().setSuccess(true).build();
                                responseBuilder.setAddRecordRes(res);
                            } else {
                                AddRecordRes res = AddRecordRes.newBuilder().setSuccess(false).build();
                                responseBuilder.setAddRecordRes(res);
                            }
                        }
                        break;
                        case OPERATION_GET_SIZE: {
                            int size = records.size();
                            GetSizeRes res = GetSizeRes.newBuilder().setSize(size).build();
                            responseBuilder.setGetSizeRes(res);
                        }
                        break;
                        default:
                            break;
                    }
                }
                responseBuilder.build().writeDelimitedTo(out);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
