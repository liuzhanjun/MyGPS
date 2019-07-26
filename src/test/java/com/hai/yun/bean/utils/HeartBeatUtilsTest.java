package com.hai.yun.bean.utils;

import com.hai.yun.bean.TerminalInfo;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeartBeatUtilsTest {

    @Test
    public void getTerminalInfo() {
        TerminalInfo info = new TerminalInfo();
        info.addmDefense((byte) 0)
                .addmAcc((byte) 1)
                .addmAnElectric((byte) 1)
                .addmWarring((byte) 1)
                .addmGpsPostion((byte) 1)
                .addmOilElectricity((byte) 0);
         HeartBeatUtils u=new HeartBeatUtils();




    }
}