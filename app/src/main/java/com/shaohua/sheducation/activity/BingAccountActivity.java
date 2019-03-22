package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolRegexUtil;
import com.njcool.lzccommon.view.downtime.CoolDownTimer;
import com.njcool.lzccommon.view.downtime.CoolDownTimerListener;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BingAccountActivity extends SHBaseActivity {

    @Bind(R.id.edit_account)
    EditText editAccount;
    @Bind(R.id.tv_send_code)
    TextView tvSendCode;
    @Bind(R.id.edit_code)
    EditText editCode;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    private CoolDownTimer mDownTimer;// 倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_bing_account);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("完善资料");
        mDownTimer = new CoolDownTimer();
        // 倒计时监听
        mDownTimer.setListener(new CoolDownTimerListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                tvSendCode.setText(millisUntilFinished / 1000 + "S后重新获取");
            }

            @Override
            public void onFinish() {
                tvSendCode.setClickable(true);
                tvSendCode.setEnabled(true);
                tvSendCode.setText("获取验证码");
            }
        });
    }

    @OnClick({R.id.tv_send_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (!CoolRegexUtil.isMobileNO(editAccount.getText().toString())) {
                    CoolPublicMethod.Toast(BingAccountActivity.this, "手机号码格式不正确");
                    return;
                }
                tvSendCode.setClickable(false);
                tvSendCode.setEnabled(false);
                mDownTimer.startDown(60 * 1000); // 每秒倒计时
                break;
            case R.id.tv_confirm:
                if (!CoolRegexUtil.isMobileNO(editAccount.getText().toString())) {
                    CoolPublicMethod.Toast(BingAccountActivity.this, "手机号码格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString())) {
                    CoolPublicMethod.Toast(BingAccountActivity.this, "验证码不可为空");
                    return;
                }
                startActivity(PlanActivity.class);
                finish();
                break;
        }
    }
}
