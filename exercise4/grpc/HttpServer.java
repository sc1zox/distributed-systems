package grpc;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class HttpServer {
    private static final int PORT = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        // gRPC brings its own accept loop and its own thread pool, so registering the
        // service implementation is all that is left to do here
        Server server = ServerBuilder.forPort(PORT)
                .addService(new HttpRequestHandler())
                .build()
                .start();

        System.out.println("gRPC server started on port " + PORT);

        // Ctrl+C should not kill running calls right away
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down");
            server.shutdown();
        }));

        // start() returns immediately and the calls run on gRPC's threads,
        // so the main thread has to wait here
        server.awaitTermination();
    }
}
