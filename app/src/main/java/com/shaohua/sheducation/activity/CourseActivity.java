package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shaohua.sheducation.R;
import com.shaohua.sheducation.fragement.CourseAllFragment;
import com.shaohua.sheducation.fragement.CourseFavoriteFragment;
import com.shaohua.sheducation.fragement.CourseMeFragment;
import com.shaohua.sheducation.fragement.CourseRecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseActivity extends SHBaseActivity {

    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    private String[] mTitles = new String[]{"全部", "推荐", "我的", "收藏"};
    private List<Fragment> list_fragment;
    private CourseAllFragment courseAllFragment;
    private CourseRecommendFragment courseRecommendFragment;
    private CourseMeFragment courseMeFragment;
    private CourseFavoriteFragment courseFavoriteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_course);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(IntelligentLibraryActivity.class);
    }

    private void findViews() {
        setmTopTitle("学习资料");
        setmTopLeftVisible(0);
        setTvRight("智能库");
        setmTvRightVisible(1);
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        list_fragment = new ArrayList<>();
        courseAllFragment = new CourseAllFragment();
        courseRecommendFragment = new CourseRecommendFragment();
        courseMeFragment = new CourseMeFragment();
        courseFavoriteFragment = new CourseFavoriteFragment();
        list_fragment.add(courseAllFragment);
        list_fragment.add(courseRecommendFragment);
        list_fragment.add(courseMeFragment);
        list_fragment.add(courseFavoriteFragment);

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
