package com.hai.yun;

import com.hai.yun.bean.GpsSessionManager;
import com.hai.yun.net.BaseHandler;
import com.hai.yun.net.MinaTcpClient;
import org.apache.mina.core.session.IoSession;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        MinaTcpClient.client.init();

        MinaTcpClient.client.setHandler(new BaseHandler() {
            @Override
            public void dealMessage(IoSession mSession, Object msg) {

            }


        });


        MinaTcpClient.client.sendMessage(GpsSessionManager.SessionManager.getIMSI("460595485214565"));


    }


}
