package com.hai.yun.bean;

import com.hai.yun.bean.utils.*;
import org.joda.time.DateTime;


/**
 * GPS会话信息 单例模式
 */
public enum GpsSessionManager {

    SessionManager {
        @Override
        public GpsSessionManager getInstance() {
            return this;
        }
    };

    public abstract GpsSessionManager getInstance();

    private DataPkgInfo.Builder builder = new DataPkgInfo.Builder();

    /**
     * 0x01
     * 登陆信息包
     *
     * @param IMEI   机器码
     * @param listNo 信息序列号
     * @return
     */
    public byte[] getLoginInfoPkg(String IMEI, int listNo) {

        return dealBuilder(AgreeMentNos.loginNO, BinaryUtils.getIMEI(IMEI), listNo);
    }


    /**
     * //解析平台响应的信息包
     * 这个方法适用于常规平台响应
     *
     * @param infos 返回的信息
     * @return
     */
    public AnalysisPkgInfo analysisCommonPkg(byte infos[]) {
        AnalysisPkgInfo.AnalysisBuilder analysisBuilder = new AnalysisPkgInfo.AnalysisBuilder();
        //获得包长度
        byte pkglen = infos[2];
        //获得协议号
        byte agreeMentNo = infos[3];
        //获得信息序列号
        byte[] infolist = new byte[]{infos[4], infos[5]};
        //获得校验位倒数第四位和第三位
        byte[] checkBite = new byte[]{infos[infos.length - 4], infos[infos.length - 3]};
        //解析时间
        DateTime d_time = null;
        //如果长度大于5说明有时间要解析
        int len = BinaryUtils.getInt(pkglen);
        byte[] time = null;
        if (len > 5) {
            time = new byte[6];
            int index = 0;
            for (int i = 6; i < 12; i++) {
                time[index++] = infos[i];
            }

        }

        AnalysisPkgInfo analysisinfo = analysisBuilder
                .addmPkgLength(pkglen)
                .addmAgreeMentNO(agreeMentNo)
                .addmTime(time)
                .addmInfolist(infolist)
                .addmCheckBit(checkBite).build();

        return analysisinfo;

    }


    /**
     * 获得GPS信息包 0x10
     *
     * @param info gps 信息包
     * @return
     */
    public byte[] getGPSInfoPkg(GpsInfo info) {
        byte[] content = GPSUtils.setGPSContent(info);
        return dealBuilder(AgreeMentNos.gpsInfo, content, info.getListNo());
    }


    /**
     * 获得LBS信息包 0x11
     *
     * @param info lbs信息包
     * @return
     */
    public byte[] getLBSPkg(LbsInfo info) {
        byte[] content = LBSUtils.setLBSContent(info);
        return dealBuilder(AgreeMentNos.LBSInfo, content, info.getListNo());
    }


    /**
     * 获得GPS 和LBS合并信息包
     * 0x12
     *
     * @param listNO  信息序列号
     * @param gpsInfo gps信息
     * @param lbsInfo lbs信息
     * @return
     */
    public byte[] getGPSAndLBSpkg(int listNO, GpsInfo gpsInfo, LbsInfo lbsInfo) {
        byte[] gps_bytes = GPSUtils.setGPSContent(gpsInfo);
        byte[] lbs_bytes = LBSUtils.setLBSContent(lbsInfo);
        byte[] lbs_re_bytes = new byte[lbs_bytes.length - 6];
        //lbs要去掉日期 也就是前六位
        int index = 0;
        for (int i = 6; i < lbs_bytes.length; i++) {
            lbs_re_bytes[index++] = lbs_bytes[i];
        }
        byte[] content = BinaryUtils.mergeBbytes(gps_bytes, lbs_re_bytes);
        return dealBuilder(AgreeMentNos.GPSAndLBSInfo, content, listNO);
    }

    /**
     * 0x13
     * 终端信息  （心跳包）
     *
     * @param info 终端信息
     * @return
     */
    public byte[] getBeartHeatPkg(HeartBeatInfo info) {
        byte[] content = HeartBeatUtils.setHearteatContent(info);
        return dealBuilder(AgreeMentNos.heartbeat, content, info.getListNO());
    }

    /**
     * 卫星信噪比
     * 0x14
     *
     * @param listNo     信息序列号
     * @param num        卫星数目
     * @param xzb        信噪比
     * @param extContent 预留扩展位
     * @return
     */
    public byte[] getSateliteSnor(int listNo, int num, byte[] xzb, byte[] extContent) {
        return dealBuilder(AgreeMentNos.SSNIRInfo, SatelliteSnorUtils.setSateliteSnor(num, xzb, extContent), listNo);
    }


