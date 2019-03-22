package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteAllStoreActivity extends SHBaseActivity {

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
    @Bind(R.id.img_camere)
    ImageView imgCamere;
    @Bind(R.id.linear_camera)
    LinearLayout linearCamera;
    @Bind(R.id.img_finish_pic)
    ImageView imgFinishPic;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.linear_complete_photo)
    LinearLayout linearCompletePhoto;
    @Bind(R.id.linear_completed)
    LinearLayout linearCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_complete_all_store);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("完成里程碑");
    }

    @OnClick({R.id.linear_camera, R.id.img_finish_pic, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_camera:
                break;
            case R.id.img_finish_pic:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
