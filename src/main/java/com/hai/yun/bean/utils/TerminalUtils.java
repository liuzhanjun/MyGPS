package com.hai.yun.bean.utils;

import com.hai.yun.bean.AgreeMentNos;

public class TerminalUtils {
    public static final String IMSI_HEAD = "IMSI:";
    public static final byte[] ext_ = {0x01, 0x01};
    public static final byte[] final_str = {BinaryUtils.getByte(0x9F)};

    public static byte[] getIMSIAllContent(String IMSI) {
        int len = 2 + 1 + 1 + 5 + 15 + 2 + 1 + 2 + 2;
        byte[] result = new byte[len];
        byte[] crc_result = new byte[len - 6];
        byte[] start = {0x78, 0x78};
        byte[] stop = {0x0D, 0x0A};
        int index = 0;
        int crc_index = 0;
        index = setResult(start, result, index);
        //计算和添加包长度
        int pkg_len = len - 5;
        index = setResult(BinaryUtils.getBytes(pkg_len, 1), result, index);
        crc_index = setResult(BinaryUtils.getBytes(pkg_len, 1), crc_result, crc_index);
        //添加协议号
        index = setResult(AgreeMentNos.IMSISendInfo, result, index);
        crc_index = setResult(AgreeMentNos.IMSISendInfo, crc_result, crc_index);
        //字符IMSI 内容
        byte[] imsiContent = getIMSIContent(IMSI);
        index = setResult(imsiContent, result, index);
        crc_index = setResult(imsiContent, crc_result, crc_index);

        //计算校验位
        byte[] crc16 = CRC16.getCRC16(crc_result);
        index = setResult(crc16, result, index);
        //添加停止位
        setResult(stop, result, index);
        return result;
    }

    private static byte[] getIMSIContent(String imsi) {
        byte[] result = new byte[5 + 15 + 2 + 1];
        byte[] IMSI_bytes = BinaryUtils.getoxBinary(BinaryUtils.string2Unicode(IMSI_HEAD));
        int index = 0;

        index = setResult(IMSI_bytes, result, index);
        //获得imsi
        byte[] imis_bytes = BinaryUtils.getoxBinary(BinaryUtils.string2Unicode(imsi));
        index = setResult(imis_bytes, result, index);
        //预留位
        index = setResult(ext_, result, index);
        //固定字符
        setResult(final_str, result, index);

        return result;
    }


    /**
     * 将 bytes 添加到result中
     *
     * @param bytes
     * @param result
     * @param index
     * @return
     */
    private static int setResult(byte[] bytes, byte[] result, int index) {
        for (int i = 0; i < bytes.length; i++) {
            result[index++] = bytes[i];
        }
        return index;
    }
}
