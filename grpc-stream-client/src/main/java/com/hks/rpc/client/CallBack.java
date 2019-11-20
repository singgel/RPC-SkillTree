package com.hks.rpc.client;


import com.hks.rpc.Result;

public class CallBack {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static void callBackStatic(Result result){
        System.out.println("get result from server...sum:" +
                result.getSum() +
                " count:" +
                result.getCnt() +
                " avg:" + result.getAvg());
    }

    public void callBackInstance(){
        System.out.println("get result from server...sum:" +
                result.getSum() +
                " count:" +
                result.getCnt() +
                " avg:" + result.getAvg());
    }

}
