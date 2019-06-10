package com.hks.grpc;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.DoubleValue;
import com.hks.grpc.bean.Hellorequest;
import com.hks.grpc.service.AddressBookProtos.Person;
import com.hks.grpc.service.HelloProto.HelloRequest;
import com.hks.grpc.service.HelloProto.HelloReply;
import com.hks.grpc.service.HelloRequestJava;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcApplicationTests {

    @Test
    public void contextLoads() throws Exception{
        Person john =
                Person.newBuilder()
                        .setId(1234)
                        .setName("John Doe")
                        .setEmail("jdoe@example.com")
                        .addPhones(
                                Person.PhoneNumber.newBuilder()
                                        .setNumber("555-4321")
                                        .setType(Person.PhoneType.HOME))
                        .build();
        /*FileOutputStream output = new FileOutputStream("./demo.db");
        john.toBuilder().build().writeTo(output);
        output.close();
        john = Person.parseFrom(new FileInputStream("./demo.db"));*/

        System.out.println(john.hasAge());
        System.out.println(john.getAge());
        System.out.println("==============");
        john = john.toBuilder().setAge(0).build();
        System.out.println(john.hasAge());
        System.out.println(john.getAge());
        System.out.println("==============");
        john = john.toBuilder().setAge(200).build();
        System.out.println(john.hasAge());
    }

    @Test
    public void testRequest() {
        HelloRequest hello =
                HelloRequest.newBuilder()
                        .setName("john")
                        .build();
        FieldDescriptor fieldDesc = hello.getDescriptorForType().findFieldByName("age");
        System.out.println(hello.hasField(fieldDesc));
        System.out.println(hello);
        System.out.println("==============");
        hello = hello.toBuilder().setAge(0).build();
        System.out.println(hello.hasField(fieldDesc));
        System.out.println("==============");
        hello = hello.toBuilder().setAge(200).build();
        System.out.println(hello.hasField(fieldDesc));
        System.out.println(hello);

        /*HelloRequestJava helloRequestJava = (HelloRequestJava)BeanHelper.protoBeanToJavaBean(hello,new HelloRequestJava(), new HashMap<>());
        System.out.println(helloRequestJava.toString());*/
    }

    @Test
    public void testRequestWrapper() {
        HelloRequest hello =
                HelloRequest.newBuilder()
                        .setName("john")
                        .build();
        System.out.println(hello.hasProfitRate());
        System.out.println(hello);
        System.out.println("==============");
        hello = hello.toBuilder().setProfitRate(DoubleValue.newBuilder().setValue(0).build()).build();
        System.out.println(hello.hasProfitRate());
        System.out.println("==============");
        hello = hello.toBuilder().setProfitRate(DoubleValue.newBuilder().setValue(200).build()).build();
        System.out.println(hello.hasProfitRate());
        System.out.println(hello);
    }

    @Test
    public void testReplyOneOf(){
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("hello")
                .build();
        System.out.println(reply.getHasCodeCase());
        System.out.println("==============");
        reply = reply.toBuilder().setCode(0).build();
        System.out.println(reply.getHasCodeCase());
        System.out.println("==============");
        reply = reply.toBuilder().setCode(200).build();
        System.out.println(reply.getHasCodeCase());
    }

    @Test
    public void testJprotobuf(){
        Codec<Hellorequest> simpleTypeCodec = ProtobufProxy
                .create(Hellorequest.class);

        HelloRequest hellorequest =
                HelloRequest.newBuilder()
                        .setName("john")
                        .setAge(100)
                        .setProfitRate(DoubleValue.of(0.0))
                        .build();
        /*Hellorequest hellorequest = new Hellorequest();
        hellorequest.setName("john");
        hellorequest.setAge(100);
        hellorequest.setProfit_rate(0.0);*/
        try {
            // 序列化
            byte[] bb = hellorequest.toByteArray();
//            byte[] bb = simpleTypeCodec.encode(hellorequest);
            // 反序列化
            HelloRequest hello = HelloRequest.parseFrom(bb);
            System.out.println(hello);
            Hellorequest newHellorequest = simpleTypeCodec.decode(bb);
            System.out.println(newHellorequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
