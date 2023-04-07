package com.wellsun.deskpos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.wellsun.deskpos.MainActivity;
import com.wellsun.deskpos.R;
import com.wellsun.deskpos.base.Api;
import com.wellsun.deskpos.base.BaseActivity;
import com.wellsun.deskpos.base.BaseDialog;
import com.wellsun.deskpos.base.OkgoCallBack;
import com.wellsun.deskpos.bean.LoginBean;
import com.wellsun.deskpos.data.Data;
import com.wellsun.deskpos.util.SPUtils;
import com.wellsun.deskpos.util.ToastPrint;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private android.widget.TextView tvStationName;
    private android.widget.EditText etAccount;
    private android.widget.EditText etPassword;
    private android.widget.TextView tvLogin;
    boolean bPermissions = false;
    private String stationId;
    private String stationName;
    private String account;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        tvStationName = (TextView) findViewById(R.id.tv_station_name);
        etAccount = (EditText) findViewById(R.id.et_account);
        etPassword = (EditText) findViewById(R.id.et_password);
        tvLogin = (TextView) findViewById(R.id.tv_login);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {
        initPermission();     //请求权限
        stationName = SPUtils.getInstance().getStationName();
        stationId = SPUtils.getInstance().getStationId();
        account = SPUtils.getInstance().getUSER_NAME();
        if (!TextUtils.isEmpty(stationName)) {
            tvStationName.setText(stationName + "/" + stationId);
        }
        etAccount.setText(account);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_station:
                setStation();
                break;
            case R.id.tv_login:
                login();
                break;
        }

    }

    private void login() {
        String stationName = tvStationName.getText().toString().trim();
        String accout = etAccount.getText().toString().trim();
        String passWord = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(stationName) || TextUtils.isEmpty(accout) || TextUtils.isEmpty(passWord)) {
            ToastPrint.showText("请检查输入参数");
            return;
        }

        OkGo.<LoginBean>post(Api.getCardNumber)
                .params("account", accout)
                .params("password", passWord)
                .params("deviceId", stationId)
                .params("stationName", stationName)
                .params("deviceType", "00")
                .execute(new OkgoCallBack<LoginBean>(LoginBean.class, mContext) {
                    @Override
                    public void onSuccess(Response<LoginBean> response) {
                        LoginBean body = response.body();

                    }

                    @Override
                    public void onError(Response<LoginBean> response) {
                        super.onError(response);
                        ToastPrint.showText(response.getException() + "");
                    }
                });
        SPUtils.getInstance().setUSER_NAME(accout);
        Data.stationName=stationName;
        Data.stationId="";
        Data.accountId="";
        startActivity(new Intent(mContext, MainActivity.class));


    }

    private void setStation() {
        BaseDialog baseDialog = new BaseDialog(LoginActivity.this, R.layout.dialog_set_station);
        baseDialog.show();
        EditText etStationName = baseDialog.findViewById(R.id.et_station_name);
        EditText etStationId = baseDialog.findViewById(R.id.et_station_id);
        etStationName.setText(stationName);
        etStationId.setText(stationId);
        baseDialog.findViewById(R.id.tv_bind_station).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationName = etStationName.getText().toString().trim();
                stationId = etStationId.getText().toString().trim();
                if (TextUtils.isEmpty(stationName) || TextUtils.isEmpty(stationId)) {
                    ToastPrint.showText("请检查参数");
                    return;
                }
                SPUtils.getInstance().setStationName(stationName);
                SPUtils.getInstance().setStationId(stationId);
                tvStationName.setText(stationName + "/" + stationId);
                baseDialog.dismissDialog();
            }
        });
    }

    private void initPermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)  // 申请多个存储
                .permission(Permission.CAMERA)         // 申请单个权限
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            //权限获取成功
                            bPermissions = true;
                        } else {
                            //获取部分权限成功，但部分权限未正常授予
                            XXPermissions.startPermissionActivity(mContext, permissions);
                            finish();
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            //toast("被永久拒绝授权，请手动授予录音和日历权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        } else {
                            //权限获取失败
                        }
                        XXPermissions.startPermissionActivity(mContext, permissions);
                        finish();
                    }
                });
    }
}
