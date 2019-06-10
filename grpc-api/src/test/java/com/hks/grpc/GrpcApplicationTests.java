package com.hks.grpc;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.DoubleValue;
import com.hks.grpc.service.AddressBookProtos.Person;
import com.hks.grpc.service.HelloProto.HelloRequest;
import com.hks.grpc.service.HelloProto.HelloReply;
import com.hks.grpc.service.HelloRequestJava;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


}
