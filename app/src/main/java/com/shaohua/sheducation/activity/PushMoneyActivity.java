package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.RechargeBody;
import com.shaohua.sheducation.resultbean.RechargeResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushMoneyActivity extends SHBaseActivity {

    @Bind(R.id.cb_alipay)
    CheckBox cbAlipay;
    @Bind(R.id.linear_alipay)
    LinearLayout linearAlipay;
    @Bind(R.id.cb_weichat)
    CheckBox cbWeichat;
    @Bind(R.id.linear_weichat)
    LinearLayout linearWeichat;
    @Bind(R.id.et_money)
    EditText etMoney;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.cb_agree)
    CheckBox cbAgree;
    @Bind(R.id.tv_rights)
    TextView tvRights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_push_money);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("充值");
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvPay.setText("支付(" + s.toString() + ".00)");
            }
        });
    }

    @OnClick({R.id.linear_alipay, R.id.linear_weichat, R.id.tv_pay, R.id.tv_rights})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_alipay:
                cbAlipay.setChecked(true);
                cbWeichat.setChecked(false);
                break;
            case R.id.linear_weichat:
                cbAlipay.setChecked(false);
                cbWeichat.setChecked(true);
                break;
            case R.id.tv_pay:
                if (TextUtils.isEmpty(etMoney.getText().toString())) {
                    CoolPublicMethod.Toast(PushMoneyActivity.this, "未输入充值金额");
                    return;
                }
                RechargeBody body = new RechargeBody();
                if (cbAlipay.isChecked()) {
                    body.setToolsType(2);
                } else {
                    body.setToolsType(3);
                }
                body.setMoney(Integer.valueOf(etMoney.getText().toString()));
                Recharge(PushMoneyActivity.this, body);
                break;
            case R.id.tv_rights:
                startActivity(CommonWebViewActivity.class);
                break;
        }
    }

    private void Recharge(final Context context, RechargeBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<RechargeResultVO> call = CoolHttpUrl.getApi().getService().Recharge(body);
            call.enqueue(new Callback<RechargeResultVO>() {
                @Override
                public void onResponse(Call<RechargeResultVO> call, Response<RechargeResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        RechargeResultVO data = response.body();
                        CoolLogTrace.e("Recharge", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(PushMoneyActivity.this, "充值成功");
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
