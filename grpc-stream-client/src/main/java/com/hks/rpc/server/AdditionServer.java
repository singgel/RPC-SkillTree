package com.hks.rpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class AdditionServer {

    private Logger logger = Logger.getLogger(AdditionServer.class.getName());

    private static final int DEFAULT_PORT = 8088;

    private int port;//服务端口号

    private Server server;


    public AdditionServer(int port) {
        this(port, ServerBuilder.forPort(port));
    }

    public AdditionServer(int port, ServerBuilder<?> serverBuilder){

        this.port = port;

        //构造服务器，添加我们实际的服务
        server = serverBuilder.addService(new AdditionServiceImplBaseImpl()).build();

    }

    private void start() throws IOException {
        server.start();
        logger.info("Server has started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                AdditionServer.this.stop();

            }
        });
    }

    private void stop() {

        if(server != null)
            server.shutdown();

    }

    //阻塞到应用停止
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) {

        AdditionServer addtionServer;

        if(args.length > 0){
            addtionServer = new AdditionServer(Integer.parseInt(args[0]));
        }else{
            addtionServer = new AdditionServer(DEFAULT_PORT);
        }

        try {
            addtionServer.start();
            addtionServer.blockUntilShutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
