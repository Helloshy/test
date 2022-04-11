package com.shy.rpc.test.protocal;

import java.io.Serializable;

public class ShyPayload implements Serializable {

    private String interfaceName;

    private String methodName;

    private Object [] args;

}
