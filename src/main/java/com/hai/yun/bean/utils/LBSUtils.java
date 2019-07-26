package com.hai.yun.bean.utils;

import com.hai.yun.bean.LbsInfo;
import com.hai.yun.bean.LbsMultiStationInfo;
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

    private static byte[] setMci(int mci) {
        return BinaryUtils.getBytes(mci, 2);
    }

    private static byte setMciss(int mciss) {
        return BinaryUtils.getByte(mciss);
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

    /**
     * @param time        时间
     * @param mcc         移动用户所属国家代号
     * @param mnc         移动网号码
     * @param lac         位置区码
     * @param cellId      移动基站
     * @param ext_content 预留扩展位
     * @return
     */
    private static byte[] setLBSContent(DateTime time, int mcc, int mnc, int lac, int cellId, byte[] ext_content) {
        int byte_len = 14;

        if (ext_content != null) {
            byte_len += ext_content.length;
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
        if (ext_content != null) {
            setResult(ext_content, result, index);
        }
        return result;
    }


    /**
     * LBSinfo
     *
     * @param info
     * @return
     */
    public static byte[] setLBSContent(LbsInfo info) {
        return setLBSContent(info.getTime(), info.getMcc(), info.getMnc(), info.getLac(), info.getCellId(), info.getExtContent());
    }


    /**
     * 设置lbs多基站定位信息
     *
     * @param info
     * @return
     */
    public static byte[] setLBSMultiStationContent(LbsMultiStationInfo info) {

        //设置时间
        byte[] time_bytes = setTime(info.getmTime());
        byte[] mcc = setMcc(info.getmMcc());
        byte mnc = setMnc(info.getmMnc());
        byte[] lac = setLac(info.getmLac());
        int[][] mciMciss = info.getmMci_Mciss();
        byte[] mci_mciss = new byte[mciMciss.length * 3];
        int index = 0;
        for (int i = 0; i < mciMciss.length; i++) {
            byte[] mcis = setMci(mciMciss[i][0]);
            mci_mciss[index++] = mcis[0];
            mci_mciss[index++] = mcis[1];
            byte mciss = setMciss(mciMciss[i][1]);
            mci_mciss[index++] = mciss;
        }
        int len = time_bytes.length + mcc.length + 1 + lac.length + mci_mciss.length;
        if (info.getExtContent() != null) {
            len += info.getExtContent().length;
        }
        byte[] bytes = new byte[len];
        int mIndex = 0;
        mIndex = setResult(bytes, time_bytes, mIndex);
        mIndex = setResult(bytes, mcc, mIndex);
        bytes[mIndex++] = mnc;
        mIndex = setResult(bytes, lac, mIndex);
        mIndex = setResult(bytes, mci_mciss, mIndex);
        if (info.getExtContent() != null) {
            setResult(bytes, info.getExtContent(), mIndex);
        }

        return bytes;

    }

    private static int setResult(byte[] bytes, byte[] result, int index) {
        for (int i = 0; i < bytes.length; i++) {
            result[index++] = bytes[i];
        }
        return index;
    }
}
