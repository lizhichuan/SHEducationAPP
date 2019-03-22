package com.shaohua.sheducation.activity;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.app.App;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolRegexUtil;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.downtime.CoolDownTimer;
import com.njcool.lzccommon.view.downtime.CoolDownTimerListener;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.RegisterBody;
import com.shaohua.sheducation.request.SMSRegisterBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.common.UserPref;
import com.shaohua.sheducation.resultbean.RegisterResultVO;
import com.shaohua.sheducation.resultbean.SMSRegisterResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends SHBaseActivity {

    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_send_code)
    TextView tvSendCode;
    @Bind(R.id.et_pass)
    EditText etPass;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.cb_agree)
    CheckBox cbAgree;
    @Bind(R.id.tv_rights)
    TextView tvRights;

    private CoolDownTimer mDownTimer;// 倒计时

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        App.getInstance().addActivity(this);
        ButterKnife.bind(this);
        findViews();

    }


    private void findViews() {

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

        tvRights.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        tvSendCode.setClickable(true);
        tvSendCode.setEnabled(true);
    }


    @OnClick({R.id.img_close, R.id.tv_send_code, R.id.tv_login, R.id.tv_register, R.id.tv_rights})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.tv_send_code:
                if (!CoolRegexUtil.isMobileNO(etPhone.getText().toString())) {
                    CoolPublicMethod.Toast(RegisterActivity.this, "手机号码格式不正确");
                    return;
                }
                tvSendCode.setClickable(false);
                tvSendCode.setEnabled(false);
                SMSRegisterBody smsRegisterBody = new SMSRegisterBody();
                smsRegisterBody.setMobile(etPhone.getText().toString());
                SMSRegister(RegisterActivity.this, smsRegisterBody);

                break;
            case R.id.tv_login:
                startActivity(LoginActivity.class);
                finish();
                break;
            case R.id.tv_register:
                if (!CoolRegexUtil.isMobileNO(etPhone.getText().toString())) {
                    CoolPublicMethod.Toast(RegisterActivity.this, "手机号码格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(etCode.getText().toString())) {
                    CoolPublicMethod.Toast(RegisterActivity.this, "验证码不可为空");
                    return;
                }
                if (!CoolRegexUtil.isPassword(etPass.getText().toString())) {
                    CoolPublicMethod.Toast(RegisterActivity.this, "密码应该为6-15位字母或数字组合");
                    return;
                }
                RegisterBody registerBody = new RegisterBody();
                registerBody.setMobile(etPhone.getText().toString());
                registerBody.setPassword(etPass.getText().toString());
                registerBody.setMobileVerifyCode(etCode.getText().toString());
                Register(RegisterActivity.this, registerBody);
                break;
            case R.id.tv_rights:
                startWebviewActivity("", "");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownTimer.stopDown();
    }

    /**
     * 发送验证码
     *
     * @param context
     * @param body
     */
    public void SMSRegister(final Context context, SMSRegisterBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<SMSRegisterResultVO> call = CoolHttpUrl.getApi().getService().SMSRegister(body);
            call.enqueue(new Callback<SMSRegisterResultVO>() {
                @Override
                public void onResponse(Call<SMSRegisterResultVO> call, Response<SMSRegisterResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        SMSRegisterResultVO result = response.body();
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(RegisterActivity.this, "验证码发送成功");
                            tvSendCode.setClickable(false);
                            tvSendCode.setEnabled(false);
                            mDownTimer.startDown(60 * 1000);
                        } else {
                            SHHttpUtil.resultCode(context, response.code(), response.message());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SMSRegisterResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }

    }

    /**
     * 注册
     *
     * @param context
     * @param body
     */
    public void Register(final Context context, RegisterBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<RegisterResultVO> call = CoolHttpUrl.getApi().getService().Register(body);
            call.enqueue(new Callback<RegisterResultVO>() {
                @Override
                public void onResponse(Call<RegisterResultVO> call, Response<RegisterResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        RegisterResultVO result = response.body();
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            mDownTimer.stopDown();
                            UserPref.saveUserInfo(result.getResult());
                            CoolSPUtil.insertDataToLoacl(RegisterActivity.this, "uid",
                                    result.getResult().getId() + "");
                            startActivity(MainActivity.class);
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, response.code(), response.message());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RegisterResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }

    }
}
