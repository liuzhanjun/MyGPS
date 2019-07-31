package com.hai.yun.bean.utils;

import com.hai.yun.bean.AgreeMentNos;
import com.hai.yun.bean.CommandInfo;
import com.hai.yun.bean.IccIdInfo;
import com.hai.yun.bean.RecordVoiceInfo;

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


    public static byte[] getICCIDContent(int info_type, IccIdInfo info) {
        byte aByte = BinaryUtils.getByte(info_type);
        int a = aByte ^ 0x0A;

        byte[] content = null;
        if (a == 0) {
            byte[] imei = BinaryUtils.getIMEI(info.getmIMEI());
            byte[] imsi = BinaryUtils.getoxBinary(info.getmIMSI());
            byte[] iccid = BinaryUtils.getoxBinary(info.getmIccid());
            int len = imei.length + imsi.length + iccid.length;
            content = new byte[len + 1];
            int index = 0;
            content[index++] = aByte;
            index = setResult(imei, content, index);
            index = setResult(imsi, content, index);
            setResult(iccid, content, index);
            return content;
        } else {
            return content;
        }

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

    /**
     * 获得录音协议包
     *
     * @param info
     * @return
     */
    public static byte[] getRecordVoiceContent(RecordVoiceInfo info) {
        //文件类型
        byte[] file_byte = BinaryUtils.getBytes(info.getFile_type(), RecordVoiceInfo.file_type_L);
        //文件总长度
        byte[] file_all_len = BinaryUtils.getBytes(info.getFile_len(), RecordVoiceInfo.file_len_L);
        //文件总包数
        byte[] file_all_pkg = BinaryUtils.getBytes(info.getFile_pkg_num(), RecordVoiceInfo.file_pkg_num_L);
        //文件当前包序列
        byte[] file_current_pkg_no = BinaryUtils.getBytes(info.getFile_current_no(), RecordVoiceInfo.file_current_no_L);
        //当前内容长度
        byte[] file_current_Content_len = BinaryUtils.getBytes(info.getCurrent_content_len(), RecordVoiceInfo.current_content_len_L);
        //当前内容
        byte[] file_content = info.getFile_content();
        int len = RecordVoiceInfo.file_type_L
                + RecordVoiceInfo.file_len_L
                + RecordVoiceInfo.file_pkg_num_L
                + RecordVoiceInfo.file_current_no_L
                + RecordVoiceInfo.current_content_len_L
                + file_content.length;
        byte[] bytes = new byte[len];
        int index = 0;
        index = setResult(file_byte, bytes, index);
        index = setResult(file_all_len, bytes, index);
        index = setResult(file_all_pkg, bytes, index);
        index = setResult(file_current_pkg_no, bytes, index);
        index = setResult(file_current_Content_len, bytes, index);
        setResult(file_content, bytes, index);
        return bytes;
    }


    public static byte[] getCommandContent(CommandInfo info) {

        //获得内容
        byte[] content = getCommand(info.getmCommand_content());
        info.addmCommandLen(BinaryUtils.getByte(content.length));
        int len = CommandInfo.mCommandLen_L + CommandInfo.mCommand_flag_L + content.length + info.getmExt().length;
        byte[] bytes = new byte[len];
        int index = 0;
        bytes[index++] = info.getmCommandLen();
        index = setResult(info.getmCommand_flag(), bytes, index);
        index = setResult(content, bytes, index);
        setResult(info.getmExt(), bytes, index);

        return bytes;

    }

    /**
     * @param getmCommand_content
     * @return
     */
    private static byte[] getCommand(String getmCommand_content) {
        //将内容转换为ascii
        return BinaryUtils.getoxBinary(BinaryUtils.stringToAscii_ox(getmCommand_content));

    }
}