    /**
     * 报警信息
     * 0x16
     *
     * @param listNO  信息序列号
     * @param gpsInfo gps信息
     * @param lbsInfo lbs信息
     * @param info    终端信息
     * @return
     */
    public byte[] getWarring(int listNO, GpsInfo gpsInfo, LbsInfo lbsInfo, HeartBeatInfo info) {
        //GPS
        byte[] gps_bytes = GPSUtils.setGPSContent(gpsInfo);
        //LBS
        byte[] lbs_bytes = LBSUtils.setLBSContent(lbsInfo);
        //终端信息
        byte[] heart_content = HeartBeatUtils.setHearteatContent(info);

        byte[] lbs_re_bytes = new byte[lbs_bytes.length - 5];
        //lbs要去掉日期 也就是前六位 并在前面加1位 ：表示lbs长度
        int index = 0;
        lbs_re_bytes[index++] = BinaryUtils.getByte(lbs_re_bytes.length);
        for (int i = 6; i < lbs_bytes.length; i++) {
            lbs_re_bytes[index++] = lbs_bytes[i];
        }

        byte[] content = BinaryUtils.mergeBbytes(gps_bytes, lbs_re_bytes, heart_content);
        return dealBuilder(AgreeMentNos.WarningInfo, content, listNO);
    }


    /**
     * 多基站定位信息
     *
     * @param info
     * @return
     */
    public byte[] getLBSMultiStation(LbsMultiStationInfo info) {
        byte[] content = LBSUtils.setLBSMultiStationContent(info);
        return dealBuilder(AgreeMentNos.LBSMultipleBaseStations, content, info.getmListNo());
    }


    /**
     * 0x1A
     * GPS查询地址
     *
     * @param gpsInfo
     * @return
     */
    public byte[] gpsQueryAddress(GpsInfo gpsInfo) {
        byte[] content = GPSUtils.setGSPqueryAddress(gpsInfo);
        return dealBuilder(AgreeMentNos.queryAddressGPS, content, gpsInfo.getListNo());
    }

    /**
     * 解析查询地址回复
     *
     * @param bytes
     * @param tip   0位中文，1为英文
     * @return
     */
    public AnalysisAddressInfo analysisQueryAddressPkg(byte[] bytes, int tip) {
        AnalysisAddressInfo info = new AnalysisAddressInfo();
        byte[] pkg_len = null;
        if (tip == 0) {
            info.setmPkgLength_L(1);
            info.setmCommandLen_L(1);
            pkg_len = new byte[]{bytes[2]};
        } else if (tip == 1) {
            info.setmPkgLength_L(2);
            info.setmCommandLen_L(2);
            pkg_len = new byte[]{bytes[2], bytes[3]};
        }
        int pkg_len_ = BinaryUtils.getInt(pkg_len);
        //地址信息长度=包长度表示的长度-（协议号到校验位的长度）
        int address_len = pkg_len_ - (info.getmAgreeMentNO_L()
                + info.getmCommandLen_L()
                + info.getmService_tip_L()
                + info.getmAddress_const().length
                + info.getSeparator_1().length
                + info.getSeparator_2().length
                + info.getmPhone_L()
                + info.getEnd_str().length
                + info.getmInfolist_L()
                + info.getmCheckBit_L()
        );

        System.out.println(address_len);

        int index = 2;
        //设置长度
        info.setmPkgLength(pkg_len);
        index += 2;
        //设置协议号
        info.setmAgreeMentNO(getBytes(bytes, index, info.getmAgreeMentNO_L()));

        index += info.getmAgreeMentNO_L();
        //设置指令长度
        info.setmCommandLen(getBytes(bytes, index, info.getmCommandLen_L()));
        index += info.getmCommandLen_L();
        //设置服务器标志
        info.setmService_tip(getBytes(bytes, index, info.getmService_tip_L()));
        index += info.getmService_tip_L();
        //过ADDRESS  已有
        index += info.getmAddress_const().length;
        //过分隔符
        index += info.getSeparator_1().length;
        //设置地址内容
        info.setmAddress_unicode(getBytes(bytes, index, address_len));
        index += address_len;
        //过分隔符
        index += info.getSeparator_2().length;
        //设置手机号码
        info.setmPhone(getBytes(bytes, index, info.getmPhone_L()));
        index += info.getmPhone_L();
        //过结束符
        index += info.getEnd_str().length;
        //设置序列号
        info.setmInfolist(getBytes(bytes, index, info.getmInfolist_L()));
        index += info.getmInfolist_L();
        //设置校验位
        info.setmCheckBit(getBytes(bytes, index, info.getmCheckBit_L()));


        System.out.println("index=" + index);


        return info;
    }

