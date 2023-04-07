package com.wellsun.deskpos.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.decard.NDKMethod.BasicOper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.Api;
import com.wellsun.deskpos.base.BaseFragment;
import com.wellsun.deskpos.base.OkgoCallBack;
import com.wellsun.deskpos.bean.CardUserMessage;
import com.wellsun.deskpos.bean.LoginBean;
import com.wellsun.deskpos.em.CardStateEm;
import com.wellsun.deskpos.util.D8;
import com.wellsun.deskpos.util.L;
import com.wellsun.deskpos.util.ToastPrint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public class CardRechargeFragment extends BaseFragment {
    private RelativeLayout rlReadCard;
    private ImageView ivCardRead;
    private TextView tvCardRead;
    private RelativeLayout rlCardRecharge;
    private ImageView ivCardSend;
    private TextView tvCardSend;
    private TextView tvCardNumber;
    private TextView tvCardState;
    private TextView tvCardDate;
    private TextView tvCardType;
    private TextView tvCardBalance;
    private TextView tvCardFree;
    private TextView etCardNumber;
    private TextView etCardState;
    private TextView etCardDate;
    private TextView etCardType;
    private TextView etCardBalance;
    private TextView etCardFree;
    private TextView tvUserIdnumber;
    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserSex;
    private TextView tvUserBirthday;
    private EditText etUserIdnumber;
    private EditText etUserName;
    private EditText etUserPhone;
    private FrameLayout flSex;
    private TextView etUserSex;
    private FrameLayout flDate;
    private TextView etUserBirthday;
    private TextView tvUserAddress;
    private EditText etUserAddress;
    private TextView tvRechargeType;
    private FrameLayout flRechargeType;
    private TextView etRechargeType;
    private TextView tvRechargeAmount;
    private EditText etRechargeAmount;
    private RelativeLayout rlCardClear;

    @Override
    public int getLayoutId() {
        return R.layout.card_recharge_fragment;
    }

    @Override
    public void initView() {

        rlReadCard = (RelativeLayout) findViewById(R.id.rl_read_card);
        ivCardRead = (ImageView) findViewById(R.id.iv_card_read);
        tvCardRead = (TextView) findViewById(R.id.tv_card_read);
        rlCardRecharge = (RelativeLayout) findViewById(R.id.rl_card_recharge);
        ivCardSend = (ImageView) findViewById(R.id.iv_card_send);
        tvCardSend = (TextView) findViewById(R.id.tv_card_send);
        tvCardNumber = (TextView) findViewById(R.id.tv_card_number);
        tvCardState = (TextView) findViewById(R.id.tv_card_state);
        tvCardDate = (TextView) findViewById(R.id.tv_card_date);
        tvCardType = (TextView) findViewById(R.id.tv_card_type);
        tvCardBalance = (TextView) findViewById(R.id.tv_card_balance);
        tvCardFree = (TextView) findViewById(R.id.tv_card_free);
        etCardNumber = (TextView) findViewById(R.id.et_card_number);
        etCardState = (TextView) findViewById(R.id.et_card_state);
        etCardDate = (TextView) findViewById(R.id.et_card_date);
        etCardType = (TextView) findViewById(R.id.et_card_type);
        etCardBalance = (TextView) findViewById(R.id.et_card_balance);
        etCardFree = (TextView) findViewById(R.id.et_card_free);
        tvUserIdnumber = (TextView) findViewById(R.id.tv_user_idnumber);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvUserPhone = (TextView) findViewById(R.id.tv_user_phone);
        tvUserSex = (TextView) findViewById(R.id.tv_user_sex);
        tvUserBirthday = (TextView) findViewById(R.id.tv_user_birthday);
        etUserIdnumber = (EditText) findViewById(R.id.et_user_idnumber);
        etUserName = (EditText) findViewById(R.id.et_user_name);
        etUserPhone = (EditText) findViewById(R.id.et_user_phone);
        flSex = (FrameLayout) findViewById(R.id.fl_sex);
        etUserSex = (TextView) findViewById(R.id.et_user_sex);
        flDate = (FrameLayout) findViewById(R.id.fl_date);
        etUserBirthday = (TextView) findViewById(R.id.et_user_birthday);
        tvUserAddress = (TextView) findViewById(R.id.tv_user_address);
        etUserAddress = (EditText) findViewById(R.id.et_user_address);
        tvRechargeType = (TextView) findViewById(R.id.tv_recharge_type);
        flRechargeType = (FrameLayout) findViewById(R.id.fl_recharge_type);
        etRechargeType = (TextView) findViewById(R.id.et_recharge_type);
        tvRechargeAmount = (TextView) findViewById(R.id.tv_recharge_amount);
        etRechargeAmount = (EditText) findViewById(R.id.et_recharge_amount);
        rlCardClear = (RelativeLayout) findViewById(R.id.rl_read_clear);
    }

    @Override
    public void setListener() {
        rlReadCard.setOnClickListener(this);
        rlCardRecharge.setOnClickListener(this);
        flRechargeType.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_read_clear:
                clear();
                break;
            case R.id.fl_recharge_type:
                chooseRechargeType();
                break;
            case R.id.rl_read_card:
                readCard();
                break;
            case R.id.rl_card_recharge:
                recharge();
                break;
        }

    }

    private void recharge() {
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
        String r_cmd_choose_3f01 = BasicOper.dc_pro_commandhex("00A40000023F01", 7);
        L.v("选中3f01=" + r_cmd_choose_3f01);
        if (r_cmd_choose_3f01.endsWith("9000")) {    //读取卡片内容
            String cardState = r_cmd_choose_3f01.split("\\|", -1)[1].substring(60, 62);
            if (cardState.equals("00")) {
                etCardState.setText(CardStateEm.INIT.getName());
                ToastPrint.showText("未初始化");
            } else if (cardState.equals("01")) {
                etCardState.setText(CardStateEm.ISSUE.getName());
                //去充值
                String cardId = r_cmd_choose_3f01.split("\\|", -1)[1].substring(68, 84);
                String etCardId = etCardNumber.getText().toString().trim();
                L.v("cardId" + cardId + "   etCardId" + etCardId);

                if (TextUtils.isEmpty(cardId) || !cardId.equals(etCardId)) {
                    ToastPrint.showText("卡号异常,请重新读卡");
                    return;
                }
                String amount = etRechargeAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amount)) {
                    ToastPrint.showText("请检查充值金额");
                    return;
                }
                String yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();
                try {
                    String cardType = etCardType.getText().toString().trim();
                    if (cardType.equals("记名卡")) {
                        cardType ="80";
                    } else {
                        cardType ="88";
                    }
                    boolean bRecharge = D8.reacharge(mActivity, amount, yyyyMMddHHmmss, csn, cardId, cardType);
                    if (bRecharge) {
                        String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
                        String r_read_wallet = BasicOper.dc_pro_commandhex(readWallet, 7);
                        String[] rA_readWallet = r_read_wallet.split("\\|", -1);
                        String cardBalanceHex = rA_readWallet[1].substring(0, 8);
                        int cardBalanceInt = Integer.parseInt(cardBalanceHex, 16);
                        etCardBalance.setText(cardBalanceInt + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    L.v("充值异常" + e.getMessage());
                }
            } else {

            }

        } else {                                     //空卡
            etCardState.setText(CardStateEm.NO_INIT.getName());
            ToastPrint.showText("空卡");
        }

    }

    String csn = "";
    public static String chooseWallet = "00A40000020002";     // 选择电子钱包
    public static String readWallet = "805C000204";           // 选择电子钱包

    private void readCard() {
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
        String r_cmd_choose_3f01 = BasicOper.dc_pro_commandhex("00A40000023F01", 7);
        L.v("选中3f01=" + r_cmd_choose_3f01);
        if (r_cmd_choose_3f01.endsWith("9000")) {    //读取卡片内容
            String cardState = r_cmd_choose_3f01.split("\\|", -1)[1].substring(60, 62);
            if (cardState.equals("00")) {
                etCardState.setText(CardStateEm.INIT.getName());
            } else if (cardState.equals("01")) {
                etCardState.setText(CardStateEm.ISSUE.getName());
                String r_choose_wallet = BasicOper.dc_pro_commandhex(chooseWallet, 7);
                String r_read_wallet = BasicOper.dc_pro_commandhex(readWallet, 7);
                String[] rA_readWallet = r_read_wallet.split("\\|", -1);
                String cardBalanceHex = rA_readWallet[1].substring(0, 8);
                int cardBalanceInt = Integer.parseInt(cardBalanceHex, 16);
                etCardBalance.setText(cardBalanceInt + "");
                //去请求卡信息
                getCardMesage();
            } else {

            }

        } else {                                     //空卡
            etCardState.setText(CardStateEm.NO_INIT.getName());
        }

    }

    private void getCardMesage() {

        OkGo.<CardUserMessage>post(Api.getCardMessage)
                .params("operateId", "1")
                .params("csn", csn.substring(8, 16))
                .execute(new OkgoCallBack<CardUserMessage>(CardUserMessage.class, mActivity) {
                    @Override
                    public void onSuccess(Response<CardUserMessage> response) {
                        CardUserMessage body = response.body();
                        int code = body.getCode();
                        if (code == 0) {
                            CardUserMessage.DataBean data = body.getData();
                            etCardNumber.setText(data.getCardNumber() + "");
                            etCardDate.setText(data.getSendCardDate() + "");
                            if (data.getCardType().equals("80")) {
                                etCardType.setText("记名卡");
                            } else if (data.getCardType().equals("88")) {
                                etCardType.setText("匿名卡");
                            }
                            etCardFree.setText(data.getDeposit() + "");
                            etUserIdnumber.setText(data.getIdNumber() + "");
                            etUserName.setText(data.getName() + "");
                            etUserPhone.setText(data.getPhoneNum() + "");
                            if (data.getSex().equals("0")) {
                                etUserSex.setText("女");
                            } else if (data.getSex().equals("1")) {
                                etUserSex.setText("男");
                            }
                            etUserBirthday.setText(data.getBirthday() + "");
                            etUserAddress.setText(data.getAddress() + "");

                        } else {
                            ToastPrint.showText(body.getMsg() + "");
                        }
                    }

                    @Override
                    public void onError(Response<CardUserMessage> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });
    }

    private void clear() {
        etCardNumber.setText("");
        etCardState.setText("");
        etCardDate.setText("");
        etCardType.setText("");
        etCardBalance.setText("");
        etCardFree.setText("");
        etUserIdnumber.setText("");
        etUserName.setText("");
        etUserPhone.setText("");
        etUserSex.setText("");
        etUserBirthday.setText("");
        etUserAddress.setText("");
        etRechargeType.setText("");
        etRechargeAmount.setText("");
    }

    private void chooseRechargeType() {
        View viewRechargeType = mInflater.inflate(R.layout.item_recharge_type, null);
        PopupWindow popupWindow = new PopupWindow(viewRechargeType, 240, 120);//参数为1.View 2.宽度 3.高度
        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        popupWindow.showAsDropDown(etRechargeType);
        viewRechargeType.findViewById(R.id.tv_cash_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etRechargeType.setText("现金");
                popupWindow.dismiss();
            }
        });
        viewRechargeType.findViewById(R.id.tv_third_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etRechargeType.setText("现金");
                popupWindow.dismiss();
            }
        });

    }
}
