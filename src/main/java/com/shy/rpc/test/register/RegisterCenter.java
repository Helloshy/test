package com.shy.rpc.test.register;

import java.util.List;

public interface RegisterCenter<I> {

    void regist(I i);

    I subcribe(String providerName);
}
