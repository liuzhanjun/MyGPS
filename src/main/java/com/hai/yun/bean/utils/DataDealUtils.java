package com.hai.yun.bean.utils;

import com.hai.yun.bean.DataPkgInfo;

import java.util.ArrayList;
import java.util.Collections;

public class DataDealUtils {




    public static ArrayList<byte[]> dealPkg(byte[] pkg) {
        ArrayList<byte[]> pkgs = new ArrayList<>();
        System.out.println(pkg[0] ^ 0x78);

        for (int i = 0; i < pkg.length; ) {
            if ((pkg[i] ^ 0x78) == 0 && (pkg[i + 1] ^ 0x78) == 0) {
                //获得包长度所占字节 包长度只占一个字节
                int pkg_len = BinaryUtils.getInt(pkg[i + 2]);
                //截取这个包的长度

            }
            if ((pkg[i] ^ 0x79) == 0 && (pkg[i + (2) - 1] ^ 0x79) == 0) {
                //获得包长度所占字节 包长度只占2字节
                int pkg_len = BinaryUtils.getInt(new byte[]{pkg[i + 2], pkg[i = 3]});//获得包长度所占字节

            }


        }

        return pkgs;
    }
}
