package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.fragement.PlanDetailsFragment;
import com.shaohua.sheducation.fragement.PlanStoreFragment;
import com.shaohua.sheducation.resultbean.FailVO;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.GetUserInfoVO;
import com.shaohua.sheducation.resultbean.PlanMemberVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author chuan
 * @date 2017/11/08
 */
public class PlanDetailsActivity extends SHBaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_help)
    ImageView imgHelp;
    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private String[] mTitles = new String[]{"详情", "里程碑"};
    private PlanDetailsFragment planDetailsFragment;
    //    private PlanStoreFragment storeFragment;
    private List<Fragment> list_fragment;

    private int id = 0;
    public static boolean PlanMeCreate = false;
    public static boolean PlanMeInvited = false;
    public static boolean PlanMeMember = false;
//
    public static PlanMemberVO CreaterBean = new PlanMemberVO();
    public static PlanMemberVO MemberBean = new PlanMemberVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);
        ButterKnife.bind(this);
        PlanMeCreate = false;
        id = getIntent().getIntExtra("id", 0);
        findViews();
    }

    private void findViews() {
        slidingTabs.setupWithViewPager(viewpager);
        slidingTabs.setTabMode(TabLayout.MODE_FIXED);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putSerializable("plan", id);
        list_fragment = new ArrayList<>();
        planDetailsFragment = new PlanDetailsFragment();
        list_fragment.add(planDetailsFragment);
        list_fragment.add(PlanStoreFragment.getInstance(bundle));
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
//        for (int i = 0; i < slidingTabs.getTabCount(); i++) {
//            TabLayout.Tab tab = slidingTabs.getTabAt(i);
//            tab.setCustomView(getTabView(i));
//        }

        GetPlanInfo(PlanDetailsActivity.this, id);
    }


    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
    }


    @Subscribe
    public void onEventMainThread(GetPlanInfoResultVO.ResultBean getPlanInfoVO) {
        if (getPlanInfoVO.getUserId() == Integer.valueOf(CoolSPUtil.getDataFromLoacl(PlanDetailsActivity.this,
                "uid").toString())) {
            PlanMeCreate = true;
        } else {
            PlanMeCreate = false;
        }
        CoolGlideUtil.urlInto(PlanDetailsActivity.this, getPlanInfoVO.getImage(), imgPic);
        tvTitle.setText(getPlanInfoVO.getName());
        tvSummary.setText(getPlanInfoVO.getDescription());
        for (int i = 0; i < getPlanInfoVO.getInvites().size(); i++) {
            if (CoolSPUtil.getDataFromLoacl(PlanDetailsActivity.this, "uid").toString()
                    .equalsIgnoreCase(getPlanInfoVO.getInvites().get(i).getInvitedId() + "")) {
                PlanMeInvited = true;
            }
        }
        for (int i = 0; i < getPlanInfoVO.getMembers().size(); i++) {
            if (getPlanInfoVO.getUserId() == getPlanInfoVO.getMembers().get(i).getUserId()) {
                CreaterBean.setDepositMoney(getPlanInfoVO.getMembers().get(i).getDepositMoney());
                CreaterBean.setFinishStatus(getPlanInfoVO.getMembers().get(i).getFinishStatus());
                CreaterBean.setId(getPlanInfoVO.getMembers().get(i).getId());
                CreaterBean.setMilestoneFinishNum(getPlanInfoVO.getMembers().get(i).getMilestoneFinishNum());
                CreaterBean.setMilestoneNum(getPlanInfoVO.getMembers().get(i).getMilestoneNum());
                CreaterBean.setPayStatus(getPlanInfoVO.getMembers().get(i).getPayStatus());
                CreaterBean.setMilestoneStatus(getPlanInfoVO.getMembers().get(i).getMilestoneStatus());
                CreaterBean.setPlanId(getPlanInfoVO.getMembers().get(i).getPlanId());
                CreaterBean.setRefuseReason(getPlanInfoVO.getMembers().get(i).getRefuseReason());
                CreaterBean.setStatus(getPlanInfoVO.getMembers().get(i).getStatus());
                CreaterBean.setTestDest(getPlanInfoVO.getMembers().get(i).getTestDest());
                CreaterBean.setUserId(getPlanInfoVO.getMembers().get(i).getUserId());
            } else {
                MemberBean.setDepositMoney(getPlanInfoVO.getMembers().get(i).getDepositMoney());
                MemberBean.setFinishStatus(getPlanInfoVO.getMembers().get(i).getFinishStatus());
                MemberBean.setId(getPlanInfoVO.getMembers().get(i).getId());
                MemberBean.setMilestoneFinishNum(getPlanInfoVO.getMembers().get(i).getMilestoneFinishNum());
                MemberBean.setMilestoneNum(getPlanInfoVO.getMembers().get(i).getMilestoneNum());
                MemberBean.setPayStatus(getPlanInfoVO.getMembers().get(i).getPayStatus());
                MemberBean.setMilestoneStatus(getPlanInfoVO.getMembers().get(i).getMilestoneStatus());
                MemberBean.setPlanId(getPlanInfoVO.getMembers().get(i).getPlanId());
                MemberBean.setRefuseReason(getPlanInfoVO.getMembers().get(i).getRefuseReason());
                MemberBean.setStatus(getPlanInfoVO.getMembers().get(i).getStatus());
                MemberBean.setTestDest(getPlanInfoVO.getMembers().get(i).getTestDest());
                MemberBean.setUserId(getPlanInfoVO.getMembers().get(i).getUserId());
            }

            if (CoolSPUtil.getDataFromLoacl(PlanDetailsActivity.this, "uid").toString()
                    .equalsIgnoreCase(getPlanInfoVO.getMembers().get(i).getUserId() + "")) {
                PlanMeMember = true;
            }

        }

        //        CoolGlideUtil.urlInto(PlanDetailsActivity.this, getPlanInfoVO.getMembers().get(0).getAvatar(), imgHead);
//        tvName.setText(getPlanInfoVO.getMembers().get(0).getNickName());
        GetUserInfo(PlanDetailsActivity.this, getPlanInfoVO.getUserId());
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

    public static void GetPlanInfo(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanInfoResultVO> call = CoolHttpUrl.getApi().getService().GetPlanInfo(id);
            call.enqueue(new Callback<GetPlanInfoResultVO>() {
                @Override
                public void onResponse(Call<GetPlanInfoResultVO> call, Response<GetPlanInfoResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetPlanInfoResultVO data = response.body();
                        CoolLogTrace.e("GetPlanInfo", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            EventBus.getDefault().post(data.getResult());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetPlanInfoResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void GetUserInfo(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserInfoVO> call = CoolHttpUrl.getApi().getService().GetUserInfo(id);
            call.enqueue(new Callback<GetUserInfoVO>() {
                @Override
                public void onResponse(Call<GetUserInfoVO> call, Response<GetUserInfoVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetUserInfoVO data = response.body();
                        CoolLogTrace.e("GetUserInfo", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            EventBus.getDefault().post(data.getResult());
                            CoolGlideUtil.urlInto(PlanDetailsActivity.this, data.getResult().getAvatar(), imgHead);
                            tvName.setText(data.getResult().getNickName());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetUserInfoVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

}
