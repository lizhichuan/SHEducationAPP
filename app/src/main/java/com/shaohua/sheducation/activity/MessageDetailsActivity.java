package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shaohua.sheducation.R;
import com.shaohua.sheducation.resultbean.GetMyMessageResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageDetailsActivity extends SHBaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    TextView tvContent;

    private GetMyMessageResultVO.ResultBean.DataBean message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_message_details);
        ButterKnife.bind(this);
        message = (GetMyMessageResultVO.ResultBean.DataBean) getIntent().getSerializableExtra("message");
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        if (message == null) {
            finish();
        }
        setmTopTitle("消息详情");
        tvTitle.setText(message.getTitle());
        tvContent.setText(message.getContent());
    }
}
