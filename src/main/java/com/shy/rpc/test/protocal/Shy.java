package com.shy.rpc.test.protocal;

import java.io.Serializable;

public class Shy implements RpcProtocal, Serializable {

    private ShyHeader header;
    private ShyPayload body;
    public Shy() {

    }

    public Shy(ShyHeader header, ShyPayload body) {
        this.header = header;
        this.body = body;
    }



    public ShyHeader getHeader() {
        return header;
    }

    public void setHeader(ShyHeader header) {
        this.header = header;
    }

    public ShyPayload getBody() {
        return body;
    }

    public void setBody(ShyPayload body) {
        this.body = body;
    }
}
