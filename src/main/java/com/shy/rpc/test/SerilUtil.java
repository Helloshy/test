package com.shy.rpc.test;


import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerilUtil {

    public static byte[] seril(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){

        }
        return null;
    }
}
