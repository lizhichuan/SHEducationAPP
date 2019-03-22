package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.BuyCoinBody;
import com.shaohua.sheducation.resultbean.BuyCoinResultVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyCoinsActivity extends SHBaseActivity {

    @Bind(R.id.rcv_money)
    RecyclerView rcvMoney;
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

    private CoolCommonRecycleviewAdapter<String> adapter;
    private List<String> mDatas;
    private GridLayoutManager gridLayoutManager;

    private int selectionPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_buy_coins);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("购买金币");
        gridLayoutManager = new GridLayoutManager(this, 3);
        rcvMoney.setLayoutManager(gridLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<String>(mDatas, BuyCoinsActivity.this, R.layout.item_buy_coins) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                CardView cv_coins = holder.getView(R.id.cv_coins);
                TextView tv_coins = holder.getView(R.id.tv_coins);
                TextView tv_title = holder.getView(R.id.tv_title);

                tv_coins.setText(mDatas.get(position));
                tv_title.setText("￥" + mDatas.get(position));
                if (position == selectionPosition) {
                    cv_coins.setCardBackgroundColor(getResources().getColor(R.color.common_color));
                    tv_coins.setTextColor(getResources().getColor(R.color.white));
                    tv_title.setTextColor(getResources().getColor(R.color.white));
                } else {
                    cv_coins.setCardBackgroundColor(getResources().getColor(R.color.white));
                    tv_coins.setTextColor(getResources().getColor(R.color.black));
                    tv_title.setTextColor(getResources().getColor(R.color.common_hint));
                }

            }
        };
        rcvMoney.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position != selectionPosition) {
                    selectionPosition = position;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        setmDatas();
    }

    private void setmDatas() {
        mDatas = new ArrayList<>();
        mDatas.add("10");
        mDatas.add("20");
        mDatas.add("30");
        mDatas.add("50");
        mDatas.add("80");
        mDatas.add("100");
        adapter.setmDatas(mDatas);
        adapter.notifyDataSetChanged();
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
                BuyCoinBody buyCoinBody = new BuyCoinBody();
                if (cbAlipay.isChecked()) {
                    buyCoinBody.setToolsType(2);
                } else if (cbWeichat.isChecked()) {
                    buyCoinBody.setToolsType(3);
                } else if (cbBanlance.isChecked()) {
                    buyCoinBody.setToolsType(1);
                }
                buyCoinBody.setCoin(Integer.valueOf(mDatas.get(selectionPosition)));
                BuyCoin(BuyCoinsActivity.this, buyCoinBody);
                break;
            case R.id.tv_rights:
                startActivity(CommonWebViewActivity.class);
                break;
        }
    }

    private void BuyCoin(final Context context, BuyCoinBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<BuyCoinResultVO> call = CoolHttpUrl.getApi().getService().BuyCoin(body);
            call.enqueue(new Callback<BuyCoinResultVO>() {
                @Override
                public void onResponse(Call<BuyCoinResultVO> call, Response<BuyCoinResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        BuyCoinResultVO data = response.body();
                        CoolLogTrace.e("BuyCoin", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(BuyCoinsActivity.this, "购买成功");
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BuyCoinResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
