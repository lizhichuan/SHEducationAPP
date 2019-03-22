package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PullMoneyActivity extends SHBaseActivity {

    @Bind(R.id.tv_abanlance)
    TextView tvAbanlance;
    @Bind(R.id.cb_alipay)
    CheckBox cbAlipay;
    @Bind(R.id.linear_alipay)
    LinearLayout linearAlipay;
    @Bind(R.id.cb_weichat)
    CheckBox cbWeichat;
    @Bind(R.id.linear_weichat)
    LinearLayout linearWeichat;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_alipay_account)
    EditText etAlipayAccount;
    @Bind(R.id.et_money)
    EditText etMoney;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_pull_money);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("提现");
    }

    @OnClick({R.id.linear_alipay, R.id.linear_weichat, R.id.tv_confirm})
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
            case R.id.tv_confirm:
                break;
        }
    }
}
