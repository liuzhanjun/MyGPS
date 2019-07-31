package com.hai.yun.bean;

public class RecordVoiceInfo {
    private int file_type;//文件类型
    public static final int file_type_L = 1;//文件包类型所占字节
    private long file_len;//文件总长度
    public static final int file_len_L = 4;
    private int file_pkg_num;//文件包分为多少个包
    public static final int file_pkg_num_L = 1;
    private int file_current_no;//文件当前包序列
    public static final int file_current_no_L = 1;//文件当前包序列
    private int current_content_len;//当前文件内容长度
    public static final int current_content_len_L = 2;//当前文件内容长度
    private byte[] file_content;//分割后的数据包内容


    public int getFile_type() {
        return file_type;
    }

    public long getFile_len() {
        return file_len;
    }

    public int getFile_pkg_num() {
        return file_pkg_num;
    }

    public int getFile_current_no() {
        return file_current_no;
    }

    public int getCurrent_content_len() {
        return current_content_len;
    }

    public byte[] getFile_content() {
        return file_content;
    }

    public RecordVoiceInfo addFile_type(int file_type) {
        this.file_type = file_type;
        return this;
    }

    public RecordVoiceInfo addFile_len(long file_len) {
        this.file_len = file_len;
        return this;
    }

    public RecordVoiceInfo addFile_pkg_num(int file_pkg_num) {
        this.file_pkg_num = file_pkg_num;
        return this;
    }

    public RecordVoiceInfo addFile_current_no(int file_current_no) {
        this.file_current_no = file_current_no;
        return this;
    }

    public RecordVoiceInfo addCurrent_content_len(int current_content_len) {
        this.current_content_len = current_content_len;
        return this;
    }

    public RecordVoiceInfo addFile_byte(byte[] file_byte) {
        this.file_content = file_byte;
        return this;
    }

}
