package com.hai.yun.bean;

import com.hai.yun.bean.utils.BinaryUtils;
import org.junit.Before;
import org.junit.Test;

public class DataPkgInfoTest {

    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void pak() {
        //登录信息
        DataPkgInfo.Builder builder = new DataPkgInfo.Builder();
        //
        DataPkgInfo datap = builder

                //协议号
                .setAgreeMentNo(AgreeMentNos.loginNO)
                //内容
                .appendContentStart(BinaryUtils.getIMEI("123456789012345"))
                //信息序列号
                .setMInfolist(1)
                .build();
        byte[] bs = datap.getDataPkg();
        for (byte b : bs) {
            System.out.print(BinaryUtils.byteToOx(BinaryUtils.getInt(b)) + "|");
        }


    }


//    0d 01 01 23 45 67 89 01 23 45 00 01
}