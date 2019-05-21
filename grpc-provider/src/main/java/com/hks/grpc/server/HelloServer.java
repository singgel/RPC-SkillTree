package com.hks.grpc.server;

import com.hks.grpc.service.GreeterGrpc;
import com.hks.grpc.service.HelloProto;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

public class HelloServer {
    private static final Logger logger = Logger.getLogger(HelloServer.class.getName());
    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        // 使用ServerBuilder来构建和启动服务，通过使用forPort方法来指定监听的地址和端口
        // 创建一个实现方法的服务GreeterImpl的实例，并通过addService方法将该实例纳入
        // 调用build() start()方法构建和启动rpcserver
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final HelloServer server = new HelloServer();
        server.start();
        server.blockUntilShutdown();
    }

    // 我们的服务GreeterImpl继承了生成抽象类GreeterGrpc.GreeterImplBase，实现了服务的所有方法
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloProto.HelloRequest req, StreamObserver<HelloProto.HelloReply> responseObserver) {
            HelloProto.HelloReply reply = HelloProto.HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            // 使用响应监视器的onNext方法返回HelloReply
            responseObserver.onNext(reply);
            // 使用onCompleted方法指定本次调用已经完成
            responseObserver.onCompleted();
        }
    }
}
