package com.hai.yun.net;

import com.hai.yun.bean.GpsSessionManager;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

public enum MinaTcpClient {
    client {
        @Override
        public MinaTcpClient init() {
            System.out.println("==============");
            sendHeartBeat();
            conn();
            return this;
        }
    };

    private IoSession mSession;

    public abstract MinaTcpClient init();

    private static final Logger LOGGER = LoggerFactory.getLogger(MinaTcpClient.class);

    public static final int PORT = 18567;
    public static final String HOST = "localhost";
    private NioSocketConnector conn = new NioSocketConnector();
    private TcpIoAdapter adapter = new TcpIoAdapter();

    public void conn() {
        conn.setHandler(adapter);
        ConnectFuture connectFuture = conn.connect(new InetSocketAddress(HOST, PORT));
        connectFuture.awaitUninterruptibly();
        mSession = connectFuture.getSession();

    }

    public void sendMessage(byte[] msg) {
        mSession.write(IoBuffer.wrap(msg));
    }

    public void closConn() {
        // 关闭会话，待所有线程处理结束后
        conn.dispose(true);
    }

    public void setHandler(BaseHandler baseHandler) {
        adapter.setHandler(baseHandler);
    }

    public void sendHeartBeat() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("-----------------------");
                sendMessage(GpsSessionManager.SessionManager.getBeartHeatPkg());

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
    }
}
