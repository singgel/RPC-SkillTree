package com.example.dubbo.exception;

public class TestRuntimeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public TestRuntimeException() {
        super();
    }

    public TestRuntimeException(String arg0, Throwable throwable) {
        super(arg0, throwable);
    }

    public TestRuntimeException(String arg0) {
        super(arg0);
    }

    public TestRuntimeException(Throwable throwable) {
        super(throwable);
    }

}
