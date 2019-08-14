package com.hai.yun.net;

import com.hai.yun.bean.utils.BinaryUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.util.ArrayList;

public class MinaMessageDecoder extends CumulativeProtocolDecoder {
//    @Override
//    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
//        byte[] bytes = new byte[ioBuffer.limit()];
//        //分包问题
//        ioBuffer.get(bytes);
//        if ((bytes[ioBuffer.limit() - 1] ^ 0x0A) != 0 || (bytes[ioBuffer.limit() - 2] ^ 0x0D) != 0) {
//            ioBuffer.rewind();
//            return false;
//        }
//        IoBuffer buffer = IoBuffer.wrap(bytes);
//        protocolDecoderOutput.write(buffer);
//        return true;
//    }

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        //每次读取两个字节，直到读取到0D0A

        //粘包问题
//        ioBuffer.l
        ArrayList<Byte> listbyte = new ArrayList<>();
        while (ioBuffer.position() < ioBuffer.limit()) {
            byte temp = ioBuffer.get();
            if ((temp ^ 0x0D) == 0) {
                byte end = ioBuffer.get();
                if ((end ^ 0x0A) == 0) {
                    listbyte.add(temp);
                    listbyte.add(end);
                    //读完一条完整的包
                    byte[] pkgs = new byte[listbyte.size()];
                    for (int i = 0; i < listbyte.size(); i++) {
                        pkgs[i] = listbyte.get(i);
                    }
                    IoBuffer buffer = IoBuffer.wrap(pkgs);
                    protocolDecoderOutput.write(buffer);
                    return true;
                }
            } else {
                listbyte.add(temp);
            }

            System.out.println("postion：" + ioBuffer.position() + "   limit:" + ioBuffer.limit() + "  capacity:" + ioBuffer.capacity());

        }
        //分包问题
        byte[] bytes = new byte[ioBuffer.limit()];
        ioBuffer.rewind();
        ioBuffer.get(bytes);
        if ((bytes[ioBuffer.limit() - 1] ^ 0x0A) != 0 || (bytes[ioBuffer.limit() - 2] ^ 0x0D) != 0) {
            ioBuffer.rewind();
            return false;
        }
        IoBuffer buffer = IoBuffer.wrap(bytes);
        protocolDecoderOutput.write(buffer);
        return true;

    }
}
