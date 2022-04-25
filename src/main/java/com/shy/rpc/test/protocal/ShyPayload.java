package com.shy.rpc.test.protocal;

import java.io.Serializable;
import java.util.Arrays;

public class ShyPayload implements Serializable {

    private String interfaceName;

    private String methodName;

    private Object [] args;

    private Object res;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "ShyPayload{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", res=" + res +
                '}';
    }
}
