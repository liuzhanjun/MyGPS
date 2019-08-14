package com.hai.yun.net;


import com.hai.yun.bean.utils.BinaryUtils;

import org.apache.mina.core.buffer.IoBuffer;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TcpIoAdapter extends IoHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(TcpIoAdapter.class);


    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        IoBuffer ioBuffer = (IoBuffer) message;


        byte[] bs = new byte[ioBuffer.limit()];
        ioBuffer.get(bs);
        StringBuffer buffer = new StringBuffer();
        for (byte b : bs) {
            buffer.append("|"+BinaryUtils.byteToOx(BinaryUtils.getInt(b)));
        }
        logger.info(buffer.toString());

//        DataDealUtils.dealPkg(bs);
//        StringBuffer buffer = new StringBuffer();
//        if ((bs[0]^0x78)!=0||(bs[1]^0x78)!=0||(bs[ioBuffer.limit()-2]^0x0D)!=0||(bs[ioBuffer.limit()-1]^0x0A)!=0){
//            logger.info(String.format("%s|%s", BinaryUtils.byteToOx(BinaryUtils.getInt(bs[0])), BinaryUtils.byteToOx(BinaryUtils.getInt(bs[1]))));
//            logger.info(String.format("%s|%s", BinaryUtils.byteToOx(BinaryUtils.getInt(bs[ioBuffer.limit() - 2])), BinaryUtils.byteToOx(BinaryUtils.getInt(bs[ioBuffer.limit() - 1]))));
//            logger.info("=========================================");
//        }


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
