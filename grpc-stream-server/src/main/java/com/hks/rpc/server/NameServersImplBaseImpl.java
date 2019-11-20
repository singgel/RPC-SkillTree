package com.hks.rpc.server;

import com.hks.rpc.Ip;
import com.hks.rpc.Name;
import com.hks.rpc.NameServersGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NameServersImplBaseImpl extends NameServersGrpc.NameServersImplBase {

    //记录名称内容的list，实际项目中应该放置在数据库
    private List<DataType> list = new ArrayList<DataType>();

    //构造方法中加入一些条目
    public NameServersImplBaseImpl() {

        list.add(new DataType(Name.newBuilder().setName("Sunny").build(),Ip.newBuilder().setIp("125.216.242.51").build()));
        list.add(new DataType(Name.newBuilder().setName("Sunny").build(),Ip.newBuilder().setIp("126.216.242.51").build()));
        list.add(new DataType(Name.newBuilder().setName("David").build(),Ip.newBuilder().setIp("117.226.178.139").build()));
        list.add(new DataType(Name.newBuilder().setName("David").build(),Ip.newBuilder().setIp("117.227.178.139").build()));
        list.add(new DataType(Name.newBuilder().setName("Tom").build(),Ip.newBuilder().setIp("111.222.336.11").build()));
        list.add(new DataType(Name.newBuilder().setName("Tom").build(),Ip.newBuilder().setIp("111.333.336.11").build()));
        list.add(new DataType(Name.newBuilder().setName("Tom").build(),Ip.newBuilder().setIp("111.222.335.11").build()));

    }


    @Override
    public void getIpsByName(Name requestName, StreamObserver<Ip> responseObserver) {

        Iterator<DataType> iter = list.iterator();

        while (iter.hasNext()){

            DataType data = iter.next();

            if(requestName.equals(data.getName())){

                System.out.println("get " + data.getIp() + " from " + requestName);

                responseObserver.onNext(data.getIp());

            }

        }

        responseObserver.onCompleted();

    }

}

class DataType{
    private Name name;
    private Ip ip;

    public DataType(Name name, Ip ip) {
        this.name = name;
        this.ip = ip;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }
}
