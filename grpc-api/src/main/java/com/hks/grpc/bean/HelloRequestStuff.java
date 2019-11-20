package com.hks.grpc.bean;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import io.protostuff.Tag;
import lombok.Data;

@Data
public class HelloRequestStuff {
    @Tag(1)
    private String name;

    @Tag(2)
    private int age;

    @Tag(3)
    private Double profit_rate;
}