    /**
     * 从bytes中获得 start开始 长度为len的数组
     *
     * @param bytes
     * @param start
     * @param len
     * @return
     */
    private byte[] getBytes(byte[] bytes, int start, int len) {
        byte[] re_bytes = new byte[len];
        int index = 0;
        for (int i = start; i < start + len; i++) {
            re_bytes[index++] = bytes[i];
        }
        return re_bytes;
    }


    /**
     * LBS查询地址
     *
     * @param info
     * @return
     */
    public byte[] lBSAddressQuery(LbsInfo info) {
        byte[] bytes = LBSUtils.setLBSQueryAddressContent(info);
        return dealBuilder(AgreeMentNos.queryAddressLBS, bytes, info.getListNo());
    }

    /**
     * 0x2C
     *
     * @param info lbs多基站wifi定位完整信息
     * @return
     */
    public byte[] lbsMultiAndWifiPostion(LbsMultiWifiPostionInfo info) {
        byte[] bytes = LBSUtils.setLbsMultiAndWifiPostion(info);
        return dealBuilder(AgreeMentNos.LBSWIFIMultipBaseStations, bytes, info.getmListNo());

    }

    /**
     * 0x90
     * 终端向后台发送IMSI信息
     *
     * @param IMSI
     * @return
     */
    public byte[] getIMSI(String IMSI) {
        return TerminalUtils.getIMSIAllContent(IMSI);
    }

    /**
     * 终端向后台发送ICCID信息
     * 0x94
     *
     * @param info_type 信息内容
     * @param iccIdInfo
     * @param listN0    信息序列号
     * @return
     */
    public byte[] getICCID(int info_type, int listN0, IccIdInfo iccIdInfo) {
        byte[] content = TerminalUtils.getICCIDContent(info_type, iccIdInfo);
        return dealBuilder2(AgreeMentNos.ICCIDSendInfo, content, listN0, 2);
    }

    /**
     * 0x8d
     * 录音协议包
     *
     * @param listN0
     * @param info
     * @return
     */
    public byte[] record_voice_Content(int listN0, RecordVoiceInfo info) {
        byte[] content = TerminalUtils.getRecordVoiceContent(info);
        return dealBuilder2(AgreeMentNos.recordFileSend, content, listN0, 2);
    }


    /**
     * 解析录音包返回信息
     *
     * @param pkg
     * @return
     */
    public AnalysisRecordPkg AnalysisiRecordpkg(byte[] pkg) {
        AnalysisRecordPkg analysisRecordPkg = new AnalysisRecordPkg();
        //包长度
        analysisRecordPkg.setmPkgLength(new byte[]{pkg[2], pkg[3]});
        analysisRecordPkg.setmAgreeMentNO(pkg[4]);
        analysisRecordPkg.setmAccept_state(pkg[5]);
        analysisRecordPkg.setmInfolist(new byte[]{pkg[6], pkg[7]});
        analysisRecordPkg.setmCheckBit(new byte[]{pkg[8], pkg[9]});
        return analysisRecordPkg;
    }


    private byte[] dealBuilder2(byte[] agreeMentNO, byte[] content, int listNo, int pkg_len) {
        DataPkgInfo.setmPkgLength_L(pkg_len);
        //登录信息
        DataPkgInfo datap = builder
                //协议号
                .setAgreeMentNo(agreeMentNO)
                //内容
                .appendContentStart(content)
                //信息序列号
                .setMInfolist(listNo)
                .build();
        return datap.getDataPkg();
    }


    /**
     * 0x80
     * @param info
     * @param listNO
     * @return
     */
    public byte[] sendCommandContent(CommandInfo info, int listNO) {
        byte[] bytes = TerminalUtils.getCommandContent(info);
        return dealBuilder(AgreeMentNos.serviceSendToClient, bytes, listNO);

    }

    private byte[] dealBuilder(byte[] agreeMentNO, byte[] content, int listNo) {

        //登录信息
        DataPkgInfo datap = builder
                //协议号
                .setAgreeMentNo(agreeMentNO)
                //内容
                .appendContentStart(content)
                //信息序列号
                .setMInfolist(listNo)
                .build();
        return datap.getDataPkg();
    }


}
