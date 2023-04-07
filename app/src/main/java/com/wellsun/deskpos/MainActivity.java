package com.wellsun.deskpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wellsun.deskpos.activity.TestActivity;
import com.wellsun.deskpos.base.BaseActivity;
import com.wellsun.deskpos.fragment.CardChangeFragment;
import com.wellsun.deskpos.fragment.CardDetailFragment;
import com.wellsun.deskpos.fragment.CardLockFragment;
import com.wellsun.deskpos.fragment.CardPsamFragment;
import com.wellsun.deskpos.fragment.CardQrFragment;
import com.wellsun.deskpos.fragment.CardRechargeCancleFragment;
import com.wellsun.deskpos.fragment.CardRechargeFragment;
import com.wellsun.deskpos.fragment.CardSendFragment;
import com.wellsun.deskpos.util.D8;
import com.wellsun.deskpos.util.FragmentManagerHelper;

import java.util.ArrayList;

import me.yokeyword.fragmentation.ISupportFragment;

public class MainActivity extends BaseActivity {
    private android.widget.LinearLayout llCardSend;
    private android.widget.TextView tvCardSend;
    private android.widget.LinearLayout llCardRecharge;
    private android.widget.TextView tvCardRecharge;
    private android.widget.LinearLayout llCardRechargeCancle;
    private android.widget.TextView tvCardRechargeCancle;
    private android.widget.LinearLayout llCardDetail;
    private android.widget.TextView tvCardDetail;
    private android.widget.LinearLayout llCardQr;
    private android.widget.TextView tvCardQr;
    private android.widget.LinearLayout llCardLock;
    private android.widget.TextView tvCardLock;
    private android.widget.LinearLayout llCardChange;
    private android.widget.TextView tvCardChange;
    private android.widget.FrameLayout flContainer;
    ArrayList<View> moduleList = new ArrayList<>();
    private CardSendFragment cardSendFragment;
    private CardRechargeFragment cardRechargeFragment;
    private CardRechargeCancleFragment cardRechargeCancleFragment;
    private CardDetailFragment cardDetailFragment;
    private CardQrFragment cardQrFragment;
    private CardLockFragment cardLockFragment;
    private CardChangeFragment cardChangeFragment;
    private FragmentManagerHelper fragmentManagerHelper;
    private LinearLayout llCardPsam;
    private TextView tvCardPsam;
    private CardPsamFragment cardPsamFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        llCardSend = (LinearLayout) findViewById(R.id.ll_card_send);
        tvCardSend = (TextView) findViewById(R.id.tv_card_send);
        llCardRecharge = (LinearLayout) findViewById(R.id.ll_card_recharge);
        tvCardRecharge = (TextView) findViewById(R.id.tv_card_recharge);
        llCardRechargeCancle = (LinearLayout) findViewById(R.id.ll_card_recharge_cancle);
        tvCardRechargeCancle = (TextView) findViewById(R.id.tv_card_recharge_cancle);
        llCardDetail = (LinearLayout) findViewById(R.id.ll_card_detail);
        tvCardDetail = (TextView) findViewById(R.id.tv_card_detail);
        llCardQr = (LinearLayout) findViewById(R.id.ll_card_qr);
        tvCardQr = (TextView) findViewById(R.id.tv_card_qr);
        llCardLock = (LinearLayout) findViewById(R.id.ll_card_lock);
        tvCardLock = (TextView) findViewById(R.id.tv_card_lock);
        llCardChange = (LinearLayout) findViewById(R.id.ll_card_change);
        tvCardChange = (TextView) findViewById(R.id.tv_card_change);
        llCardPsam = (LinearLayout) findViewById(R.id.ll_card_psam);
        tvCardPsam = (TextView) findViewById(R.id.tv_card_psam);
        flContainer = (FrameLayout) findViewById(R.id.fl_container);

        moduleList.add(llCardSend);
        moduleList.add(llCardRecharge);
        moduleList.add(llCardRechargeCancle);
        moduleList.add(llCardDetail);
        moduleList.add(llCardQr);
        moduleList.add(llCardLock);
        moduleList.add(llCardChange);
        moduleList.add(llCardPsam);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        initReadCard();
        initFragment();
    }

    private void initFragment() {
        cardSendFragment = new CardSendFragment();
        cardRechargeFragment = new CardRechargeFragment();
        cardRechargeCancleFragment = new CardRechargeCancleFragment();
        cardDetailFragment = new CardDetailFragment();
        cardQrFragment = new CardQrFragment();
        cardLockFragment = new CardLockFragment();
        cardChangeFragment = new CardChangeFragment();
        cardPsamFragment = new CardPsamFragment();
//        loadMultipleRootFragment(R.id.fl_container,0,cardSendFragment,cardRechargeFragment,cardRechargeCancleFragment
//        ,cardDetailFragment,cardQrFragment,cardLockFragment,cardChangeFragment);
        fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.fl_container);
        fragmentManagerHelper.add(cardSendFragment);
        llCardSend.setSelected(true);
    }

    //初始化读卡
    private void initReadCard() {
        boolean bCardRead = D8.connectD8(mContext);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_card_send:
                selectorModule(llCardSend);
                fragmentManagerHelper.switchFragment(cardSendFragment);

                break;
            case R.id.ll_card_recharge:
                selectorModule(llCardRecharge);
                fragmentManagerHelper.switchFragment(cardRechargeFragment);

                break;
            case R.id.ll_card_recharge_cancle:
                selectorModule(llCardRechargeCancle);
                fragmentManagerHelper.switchFragment(cardRechargeCancleFragment);


                break;
            case R.id.ll_card_detail:
                selectorModule(llCardDetail);


                break;
            case R.id.ll_card_qr:
                selectorModule(llCardQr);

                break;
            case R.id.ll_card_lock:
                selectorModule(llCardLock);

                break;
            case R.id.ll_card_change:
                selectorModule(llCardChange);

                break;
            case R.id.ll_card_psam:
                selectorModule(llCardPsam);
                fragmentManagerHelper.switchFragment(cardPsamFragment);

                break;


        }

    }

    private void selectorModule(LinearLayout llModule) {
        for (int i = 0; i < moduleList.size(); i++) {
            moduleList.get(i).setSelected(false);
        }
        llModule.setSelected(true);
    }
}
