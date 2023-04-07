package com.wellsun.deskpos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.BaseFragment;
import com.wellsun.deskpos.util.ToastPrint;

/**
 * date     : 2023-03-22
 * author   : ZhaoZheng
 * describe :
 */
public class TestFrament extends BaseFragment implements View.OnClickListener{


    @Override
    public int getLayoutId() {
        return R.layout.card_send_fragment;
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
        ToastPrint.showText("点击了");
    }
}
