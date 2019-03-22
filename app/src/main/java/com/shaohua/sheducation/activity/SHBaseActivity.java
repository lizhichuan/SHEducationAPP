package com.shaohua.sheducation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.igexin.sdk.PushManager;
import com.njcool.lzccommon.activity.CoolBaseActivity;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.service.SHIntentService;
import com.shaohua.sheducation.service.SHPushService;
import com.shaohua.sheducation.resultbean.FailVO;

import org.greenrobot.eventbus.Subscribe;


public class SHBaseActivity extends CoolBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_aybase);


    }

    @Override
    protected void onResume() {
        super.onResume();
        PushManager.getInstance().initialize(this.getApplicationContext(), SHPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), SHIntentService.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onClickCenter(View v) {
        super.onClickCenter(v);
    }

    @Subscribe
    public void onEventMainThread(FailVO failVO) {
        CoolPublicMethod.hideProgressDialog();
    }


    public void startWebviewActivity(String url, String title) {
        Intent intent = new Intent();
        intent.setClass(SHBaseActivity.this, CommonWebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
        overridePendingTransition(R.anim.bg_alpha_in, R.anim.bg_alpha_out);
    }
}
