package com.shy.rpc.test.excepton;

public class ServiceException extends RuntimeException{


    public ServiceException(String message) {
        super(message);
    }
}
