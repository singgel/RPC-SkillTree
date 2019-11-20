package com.hks.rpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.hks.rpc.Ip;
import com.hks.rpc.Name;
import com.hks.rpc.NameServersGrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NameClient {

    private static final String DEFAULT_HOST = "localhost";

    private static final int DEFAULT_PORT = 8088;

    private ManagedChannel managedChannel;

    //服务存根，用于客户端本地调用
    private NameServersGrpc.NameServersBlockingStub nameServiceBlockingStub;

    public NameClient(String host, int port) {

        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext(true).build());

    }

    public NameClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.nameServiceBlockingStub = NameServersGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public List<Ip> getIpsByName(String n){

        List<Ip> result = new ArrayList<Ip>();

        Name name = Name.newBuilder().setName(n).build();

        Iterator<Ip> iterator = nameServiceBlockingStub.getIpsByName(name);

        while (iterator.hasNext()){

            result.add(iterator.next());

        }

        return result;
    }

    public static void main(String[] args) {

        NameClient nameClient = new NameClient(DEFAULT_HOST,DEFAULT_PORT);

        for(String arg : args){

            List<Ip> result = nameClient.getIpsByName(arg);

            for(int i=0;i<result.size();i++){
                System.out.println("get result from server: " + result.get(i) + " as param is " + arg);
            }


        }

    }

}
