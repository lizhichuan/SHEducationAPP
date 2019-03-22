package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaohua.sheducation.R;
import com.shaohua.sheducation.fragement.PlanDetailsFragment;
import com.shaohua.sheducation.fragement.PlanStoreNoHeaderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanDetailsNoHeaderActivity extends SHBaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_help)
    ImageView imgHelp;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private String[] mTitles = new String[]{"详情", "里程碑"};
    private PlanDetailsFragment planDetailsFragment;
    private PlanStoreNoHeaderFragment storeFragment;
    private List<Fragment> list_fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details_no_header);
        ButterKnife.bind(this);
        findViews();
    }


    private void findViews() {
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        list_fragment = new ArrayList<>();
        planDetailsFragment = new PlanDetailsFragment();
        storeFragment = new PlanStoreNoHeaderFragment();
        list_fragment.add(planDetailsFragment);
        list_fragment.add(storeFragment);
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_fragment.get(position);
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
        //TabLayout和ViewPager的关联
        slidingTabs.setupWithViewPager(viewpager);
    }

    @OnClick({R.id.img_back, R.id.img_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_help:
                break;
        }
    }
}
