package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyDataActivity extends SHBaseActivity {

    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_need_coins)
    TextView tvNeedCoins;
    @Bind(R.id.tv_all_coins)
    TextView tvAllCoins;
    @Bind(R.id.tv_coins_buy)
    TextView tvCoinsBuy;
    @Bind(R.id.tv_buy)
    TextView tvBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_buy_data);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("购买资料");
    }

    @OnClick({R.id.tv_coins_buy, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_coins_buy:
                startActivity(BuyCoinsActivity.class);
                break;
            case R.id.tv_buy:
                break;
        }
    }
}
