package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanSummaryActivity extends SHBaseActivity {

    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_store_all)
    TextView tvStoreAll;
    @Bind(R.id.tv_store_completed)
    TextView tvStoreCompleted;
    @Bind(R.id.tv_store_uncomplete)
    TextView tvStoreUncomplete;
    @Bind(R.id.tv_store_percent)
    TextView tvStorePercent;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_money_details)
    TextView tvMoneyDetails;
    @Bind(R.id.linear_money)
    LinearLayout linearMoney;
    @Bind(R.id.tv_know)
    TextView tvKnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_plan_summary);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("计划总结");
    }

    @OnClick(R.id.tv_know)
    public void onViewClicked() {
    }
}
