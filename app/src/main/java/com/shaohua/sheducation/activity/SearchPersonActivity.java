package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchPersonActivity extends SHBaseActivity {

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
    private List<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_person);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        tags = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tags.add("");
        }
        linearLayoutManager = new LinearLayoutManager(SearchPersonActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvPlans.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<String>(mDatas, SearchPersonActivity.this,
                R.layout.item_classmate_search) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                ImageView img_gender = holder.getView(R.id.img_gender);
                ImageView img_invite = holder.getView(R.id.img_invite);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_distance = holder.getView(R.id.tv_distance);
                TextView tv_status = holder.getView(R.id.tv_status);
                TextView tv_nums = holder.getView(R.id.tv_nums);
                LinearLayout linear_nums = holder.getView(R.id.linear_nums);
                LinearLayout linear_content = holder.getView(R.id.linear_content);
                RecyclerView rcv_tags = holder.getView(R.id.rcv_tags);
                LinearLayoutManager li = new LinearLayoutManager(SearchPersonActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(tags, SearchPersonActivity.this,
                        R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int position) {

                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                    }
                });

                if (position == 0) {
                    linear_content.setVisibility(View.GONE);
                    linear_nums.setVisibility(View.VISIBLE);
                } else {
                    linear_content.setVisibility(View.VISIBLE);
                    linear_nums.setVisibility(View.GONE);
                }
            }
        };
        rcvPlans.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(UserHomepageActivity.class);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
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
