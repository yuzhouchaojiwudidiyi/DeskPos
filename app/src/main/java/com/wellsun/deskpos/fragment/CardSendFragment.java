package com.wellsun.deskpos.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.Api;
import com.wellsun.deskpos.base.BaseDialog;
import com.wellsun.deskpos.base.BaseFragment;
import com.wellsun.deskpos.base.OkgoCallBack;
import com.wellsun.deskpos.bean.GetCardNumberBean;
import com.wellsun.deskpos.bean.OpenCardBean;
import com.wellsun.deskpos.cardbean.Card0015;
import com.wellsun.deskpos.cardbean.Card0016;
import com.wellsun.deskpos.util.D8;
import com.wellsun.deskpos.util.Hex;
import com.wellsun.deskpos.util.L;
import com.wellsun.deskpos.util.SPUtils;
import com.wellsun.deskpos.util.ToastPrint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public class CardSendFragment extends BaseFragment {

    private TextView tvCardType;
    private RelativeLayout rlReadClear;
    private ImageView ivCardClear;
    private TextView tvCardClear;
    private RelativeLayout rlReadCard;
    private ImageView ivCardRead;
    private TextView tvCardRead;
    private ImageView ivCardInit;
    private TextView tvCardInit;
    private ImageView ivCardSend;
    private TextView tvCardSend;
    private TextView tvCardNumber;
    private TextView tvCardState;
    private TextView tvCardDate;
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
    private TextView etUserSex;
    private TextView etUserBirthday;
    private TextView tvUserAddress;
    private EditText etUserAddress;
    private RelativeLayout rlCardInit;
    private RelativeLayout rlCardSend;
    private FrameLayout flDate;
    private FrameLayout flSex;
    private Button bt_reseat;

    @Override
    public int getLayoutId() {
        return R.layout.card_send_fragment;
    }

    @Override
    public void initView() {
        rlReadClear = (RelativeLayout) findViewById(R.id.rl_read_clear);
        rlCardInit = (RelativeLayout) findViewById(R.id.rl_card_init);
        rlCardSend = (RelativeLayout) findViewById(R.id.rl_card_send);
        ivCardClear = (ImageView) findViewById(R.id.iv_card_clear);
        tvCardClear = (TextView) findViewById(R.id.tv_card_clear);
        rlReadCard = (RelativeLayout) findViewById(R.id.rl_read_card);
        ivCardRead = (ImageView) findViewById(R.id.iv_card_read);
        tvCardRead = (TextView) findViewById(R.id.tv_card_read);
        ivCardInit = (ImageView) findViewById(R.id.iv_card_init);
        tvCardInit = (TextView) findViewById(R.id.tv_card_init);
        ivCardSend = (ImageView) findViewById(R.id.iv_card_send);
        tvCardSend = (TextView) findViewById(R.id.tv_card_send);
        tvCardNumber = (TextView) findViewById(R.id.tv_card_number);
        tvCardState = (TextView) findViewById(R.id.tv_card_state);
        tvCardDate = (TextView) findViewById(R.id.tv_card_date);
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
        etUserSex = (TextView) findViewById(R.id.et_user_sex);
        etUserBirthday = (TextView) findViewById(R.id.et_user_birthday);
        tvUserAddress = (TextView) findViewById(R.id.tv_user_address);
        etUserAddress = (EditText) findViewById(R.id.et_user_address);
        flSex = (FrameLayout) mView.findViewById(R.id.fl_sex);
        flDate = (FrameLayout) findViewById(R.id.fl_date);
        flDate = (FrameLayout) findViewById(R.id.fl_date);
        bt_reseat = (Button) findViewById(R.id.bt_reseat);

        etCardType.setText("匿名卡");
        etCardFree.setText("50");

    }


    @Override
    public void setListener() {
        rlReadCard.setOnClickListener(this);
        rlReadClear.setOnClickListener(this);
        rlCardInit.setOnClickListener(this);
        rlCardSend.setOnClickListener(this);
        etCardType.setOnClickListener(this);
        etCardType.setOnClickListener(this);
        flSex.setOnClickListener(this);
        flDate.setOnClickListener(this);
        bt_reseat.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_read_clear://清空
                clearLayout();
                break;
            case R.id.rl_read_card: //读卡
                readCard();
                break;
            case R.id.rl_card_init://卡片初始化
                initCard();
                break;
            case R.id.rl_card_send: //发卡
                activateCard();
                break;
            case R.id.et_card_type: //选择卡类型
                chooseCardType();
                break;
            case R.id.fl_sex: //选择性别
                chooseSex();
                break;
            case R.id.fl_date: //选择生日
                chooseBirthday();
                break;
            case R.id.bt_reseat:
                D8.cardReset(mActivity);
                break;
        }


    }


    //读卡
    private void readCard() {
        String yyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString();
        etCardDate.setText(yyyyMMdd);
        etCardFree.setText("50");

        D8.readCard(mActivity, etCardNumber, etCardState);
    }

    private void activateCard() {
        String idnumber = etUserIdnumber.getText().toString().trim();
        String idnumberHex = Hex.stringToHex(idnumber);
        idnumberHex = String.format("%64s", idnumberHex).replace("  ", "30"); //前面补零到16位

        String userName = etUserName.getText().toString().trim();
        String userNameHex = Hex.stringToHex(userName);
        userNameHex = String.format("%40s", userNameHex).replace("  ", "30"); //前面补零到16位

        //0016内容
        Card0016 card0016 = new Card0016();
        card0016.setUserId4(idnumberHex);
        card0016.setUserName3(userNameHex);
        String userMessage = card0016.getStr();
        L.v("用户信息" + userMessage);

        //检查输入参数
        String cardid = etCardNumber.getText().toString().trim();
        String cardType = etCardType.getText().toString().trim();
        String phone = etUserPhone.getText().toString().trim();
        String sex = etUserSex.getText().toString().trim();
        String birthday = etUserBirthday.getText().toString().trim();
        String address = etUserAddress.getText().toString().trim();
        String cardDate = etCardDate.getText().toString().trim();
        if (TextUtils.isEmpty(cardid)) {
            ToastPrint.showText("请检查卡卡号");
            return;
        }
        if (TextUtils.isEmpty(cardType)) {
            ToastPrint.showText("请检查卡类型");
            return;
        }
        if (TextUtils.isEmpty(cardDate)) {
            ToastPrint.showText("请检查卡发卡日期");
            return;
        }


        cardid = String.format("%16s", cardid).replace(" ", "0"); //前面补零到16位
        if (cardType.equals("记名卡")) {

            if (TextUtils.isEmpty(idnumber) || TextUtils.isEmpty(userName)) {
                ToastPrint.showText("请检查姓名或证件号");
                return;
            }
            if (TextUtils.isEmpty(phone)) {
                ToastPrint.showText("请检查电话号");
                return;
            }
            if (TextUtils.isEmpty(sex) || TextUtils.isEmpty(birthday)) {
                ToastPrint.showText("请检查性别或生日");
                return;
            }
            if (TextUtils.isEmpty(address)) {
                ToastPrint.showText("请检查地址");
                return;
            }
        }

        PostRequest<OpenCardBean> post = OkGo.<OpenCardBean>post(Api.openCard);
        post.params("operateId", "1");
        post.params("deviceId", SPUtils.getInstance().getStationId());
        post.params("sendCardDate",cardDate );
        Card0015 card0015 = new Card0015();
        if (cardType.endsWith("记名卡")) {
            post.params("cardType", "80");
            card0015.setCardTypeFirst12("80");
        } else if (cardType.equals("匿名卡")) {
            post.params("cardType", "88");
            card0015.setCardTypeFirst12("88");
        }
        post.params("deposit", etCardFree.getText().toString().trim());
        post.params("name", userName);
        post.params("phoneNum", phone);
        if (sex.endsWith("男")) {
            post.params("sex", "1");
        } else if (sex.equals("女")) {
            post.params("sex", "0");
        }
        post.params("birthday", birthday);
        post.params("address", address);
        post.params("idNumber", idnumber);
        post.params("cardNumber", cardid);

        String yyyyMMddStart = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();


        card0015.setCardFee5("32"); //押金
        card0015.setCardNumber9(cardid);
        card0015.setCardState6("01");
        card0015.setStartDate10(yyyyMMddStart);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currenYear = calendar.get(Calendar.YEAR);
        calendar.set(Calendar.YEAR, currenYear + 2);
        Date newDate = new Date(calendar.getTimeInMillis());
        String yyyyMMddEnd = new SimpleDateFormat("yyyyMMdd").format(newDate);
        card0015.setEndDate11(yyyyMMddEnd);
        L.v("0015=" + card0015.getStr());
        L.v("0016=" + userMessage);
        D8.openCard(mActivity, userMessage, card0015.getStr(), post);
    }

    //卡片初始化发 卡机的步骤建卡结构 写秘钥
    private void initCard() {
        String cardNumber = etCardNumber.getText().toString();
        if (TextUtils.isEmpty(cardNumber)){
            ToastPrint.showText("请先读卡");
            return;
        }
        try {
            D8.cardInit(mActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearLayout() {
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
    }

    private void chooseCardType() {
        View viewCardType = mInflater.inflate(R.layout.item_cardtype, null);
        PopupWindow popupWindow = new PopupWindow(viewCardType, 240, 120);//参数为1.View 2.宽度 3.高度
        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        popupWindow.showAsDropDown(etCardType);
        viewCardType.findViewById(R.id.tv_nomal_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCardType.setText("记名卡");
                popupWindow.dismiss();
            }
        });
        viewCardType.findViewById(R.id.tv_noregister_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCardType.setText("匿名卡");
                etUserIdnumber.setText("");
                etUserName.setText("");
                etUserPhone.setText("");
                etUserSex.setText("");
                etUserBirthday.setText("");
                etUserAddress.setText("");
                popupWindow.dismiss();
            }
        });


    }

    public void chooseSex() {
        View viewSex = mInflater.inflate(R.layout.item_sex, null);
        PopupWindow popupWindow = new PopupWindow(viewSex, 240, 120);//参数为1.View 2.宽度 3.高度
        popupWindow.setOutsideTouchable(true);//设置点击外部区域可以取消popupWindow
        popupWindow.showAsDropDown(etUserSex);
        viewSex.findViewById(R.id.tv_sex_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserSex.setText("男");
                popupWindow.dismiss();
            }
        });
        viewSex.findViewById(R.id.tv_sex_woman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUserSex.setText("女");
                popupWindow.dismiss();
            }
        });

    }

    public void chooseBirthday() {
        BaseDialog dateDialog = new BaseDialog(mActivity, R.layout.item_choose_date);
        dateDialog.show();
        DatePicker datePicker = dateDialog.findViewById(R.id.datePicker);
        TextView tvSure = dateDialog.findViewById(R.id.tv_sure_choose_date);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                etUserBirthday.setText(year + "-" + month + "-" + dayOfMonth);
                dateDialog.dismissDialog();
            }
        });

    }
}
