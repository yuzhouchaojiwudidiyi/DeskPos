package com.wellsun.deskpos.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.decard.NDKMethod.BasicOper;
import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.App;
import com.wellsun.deskpos.base.BaseFragment;
import com.wellsun.deskpos.pboc.PBOCUtil;
import com.wellsun.deskpos.util.D8;

/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public class CardPsamFragment extends BaseFragment {
    private Button btPsamSend;
    private Button btConsume;
    private Button btPsamConsume;

    //发psam卡
    public String cmd_getpasmid = "112233445566";                    //获取终端机号
    public String cmdPs_reset = "800E000008FFFFFFFFFFFFFFFF";          //清空psam卡
    public String cmdPs_creadMf = "80E0000018FFFFFFFFFFFFFFFF0F01315041592E5359532E4444463031";          //创建mf文件
    public String cmdPs_cread0016 = "80E00200070016000F0F0006";          //创建16文件
    public String cmdPs_write0016 = "00D6960006112233445566";              //写0016文件 终端机编号
    public String cmdPs_creat2f01 = "80E00100092F010F002222222222";      //创建2f01文件应用
    public String cmdPs_creatkey = "80E00200070000050F003018";           //创建0000密匙文件
    public String cmdPs_addcosumekey = "80D40000170001220F030FFF3E013E013E013E013E013E013E013E01"; //添加消费密匙  (00版本号  01算法标识  22密匙用途)
    public String cmdPs_addlineKey = "80D40000170400260F030FFF36363636363636363636363636363636"; //添加线路保护密匙  (04版本号 00算法标识 26密匙用途)
    public String cmdPs_addlineEpKey = "80D40000170400280F030FFF36363636363636363636363636363636";//添加线路保护密匙加密密匙  (04版本号  00算法标识  28密匙用途) 添加08 MAC、加密密钥
    public String cmdPs_creat0018 = "80E00200070018000F0F0004";//创建0018二进制文件
    public String cmdPs_creat2f01_finish = "80E00101022F01";//创建2f01结束
    public String cmdPs_creat3f00_finish = "80E00101023F00";//创建3f01结束

    String result = "开始:";
    private TextView tvResult;
    private TextView tvBalance;
    private Button btReadBalance;

    @Override
    public int getLayoutId() {
        return R.layout.card_psam_fragment;
    }

    @Override
    public void initView() {

        btReadBalance = (Button) findViewById(R.id.bt_read_balance);
        btPsamSend = (Button) findViewById(R.id.bt_psam_send);
        btConsume = (Button) findViewById(R.id.bt_consume);
        btPsamConsume = (Button) findViewById(R.id.bt_psam_consume);
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
    }

    @Override
    public void setListener() {
        btReadBalance.setOnClickListener(this);
        btPsamSend.setOnClickListener(this);
        btConsume.setOnClickListener(this);
        btPsamConsume.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_read_balance:
                  tvBalance.setText("余额:"+readBalance());
                break;
            case R.id.bt_psam_send:
                sendPsamCard();
                break;
            case R.id.bt_consume:
                try {
                    consume();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("卡操作",e.toString());
                }
                break;
            case R.id.bt_psam_consume:
                try {
                    consumePsam();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.v("卡操作",e.toString());
                }
                break;
        }

    }

    private void sendPsamCard() {

        String r_cmdPs_reset = BasicOper.dc_cpuapdu_hex(cmdPs_reset);
        result = "清除psam" + r_cmdPs_reset + "\n" + result;
        String r_cmdPs_creadMf = BasicOper.dc_cpuapdu_hex(cmdPs_creadMf);
        result = "创建mf" + r_cmdPs_creadMf + "\n" + result;
        String r_cmdPs_cread0016 = BasicOper.dc_cpuapdu_hex(cmdPs_cread0016);
        result = "创建0016" + r_cmdPs_cread0016 + "\n" + result;
        String r_cmdPs_write0016 = BasicOper.dc_cpuapdu_hex(cmdPs_write0016);
        result = "更新0016" + r_cmdPs_write0016 + "\n" + result;
        String r_cmdPs_creat2f01 = BasicOper.dc_cpuapdu_hex(cmdPs_creat2f01);
        result = "创建2f01" + r_cmdPs_creat2f01 + "\n" + result;
        String r_cmdPs_creatkey = BasicOper.dc_cpuapdu_hex(cmdPs_creatkey);
        result = "创建key文件" + r_cmdPs_creatkey + "\n" + result;
        String r_cmdPs_addcosumekey = BasicOper.dc_cpuapdu_hex(cmdPs_addcosumekey);
        result = "添加消费密匙" + r_cmdPs_addcosumekey + "\n" + result;
        String r_cmdPs_addlineKey = BasicOper.dc_cpuapdu_hex(cmdPs_addlineKey);
        result = "添加线路保护密匙" + r_cmdPs_addlineKey + "\n" + result;
        String r_cmdPs_addlineEpKey = BasicOper.dc_cpuapdu_hex(cmdPs_addlineEpKey);
        result = "添加线路保护密匙和加密" + r_cmdPs_addlineEpKey + "\n" + result;
        String r_cmdPs_creat0018 = BasicOper.dc_cpuapdu_hex(cmdPs_creat0018);
        result = "创建0018" + r_cmdPs_creat0018 + "\n" + result;
        String r_cmdPs_creat2f01_finish = BasicOper.dc_cpuapdu_hex(cmdPs_creat2f01_finish);
        result = "创建结束2f01" + r_cmdPs_creat2f01_finish + "\n" + result;
        String r_cmdPs_creat3f00_finish = BasicOper.dc_cpuapdu_hex(cmdPs_creat3f00_finish);
        result = "创建结束3f00" + r_cmdPs_creat3f00_finish + "\n" + result;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(result);
            }
        });
        App.tts.speakText(" psam卡发卡完成");
    }


    String csn = "";
    String cmdps_choose2f01 = "00A40000022f01";
    String cmd_choose_3f01 = "00A4040009A00000000386980701";//选中3f01文件 应用
    String tradeType = "02"; // 交易类型
    String consumeType = "06";//06普通消费 09复合消费
    public String chooseWallet = "00A40000020002";     // 选择电子钱包
    public String pin = "002000000812345FFFFFFFFFFF";  // 验证口令
    String date = "20230308112233";
    String posid = "112233445566"; //终端机编号
    public static String readWallet = "805C000204";           // 选择电子钱包
    String consumeKey = "3E013E013E013E013E013E013E013E01";    // 消费密匙
    public static String tacKey = "34343434343434343434343434343434";    // 验证tac的key 内部密匙

    String amountString = "2";


    private void consume() throws Exception {
        //获取物理卡号
        String result = BasicOper.dc_reset();
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        Log.v("卡操作", r_search_card);
        String[] rA_r_xunKa = r_search_card.split("\\|", -1);
        csn = rA_r_xunKa[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        Log.v("卡操作", "寻卡结果=" + r_search_card + "     csn=" + csn);
        result = "物理卡号:" + r_search_card + "\n" + result;
        //卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        String r_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
        Log.v("卡操作", "选中3f01=" + r_choose_3f01);

        //1.选择电子钱包
        String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
        //消费金额
        String amountHex = String.format("%08X", Integer.parseInt(amountString));
        //2.消费初始化
        String consumeCommand = "805001020B" + "01" + amountHex + posid; //01密匙标识符
        String r_consume_init = BasicOper.dc_pro_commandhex(consumeCommand, 7);
        String r_consume_init_result = r_consume_init.split("\\|", -1)[1];
        Log.v("卡操作", "消费初始化=" + r_consume_init);
        //消费前的余额
        String balanceHex = r_consume_init_result.substring(0, 8);
        //脱机交易序号
        String cardCnt = r_consume_init_result.substring(8, 12);
        //透支额度
        String overDraw = r_consume_init_result.substring(12, 18);
        // 密钥版本号
        String keyVersion = r_consume_init_result.substring(18, 20);
        // 算法标识
        String alglndMark = r_consume_init_result.substring(20, 22);
        // 随机数
        String random = r_consume_init_result.substring(22, 30);
        Log.v("卡操作,", "balance=" + balanceHex + "  cardCnt=" + cardCnt + "  keyVersion=" + keyVersion +
                " alglndMark=" + alglndMark + "  random=" + random);

        //求过程密匙sessionKey
        String inputData = random + cardCnt + "0001";   //0001为终端机交易序号后四位
        consumeKey = PBOCUtil.getDisperseKeyOnce(csn, "3E013E013E013E013E013E013E013E01");
        String sessionKey = PBOCUtil.encryptECB3Des(inputData, consumeKey);
        Log.v("卡操作", "consumeKey=" + consumeKey);
        Log.v("卡操作", "inputData=" + inputData);
        Log.v("卡操作", "sessionKey=" + sessionKey);
        String input1_mac1 = amountHex + consumeType + posid + date;
        Log.v("卡操作", "求mac1指令=" + input1_mac1);
        String computer_mac1 = PBOCUtil.getMac2(input1_mac1, sessionKey, "0000000000000000").substring(0, 8);
        Log.v("卡操作", "computer_mac1=" + computer_mac1);
        String consumeCommand1 = "805401000F" + "00000001" + date + computer_mac1;  //00000001终端序号终端机产生
        Log.v("卡操作", "消费指令=" + consumeCommand1);
        String r_consumeCommand1 = BasicOper.dc_pro_commandhex(consumeCommand1, 7);
        Log.v("卡操作", "消费r_consumeCommand1=" + r_consumeCommand1);
        String r_consumeCommand1_tac_mac2 = r_consumeCommand1.split("\\|", -1)[1].substring(0, 16);
        Log.v("卡操作", "tac和mac=" + r_consumeCommand1_tac_mac2);

        String tac = r_consumeCommand1_tac_mac2.substring(0, 8);
        String mac2 = r_consumeCommand1_tac_mac2.substring(8, 16);
        //效验mac2
        String inputData1 = amountHex;
        String veryMac2 = PBOCUtil.getMac2(inputData1, sessionKey, "0000000000000000").substring(0, 8);
        if (veryMac2.equals(mac2)) {
            Log.v("卡操作", "mac2效验成功");
        } else {
            Log.v("卡操作", "mac2效验失败");
        }

        //效验tac码
        //计算xor
        Log.v("卡操作", "计算xor consumeKey="+consumeKey);

        byte[] bytesConsume1 = PBOCUtil.hexString2byte(tacKey.substring(0, 16));
        byte[] bytesConsume2 = PBOCUtil.hexString2byte(tacKey.substring(16, 32));
        byte[] bytesXor = PBOCUtil.xOr(bytesConsume1, bytesConsume2);
        String stringXor = PBOCUtil.byte2hexString(bytesXor);

        String veryTacCommand = amountHex + consumeType + posid + "00000001" + date; //0001为终端机交易序号后四位 前面补齐共8位
        String veryTac = PBOCUtil.getMac2(veryTacCommand, stringXor, "0000000000000000").substring(0, 8);
        Log.v("卡操作","veryTac="+veryTac);
        if (tac.equals(veryTac)) {
            Log.v("卡操作", "tac2效验成功");
            App.tts.speakText("消费成功:");
            tvBalance.setText("余额:"+readBalance());
        } else {
            Log.v("卡操作", "tac2效验失败");
            App.tts.speakText("消费失败:");
        }
        tvResult.setText(result);
    }


    private void consumePsam() throws Exception {
        //获取物理卡号
        String result = BasicOper.dc_reset();
        String r_search_card = BasicOper.dc_card_n_hex(0x01);
        Log.v("卡操作", r_search_card);
        String[] rA_r_xunKa = r_search_card.split("\\|", -1);
        csn = rA_r_xunKa[1].substring(0, 8);
        csn = String.format("%16s", csn).replace(" ", "0"); //前面补零到16位
        Log.v("卡操作", "寻卡结果=" + r_search_card + "     csn=" + csn);
        result = "物理卡号:" + r_search_card + "\n" + result;
        //卡片复位
        String r_fuWei = BasicOper.dc_pro_resethex();
        String r_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
        Log.v("卡操作", "选中3f01=" + r_choose_3f01);
        //1.选择电子钱包
        String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
        Log.v("卡操作", "选择电子钱包结果=" + r_choose_wallet);
        String amountString = "2";
//        App.tts.speakText("消费金额:" + amountString + "元");
        //消费金额
        String amountHex = String.format("%08X", Integer.parseInt(amountString));
        //2.消费初始化
        String consumeCommand = "805001020B" + "01" + amountHex + posid; //01密匙标识符
        String r_consume_init = BasicOper.dc_pro_commandhex(consumeCommand, 7);
        String r_consume_init_result = r_consume_init.split("\\|", -1)[1];
        Log.v("卡操作", "消费初始化=" + r_consume_init);
        //消费前的余额
        String balanceHex = r_consume_init_result.substring(0, 8);
        //脱机交易序号
        String cardCnt = r_consume_init_result.substring(8, 12);
        //透支额度
        String overDraw = r_consume_init_result.substring(12, 18);
        // 密钥版本号
        String keyVersion = r_consume_init_result.substring(18, 20);
        // 算法标识
        String alglndMark = r_consume_init_result.substring(20, 22);
        // 随机数
        String random = r_consume_init_result.substring(22, 30);
        Log.v("卡操作,", "balance=" + balanceHex + "  cardCnt=" + cardCnt + "  keyVersion=" + keyVersion +
                " alglndMark=" + alglndMark + "  random=" + random);

        String r_cmdps_choose2f01 = BasicOper.dc_cpuapdu_hex(cmdps_choose2f01);
        result = "选中2f01" + cmdps_choose2f01 + "\n" + result;
        Log.v("卡操作", "r_cmdps_choose2f01=" + r_cmdps_choose2f01);
        String cmdPsamMac1 = "80700000" + "1C" + random + cardCnt + amountHex + "06" + date + keyVersion + alglndMark + csn;
        Log.v("卡操作", "psam卡求mac1指令=" + cmdPsamMac1);
        if (false) {
            return;
        }
        String r_psamMac1 = BasicOper.dc_cpuapdu_hex(cmdPsamMac1);
        Log.v("卡操作", "r_psamMac1=" + r_psamMac1);
        String r_trade_Mac1 = BasicOper.dc_cpuapdu_hex("00C0000008");
        Log.v("卡操作", "求终端交易序号和Mac1=" + r_trade_Mac1);

        String r_trade_Mac1_Rsult = r_trade_Mac1.split("\\|", -1)[1];
        result = "求终端交易序号和Mac1" + r_psamMac1 + "\n" + result;
        String tradeNumber = r_trade_Mac1_Rsult.substring(0, 8);
        String psamMac1 = r_trade_Mac1_Rsult.substring(8, 16);
        String consumeCommand1 = "805401000F" + tradeNumber + date + psamMac1;     //tradeNumber终端序号终端机产生
        Log.v("卡操作", "消费指令=" + consumeCommand1);
        String r_consumeCommand1 = BasicOper.dc_pro_commandhex(consumeCommand1, 7);
        Log.v("卡操作", "消费r_consumeCommand1=" + r_consumeCommand1);
        String r_consumeCommand1_tac_mac2 = r_consumeCommand1.split("\\|", -1)[1].substring(0, 16);
        String tac = r_consumeCommand1_tac_mac2.substring(0, 8);
        String mac2 = r_consumeCommand1_tac_mac2.substring(8, 16);
        String veryMac2 = BasicOper.dc_cpuapdu_hex("8072000004" + mac2);

        Log.v("卡操作", "效验mac2=" + veryMac2);
        Log.v("卡操作", "消费成功");
        Log.v("卡操作", "消费成功");
        App.tts.speakText("消费成功");
        tvResult.setText(result);
        tvBalance.setText("余额:"+readBalance());

    }


    //读取余额
    public  String readBalance() {
        //射频复位
        String result = BasicOper.dc_reset();
        String r_xunKa = BasicOper.dc_card_hex(0x01);
        String r_fuWei = BasicOper.dc_pro_resethex();
        String r_choose_3f01 = BasicOper.dc_pro_commandhex(cmd_choose_3f01, 7);
        String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
        String r_read_wallet = BasicOper.dc_pro_commandhex(readWallet, 7);
        String[] rA_readWallet = r_read_wallet.split("\\|", -1);
        String cardBalanceHex = rA_readWallet[1].substring(0, 8);
        int cardBalanceInt = Integer.parseInt(cardBalanceHex, 16);
        return cardBalanceInt + "";
    }

}
