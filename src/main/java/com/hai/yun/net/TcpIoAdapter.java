package com.hai.yun.net;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TcpIoAdapter extends IoHandlerAdapter {

    private BaseHandler handler;

    public void setHandler(BaseHandler handler) {
        this.handler = handler;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        handler.dealMessage(session, message);
        System.out.println("接收到信息");

    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        //连接断开了
        System.out.println("连接断开");

        MinaTcpClient.client.conn();
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("client-messageSent");
        super.messageSent(session, message);
    }
}
