package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchPlanActivity extends SHBaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.et_key)
    EditText etKey;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.rcv_plans)
    RecyclerView rcvPlans;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<String> adapter;
    private List<String> mDatas;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plan);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        linearLayoutManager = new LinearLayoutManager(SearchPlanActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvPlans.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<String>(mDatas, SearchPlanActivity.this, R.layout.item_home_recommend_plan) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                ImageView img_pic = holder.getView(R.id.img_pic);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
            }
        };
        rcvPlans.setAdapter(adapter);
        setmDatas();
    }

    private void setmDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas.add("");
        }
        adapter.setmDatas(mDatas);
    }

    @OnClick({R.id.img_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_search:
                break;
        }
    }
}
