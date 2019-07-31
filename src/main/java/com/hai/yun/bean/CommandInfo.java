package com.hai.yun.bean;

public class CommandInfo {
    private byte mCommandLen;//指令长度 值表示指令内容的字节长度
    public static final int mCommandLen_L = 1;//指令长度所占字节
    private byte[] mCommand_flag;//指令标志位
    public static final int mCommand_flag_L = 4;
    private String mCommand_content;//指令内容
    private byte[] mExt;//预留扩展位

    public CommandInfo addmCommandLen(byte mCommandLen) {
        this.mCommandLen = mCommandLen;
        return this;
    }

    public CommandInfo addmCommand_flag(byte[] mCommand_flag) {
        this.mCommand_flag = mCommand_flag;
        return this;
    }

    public CommandInfo addmCommand_content(String mCommand_content) {
        this.mCommand_content = mCommand_content;
        return this;
    }

    public CommandInfo addmExt(byte[] mExt) {
        this.mExt = mExt;
        return this;
    }

    public byte getmCommandLen() {
        return mCommandLen;
    }

    public byte[] getmCommand_flag() {
        return mCommand_flag;
    }

    public String getmCommand_content() {
        return mCommand_content;
    }

    public byte[] getmExt() {
        return mExt;
    }
}
