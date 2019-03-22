package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.PlanPayBody;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.PlanPayVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayMoneyActivity extends SHBaseActivity {

    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.tv_store)
    TextView tvStore;
    @Bind(R.id.cb_alipay)
    CheckBox cbAlipay;
    @Bind(R.id.linear_alipay)
    LinearLayout linearAlipay;
    @Bind(R.id.cb_weichat)
    CheckBox cbWeichat;
    @Bind(R.id.linear_weichat)
    LinearLayout linearWeichat;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.cb_banlance)
    CheckBox cbBanlance;
    @Bind(R.id.linear_banlance)
    LinearLayout linearBanlance;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.cb_agree)
    CheckBox cbAgree;
    @Bind(R.id.tv_rights)
    TextView tvRights;


    private GetPlanInfoResultVO.ResultBean mgetPlanInfoVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_pay_money);
        ButterKnife.bind(this);
        mgetPlanInfoVO = (GetPlanInfoResultVO.ResultBean) getIntent().getSerializableExtra("plan");
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("支付押金");
        CoolGlideUtil.urlInto(PayMoneyActivity.this, mgetPlanInfoVO.getImage(), imgPic);
        tvTitle.setText(mgetPlanInfoVO.getName());
        tvId.setText("计划ID:" + mgetPlanInfoVO.getId());
//        tvStore.setText("里程碑个数:"+mgetPlanInfoVO.get);
    }

    @OnClick({R.id.linear_alipay, R.id.linear_weichat, R.id.linear_banlance, R.id.tv_pay, R.id.tv_rights})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_alipay:
                cbAlipay.setChecked(true);
                cbWeichat.setChecked(false);
                cbBanlance.setChecked(false);
                break;
            case R.id.linear_weichat:
                cbAlipay.setChecked(false);
                cbWeichat.setChecked(true);
                cbBanlance.setChecked(false);
                break;
            case R.id.linear_banlance:
                cbAlipay.setChecked(false);
                cbWeichat.setChecked(false);
                cbBanlance.setChecked(true);
                break;
            case R.id.tv_pay:
                PlanPayBody body = new PlanPayBody();
                if (cbAlipay.isChecked()) {
                    body.setPayType(2);
                } else if (cbWeichat.isChecked()) {
                    body.setPayType(3);
                } else if (cbBanlance.isChecked()) {
                    body.setPayType(1);
                }
                PlanPay(PayMoneyActivity.this, body, mgetPlanInfoVO.getId());
                break;
            case R.id.tv_rights:
                break;
        }
    }

    private void PlanPay(final Context context, PlanPayBody body, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanPayVO> call = CoolHttpUrl.getApi().getService().PlanPay(id, body);
            call.enqueue(new Callback<PlanPayVO>() {
                @Override
                public void onResponse(Call<PlanPayVO> call, Response<PlanPayVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanPayVO data = response.body();
                        CoolLogTrace.e("PlanPay", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(PayMoneyActivity.this, "成功");
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanPayVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
