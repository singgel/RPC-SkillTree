package com.hks.grpc.client;

import com.hks.grpc.service.GreeterGrpc;
import com.hks.grpc.service.HelloProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloClient {
    private static final Logger logger = Logger.getLogger(HelloClient.class.getName());

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    // 首先，我们需要为stub创建一个grpc的channel，指定我们连接服务端的地址和端口
    // 使用ManagedChannelBuilder方法来创建channel
    public HelloClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build();
        // 使用我们从proto文件生成的GreeterGrpc类提供的newBlockingStub方法指定channel创建stubs
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    // 调用服务端方法
    /** Say hello to server. */
    public void greet(String name) {
        logger.info("Will try to greet " + name + " ...");
        // 创建并定制protocol buffer对象，使用该对象调用服务端的sayHello方法，获得response
        HelloProto.HelloRequest request = HelloProto.HelloRequest.newBuilder().setName(name).build();
        HelloProto.HelloReply response;
        try {
            response = blockingStub.sayHello(request);
            // 如果有异常发生，则异常被编码成Status，可以从StatusRuntimeException异常中捕获
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getMessage());
    }

    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        HelloClient client = new HelloClient("localhost", 50051);
        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "hans";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
