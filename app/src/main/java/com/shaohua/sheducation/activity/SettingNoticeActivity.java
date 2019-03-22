package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.AppSettingBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.common.UserPref;
import com.shaohua.sheducation.resultbean.AppSettingResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingNoticeActivity extends SHBaseActivity {

    @Bind(R.id.cb_open_app)
    CheckBox cbOpenApp;
    @Bind(R.id.cb_open_friends)
    CheckBox cbOpenFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_setting_notice);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("设置");
        if (UserPref.readUserInfo().getAppPush() == 1) {
            cbOpenApp.setChecked(true);
        } else {
            cbOpenApp.setChecked(false);
        }
        if (UserPref.readUserInfo().getFriendPush() == 1) {
            cbOpenFriends.setChecked(true);
        } else {
            cbOpenFriends.setChecked(false);
        }
        cbOpenApp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettingBody appSettingRequest = new AppSettingBody();
                    appSettingRequest.setAppPush(1);
                    if (cbOpenFriends.isChecked()) {
                        appSettingRequest.setFriendPush(1);
                    } else {
                        appSettingRequest.setFriendPush(0);
                    }
                    AppSetting(SettingNoticeActivity.this, appSettingRequest);
                } else {
                    AppSettingBody appSettingRequest = new AppSettingBody();
                    appSettingRequest.setAppPush(0);
                    if (cbOpenFriends.isChecked()) {
                        appSettingRequest.setFriendPush(1);
                    } else {
                        appSettingRequest.setFriendPush(0);
                    }
                    AppSetting(SettingNoticeActivity.this, appSettingRequest);
                }
            }
        });
        cbOpenFriends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppSettingBody appSettingRequest = new AppSettingBody();
                    appSettingRequest.setFriendPush(1);
                    if (cbOpenApp.isChecked()) {
                        appSettingRequest.setAppPush(1);
                    } else {
                        appSettingRequest.setAppPush(0);
                    }
                    AppSetting(SettingNoticeActivity.this, appSettingRequest);
                } else {
                    AppSettingBody appSettingRequest = new AppSettingBody();
                    appSettingRequest.setFriendPush(0);
                    if (cbOpenApp.isChecked()) {
                        appSettingRequest.setAppPush(1);
                    } else {
                        appSettingRequest.setAppPush(0);
                    }
                    AppSetting(SettingNoticeActivity.this, appSettingRequest);
                }
            }
        });
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
    }


    private void AppSetting(final Context context, AppSettingBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<AppSettingResultVO> call = CoolHttpUrl.getApi().getService().AppSetting(body);
            call.enqueue(new Callback<AppSettingResultVO>() {
                @Override
                public void onResponse(Call<AppSettingResultVO> call, Response<AppSettingResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        AppSettingResultVO data = response.body();
                        CoolLogTrace.e("AppSetting", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<AppSettingResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

}
