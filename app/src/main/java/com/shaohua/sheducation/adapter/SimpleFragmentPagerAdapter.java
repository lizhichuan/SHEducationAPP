//package com.shaohua.sheducation.adapter;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.shaohua.sheducation.fragement.ClassmateChatFragment;
//
///**
// * Created by chuan on 2017/11/6.
// */
//
//public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
//
//    final int PAGE_COUNT = 2;
//    private String tabTitles[] = new String[]{"推荐", "同学录"};
//    private Context context;
//
//    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
//        super(fm);
//        this.context = context;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return ClassmateChatFragment.newInstance(position + 1);
//    }
//
//    @Override
//    public int getCount() {
//        return PAGE_COUNT;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return tabTitles[position];
//    }
//}
