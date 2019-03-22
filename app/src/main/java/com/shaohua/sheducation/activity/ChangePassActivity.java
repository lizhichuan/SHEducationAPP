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
import com.shaohua.sheducation.request.ChangePassBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.CommonResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends SHBaseActivity {

    @Bind(R.id.et_old_pass)
    EditText etOldPass;
    @Bind(R.id.et_new_pass)
    EditText etNewPass;
    @Bind(R.id.et_new_confirm_pass)
    EditText etNewConfirmPass;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
    }

    private void findViews() {
        setmTopTitle("修改密码");
    }


    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        if (!CoolRegexUtil.isPassword(etOldPass.getText().toString())) {
            CoolPublicMethod.Toast(ChangePassActivity.this, "旧密码应该为6-15位数字或字母组合");
            return;
        }
        if (!CoolRegexUtil.isPassword(etNewPass.getText().toString())) {
            CoolPublicMethod.Toast(ChangePassActivity.this, "新密码应该为6-15位数字或字母组合");
            return;
        }
        if (etNewConfirmPass.getText().toString().equals(etNewPass.getText().toString())) {
            ChangePassBody changePassRequest = new ChangePassBody();
            changePassRequest.setPassword(etNewPass.getText().toString());
            changePassRequest.setPasswordOld(etOldPass.getText().toString());
            changePassRequest.setPasswordSecond(etNewConfirmPass.getText().toString());
            ChangePass(ChangePassActivity.this, changePassRequest);

        } else {
            CoolPublicMethod.Toast(ChangePassActivity.this, "两次新密码输入不一致");
            return;
        }
    }

    private void ChangePass(final Context context, ChangePassBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<CommonResultVO> call = CoolHttpUrl.getApi().getService().ChangePass(body);
            call.enqueue(new Callback<CommonResultVO>() {
                @Override
                public void onResponse(Call<CommonResultVO> call, Response<CommonResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        CommonResultVO data = response.body();
                        CoolLogTrace.e("ResetPass", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(ChangePassActivity.this, "修改成功，请重新登录");
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
                public void onFailure(Call<CommonResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
