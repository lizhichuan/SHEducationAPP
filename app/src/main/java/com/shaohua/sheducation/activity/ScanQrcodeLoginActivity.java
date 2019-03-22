package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;

import com.shaohua.sheducation.R;

import butterknife.ButterKnife;

public class ScanQrcodeLoginActivity extends SHBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_scan_qrcode_login);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("扫码登录");
    }
}
