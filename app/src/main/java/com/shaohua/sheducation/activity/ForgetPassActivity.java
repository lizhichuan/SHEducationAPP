package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolRegexUtil;
import com.njcool.lzccommon.view.downtime.CoolDownTimer;
import com.njcool.lzccommon.view.downtime.CoolDownTimerListener;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.SMSRegisterBody;
import com.shaohua.sheducation.request.ValidCodeBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.CommonResultVO;
import com.shaohua.sheducation.resultbean.SMSForgetResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassActivity extends SHBaseActivity {

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
        setContentLayout(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("忘记密码");
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

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        tvSendCode.setClickable(true);
        tvSendCode.setEnabled(true);
    }

    @OnClick({R.id.tv_send_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (!CoolRegexUtil.isMobileNO(editAccount.getText().toString())) {
                    CoolPublicMethod.Toast(ForgetPassActivity.this, "手机号码格式不正确");
                    return;
                }
                tvSendCode.setClickable(false);
                tvSendCode.setEnabled(false);
                SMSRegisterBody smsRegisterRequest = new SMSRegisterBody();
                smsRegisterRequest.setMobile(editAccount.getText().toString());
                SMSForget(ForgetPassActivity.this, smsRegisterRequest);
                break;
            case R.id.tv_confirm:
                if (!CoolRegexUtil.isMobileNO(editAccount.getText().toString())) {
                    CoolPublicMethod.Toast(ForgetPassActivity.this, "手机号码格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(editCode.getText().toString())) {
                    CoolPublicMethod.Toast(ForgetPassActivity.this, "验证码不可为空");
                    return;
                }
                ValidCodeBody validCodeRequest = new ValidCodeBody();
                validCodeRequest.setMobile(editAccount.getText().toString());
                validCodeRequest.setMobileVerifyCode(editCode.getText().toString());
                ValidCode(ForgetPassActivity.this, validCodeRequest);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownTimer.stopDown();
    }

    private void SMSForget(final Context context, SMSRegisterBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<SMSForgetResultVO> call = CoolHttpUrl.getApi().getService().SMSForget(body);
            call.enqueue(new Callback<SMSForgetResultVO>() {
                @Override
                public void onResponse(Call<SMSForgetResultVO> call, Response<SMSForgetResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        SMSForgetResultVO data = response.body();
                        CoolLogTrace.e("SMSForget", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(ForgetPassActivity.this, "验证码发送成功");
                            tvSendCode.setClickable(false);
                            tvSendCode.setEnabled(false);
                            mDownTimer.startDown(60 * 1000); // 每秒倒计时
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SMSForgetResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void ValidCode(final Context context, ValidCodeBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<CommonResultVO> call = CoolHttpUrl.getApi().getService().ValidCode(body);
            call.enqueue(new Callback<CommonResultVO>() {
                @Override
                public void onResponse(Call<CommonResultVO> call, Response<CommonResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        CommonResultVO data = response.body();
                        CoolLogTrace.e("ResetPass", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            mDownTimer.stopDown();
                            startActivity(ResetPassActivity.class);
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<CommonResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
