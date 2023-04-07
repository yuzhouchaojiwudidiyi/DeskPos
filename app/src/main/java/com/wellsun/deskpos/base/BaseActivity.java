package com.wellsun.deskpos.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.wellsun.deskpos.util.SystemUIUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * content:
 *
 * @date: 2021/6/22
 * @author: ZhaoZheng
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context mContext;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) { // //隐藏底部导航栏
        super.onWindowFocusChanged(hasFocus);
        SystemUIUtils.setStickFullScreen(getWindow().getDecorView());
    }

    @Subscribe
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM); //禁用软键盘
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (!EventBus.getDefault().isRegistered(this)) {//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }
        mContext = this;
        App.getAppContext().addActivity(this);  //添加管理activity
        init();
    }

    /**
     * 加载页面布局
     */
    public abstract int getLayoutId();

    /**
     * 初始化方法
     */
    public void init() {
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化布局控件
     */
    public abstract void initView();

    /**
     * 初始化设置监听
     */
    public abstract void setListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        App.getAppContext().finishActivity(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //判断是否有网
    public boolean isnetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable();
        }
        return false;
    }
}
