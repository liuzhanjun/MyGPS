package com.hai.yun.bean;

/**
 * 终端扩展位
 * <p>
 * 部分终端设置当前语言 0x00 0x01 中文 0x00 0x02英文
 * 部分终端 语言的第一位用作设备外接电压
 * //902B超长待机设备这个值作为工作模式和设备休眠状态
 * //工作模式：
 * // 0x00 =正常模式
 * // 0x01 =智能休眠
 * //0x03  =深度休眠
 * //0x04  =定时开关机
 * //设备休眠状态
 * //0x00=正常
 * //0x01=休眠
 */
public class TerminaExt {
    int mN01;
    int mNo2;

    public TerminaExt(int mN01, int mNo2) {
        this.mN01 = mN01;
        this.mNo2 = mNo2;
    }

    public int getmN01() {
        return mN01;
    }

    public int getmNo2() {
        return mNo2;
    }
}
