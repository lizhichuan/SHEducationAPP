package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyDataResultActivity extends SHBaseActivity {

    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_continue)
    TextView tvContinue;
    @Bind(R.id.tv_look)
    TextView tvLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_buy_data_result);
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
    }

    private void findViews() {
        setmTopTitle("资料购买");
        setTvRight("说明");
        setmTvRightVisible(1);
    }

    @OnClick({R.id.tv_continue, R.id.tv_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_continue:
                break;
            case R.id.tv_look:
                break;
        }
    }
}
