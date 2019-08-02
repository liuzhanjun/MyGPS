package com.hai.yun.net;

import com.hai.yun.bean.utils.BinaryUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class MinaMessageDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        byte[] bytes = new byte[ioBuffer.limit()];
        ioBuffer.get(bytes);
        if ((bytes[ioBuffer.limit() - 1] ^ 0x0A) == 1 || (bytes[ioBuffer.limit() - 2] ^ 0x0D) == 1) {
            ioBuffer.rewind();
            return false;
        }
        IoBuffer buffer = IoBuffer.wrap(bytes);
        protocolDecoderOutput.write(buffer);
        return true;
    }
}
