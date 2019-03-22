package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.rooler.CoolIconHintView;
import com.njcool.lzccommon.view.rooler.CoolRollPagerView;
import com.njcool.lzccommon.vo.CoolADInfo;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.adapter.TestNormalAdapter;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.fragement.HomeAllFragment;
import com.shaohua.sheducation.fragement.HomeMeFragment;
import com.shaohua.sheducation.fragement.HomeRecommendFragment;
import com.shaohua.sheducation.resultbean.GetBannersResultVO;
import com.shaohua.sheducation.resultbean.GetInformationResultVO;
import com.xiaosu.DataSetAdapter;
import com.xiaosu.VerticalRollingTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanActivity extends SHBaseActivity {


    @Bind(R.id.rl_ads)
    CoolRollPagerView rlAds;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.img_message)
    ImageView imgMessage;
    @Bind(R.id.linear_notices)
    LinearLayout linearNotices;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.rollingView)
    VerticalRollingTextView rollingView;
    private List<CoolADInfo> adInfos;
    private TestNormalAdapter normalAdapter;


    private String[] mTitles = new String[]{"我的", "推荐", "全部"};
    private HomeMeFragment homeMeFragment;
    private HomeRecommendFragment homeRecommendFragment;
    private HomeAllFragment homeAllFragment;
    private List<Fragment> list_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {
        //设置透明度
        rlAds.setAnimationDurtion(500);
        //设置适配器
        normalAdapter = new TestNormalAdapter(PlanActivity.this);
        rlAds.setAdapter(normalAdapter);
        rlAds.setHintView(new CoolIconHintView(PlanActivity.this,
                R.mipmap.dott_f, R.mipmap.dott_n));


        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        list_fragment = new ArrayList<>();
        homeMeFragment = new HomeMeFragment();
        homeRecommendFragment = new HomeRecommendFragment();
        homeAllFragment = new HomeAllFragment();
        list_fragment.add(homeMeFragment);
        list_fragment.add(homeRecommendFragment);
        list_fragment.add(homeAllFragment);
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

        GetBanners(PlanActivity.this);

        GetInformation(PlanActivity.this, CoolSPUtil.getDataFromLoacl(PlanActivity.this,
                "labels"));

    }


    @OnClick({R.id.tv_address, R.id.img_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:

                break;
            case R.id.img_message:
                startActivity(MessageActivity.class);
                break;
        }
    }

    private void GetBanners(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetBannersResultVO> call = CoolHttpUrl.getApi().getService().GetBanners();
            call.enqueue(new Callback<GetBannersResultVO>() {
                @Override
                public void onResponse(Call<GetBannersResultVO> call, Response<GetBannersResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetBannersResultVO result = response.body();
                        CoolLogTrace.e("GetBanners", "", GsonUtil.gson().toJson(result).toString());
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (result.getResult() != null && result.getResult().size() > 0) {
                                adInfos = new ArrayList<>();
                                for (int i = 0; i < result.getResult().size(); i++) {
                                    CoolADInfo coolADInfo = new CoolADInfo();
                                    coolADInfo.setImgUrl(result.getResult().get(i).getImage());
                                    coolADInfo.setContent(result.getResult().get(i).getContent());
                                    coolADInfo.setTargetUrl(result.getResult().get(i).getOtherLink());
                                    coolADInfo.setKeyWords(result.getResult().get(i).getTitle());
                                    adInfos.add(coolADInfo);
                                }
                                normalAdapter.setAdInfos(adInfos);
                                normalAdapter.notifyDataSetChanged();
                            }
                        } else {
                            SHHttpUtil.resultCode(context, result.getStatus(), result.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetBannersResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void GetInformation(final Context context, String labels) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetInformationResultVO> call = CoolHttpUrl.getApi().getService().GetInformation(labels);
            call.enqueue(new Callback<GetInformationResultVO>() {
                @Override
                public void onResponse(Call<GetInformationResultVO> call, Response<GetInformationResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        final GetInformationResultVO data = response.body();
                        CoolLogTrace.e("GetInformation", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (data.getResult() != null && data.getResult().size() > 0) {
                                linearNotices.setVisibility(View.VISIBLE);
                                rollingView.setDataSetAdapter(new DataSetAdapter<GetInformationResultVO.ResultBean>(data.getResult()) {
                                    @Override
                                    protected String text(GetInformationResultVO.ResultBean s) {
                                        return s.getTitle();
                                    }
                                });
                                // 开始滚动
                                rollingView.run();

                                // 设置点击监听
                                rollingView.setOnItemClickListener(new VerticalRollingTextView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(VerticalRollingTextView view, int index) {
                                        // your code
                                        CoolPublicMethod.Toast(PlanActivity.this, data.getResult().get(index).getTitle());
                                    }
                                });
                            } else {
                                linearNotices.setVisibility(View.GONE);
                            }
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetInformationResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
