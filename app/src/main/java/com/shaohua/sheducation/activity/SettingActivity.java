package com.shaohua.sheducation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.njcool.lzccommon.common.CoolCommonPop;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.utils.DataCleanManager;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends SHBaseActivity {

    @Bind(R.id.linear_notice)
    LinearLayout linearNotice;
    @Bind(R.id.linear_clear)
    LinearLayout linearClear;
    @Bind(R.id.linear_clear_data)
    LinearLayout linearClearData;
    @Bind(R.id.linear_change_pass)
    LinearLayout linearChangePass;
    @Bind(R.id.linear_logout)
    LinearLayout linearLogout;
    @Bind(R.id.activity_setting)
    ConstraintLayout activitySetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_setting);
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
    }

    @OnClick({R.id.linear_notice, R.id.linear_clear, R.id.linear_clear_data, R.id.linear_change_pass, R.id.linear_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_notice:
                startActivity(SettingNoticeActivity.class);
                break;
            case R.id.linear_clear:
                CoolCommonPop coolCommonPop_Clear = new CoolCommonPop();
                coolCommonPop_Clear.ShowPop(SettingActivity.this,
                        activitySetting, Gravity.CENTER, "清除缓存", "确定清除缓存吗?");
                coolCommonPop_Clear.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        DataCleanManager.cleanInternalCache(SettingActivity.this);
                        CoolPublicMethod.Toast(SettingActivity.this, "清除成功");
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.linear_clear_data:
                CoolCommonPop coolCommonPop_Clear_im = new CoolCommonPop();
                coolCommonPop_Clear_im.ShowPop(SettingActivity.this,
                        activitySetting, Gravity.CENTER, "清除缓存", "确定清除聊天记录吗?");
                coolCommonPop_Clear_im.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        DataCleanManager.cleanInternalCache(SettingActivity.this);
                        CoolPublicMethod.Toast(SettingActivity.this, "清除成功");
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.linear_change_pass:
                startActivity(ChangePassActivity.class);
                break;
            case R.id.linear_logout:
                CoolCommonPop coolCommonPop = new CoolCommonPop();
                coolCommonPop.ShowPop(SettingActivity.this, activitySetting, Gravity.CENTER, "", "\n确定退出登录吗?\n");
                coolCommonPop.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        CoolSPUtil.clearDataFromLoacl(SettingActivity.this);
                        CoolSPUtil.insertDataToLoacl(SettingActivity.this, "firstin", "true");
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class).
                                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
        }
    }
}
