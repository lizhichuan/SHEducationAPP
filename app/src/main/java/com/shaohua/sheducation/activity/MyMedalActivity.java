package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyMedalActivity extends SHBaseActivity {

    @Bind(R.id.rcv_get)
    RecyclerView rcvGet;
    @Bind(R.id.rcv_unget)
    RecyclerView rcvUnget;
    @Bind(R.id.nv_medal)
    NestedScrollView nvMedal;

    private List<String> mDatas_get;
    private CoolCommonRecycleviewAdapter<String> adapter_get;

    private List<String> mDatas_unget;
    private CoolCommonRecycleviewAdapter<String> adapter_unget;
    private GridLayoutManager gridLayoutManager_get;
    private GridLayoutManager gridLayoutManager_unget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_medal);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("我的勋章");
        gridLayoutManager_get = new GridLayoutManager(this, 3);
        rcvGet.setLayoutManager(gridLayoutManager_get);
        adapter_get = new CoolCommonRecycleviewAdapter<String>(MyMedalActivity.this,
                mDatas_get, R.layout.item_my_dedal) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {

            }
        };
        rcvGet.setAdapter(adapter_get);

        gridLayoutManager_unget = new GridLayoutManager(this, 3);
        rcvUnget.setLayoutManager(gridLayoutManager_unget);
        adapter_unget = new CoolCommonRecycleviewAdapter<String>(MyMedalActivity.this,
                mDatas_unget, R.layout.item_my_dedal) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {

            }
        };
        rcvUnget.setAdapter(adapter_unget);
        setmDatas_get();
        rcvGet.setNestedScrollingEnabled(false);
        rcvUnget.setNestedScrollingEnabled(false);
    }

    private void setmDatas_get() {
        mDatas_get = new ArrayList<>();
        mDatas_unget = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas_unget.add("");
            mDatas_get.add("");
        }
        adapter_get.setmDatas(mDatas_get);
        adapter_get.notifyDataSetChanged();
        adapter_unget.setmDatas(mDatas_unget);
        adapter_unget.notifyDataSetChanged();
    }
}
