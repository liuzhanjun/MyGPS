package com.hai.yun.bean.utils;

import com.hai.yun.bean.LbsInfo;
import com.hai.yun.bean.LbsMultiStationInfo;
import com.hai.yun.bean.LbsMultiWifiPostionInfo;
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
     * @param len mcc所占长度
     * @return
     */
    private static byte[] setMcc(int mcc, int len) {
        return BinaryUtils.getBytes(mcc, len);
    }


    /**
     * 移动网号码
     * 例如中国移动为0x00
     *
     * @param mnc
     * @param len mnc所占长度
     * @return
     */
    private static byte[] setMnc(int mnc, int len) {
        return BinaryUtils.getBytes(mnc, len);
    }

    /**
     * @param lac
     * @param len lac 所占长度
     * @return
     */
    private static byte[] setLac(int lac, int len) {
        return BinaryUtils.getBytes(lac, len);
    }

    /**
     * @param rssi 取值范围
     * @param len  lac 所占长度
     * @return
     */
    private static byte[] setRssi(int rssi, int len) {
        return BinaryUtils.getBytes(rssi, len);
    }

    /**
     * @param ci  取值范围 1-65534
     * @param len lac 所占长度
     * @return
     */
    private static byte[] setCi(int ci, int len) {
        return BinaryUtils.getBytes(ci, len);
    }


    /**
     * 移动基站
     *
     * @param cellId
     * @param len    cellId所占长度
     * @return
     */
    private static byte[] setCellId(int cellId, int len) {
        return BinaryUtils.getBytes(cellId, len);
    }

    private static byte[] setMci(int mci, int len) {
        return BinaryUtils.getBytes(mci, len);
    }

    private static byte[] setMciss(int mciss, int len) {
        return BinaryUtils.getBytes(mciss, len);
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
        byte[] mcc_bytes = setMcc(mcc, LbsInfo.mMcc_L);
        //setmnc
        byte[] mnc_byte = setMnc(mnc, LbsInfo.mMnc_L);
        //setlac
        byte[] lac_bytes = setLac(lac, LbsInfo.mLac_L);
        //setCellID
        byte[] cell_id_bytes = setCellId(cellId, LbsInfo.cellId_L);

        int index = 0;
        index = setResult(time_bytes, result, index);
        index = setResult(mcc_bytes, result, index);
        index = setResult(mnc_byte, result, index);
        index = setResult(lac_bytes, result, index);
        index = setResult(cell_id_bytes, result, index);
        //设置扩展
        if (ext_content != null) {
            setResult(ext_content, result, index);
        }
        return result;
    }

    /**
     * @param time        时间
     * @param mcc         移动用户所属国家代号
     * @param mnc         移动网号码
     * @param lac         位置区码
     * @param cellId      移动基站
     * @param phone       电话号码
     * @param ext_content 预留扩展位
     * @return
     */
    private static byte[] setLBSContent(DateTime time, int mcc, int mnc, int lac, int cellId, byte[] ext_content, String phone) {
        int byte_len = 14;

        if (ext_content != null) {
            byte_len += ext_content.length;
        }
        byte[] result = new byte[byte_len];
        //设置时间
        byte[] time_bytes = setTime(time);
        //setMcc
        byte[] mcc_bytes = setMcc(mcc, LbsInfo.mMcc_L);
        //setmnc
        byte[] mnc_byte = setMnc(mnc, LbsInfo.mMnc_L);
        //setlac
        byte[] lac_bytes = setLac(lac, LbsInfo.mLac_L);
        //setCellID
        byte[] cell_id_bytes = setCellId(cellId, LbsInfo.cellId_L);
        //电话号码
        byte[] mPhone = BinaryUtils.getPhoneBytes(phone, LbsInfo.Phone_L);
        int index = 0;
        index = setResult(time_bytes, result, index);
        index = setResult(mcc_bytes, result, index);
        index = setResult(mnc_byte, result, index);
        index = setResult(lac_bytes, result, index);
        //
        index = setResult(cell_id_bytes, result, index);
        //设置电话号码
        index = setResult(mPhone, result, index);
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
     * LBSinfo
     *
     * @param info
     * @return
     */
    public static byte[] setLBSQueryAddressContent(LbsInfo info) {
        return setLBSContent(info.getTime(), info.getMcc(), info.getMnc(), info.getLac(), info.getCellId(), info.getExtContent(), info.getmPhone());
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
        byte[] mcc = setMcc(info.getmMcc(), LbsMultiStationInfo.mMcc_L);
        byte[] mnc = setMnc(info.getmMnc(), LbsMultiStationInfo.mMnc_L);
        byte[] lac = setLac(info.getmLac(), LbsMultiStationInfo.mLac_L);
        LbsMultiStationInfo.Mci_Mciss[] mciMciss = info.getmMci_Mciss();
        int mci_mciss_len = mciMciss.length * (LbsMultiStationInfo.Mci_Mciss.mMci_L + LbsMultiStationInfo.Mci_Mciss.mMciss_L);
        int len = time_bytes.length + mcc.length + 1 + lac.length + mci_mciss_len;
        if (info.getExtContent() != null) {
            len += info.getExtContent().length;
        }
        byte[] bytes = new byte[len];
        int mIndex = 0;
        mIndex = setResult(time_bytes, bytes, mIndex);
        mIndex = setResult(mcc, bytes, mIndex);
        mIndex = setResult(mnc, bytes, mIndex);
        mIndex = setResult(lac, bytes, mIndex);
        for (int i = 0; i < mciMciss.length; i++) {
            if (mciMciss[i] != null) {
                byte[] mcis = setMci(mciMciss[i].getmMci(), LbsMultiStationInfo.Mci_Mciss.mMci_L);
                mIndex = setResult(mcis, bytes, mIndex);

                byte[] mciss = setMciss(mciMciss[i].getmMciss(), LbsMultiStationInfo.Mci_Mciss.mMciss_L);
                mIndex = setResult(mciss, bytes, mIndex);
            } else {
                byte[] mcis = setMci(0, LbsMultiStationInfo.Mci_Mciss.mMci_L);
                mIndex = setResult(mcis, bytes, mIndex);

                byte[] mciss = setMciss(0, LbsMultiStationInfo.Mci_Mciss.mMciss_L);
                mIndex = setResult(mciss, bytes, mIndex);
            }
        }

        if (info.getExtContent() != null) {
            setResult(info.getExtContent(), bytes, mIndex);
        }

        return bytes;

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
     * 设置
     *
     * @param info
     * @return
     */
    public static byte[] setLbsMultiAndWifiPostion(LbsMultiWifiPostionInfo info) {
        //添加时间
        byte[] time = DateUtils.getTime(info.getmTime());
        byte[] mcc = setMcc(info.getmMcc(), LbsMultiWifiPostionInfo.mMcc_L);
        byte[] mnc = setMnc(info.getmMnc(), LbsMultiWifiPostionInfo.mMnc_L);
        //lac+ci+rssi
        LbsMultiWifiPostionInfo.Lac_Ci_Rssi[] lac_ci_rssis = info.getmLac_ci_rssis();
        int lac_ci_rssi_bytes_L = lac_ci_rssis.length * (LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mCi_L + LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mLac_L + LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mRssi_L);
        LbsMultiWifiPostionInfo.WiFiInfo[] wiFiInfos = info.getmWifiinfo();
        int wifiinfoLen = wiFiInfos.length * (LbsMultiWifiPostionInfo.WiFiInfo.mWifiMac_L + LbsMultiWifiPostionInfo.WiFiInfo.mWifiInten_L);
        int result_len = 6
                + LbsMultiWifiPostionInfo.mMcc_L
                + LbsMultiWifiPostionInfo.mMnc_L
                + lac_ci_rssi_bytes_L
                + LbsMultiWifiPostionInfo.time_lead_L
                + LbsMultiWifiPostionInfo.mWifiNumber_L
                + wifiinfoLen;
        byte[] result = new byte[result_len];
        int index = 0;
        //添加时间
        index = setResult(time, result, index);
        //mcc
        index = setResult(mcc, result, index);
        //mnc
        index = setResult(mnc, result, index);


        //lac+ci+rssi
        for (int i = 0; i < lac_ci_rssis.length; i++) {
            LbsMultiWifiPostionInfo.Lac_Ci_Rssi temp = lac_ci_rssis[i];
            index = setResult(setLac(temp.getmLac(), LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mLac_L), result, index);
            index = setResult(setCi(temp.getmCi(), LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mCi_L), result, index);
            index = setResult(setRssi(temp.getmRssi(), LbsMultiWifiPostionInfo.Lac_Ci_Rssi.mRssi_L), result, index);
        }
        //时间提前量
        index = setResult(getByte(info.getmTime_lead(), LbsMultiWifiPostionInfo.time_lead_L), result, index);
        //wifi数量
        index = setResult(getByte(info.getmWifiNumber(), LbsMultiWifiPostionInfo.mWifiNumber_L), result, index);
        //wifi信息
        for (int i = 0; i < wiFiInfos.length; i++) {
            LbsMultiWifiPostionInfo.WiFiInfo wiFiInfo = wiFiInfos[i];
            index = setResult(wiFiInfo.getmWifiMac(), result, index);
            index = setResult(getByte(wiFiInfo.getmWifiInten(), LbsMultiWifiPostionInfo.WiFiInfo.mWifiInten_L), result, index);
        }

        return result;
    }


    private static byte[] getByte(int x, int len) {
        return BinaryUtils.getBytes(x, len);
    }
}
