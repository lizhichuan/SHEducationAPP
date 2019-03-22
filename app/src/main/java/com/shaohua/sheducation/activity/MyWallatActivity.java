package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetMyWalletResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWallatActivity extends SHBaseActivity {

    @Bind(R.id.rdbt_abalance)
    RadioButton rdbtAbalance;
    @Bind(R.id.rdbt_coins)
    RadioButton rdbtCoins;
    @Bind(R.id.tv_coins)
    TextView tvCoins;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.linear_money)
    LinearLayout linearMoney;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.tv_push)
    TextView tvPush;
    @Bind(R.id.tv_pull)
    TextView tvPull;
    @Bind(R.id.linear_money_operate)
    LinearLayout linearMoneyOperate;
    @Bind(R.id.rdg_wallat)
    RadioGroup rdgWallat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_wallat);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    @Override
    protected void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(FlowDetailsActivity.class);
    }

    private void findViews() {
        setmTopTitle("我的钱包");
        setTvRight("明细");
        setmTvRightVisible(1);
        rdgWallat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdbt_abalance:
                        linearMoneyOperate.setVisibility(View.VISIBLE);
                        linearMoney.setVisibility(View.VISIBLE);
                        tvCoins.setVisibility(View.GONE);
                        tvBuy.setVisibility(View.GONE);
                        break;
                    case R.id.rdbt_coins:
                        linearMoneyOperate.setVisibility(View.GONE);
                        linearMoney.setVisibility(View.GONE);
                        tvCoins.setVisibility(View.VISIBLE);
                        tvBuy.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        GetMyWallet(MyWallatActivity.this);
    }

    @OnClick({R.id.tv_buy, R.id.tv_push, R.id.tv_pull})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_buy:
                startActivity(BuyCoinsActivity.class);
                break;
            case R.id.tv_push:
                startActivity(PushMoneyActivity.class);
                break;
            case R.id.tv_pull:
                startActivity(PullMoneyActivity.class);
                break;
        }
    }


    private void GetMyWallet(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetMyWalletResultVO> call = CoolHttpUrl.getApi().getService().GetMyWallet();
            call.enqueue(new Callback<GetMyWalletResultVO>() {
                @Override
                public void onResponse(Call<GetMyWalletResultVO> call, Response<GetMyWalletResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetMyWalletResultVO data = response.body();
                        CoolLogTrace.e("GetMyWallet", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            tvCoins.setText(data.getResult().getCoin() + "");
                            tvMoney.setText(data.getResult().getBalance() + "");
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetMyWalletResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}

