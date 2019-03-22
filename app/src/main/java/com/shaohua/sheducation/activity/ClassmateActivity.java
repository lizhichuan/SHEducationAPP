package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.fragement.ClassmateChatFragment;
import com.shaohua.sheducation.fragement.ClassmateFriendsFragment;
import com.shaohua.sheducation.fragement.ClassmateRecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassmateActivity extends SHBaseActivity {


    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.img_add_classmate)
    ImageView imgAddClassmate;
    @Bind(R.id.img_search)
    ImageView imgSearch;


    private List<Fragment> list_fragment;

    private String[] mTitles = new String[]{"推荐", "聊天", "好友"};
    private ClassmateRecommendFragment fragmentRecommend;
    private ClassmateChatFragment classmateChatFragment;
    private ClassmateFriendsFragment classmateFriendsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classmate);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        list_fragment = new ArrayList<>();
        fragmentRecommend = new ClassmateRecommendFragment();
        classmateChatFragment = new ClassmateChatFragment();
        classmateFriendsFragment = new ClassmateFriendsFragment();
        list_fragment.add(fragmentRecommend);
        list_fragment.add(classmateChatFragment);
        list_fragment.add(classmateFriendsFragment);
        slidingTabs.post(new Runnable() {
            @Override
            public void run() {
                CoolPublicMethod.setIndicator(slidingTabs, 15, 15);
            }
        });
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

    @OnClick({R.id.img_add_classmate, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_add_classmate:
                startActivity(NewFriendsInviteActivity.class);
                break;
            case R.id.img_search:
                startActivity(SearchPersonActivity.class);
                break;
        }
    }
}
