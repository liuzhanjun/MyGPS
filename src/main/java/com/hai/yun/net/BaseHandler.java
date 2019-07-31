package com.hai.yun.net;

import org.apache.mina.core.session.IoSession;

public interface BaseHandler {

    public void dealMessage(IoSession mSession, Object msg);
}
