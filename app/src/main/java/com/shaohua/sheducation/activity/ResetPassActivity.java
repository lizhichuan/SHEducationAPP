package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolRegexUtil;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.ResetPassBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.ResetPassResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassActivity extends SHBaseActivity {

    @Bind(R.id.et_pass)
    EditText etPass;
    @Bind(R.id.et_pass_confirm)
    EditText etPassConfirm;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_reset_pass);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("重置密码");
    }


    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        if (!CoolRegexUtil.isPassword(etPass.getText().toString())) {
            CoolPublicMethod.Toast(ResetPassActivity.this, "请输入6-15位数字与字母组合密码");
            return;
        }
        if (etPassConfirm.getText().toString().equals(etPass.getText().toString())) {
            ResetPassBody resetPassRequest = new ResetPassBody();
            resetPassRequest.setPassword(etPass.getText().toString());
            resetPassRequest.setPasswordSecond(etPassConfirm.getText().toString());
            ResetPass(ResetPassActivity.this, resetPassRequest);
        } else {
            CoolPublicMethod.Toast(ResetPassActivity.this, "两次密码输入不一致，请检查");
            return;
        }
    }

    private void ResetPass(final Context context, ResetPassBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<ResetPassResultVO> call = CoolHttpUrl.getApi().getService().ResetPass(body);
            call.enqueue(new Callback<ResetPassResultVO>() {
                @Override
                public void onResponse(Call<ResetPassResultVO> call, Response<ResetPassResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        ResetPassResultVO data = response.body();
                        CoolLogTrace.e("ResetPass", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(ResetPassActivity.this, "重置成功，请重新登录");
                            startActivity(LoginActivity.class);
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResetPassResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
