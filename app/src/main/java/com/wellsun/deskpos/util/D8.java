package com.wellsun.deskpos.util;

import android.content.Context;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.decard.NDKMethod.BasicOper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.wellsun.deskpos.base.Api;
import com.wellsun.deskpos.base.App;
import com.wellsun.deskpos.base.BaseActivity;
import com.wellsun.deskpos.base.OkgoCallBack;
import com.wellsun.deskpos.bean.GetCardNumberBean;
import com.wellsun.deskpos.bean.OpenCardBean;
import com.wellsun.deskpos.cardbean.Card0005;
import com.wellsun.deskpos.cardbean.Card0015;
import com.wellsun.deskpos.data.Data;
import com.wellsun.deskpos.em.CardStateEm;
import com.wellsun.deskpos.eventBusBean.InitCardBean;
import com.wellsun.deskpos.pboc.PBOCUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public class D8 {
    public volatile static boolean bOkgo = false;
    public static String csn;
    public static String cardId;

    //读卡器连接
    public static boolean connectD8(Context context) {
        //向系统申请使用USB权限,此过程为异步,建议放在程序启动时调用。 返回0请求权限
        int iReqPermission = BasicOper.dc_AUSB_ReqPermission(context);
        //打开端口，usb模式，打开之前必须确保已经获取到USB权限，返回值为设备句柄号。 //成功返回180
        int devHandle = BasicOper.dc_open("AUSB", context, "", 0);
        if (devHandle > 0) {
            Data.readCardState = true;
            //psam卡初始化
            //第一步设置卡座
            String r_kaZuo = BasicOper.dc_setcpu(2); //2表示sim1卡 3表示sim2卡
            //第二步设置参数
            String r_canShu = BasicOper.dc_setcpupara(2, 0x00, 0x5C);  //2表示sim1卡 3sim2  卡协议编号，0x00表示T0，0x01表示T1，默认为0x00 卡复位波特率编号，0x5C表示9600，0x14表示38400
            //第三步 复位
            String r_fuWei = BasicOper.dc_cpureset_hex();
            Log.v("卡操作", "psam卡复位结果" + r_fuWei);
            return true;
        }
        return false;
    }

    /*指令文档*/
    static String cmd_choose_3f00 = "00A40000023F00"; //选中根目录
    static String cmd_choose_3f01 = "00A40000023F01"; //选中3F01用用
    static String cmd_random8 = "0084000008"; //取随机数
    static String cmd_random4 = "0084000004"; //取随机数
    static String outsideKey = "FFFFFFFFFFFFFFFF"; //默认外部认证秘钥
    static String cmd_outsideVertiy = "0082000008"; //外部认证
    static String cmd_erase = "800E000000"; //擦除卡片
    static String cmd_create_mf0000 = "80E00000073F005001F0FFFF";//创建根目录密匙文件0000
    static String cmd_add_mfLineProtectKey = "80D401000D36F0F0FF3336363636363636363636363636363636";//添加线路保护密匙
    //    static String cmd_add_mfoutsideKey = "80D401001539F0F0AA33FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";//添加外部保护密匙
    static String cmd_add_mfoutsideKey = "80D401000D39F0F0AA33FFFFFFFFFFFFFFFF";//添加外部保护密匙
    static String cmd_create_mf0001 = "80E00001072A0213F000FFFF";//创建0001定长文件  2条记录单长13
    static String cmd_add_mf0001_data = "00E2000C1361114F09A00000000386980701500450424F43";//添加定长记录文件 一条数据
    static String cmd_create_mf0005 = "80E0000507A8001EF0F0FFFF";//创建0005线路保护文件  空间001E
    static String cmd_create_3f01 = "80E03F011138036FF0F095FFFFA00000000386980701";//创建3f01文件 应用

    static String cmd_creat_0000 = "80E00000073F018F95F0FFFF";//创建密匙文件
    static String cmd_add_insideKey = "80D401001534F002000134343434343434343434343434343434";//添加内部密匙 tac key
    static String cmd_add_lineProtectKey = "80D401001536F002FF33";//添加线路保护密匙
    static String cmd_add_pinUnlockKey = "80D401001537F002FF3337373737373737373737373737373737";//添加口令解锁密匙
    static String cmd_add_pinResetKey = "80D401001538F002FF3338383838383838383838383838383838";//添加口令重装密匙
    static String cmd_add_outsideKey = "80D401001539F002443339393939393939393939393939393939";//添加外部认证密匙
    static String cmd_add_consumeKey = "80D40101153EF00200013E013E013E013E013E013E013E013E01";//添加消费密匙
    static String cmd_add_rechargeKey = "80D40101153FF00200013F013F013F013F013F013F013F013F01";//添加充值密匙
    static String cmd_add_takeOutKey = "80D40101153DF00201003D013D013D013D013D013D013D013D01";//添加圈提密匙
    static String cmd_add_overDrawKey = "80D40101153CF00201003C013C013C013C013C013C013C013C01";//添加修改透支额度密匙
    static String cmd_add_pinKey = "80D401000D3AF0EF013312345FFFFFFFFFFF";//添加Pin   口令密匙 12345FFFFFFFFFFF

    static String cmd_creat_0015 = "80E0001507A8001EF0F0FFFF";//创建0015二进制文件  mac线路保护
    static String cmd_creat_0016 = "80E0001607280037F0F0FFFF";//创建0017二进制文件  明文文件空间0037 55个字节
    static String cmd_creat_0018 = "80E00018072E0A17F0EFFFFF";//创建0018循环记录文件文件
    static String cmd_creat_0002 = "80E00002072F0208F000FF18";//创建电子钱包应用
    static String cmd_creat_001A = "80E0001A072C003CF0F0FFFF";//创建复合交易文件  文件空间003c 60个字节
    static String cmd_creat_001A_data = "00E200D405BB03112233";//添加复合交易文件数据
    static String cmd_creat_0001 = "80E00001072F0208F100FF18";//创建电子存折 0001
    //psam卡
    static String cmdps_choose2f01 = "00A40000022f01";
    static String cmd_des_init = "801A260408"; //des初始化
    static String cmd_request_4byte = "00C0000004"; //去返回的8字节数
    static String cmd_write_0005 = "04D6850022"; //去写0005文件
    //开卡
    static String cmd_read_0005 = "00B0850000"; //读0005文件
    static String cmd_read_0015 = "00B0950000"; //读0015文件
    static String cmd_read_0016 = "00B0960000"; //读0016文件
    static String cmd_write_0016 = "00D6960037"; //写0016文件
    static String cmd_write_0015 = "04D6950022"; //去写0015文件


    //卡片清空
    public static void cardReset(BaseActivity mActivity) {
        //射频复位
        String result = BasicOper.dc_reset();
        //1.寻卡
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        L.v("寻卡=" + r_search_card);
        if (!r_search_card.startsWith("0000")) {
            ToastPrint.showText("请重新放置卡片");
            return;
        }
        csn = r_search_card.split("\\|", -1)[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        //2.卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        //2.取随机数
        String r_cmd_random = BasicOper.dc_pro_commandhex(cmd_random8, 7);
        String random8 = r_cmd_random.split("\\|", -1)[1].substring(0, 16);
        //3.随机数加密
        String random8ep = null;
        try {
            random8ep = PBOCUtil.getDes(random8, outsideKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.外部认证
        String r_cmd_outsideVertiy = BasicOper.dc_pro_commandhex(cmd_outsideVertiy + random8ep, 7);
        L.v("外部认证结果=" + r_cmd_outsideVertiy);
        if (!r_cmd_outsideVertiy.endsWith("9000")) {
            ToastPrint.showText("外部认证失败");
        }
        //5.擦除卡片
        String r_cmd_erase = BasicOper.dc_pro_commandhex(cmd_erase, 7);
        L.v("擦除卡片" + r_cmd_erase);
        ToastPrint.showText("卡片擦除成功");

        OkGo.<Object>post(Api.cardReset)
                .params("operateId", "1")
                .params("csn", csn.substring(8, 16))
                .execute(new OkgoCallBack<Object>(Object.class, mActivity) {
                    @Override
                    public void onSuccess(Response<Object> response) {

                    }

                    @Override
                    public void onError(Response<Object> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });
    }


    //卡片初始化  批量由发卡机完成
    public static void cardInit(BaseActivity mActivity) throws Exception {
        //射频复位
        String result = BasicOper.dc_reset();
        //1.寻卡
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        L.v("寻卡=" + r_search_card);
        if (!r_search_card.startsWith("0000")) {
            ToastPrint.showText("请重新放置卡片");
            return;
        }
        csn = r_search_card.split("\\|", -1)[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        //2.卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        //1.判断卡是否有3f01应用
        String r_cmd_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
        L.v("选中3f01=" + r_cmd_choose_3f01);
        if (r_cmd_choose_3f01.endsWith("9000")) {
            ToastPrint.showText("卡片已经初始化");
            return;
        }
        getCardNumber(mActivity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    L.v("卡号=" + cardId);
                    if (cardId == null || cardId.length() != 16) {
                        ToastPrint.showText("卡号错误");
                        return;
                    }
                    //2.取随机数
                    String r_cmd_random = BasicOper.dc_pro_commandhex(cmd_random8, 7);
                    String random8 = r_cmd_random.split("\\|", -1)[1].substring(0, 16);
                    //3.随机数加密
                    String random8ep = PBOCUtil.getDes(random8, outsideKey);
                    //4.外部认证
                    String r_cmd_outsideVertiy = BasicOper.dc_pro_commandhex(cmd_outsideVertiy + random8ep, 7);
                    L.v("外部认证结果=" + r_cmd_outsideVertiy);

                    if (!r_cmd_outsideVertiy.endsWith("9000")) {
                        if (r_cmd_outsideVertiy.endsWith("6A88")) {
                            //未找到密匙 可以向下执行
                        } else {
                            ToastPrint.showText("外部认证失败");
                            return;
                        }
                    }
                    //5.擦除卡片
                    String r_cmd_erase = BasicOper.dc_pro_commandhex(cmd_erase, 7);
                    L.v("擦除卡片" + r_cmd_erase);
                    //6.选中根目录
                    String r_cmd_choose_3f00 = BasicOper.dc_pro_commandhex(cmd_choose_3f00, 7);
                    //7.创建密匙文件
                    String r_cmd_create_mf0000 = BasicOper.dc_pro_commandhex(cmd_create_mf0000, 7);
                    //8.添加线路保护密匙
                    String cmdProtectLinekey = cmd_add_lineProtectKey + PBOCUtil.getDisperseKeyOnce(csn, "36363636363636363636363636363636");
                    String r_cmd_add_mfLineProtectKey = BasicOper.dc_pro_commandhex(cmdProtectLinekey, 7);
                    //9.添加外部保护密匙
                    String r_cmd_add_mfoutsideKey = BasicOper.dc_pro_commandhex(cmd_add_mfoutsideKey, 7);
                    //10创建定长文件
                    String r_cmd_create_mf0001 = BasicOper.dc_pro_commandhex(cmd_create_mf0001, 7);
                    //11.添加定长文件两天记录
                    String r_cmd_add_mf0001_data1 = BasicOper.dc_pro_commandhex(cmd_add_mf0001_data, 7);
                    String r_cmd_add_mf0001_data2 = BasicOper.dc_pro_commandhex(cmd_add_mf0001_data, 7);
                    //11.创建0005文件
                    String r_cmd_create_mf0005 = BasicOper.dc_pro_commandhex(cmd_create_mf0005, 7);
                    L.v("创建0005文件" + r_cmd_create_mf0005);
                    //12.创建3f01应用
                    String r_cmd_create_3f01 = BasicOper.dc_pro_commandhex(cmd_create_3f01, 7);
                    L.v("创建3f01应用" + r_cmd_create_3f01);
                    //13.选中3f01
                    BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
                    //14.创建密匙文件
                    String r_cmd_creat_0000 = BasicOper.dc_pro_commandhex(cmd_creat_0000, 7);
                    L.v("创建密匙文件" + r_cmd_creat_0000);
                    //15.添加内部密匙
                    String r_cmd_add_insideKey = BasicOper.dc_pro_commandhex(cmd_add_insideKey, 7);
                    //16.添加线路保护密匙  mf df的线路保护密匙一样
                    String r_cmd_add_lineProtectKey = BasicOper.dc_pro_commandhex(cmdProtectLinekey, 7);
                    //17.添加口令解锁密匙
                    String r_cmd_add_pinUnlockKey = BasicOper.dc_pro_commandhex(cmd_add_pinUnlockKey, 7);
                    //18.添加口令重装密匙
                    String r_cmd_add_pinResetKey = BasicOper.dc_pro_commandhex(cmd_add_pinResetKey, 7);
                    //19.添加外部认证密匙 mf和df外部认证密匙不一样
                    String r_cmd_add_outsideKey = BasicOper.dc_pro_commandhex(cmd_add_outsideKey, 7);

                    //添加消费密匙 添加充值密匙 后四位为 密匙版本号00 算法标识01
                    String cmd_add_consumeKey = "80D40101153EF0020001" + PBOCUtil.getDisperseKeyOnce(csn, "3E013E013E013E013E013E013E013E01");
                    String cmd_add_rechargeKey = "80D40101153FF0020001" + PBOCUtil.getDisperseKeyOnce(csn, "3F013F013F013F013F013F013F013F01");
                    //20.添加消费密匙
                    String r_cmd_add_consumeKey = BasicOper.dc_pro_commandhex(cmd_add_consumeKey, 7);
                    //21.添加充值密匙
                    String r_cmd_add_rechargeKey = BasicOper.dc_pro_commandhex(cmd_add_rechargeKey, 7);
                    //22.添加圈提密匙
                    String r_cmd_add_takeOutKey = BasicOper.dc_pro_commandhex(cmd_add_takeOutKey, 7);
                    //23.添加修改透支密匙
                    String r_cmd_add_overDrawKey = BasicOper.dc_pro_commandhex(cmd_add_overDrawKey, 7);
                    //24.添加pin口令
                    String r_cmd_add_pinKey = BasicOper.dc_pro_commandhex(cmd_add_pinKey, 7);
                    //25.创建0015二进制文件
                    String r_cmd_creat_0015 = BasicOper.dc_pro_commandhex(cmd_creat_0015, 7);
                    //26.创建0016二进制文件
                    String r_cmd_creat_0016 = BasicOper.dc_pro_commandhex(cmd_creat_0016, 7);
                    L.v("创建0016二进制文件" + r_cmd_creat_0016);
                    //27.创建0018循环记录文件
                    String r_cmd_creat_0018 = BasicOper.dc_pro_commandhex(cmd_creat_0018, 7);
                    L.v("创建0018循环记录文件" + r_cmd_creat_0018);
                    //28.创建电子钱包应用 0002
                    String r_cmd_creat_0002 = BasicOper.dc_pro_commandhex(cmd_creat_0002, 7);
                    L.v("创建电子钱包" + r_cmd_creat_0002 + "");
                    //29.创建复合交易文件001A
                    String r_cmd_creat_001A = BasicOper.dc_pro_commandhex(cmd_creat_001A, 7);
                    L.v("创建复合交易文件001A" + r_cmd_creat_001A);
                    //30.添加复合交易文件 标志为AA 个字节数据
                    cmd_creat_001A_data = "00E200D43CAA3A00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";//添加复合交易文件数据 3C后面长度AA标志3A后面数据长度
                    String r_cmd_creat_001A_data = BasicOper.dc_pro_commandhex(cmd_creat_001A_data, 7);
                    L.v("添加复合交易文件1A" + r_cmd_creat_001A_data);
                    //写入0005发行基本信息文件文件内容
                    //1.选中根目录
                    BasicOper.dc_pro_commandhex(cmd_choose_3f00, 7);
                    //2.取随机数
                    String random4 = BasicOper.dc_pro_commandhex(cmd_random4, 7).split("\\|", -1)[1].substring(0, 8);
                    //3.psam卡计算mac  进入psam应用
                    String r_cmdps_choose2f01 = BasicOper.dc_cpuapdu_hex(cmdps_choose2f01);
                    //4.DES初始化
                    String r_cmd_des_init = BasicOper.dc_cpuapdu_hex(cmd_des_init + csn);
                    //5.des计算
                    Card0005 card0005 = new Card0005();
                    String yyyyMMdd = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
                    card0005.setDate7(yyyyMMdd);
                    card0005.setCardNumber5(cardId);
                    String content = card0005.getStr();
                    String cmd_request_mac = "80FA0500" + "30" + random4 + "00000000" + "04D68500" + "22" + content + "8000000000";
                    String r_cmd_request_mac = BasicOper.dc_cpuapdu_hex(cmd_request_mac);
                    String r_cmd_request_4byte = BasicOper.dc_cpuapdu_hex(cmd_request_4byte);
                    String lineMac = r_cmd_request_4byte.split("\\|", -1)[1].substring(0, 8);
                    L.v("content0005内容"+content);
                    //6.去修改线0005路内容
                    String r_cmd_write_0005 = BasicOper.dc_pro_commandhex(cmd_write_0005 + content + lineMac, 7);
                    L.v("去修改线0005路内容" + r_cmd_write_0005);
                    if (r_cmd_write_0005.endsWith("9000")) {
                        InitCardBean initCardBean = new InitCardBean();
                    }
                    App.tts.speakText("卡片初始化完成");
                } catch (Exception e) {
                    ToastPrint.showText(e.getMessage());
                }
            }

        }, 500);

    }

    //发卡激活卡
    public static void openCard(BaseActivity mActivity, String userMessage, String cardMessage, PostRequest<OpenCardBean> post) {
        //射频复位
        String result = BasicOper.dc_reset();
        //1.寻卡
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        L.v("寻卡=" + r_search_card);
        if (!r_search_card.startsWith("0000")) {
            ToastPrint.showText("请重新放置卡片");
            return;
        }
        csn = r_search_card.split("\\|", -1)[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        //2.卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        String result0005 = BasicOper.dc_pro_commandhex(cmd_read_0005, 7);
        //1.选中3f01文件 返回0015文件内容解析是否开卡
        String r_cmd_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7); //返回0015文件内容
        L.v("选中3f01=" + r_cmd_choose_3f01);
        if (r_cmd_choose_3f01.endsWith("9000")) {
            String choose3f01Result = r_cmd_choose_3f01.split("\\|", -1)[1];
            String choose0015 = choose3f01Result.substring(44, 104);
            Card0015 card0015 = new Card0015();
            card0015.subCard0015(choose0015);
            if (card0015.getCardState6().endsWith("01")) {
                ToastPrint.showText("卡片已经发卡");
                return;
            } else {         //未开卡
                //1.写0016个人信息文件
                cardId = result0005.split("\\|", -1)[1].substring(16, 32);
                L.v("卡号是" + cardId);
                String r_cmd_write_0016 = BasicOper.dc_pro_commandhex(cmd_write_0016 + userMessage, 7); //返回0015文件内容
                L.v("写0016结果" + r_cmd_write_0016);
                //2.请求后台开卡
                post.execute(new OkgoCallBack<OpenCardBean>(OpenCardBean.class, mActivity) {
                    @Override
                    public void onSuccess(Response<OpenCardBean> response) {
                        //3.写0015文件 cardMessage
                        //取随机数
                        String random4 = BasicOper.dc_pro_commandhex(cmd_random4, 7).split("\\|", -1)[1].substring(0, 8);
                        L.v("随机数"+random4);
                        //psam卡计算mac  进入psam应用
                        String r_cmdps_choose2f01 = BasicOper.dc_cpuapdu_hex(cmdps_choose2f01);
                        L.v("选择psam应用"+r_cmdps_choose2f01);
                        //DES初始化
                        String r_cmd_des_init = BasicOper.dc_cpuapdu_hex(cmd_des_init + csn);
                        L.v("des初始化"+r_cmd_des_init);
                        L.v("cardMessage"+cardMessage);
                        String cmd_request_mac = "80FA0500" + "30" + random4 + "00000000" + "04D69500" + "22" + cardMessage + "8000000000";
                        String r_cmd_request_mac = BasicOper.dc_cpuapdu_hex(cmd_request_mac);
                        L.v("计算mac"+r_cmd_request_mac);
                        String r_cmd_request_4byte = BasicOper.dc_cpuapdu_hex(cmd_request_4byte);
                        L.v("求出mac"+r_cmd_request_4byte);
                        String lineMac = r_cmd_request_4byte.split("\\|", -1)[1].substring(0, 8);
                        L.v("求出maclineMac"+lineMac);
                        //4.去修改线0015路内容
                        String r_cmd_write_00015 = BasicOper.dc_pro_commandhex(cmd_write_0015 + cardMessage + lineMac, 7);
                        L.v("发卡结果" + r_cmd_write_00015);
                        App.tts.speakText("发卡成功");
                    }

                    @Override
                    public void onError(Response<OpenCardBean> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });


            }
        } else {
            ToastPrint.showText("卡片未初始化");
        }
    }

    //读卡
    public static void readCard(BaseActivity mActivity, TextView cardNumber, TextView etCardState) {
        //射频复位
        String result = BasicOper.dc_reset();
        //1.寻卡
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        L.v("寻卡=" + r_search_card);
        if (!r_search_card.startsWith("0000")) {
            ToastPrint.showText("请重新放置卡片");
            return;
        }
        csn = r_search_card.split("\\|", -1)[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        //2.卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        //1.判断卡是否有3f01应用
        String r_cmd_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
        L.v("选中3f01=" + r_cmd_choose_3f01);
        if (r_cmd_choose_3f01.endsWith("9000")) {    //读取卡片内容
            String cardState = r_cmd_choose_3f01.split("\\|", -1)[1].substring(60, 62);
            if (cardState.equals("00")) {
                etCardState.setText(CardStateEm.INIT.getName());
            } else if (cardState.equals("01")) {
                etCardState.setText(CardStateEm.ISSUE.getName());
            } else {

            }

        } else {                                     //空卡
            etCardState.setText(CardStateEm.NO_INIT.getName());
        }
        getCardNumber(mActivity, cardNumber);

    }

    private static void getCardNumber(BaseActivity mActivity, TextView cardNumber) {
        OkGo.<GetCardNumberBean>post(Api.getCardNumber)
                .params("operateId", "1")
                .params("csn", csn.substring(8, 16))
                .execute(new OkgoCallBack<GetCardNumberBean>(GetCardNumberBean.class, mActivity) {
                    @Override
                    public void onSuccess(Response<GetCardNumberBean> response) {
                        GetCardNumberBean body = response.body();
                        if (body.getCode() == 0) {
                            cardId = body.getData().getCardNumber();
                            L.v("cardId=" + cardId);
                            cardNumber.setText(cardId);
                            if (cardId == null || cardId.length() != 16) {
                                ToastPrint.showText("卡号错误");
                                return;
                            }
                        } else {
                            ToastPrint.showText(body.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<GetCardNumberBean> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });


    }
    public static String chooseWallet = "00A40000020002";     // 选择电子钱包
    public static String pin = "002000000812345FFFFFFFFFFF";  // 验证口令
    public static String posid = "112233445566";    //终端机编号
    public static String rechargeKey = "3F013F013F013F013F013F013F013F01"; // 圈存的key
    public static String tradeType = "02"; //交易类型
    public static String consumeType = "06";//06普通消费 09复合消费
    public static String tacKey = "34343434343434343434343434343434";    // 验证tac的key 内部密匙

    public static boolean reacharge(Context mContext,String amountString,String date,String csn,String cardId,String cardType) throws Exception {
        //1.选择电子钱包
        String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
        //2.验证口令
        String r_pin = BasicOper.dc_pro_commandhex(pin, 7);
        //3.圈存初始化
        //充值金额
        String amountHex = String.format("%08X", Integer.parseInt(amountString));
        Log.v("卡操作", "充值16进制金额:" + amountHex);
        String recharge_init = "805000020b01" + amountHex + posid;     // 充值初始化
        Log.v("卡操作", "预充值指令:" + recharge_init);
        String r_recharge_init = BasicOper.dc_pro_commandhex(recharge_init, 7);
        String r_recharge_init_result = r_recharge_init.split("\\|", -1)[1];
        // 卡余额
        String balance = r_recharge_init_result.substring(0, 8);
        // 联机计数器
        String cardCnt = r_recharge_init_result.substring(8, 12);
        // 密钥版本
        String keyVersion = r_recharge_init_result.substring(12, 14);
        // 算法标识
        String alglndMark = r_recharge_init_result.substring(14, 16);
        // 随机数
        String random = r_recharge_init_result.substring(16, 24);
        // mac1
        String mac1 = r_recharge_init_result.substring(24, 32);//mac1
        Log.v("卡操作,", "balance=" + balance + "  cardCnt=" + cardCnt + "  keyVersion=" + keyVersion +
                " alglndMark=" + alglndMark + "  random=" + random + "  mac1=" + mac1);

        String inputData = random + cardCnt + "8000";
        rechargeKey = PBOCUtil.getDisperseKeyOnce(csn, "3F013F013F013F013F013F013F013F01");

        String sessionKey = PBOCUtil.encryptECB3Des(inputData, rechargeKey);
        Log.v("卡操作", "inputData:" + inputData + "   sessionKey=" + sessionKey);
        String input1_mac1 = balance + amountHex + tradeType + posid;
        String computer_mac1 = PBOCUtil.getMac2(input1_mac1, sessionKey, "0000000000000000").substring(0, 8);
        Log.v("卡操作", "计算mac1指令:" + input1_mac1);
        Log.v("卡操作", "计算mac1=:" + computer_mac1);

        if (computer_mac1.equals(mac1)) {
            Log.v("卡操作", "验证mac1成功:");
        } else {
            Log.v("卡操作", "验证mac1失败:");
            return false;
        }

        String input2 = amountHex + tradeType + posid + date;
        String mac2 = PBOCUtil.getMac2(input2, sessionKey, "0000000000000000").substring(0, 8);
        Log.v("卡操作", "sessionKey=" + sessionKey + "   input2=" + input2 + "   mac2=" + mac2);
        String recharge = "805200000B" + date + mac2;
        Log.v("卡操作", "预充值指令recharge:" + recharge);

        String r_recharge_tag = BasicOper.dc_pro_commandhex(recharge, 7);
        Log.v("卡操作", "充值结果:" + r_recharge_tag);
        String r_recharge_tag_result = r_recharge_tag.split("\\|", -1)[1].substring(0, 8);

        byte[] bytesTac1 = PBOCUtil.hexString2byte(tacKey.substring(0, 16));
        byte[] bytesTac2 = PBOCUtil.hexString2byte(tacKey.substring(16, 32));
        byte[] bytesXor = PBOCUtil.xOr(bytesTac1, bytesTac2);
        String stringXor = PBOCUtil.byte2hexString(bytesXor);
        Log.v("卡操作", "stringXor:" + stringXor);

        int balanceInt = Integer.parseInt(balance, 16); //余额
        int amountInt = Integer.parseInt(amountString);
        String totalHex = String.format("%08X", balanceInt + amountInt);

        Log.v("卡操作", "金额:" + "balanceInt=" + balanceInt + "  amountInt=" + amountInt +
                "  totalHex:" + totalHex);

        //充值余额  交易序号 充值金额  终端机号 交易日期
        String comandVerTac = totalHex + cardCnt + amountHex + tradeType + posid + date;
        Log.v("卡操作", "验证tac指令:" + comandVerTac);
        String veryTac = PBOCUtil.getMac2(comandVerTac, stringXor, "0000000000000000").substring(0, 8);
        Log.v("卡操作", "验证tac结果:" + veryTac);
        if (r_recharge_tag_result.equals(veryTac)) {
            Log.v("卡操作", "验证tac成功");
            App.tts.speakText("充值成功:");

            //通知后台充值成功
            OkGo.<GetCardNumberBean>post(Api.rechargeSuccess)
                    .params("operateId", "1")
                    .params("deviceId", SPUtils.getInstance().getStationId())
                    .params("cardNumber", cardId)
                    .params("psamNumber", "112233445566")
                    .params("rechargeMoney",amountInt)
                    .params("rechargeBeforeMoney",balanceInt)
                    .params("rechargeAfterMoney",amountInt+balanceInt)
                    .params("rechargeFlow",0)
                    .params("cardTag",cardType)
                    .params("donationAmount",0)
                    .params("cityCode","3301")
                    .params("rechargeTime",date)
                    .params("payType",0)
                    .params("cardCategory",0)
                    .params("tac",veryTac)
                    .params("chargeType",0)
                    .params("correctionId",0)
                    .execute(new OkgoCallBack<GetCardNumberBean>(GetCardNumberBean.class, mContext) {
                        @Override
                        public void onSuccess(Response<GetCardNumberBean> response) {
                            GetCardNumberBean body = response.body();

                        }

                        @Override
                        public void onError(Response<GetCardNumberBean> response) {
                            super.onError(response);
                            ToastPrint.showText(response.getException() + "");
                        }
                    });
            return true;
        } else {
            Log.v("卡操作", "验证tac失败");
            App.tts.speakText("充值失败:");
            return false;
        }
    }



    /**********************网络请求***********************/


    private static void getCardNumber(Context mActivity) {

        L.v("csn=" + csn);
        OkGo.<GetCardNumberBean>post(Api.getCardNumber)
                .params("operateId", "1")
                .params("csn", csn.substring(8, 16))
                .execute(new OkgoCallBack<GetCardNumberBean>(GetCardNumberBean.class, mActivity) {
                    @Override
                    public void onSuccess(Response<GetCardNumberBean> response) {
                        GetCardNumberBean body = response.body();
                        if (body.getCode() == 0) {
                            cardId = body.getData().getCardNumber();
                            L.v("请求卡号结果" + cardId);
                        } else {
                            ToastPrint.showText(body.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<GetCardNumberBean> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });

    }


}
