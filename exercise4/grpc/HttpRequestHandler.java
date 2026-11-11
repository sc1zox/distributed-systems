package exercise4.grpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import exercise4.grpc.rpc.AddRecordArgs;
import exercise4.grpc.rpc.AddRecordResult;
import exercise4.grpc.rpc.GetRecordArgs;
import exercise4.grpc.rpc.GetRecordResult;
import exercise4.grpc.rpc.GetSizeArgs;
import exercise4.grpc.rpc.GetSizeResult;
import exercise4.grpc.rpc.RecordServiceGrpc;

/*
 * The service implementation. gRPC reads the request, decodes it and calls the method
 * that belongs to it, so there is no accept loop, no length prefix framing and no
 * dispatching by hand any more.
 *
 * Every method has to finish its call, either with
 *   responseObserver.onNext(result) followed by responseObserver.onCompleted()
 * or with
 *   responseObserver.onError(Status.NOT_FOUND.withDescription("...").asRuntimeException())
 * A method that returns without doing one of the two leaves the client waiting forever.
 */
public class HttpRequestHandler extends RecordServiceGrpc.RecordServiceImplBase {

    // gRPC serves calls on several threads, so the record store has to be thread
    // safe
    private final Map<Integer, String> records = new ConcurrentHashMap<>();

    @Override
    public void getRecord(GetRecordArgs request, StreamObserver<GetRecordResult> responseObserver) {
        int index = request.getIndex();
        String foundRecord = records.get(index);
        if (foundRecord == null) {
            // no result message is sent in this case, the status replaces it
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("No record at index " + index)
                    .asRuntimeException());
        } else {
            // onNext carries the result, onCompleted closes the call with status OK
            responseObserver.onNext(GetRecordResult.newBuilder()
                    .setRecord(foundRecord)
                    .build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void addRecord(AddRecordArgs request, StreamObserver<AddRecordResult> responseObserver) {
        int index = request.getIndex();
        if (index < 0) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Index must not be negative: " + index)
                    .asRuntimeException());
            // the call is finished here, everything below would close it a second time
            return;
        }

        records.put(index, request.getRecord());

        responseObserver.onNext(AddRecordResult.newBuilder()
                .setIndex(request.getIndex())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getSize(GetSizeArgs request, StreamObserver<GetSizeResult> responseObserver) {
        /**
         * Bei parallelen Clients kann zwischen size() und
         * dem Senden schon wieder etwas hinzugekommen sein.
         */
        int size = records.size();
        responseObserver.onNext(GetSizeResult.newBuilder()
                .setSize(size)
                .build());
        responseObserver.onCompleted();
    }
}
