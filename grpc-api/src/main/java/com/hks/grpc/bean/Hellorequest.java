package com.hks.grpc.bean;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Data;

@Data
public class Hellorequest {
    @Protobuf(fieldType = FieldType.STRING, order = 1)
    private String name;

    @Protobuf(fieldType = FieldType.SINT32, order = 2)
    private int age;

    @Protobuf(fieldType = FieldType.DOUBLE, order = 3)
    private Double profit_rate;
}
