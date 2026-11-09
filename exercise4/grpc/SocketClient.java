package grpc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import grpc.rpc.AddRecordArgs;
import grpc.rpc.GetRecordArgs;
import grpc.rpc.GetSizeArgs;
import grpc.rpc.RecordServiceGrpc;

public class SocketClient {

    private static final String HOST = "localhost";
    private static final int PORT = 50051;

    private static final Map<Integer, String> RECORDS_TO_ADD = new LinkedHashMap<>();
    static {
        RECORDS_TO_ADD.put(4101, "Appen");
        RECORDS_TO_ADD.put(4102, "Ahrensburg");
        RECORDS_TO_ADD.put(4103, "Wedel");
        RECORDS_TO_ADD.put(4104, "Aumuhle");
        RECORDS_TO_ADD.put(4105, "Seevetal");
        RECORDS_TO_ADD.put(4106, "Quickborn");
    }

    public static void main(String[] args) throws InterruptedException {
        // the channel keeps the connection for every call, so one per program is
        // enough.
        // usePlaintext() because this exercise runs without TLS
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        try {
            // the blocking stub turns every operation into a plain method call
            RecordServiceGrpc.RecordServiceBlockingStub stub = RecordServiceGrpc.newBlockingStub(channel);

            for (Map.Entry<Integer, String> entry : RECORDS_TO_ADD.entrySet()) {
                grpc.rpc.AddRecordArgs addRecArg = AddRecordArgs.newBuilder().setIndex(entry.getKey())
                        .setRecord(entry.getValue()).build();
                grpc.rpc.AddRecordResult result = stub.addRecord(addRecArg);
                System.out.println(result.toString());
            }
            for (int index : new int[] { 4103, 4107 }) {
                grpc.rpc.GetRecordArgs getRecordArg = GetRecordArgs.newBuilder().setIndex(index)
                        .build();
                grpc.rpc.GetRecordResult result = stub.getRecord(getRecordArg);
                System.out.println(result.toString());
            }

            // Get Size Query
            {
                grpc.rpc.GetSizeArgs getSizeArgs = GetSizeArgs.newBuilder().build();
                grpc.rpc.GetSizeResult result = stub.getSize(getSizeArgs);
                System.out.println(result.toString());
            }

        } finally {
            // the transport threads are no daemon threads, so without this shutdown
            // the program would never terminate
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
