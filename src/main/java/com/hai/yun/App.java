package com.hai.yun;

import com.hai.yun.bean.GpsSessionManager;
import com.hai.yun.bean.utils.BinaryUtils;
import com.hai.yun.net.BaseHandler;
import com.hai.yun.net.MinaTcpClient;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        MinaTcpClient.client.init();

        MinaTcpClient.client.sendMessage(GpsSessionManager.SessionManager.getIMSI("460595485214565"));
        MinaTcpClient.client.sendMessage(GpsSessionManager.SessionManager.getIMSI("460595485214565"));
        MinaTcpClient.client.sendMessage(GpsSessionManager.SessionManager.getIMSI("460595485214565"));
        MinaTcpClient.client.sendMessage(GpsSessionManager.SessionManager.getIMSI("460595485214565"));


    }


}
