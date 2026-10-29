package exercise3.aufgabe2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseClient {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 7001;

        Map<Integer, String> records = new LinkedHashMap<>();
        records.put(4101, "Appen");
        records.put(4102, "Ahrensburg");
        records.put(4103, "Wedel");
        records.put(4104, "Aumühle");
        records.put(4105, "Seevetal");
        records.put(4106, "Quickborn");

        try (Socket socket = new Socket(host, port)) {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            for (Map.Entry<Integer, String> record : records.entrySet()) {
                RpcRequest req = RpcRequest.newBuilder()
                        .setOp(Operation.OPERATION_ADD_RECORD)
                        .setAddRecordArgs(AddRecordArgs.newBuilder()
                                .setIndex(record.getKey())
                                .setContent(record.getValue()))
                        .build();
                RpcResponse res = call(req, in, out);
                System.out.println("addRecord(" + record.getKey() + ", " + record.getValue() + "): "
                        + (res.hasAddRecordRes() && res.getAddRecordRes().getSuccess() ? "ok" : "failed"));
            }

            getRecord(4103, in, out);
            getRecord(4107, in, out);

            RpcRequest req = RpcRequest.newBuilder().setOp(Operation.OPERATION_GET_SIZE).build();
            RpcResponse res = call(req, in, out);
            System.out.println("getSize(): " + res.getGetSizeRes().getSize());
        }
    }

    private static void getRecord(int index, InputStream in, OutputStream out) throws IOException {
        RpcRequest req = RpcRequest.newBuilder()
                .setOp(Operation.OPERATION_GET_RECORD)
                .setGetRecordArgs(GetRecordArgs.newBuilder().setIndex(index))
                .build();
        RpcResponse res = call(req, in, out);
        // the server sends an empty response if the index does not exist
        if (res.hasGetRecordRes()) {
            System.out.println("getRecord(" + index + "): " + res.getGetRecordRes().getContent());
        } else {
            System.out.println("getRecord(" + index + "): not found");
        }
    }

    private static RpcResponse call(RpcRequest req, InputStream in, OutputStream out) throws IOException {
        req.writeDelimitedTo(out);
        out.flush();

        RpcResponse res = RpcResponse.parseDelimitedFrom(in);
        if (res == null) {
            throw new IOException("Server closed the connection");
        }
        return res;
    }
}
