package com.shy.rpc.test.protocal;

import java.io.Serializable;
import java.util.UUID;

public class ShyHeader implements Serializable {

    public static final Byte REQUEST_FLAG = 0;

    public static final Byte RESPONSE_FLAG = 1;

    private long requestId;//请求唯一标识

    private Byte flag;//标志是request还是response

    private int dataLength;

    public ShyHeader() {
    }

    public ShyHeader(Byte flag) {
        this.requestId = UUID.randomUUID().getLeastSignificantBits();
        this.flag = flag;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    @Override
    public String toString() {
        return "ShyHeader{" +
                "requestId=" + requestId +
                ", flag=" + flag +
                ", dataLength=" + dataLength +
                '}';
    }
}
