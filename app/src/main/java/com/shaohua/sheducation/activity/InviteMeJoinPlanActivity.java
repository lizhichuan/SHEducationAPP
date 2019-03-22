package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetPlanInvitedResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteMeJoinPlanActivity extends SHBaseActivity {

    @Bind(R.id.rcv_plans)
    RecyclerView rcvPlans;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetPlanInvitedResultVO.ResultBean.DataBean> adapter;
    private List<GetPlanInvitedResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;

    private int page = 1;

    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_invite_me_join_plan);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("邀请的加入的计划(4)");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvPlans.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanInvitedResultVO.ResultBean.DataBean>(mDatas,
                InviteMeJoinPlanActivity.this, R.layout.item_invite_plan) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                CoolRoundAngleImageView img_pic = holder.getView(R.id.img_pic);
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);

                CoolGlideUtil.urlInto(InviteMeJoinPlanActivity.this, mDatas.get(position).getPlan().getImage(), img_pic);
                CoolGlideUtil.urlInto(InviteMeJoinPlanActivity.this, mDatas.get(position).getInvite().getAvatar(), img_head);

                tv_name.setText(mDatas.get(position).getInvite().getNickName());
                tv_title.setText(mDatas.get(position).getPlan().getName());
                tv_time.setText(mDatas.get(position).getInviteTime());
            }
        };
        rcvPlans.setAdapter(adapter);
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swp.setRefreshing(false);
            }
        });
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", mDatas.get(position).getPlanId());
                startActivity(PlanDetails2Activity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        coolRecycleViewLoadMoreListener = new CoolRecycleViewLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                coolRecycleViewLoadMoreListener.isLoading = true;
                getList(false);
            }

            @Override
            public void onScrollMore() {

            }
        };
        rcvPlans.addOnScrollListener(coolRecycleViewLoadMoreListener);
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList(true);
            }
        });
        getList(true);
    }

    private void getList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetPlanInvited(InviteMeJoinPlanActivity.this, page, 15);
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    private void setmDatas(GetPlanInvitedResultVO.ResultBean getPlanInvitedVO) {
        if (getPlanInvitedVO.getData() != null && getPlanInvitedVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getPlanInvitedVO.getData();
            } else {
                for (int i = 0; i < getPlanInvitedVO.getData().size(); i++) {
                    mDatas.add(getPlanInvitedVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            setmTopTitle("邀请加入的计划(" + getPlanInvitedVO.getCount() + ")");
            if (getPlanInvitedVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                CoolPublicMethod.Toast(InviteMeJoinPlanActivity.this, "暂无数据");
                setmTopTitle("邀请加入的计划(0)");
            }
            coolRecycleViewLoadMoreListener.isLoading = true;
        }
    }


    private void GetPlanInvited(final Context context, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanInvitedResultVO> call = CoolHttpUrl.getApi().getService().GetPlanInvited(page, pagesize);
            call.enqueue(new Callback<GetPlanInvitedResultVO>() {
                @Override
                public void onResponse(Call<GetPlanInvitedResultVO> call, Response<GetPlanInvitedResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetPlanInvitedResultVO data = response.body();
                        CoolLogTrace.e("GetPlanInvited", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            setmDatas(data.getResult());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetPlanInvitedResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
