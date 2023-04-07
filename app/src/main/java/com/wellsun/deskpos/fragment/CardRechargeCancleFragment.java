package com.wellsun.deskpos.fragment;

import android.view.View;

import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.BaseFragment;
import com.wellsun.deskpos.util.ToastPrint;

/**
 * date     : 2023-03-21
 * author   : ZhaoZheng
 * describe :
 */
public class CardRechargeCancleFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.card_recharge_cancle_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        ToastPrint.showText("冲正");
    }
}
