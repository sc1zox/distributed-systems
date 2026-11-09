package pt2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import pt1.log_message.LogMessage;
import pt2.rpc.AddRecordArgs;
import pt2.rpc.GetRecordArgs;
import pt2.rpc.GetSizeArgs;
import pt2.rpc.RpcRequest;
import pt2.rpc.RpcResponse;

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

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port)) {
            // Add Record Queries
            for (Map.Entry<Integer, String> entry : RECORDS_TO_ADD.entrySet()) {
                RpcRequest request = RpcRequest.newBuilder()
                        .setAddRecord(AddRecordArgs.newBuilder()
                                .setIndex(entry.getKey())
                                .setRecord(entry.getValue()))
                        .build();

                OutputStream out = socket.getOutputStream();
                request.writeDelimitedTo(out);
                out.flush();

                InputStream inputStream = socket.getInputStream();
                RpcResponse response = RpcResponse.parseDelimitedFrom(inputStream);

                System.out.println(response.toString());
            }

            // Get Record Querys
            for (int index : new int[] { 4103, 4107 }) {
                RpcRequest request = RpcRequest.newBuilder()
                        .setGetRecord(GetRecordArgs.newBuilder()
                                .setIndex(index))
                        .build();

                OutputStream out = socket.getOutputStream();
                request.writeDelimitedTo(out);
                out.flush();

                InputStream inputStream = socket.getInputStream();
                RpcResponse response = RpcResponse.parseDelimitedFrom(inputStream);

                System.out.println(response.toString());
            }

            // Get Size Query
            {
                RpcRequest request = RpcRequest.newBuilder()
                        .setGetSize(GetSizeArgs.newBuilder().build())
                        .build();

                OutputStream out = socket.getOutputStream();
                request.writeDelimitedTo(out);
                out.flush();

                InputStream inputStream = socket.getInputStream();
                RpcResponse response = RpcResponse.parseDelimitedFrom(inputStream);

                System.out.println(response.toString());
            }
        }
    }
}
