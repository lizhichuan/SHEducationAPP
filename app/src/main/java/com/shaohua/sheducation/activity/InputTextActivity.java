package com.shaohua.sheducation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InputTextActivity extends SHBaseActivity {

    @Bind(R.id.et_content)
    EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_input_text);
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
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            CoolPublicMethod.Toast(InputTextActivity.this, "无任何内容");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("content", etContent.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void findViews() {
        setmTopTitle("编辑内容");
        setTvRight("确定");
        setmTvRightVisible(1);
    }
}
