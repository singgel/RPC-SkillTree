package com.hks.rpc.server;

import com.hks.rpc.AdditionServiceGrpc;
import com.hks.rpc.Result;
import com.hks.rpc.Value;
import io.grpc.stub.StreamObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdditionServiceImplBaseImpl extends AdditionServiceGrpc.AdditionServiceImplBase {

    private Logger logger = Logger.getLogger(AdditionServiceImplBaseImpl.class.getName());

    @Override
    public StreamObserver<Value> getResult(final StreamObserver<Result> responseObserver) {
        //如果处理过程较为复杂，可以考虑单独写一个类来实现。这里用匿名内部类的方式，在内部类中用到参数需要加final修饰符
        return new StreamObserver<Value>() {

            private int sum = 0;
            private int cnt = 0;
            private double avg;

            @Override
            public void onNext(Value value) {
                sum += value.getValue();
                cnt++;
            }

            @Override
            public void onError(Throwable throwable) {
                logger.log(Level.SEVERE, throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                avg = 1.0 * sum / cnt;
                responseObserver.onNext(Result.newBuilder().setSum(sum).setCnt(cnt).setAvg(avg).build());
                responseObserver.onCompleted();
            }
        };
    }
}
