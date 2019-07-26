package com.hai.yun.bean.utils;

/**
 * 卫星信噪比
 */
public class SatelliteSnorUtils {

    /**
     * 获得卫星数
     *
     * @param num
     * @return
     */
    private static byte getSatelLiteNum(int num) {
        num = num > 15 ? 15 : num;
        return BinaryUtils.getByte(num);
    }

    /**
     * @param num        卫星数
     * @param info       卫星信噪比
     * @param extContent 预留扩展位
     * @return
     */
    public static byte[] setSateliteSnor(int num, byte[] info, byte[] extContent) {
        int len = info.length + 1;
        if (extContent != null) {
            len += extContent.length;
        }
        byte[] bytes = new byte[info.length + 1];
        bytes[0] = getSatelLiteNum(num);
        int index = 1;
        for (int i = 0; i < info.length; i++) {
            bytes[index++] = info[i];
        }
        if (extContent != null) {
            for (int i = 0; i < extContent.length; i++) {
                bytes[index++] = extContent[i];
            }
        }


        return bytes;
    }
}
