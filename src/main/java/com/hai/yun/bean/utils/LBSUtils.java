package com.hai.yun.bean.utils;

import org.joda.time.DateTime;

public class LBSUtils {
    /**
     * 设置时间
     *
     * @param time
     * @return
     */
    private static byte[] setTime(DateTime time) {
        return DateUtils.getTime(time);
    }

    /**
     * 设置MCC移动用户所属国家代号
     * 中国为460
     *
     * @param mcc 取值范围0-999
     * @return
     */
    private static byte[] setMcc(int mcc) {
        return BinaryUtils.getBytes(mcc, 2);
    }


    /**
     * 移动网号码
     * 例如中国移动为0x00
     *
     * @param mnc
     * @return
     */
    private static byte setMnc(int mnc) {
        return BinaryUtils.getByte(mnc);
    }

    /**
     * @param lac 取值范围 1-65534
     * @return
     */
    private static byte[] setLac(int lac) {
        return BinaryUtils.getBytes(lac, 2);
    }


    /**
     * 移动基站
     *
     * @param cellId
     * @return
     */
    private static byte[] setCellId(int cellId) {
        return BinaryUtils.getBytes(cellId, 3);
    }

    /**
     * 预留扩展位
     *
     * @param content 内容
     * @param len     扩展位的位宽
     * @return
     */
    private static byte[] extBit(int content, int len) {
        return BinaryUtils.getBytes(content, len);
    }

    public static byte[] setLBSContent(DateTime time, int mcc, int mnc, int lac, int cellId, int ext_content, int ext_len) {
        int byte_len = 14;
        byte[] extBit = null;
        if (ext_content != 0 && ext_len != 0) {
            extBit = extBit(ext_content, ext_len);
            byte_len += ext_len;
        }
        byte[] result = new byte[byte_len];
        //设置时间
        byte[] time_bytes = setTime(time);
        //setMcc
        byte[] mcc_bytes = setMcc(mcc);
        //setmnc
        byte mnc_byte = setMnc(mnc);
        //setlac
        byte[] lac_bytes = setLac(lac);
        //setCellID
        byte[] cell_id_bytes = setCellId(cellId);

        int index = 0;
        index = setResult(time_bytes, result, index);
        index = setResult(mcc_bytes, result, index);
        result[index++] = mnc_byte;
        index = setResult(lac_bytes, result, index);
        index = setResult(cell_id_bytes, result, index);
        //设置扩展
        if (ext_content != 0 && ext_len != 0) {
            setResult(extBit, result, index);
        }
        return result;
    }

    public static byte[] setLBSContent(DateTime time, int mcc, int mnc, int lac, int cellId) {
        return setLBSContent(time, mcc, mnc, lac, cellId, 0, 0);
    }

    private static int setResult(byte[] bytes, byte[] result, int index) {
        for (int i = 0; i < bytes.length; i++) {
            result[index++] = bytes[i];
        }
        return index;
    }
}
