package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseDetailsActivity extends SHBaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_tag)
    TextView tvTag;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_coins)
    TextView tvCoins;
    @Bind(R.id.fl_tag)
    CoolTagFlowLayout flTag;
    @Bind(R.id.img_course_pic)
    CoolRoundAngleImageView imgCoursePic;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.cb_good)
    CheckBox cbGood;
    @Bind(R.id.linear_good)
    LinearLayout linearGood;
    @Bind(R.id.cb_favorite)
    CheckBox cbFavorite;
    @Bind(R.id.linear_favorite)
    LinearLayout linearFavorite;
    @Bind(R.id.linear_download)
    LinearLayout linearDownload;


    private TagAdapter<String> adapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mDatas.add("");
        }
        adapter = new TagAdapter<String>(mDatas) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_course_details_tag,
                        flTag, false);
                tv.setText("标签" + position);
                return tv;
            }
        };
        flTag.setAdapter(adapter);
    }

    @OnClick({R.id.img_back, R.id.linear_good, R.id.linear_favorite, R.id.linear_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.linear_good:
                break;
            case R.id.linear_favorite:
                break;
            case R.id.linear_download:
                startActivity(BuyDataActivity.class);
                break;
        }
    }
}
