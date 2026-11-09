package grpc.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class RecordServiceGrpc {

  private RecordServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "RecordService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<grpc.rpc.GetRecordArgs,
      grpc.rpc.GetRecordResult> getGetRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getRecord",
      requestType = grpc.rpc.GetRecordArgs.class,
      responseType = grpc.rpc.GetRecordResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.rpc.GetRecordArgs,
      grpc.rpc.GetRecordResult> getGetRecordMethod() {
    io.grpc.MethodDescriptor<grpc.rpc.GetRecordArgs, grpc.rpc.GetRecordResult> getGetRecordMethod;
    if ((getGetRecordMethod = RecordServiceGrpc.getGetRecordMethod) == null) {
      synchronized (RecordServiceGrpc.class) {
        if ((getGetRecordMethod = RecordServiceGrpc.getGetRecordMethod) == null) {
          RecordServiceGrpc.getGetRecordMethod = getGetRecordMethod =
              io.grpc.MethodDescriptor.<grpc.rpc.GetRecordArgs, grpc.rpc.GetRecordResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.GetRecordArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.GetRecordResult.getDefaultInstance()))
              .setSchemaDescriptor(new RecordServiceMethodDescriptorSupplier("getRecord"))
              .build();
        }
      }
    }
    return getGetRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.rpc.AddRecordArgs,
      grpc.rpc.AddRecordResult> getAddRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addRecord",
      requestType = grpc.rpc.AddRecordArgs.class,
      responseType = grpc.rpc.AddRecordResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.rpc.AddRecordArgs,
      grpc.rpc.AddRecordResult> getAddRecordMethod() {
    io.grpc.MethodDescriptor<grpc.rpc.AddRecordArgs, grpc.rpc.AddRecordResult> getAddRecordMethod;
    if ((getAddRecordMethod = RecordServiceGrpc.getAddRecordMethod) == null) {
      synchronized (RecordServiceGrpc.class) {
        if ((getAddRecordMethod = RecordServiceGrpc.getAddRecordMethod) == null) {
          RecordServiceGrpc.getAddRecordMethod = getAddRecordMethod =
              io.grpc.MethodDescriptor.<grpc.rpc.AddRecordArgs, grpc.rpc.AddRecordResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.AddRecordArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.AddRecordResult.getDefaultInstance()))
              .setSchemaDescriptor(new RecordServiceMethodDescriptorSupplier("addRecord"))
              .build();
        }
      }
    }
    return getAddRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<grpc.rpc.GetSizeArgs,
      grpc.rpc.GetSizeResult> getGetSizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getSize",
      requestType = grpc.rpc.GetSizeArgs.class,
      responseType = grpc.rpc.GetSizeResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<grpc.rpc.GetSizeArgs,
      grpc.rpc.GetSizeResult> getGetSizeMethod() {
    io.grpc.MethodDescriptor<grpc.rpc.GetSizeArgs, grpc.rpc.GetSizeResult> getGetSizeMethod;
    if ((getGetSizeMethod = RecordServiceGrpc.getGetSizeMethod) == null) {
      synchronized (RecordServiceGrpc.class) {
        if ((getGetSizeMethod = RecordServiceGrpc.getGetSizeMethod) == null) {
          RecordServiceGrpc.getGetSizeMethod = getGetSizeMethod =
              io.grpc.MethodDescriptor.<grpc.rpc.GetSizeArgs, grpc.rpc.GetSizeResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getSize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.GetSizeArgs.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  grpc.rpc.GetSizeResult.getDefaultInstance()))
              .setSchemaDescriptor(new RecordServiceMethodDescriptorSupplier("getSize"))
              .build();
        }
      }
    }
    return getGetSizeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RecordServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecordServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecordServiceStub>() {
        @java.lang.Override
        public RecordServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecordServiceStub(channel, callOptions);
        }
      };
    return RecordServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static RecordServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecordServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecordServiceBlockingV2Stub>() {
        @java.lang.Override
        public RecordServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecordServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return RecordServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RecordServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecordServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecordServiceBlockingStub>() {
        @java.lang.Override
        public RecordServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecordServiceBlockingStub(channel, callOptions);
        }
      };
    return RecordServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RecordServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RecordServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RecordServiceFutureStub>() {
        @java.lang.Override
        public RecordServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RecordServiceFutureStub(channel, callOptions);
        }
      };
    return RecordServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getRecord(grpc.rpc.GetRecordArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.GetRecordResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRecordMethod(), responseObserver);
    }

    /**
     */
    default void addRecord(grpc.rpc.AddRecordArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.AddRecordResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddRecordMethod(), responseObserver);
    }

    /**
     */
    default void getSize(grpc.rpc.GetSizeArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.GetSizeResult> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSizeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RecordService.
   */
  public static abstract class RecordServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RecordServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RecordService.
   */
  public static final class RecordServiceStub
      extends io.grpc.stub.AbstractAsyncStub<RecordServiceStub> {
    private RecordServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecordServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecordServiceStub(channel, callOptions);
    }

    /**
     */
    public void getRecord(grpc.rpc.GetRecordArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.GetRecordResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addRecord(grpc.rpc.AddRecordArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.AddRecordResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSize(grpc.rpc.GetSizeArgs request,
        io.grpc.stub.StreamObserver<grpc.rpc.GetSizeResult> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSizeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RecordService.
   */
  public static final class RecordServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<RecordServiceBlockingV2Stub> {
    private RecordServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecordServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecordServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public grpc.rpc.GetRecordResult getRecord(grpc.rpc.GetRecordArgs request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.rpc.AddRecordResult addRecord(grpc.rpc.AddRecordArgs request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getAddRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.rpc.GetSizeResult getSize(grpc.rpc.GetSizeArgs request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetSizeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service RecordService.
   */
  public static final class RecordServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RecordServiceBlockingStub> {
    private RecordServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecordServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecordServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public grpc.rpc.GetRecordResult getRecord(grpc.rpc.GetRecordArgs request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.rpc.AddRecordResult addRecord(grpc.rpc.AddRecordArgs request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public grpc.rpc.GetSizeResult getSize(grpc.rpc.GetSizeArgs request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSizeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RecordService.
   */
  public static final class RecordServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<RecordServiceFutureStub> {
    private RecordServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RecordServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RecordServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.rpc.GetRecordResult> getRecord(
        grpc.rpc.GetRecordArgs request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRecordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.rpc.AddRecordResult> addRecord(
        grpc.rpc.AddRecordArgs request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddRecordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<grpc.rpc.GetSizeResult> getSize(
        grpc.rpc.GetSizeArgs request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSizeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_RECORD = 0;
  private static final int METHODID_ADD_RECORD = 1;
  private static final int METHODID_GET_SIZE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_RECORD:
          serviceImpl.getRecord((grpc.rpc.GetRecordArgs) request,
              (io.grpc.stub.StreamObserver<grpc.rpc.GetRecordResult>) responseObserver);
          break;
        case METHODID_ADD_RECORD:
          serviceImpl.addRecord((grpc.rpc.AddRecordArgs) request,
              (io.grpc.stub.StreamObserver<grpc.rpc.AddRecordResult>) responseObserver);
          break;
        case METHODID_GET_SIZE:
          serviceImpl.getSize((grpc.rpc.GetSizeArgs) request,
              (io.grpc.stub.StreamObserver<grpc.rpc.GetSizeResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.rpc.GetRecordArgs,
              grpc.rpc.GetRecordResult>(
                service, METHODID_GET_RECORD)))
        .addMethod(
          getAddRecordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.rpc.AddRecordArgs,
              grpc.rpc.AddRecordResult>(
                service, METHODID_ADD_RECORD)))
        .addMethod(
          getGetSizeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              grpc.rpc.GetSizeArgs,
              grpc.rpc.GetSizeResult>(
                service, METHODID_GET_SIZE)))
        .build();
  }

  private static abstract class RecordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RecordServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return grpc.rpc.RpcRequest.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RecordService");
    }
  }

  private static final class RecordServiceFileDescriptorSupplier
      extends RecordServiceBaseDescriptorSupplier {
    RecordServiceFileDescriptorSupplier() {}
  }

  private static final class RecordServiceMethodDescriptorSupplier
      extends RecordServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RecordServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RecordServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RecordServiceFileDescriptorSupplier())
              .addMethod(getGetRecordMethod())
              .addMethod(getAddRecordMethod())
              .addMethod(getGetSizeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
