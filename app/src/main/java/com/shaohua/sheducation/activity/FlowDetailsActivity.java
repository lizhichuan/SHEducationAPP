package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shaohua.sheducation.R;
import com.shaohua.sheducation.fragement.FlowDetailsAbalanceFragment;
import com.shaohua.sheducation.fragement.FlowDetailsCoinsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlowDetailsActivity extends SHBaseActivity {

    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private String[] mTitles = new String[]{"余额", "金币"};
    private FlowDetailsAbalanceFragment flowDetailsAbalanceFragment;
    private FlowDetailsCoinsFragment flowDetailsCoinsFragment;
    private List<Fragment> list_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_flow_details);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("明细");
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        list_fragment = new ArrayList<>();
        flowDetailsAbalanceFragment = new FlowDetailsAbalanceFragment();
        flowDetailsCoinsFragment = new FlowDetailsCoinsFragment();
        list_fragment.add(flowDetailsAbalanceFragment);
        list_fragment.add(flowDetailsCoinsFragment);
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
}
