package com.shaohua.sheducation.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.utils.PermissionsChecker;

import butterknife.ButterKnife;


public class StartActivity extends SHBaseActivity {


    // 要申请的权限

    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onRefreshContentView() {
        super.onRefreshContentView();

    }

    private void findViews() {
        // TODO Auto-generated method stub
        mHandler.sendEmptyMessageDelayed(999, 1000);
        mPermissionsChecker = new PermissionsChecker(StartActivity.this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        } else {
            if (CoolPublicMethod.isNetworkAvailable(StartActivity.this)) {
                mHandler.sendEmptyMessageAtTime(999, 2000);
            } else {
                CoolPublicMethod.Toast(StartActivity.this, "无网络连接，请检查并重启应用");
            }
        }

    }


    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 999:
                    String firstin = CoolSPUtil.getDataFromLoacl(StartActivity.this,
                            "firstin").toString();
                    String uid = CoolSPUtil.getDataFromLoacl(StartActivity.this,
                            "uid").toString();
                    if (TextUtils.isEmpty(uid)) {
                        startActivity(LoginActivity.class);
                    } else {
                        startActivity(MainActivity.class);
                    }
                    finish();

                    break;

            }
            super.handleMessage(msg);
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (CoolPublicMethod.isNetworkAvailable(StartActivity.this)) {
                    mHandler.sendEmptyMessageAtTime(999, 2000);
                } else {
                    CoolPublicMethod.Toast(StartActivity.this, "无网络连接，请检查并重启应用");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        mHandler.removeMessages(999);
        super.onDestroy();
    }


}
