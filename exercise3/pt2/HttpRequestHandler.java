package pt2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.InvalidProtocolBufferException;

import pt2.rpc.AddRecordArgs;
import pt2.rpc.AddRecordResult;
import pt2.rpc.GetRecordArgs;
import pt2.rpc.GetRecordResult;
import pt2.rpc.GetSizeResult;
import pt2.rpc.RpcRequest;
import pt2.rpc.RpcResponse;

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
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // nothing more to do
            }
        }
    }

    private void handleRequests() throws IOException {
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // the client keeps the connection open and sends several requests on it,
        // so answer requests until the client closes its side
        while (true) {
            RpcRequest request;
            try {
                // parseDelimitedFrom reads the length prefix first, so it knows where the
                // message ends without waiting for the client to close the connection
                request = RpcRequest.parseDelimitedFrom(in);
            } catch (InvalidProtocolBufferException e) {
                // the length prefix is already consumed, so the stream is out of sync:
                // report the problem once and give up on this connection
                sendError(out, "Malformed request: " + e.getMessage());
                return;
            }

            if (request == null) {
                // client disconnected, no more requests on this connection
                return;
            }

            RpcResponse response = handleRequest(request);
            response.writeDelimitedTo(out);
            out.flush();
        }
    }

    // dispatch one request to the operation it carries
    private static RpcResponse handleRequest(RpcRequest request) {
        switch (request.getArgsCase()) {
            case GET_RECORD:
                return getRecord(request.getGetRecord());
            case ADD_RECORD:
                return addRecord(request.getAddRecord());
            case GET_SIZE:
                return getSize();
            case ARGS_NOT_SET:
            default:
                // the oneof is empty, so there is nothing to execute
                return error("Request contains no operation");
        }
    }

    private static RpcResponse getRecord(GetRecordArgs args) {
        int index = args.getIndex();
        System.out.println("get_record(" + index + ")");

        String record = RECORDS.get(index);
        if (record == null) {
            return error("No record at index " + index);
        }

        return RpcResponse.newBuilder()
                .setGetRecord(GetRecordResult.newBuilder().setRecord(record))
                .build();
    }

    private static RpcResponse addRecord(AddRecordArgs args) {
        int index = args.getIndex();
        String record = args.getRecord();
        System.out.println("add_record(" + index + ", \"" + record + "\")");

        if (index < 0) {
            return error("Index must not be negative: " + index);
        }

        // an existing record at the same index is replaced
        RECORDS.put(index, record);

        return RpcResponse.newBuilder()
                .setAddRecord(AddRecordResult.newBuilder().setIndex(index))
                .build();
    }

    private static RpcResponse getSize() {
        int size = RECORDS.size();
        System.out.println("get_size() -> " + size);

        return RpcResponse.newBuilder()
                .setGetSize(GetSizeResult.newBuilder().setSize(size))
                .build();
    }

    // every failure is answered with the Error branch of the oneof
    private static RpcResponse error(String reason) {
        System.out.println("Error: " + reason);
        return RpcResponse.newBuilder()
                .setError(pt2.rpc.Error.newBuilder().setReason(reason))
                .build();
    }

    private static void sendError(OutputStream out, String reason) throws IOException {
        error(reason).writeDelimitedTo(out);
        out.flush();
    }
}
