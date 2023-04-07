package com.wellsun.deskpos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.BaseActivity;
import com.wellsun.deskpos.fragment.TestFrament;
import com.wellsun.deskpos.util.FragmentManagerHelper;

public class TestActivity extends BaseActivity {



    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(),R.id.fl_test);

        fragmentManagerHelper.add(new TestFrament());
    }

    @Override
    public void onClick(View view) {

    }
}
