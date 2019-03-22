package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.PlanInviteBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetPlanMatchResultVO;
import com.shaohua.sheducation.resultbean.PlanInviteResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendPairPersonActivity extends SHBaseActivity {

    @Bind(R.id.rcv_persons)
    RecyclerView rcvPersons;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetPlanMatchResultVO.ResultBean.DataBean> adapter;
    private List<GetPlanMatchResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;

    private int page;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;
    private int planId;

    private int selectPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_recommend_pair_person);
        ButterKnife.bind(this);
        planId = getIntent().getIntExtra("id", 0);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
    }

    private void findViews() {
        if (planId == 0) {
            finish();
        }
        setmTopTitle("推荐配对用户");

        linearLayoutManager = new LinearLayoutManager(RecommendPairPersonActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvPersons.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanMatchResultVO.ResultBean.DataBean>(mDatas, RecommendPairPersonActivity.this,
                R.layout.item_classmate_pair) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                ImageView img_gender = holder.getView(R.id.img_gender);
                ImageView img_invite = holder.getView(R.id.img_invite);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_distance = holder.getView(R.id.tv_distance);
                TextView tv_status = holder.getView(R.id.tv_status);
                LinearLayout linear_nums = holder.getView(R.id.linear_nums);
                LinearLayout linear_content = holder.getView(R.id.linear_content);
                RecyclerView rcv_tags = holder.getView(R.id.rcv_tags);
                LinearLayoutManager li = new LinearLayoutManager(RecommendPairPersonActivity.this,
                        LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(mDatas.get(position).getLabels(),
                        RecommendPairPersonActivity.this,
                        R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int position) {

                    }
                });
                if (position == 0) {
                    linear_content.setVisibility(View.GONE);
                    linear_nums.setVisibility(View.VISIBLE);
                } else {
                    linear_content.setVisibility(View.VISIBLE);
                    linear_nums.setVisibility(View.GONE);
                }

                CoolGlideUtil.urlInto(RecommendPairPersonActivity.this, mDatas.get(position).getAvatar(), img_head);
                tv_name.setText(mDatas.get(position).getNickName());
                if (mDatas.get(position).getGender() == 1) {
                    img_gender.setImageResource(R.mipmap.icon_women);
                } else {
                    img_gender.setImageResource(R.mipmap.icon_man);
                }

                if (selectPosition == position) {
                    tv_status.setVisibility(View.VISIBLE);
                    img_invite.setVisibility(View.GONE);
                } else {
                    tv_status.setVisibility(View.GONE);
                    img_invite.setVisibility(View.VISIBLE);
                }

                img_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        PlanInviteBody planInviteRequest = new PlanInviteBody();
                        planInviteRequest.setUserId(mDatas.get(position).getId());
                        PlanInvite(RecommendPairPersonActivity.this, planId, planInviteRequest);
                    }
                });
            }
        };
        rcvPersons.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(UserHomepageActivity.class);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList(true);
            }
        });
        coolRecycleViewLoadMoreListener = new CoolRecycleViewLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                coolRecycleViewLoadMoreListener.isLoading = false;
                getList(false);
            }

            @Override
            public void onScrollMore() {

            }
        };
        rcvPersons.addOnScrollListener(coolRecycleViewLoadMoreListener);
        getList(true);
    }

    private void getList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetPlanMatch(RecommendPairPersonActivity.this, planId, page, 15);

    }


    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    public void setData(GetPlanMatchResultVO.ResultBean getPlanMatchVO) {
        swp.setRefreshing(false);
        if (getPlanMatchVO.getData() != null && getPlanMatchVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getPlanMatchVO.getData();
                mDatas.add(0, new GetPlanMatchResultVO.ResultBean.DataBean());
            } else {
                for (int i = 0; i < getPlanMatchVO.getData().size(); i++) {
                    mDatas.add(getPlanMatchVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            if (getPlanMatchVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                CoolPublicMethod.Toast(RecommendPairPersonActivity.this, "暂无推荐用户");
            }
            coolRecycleViewLoadMoreListener.isLoading = true;
        }

    }


    private void GetPlanMatch(final Context context, int id, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanMatchResultVO> call = CoolHttpUrl.getApi().getService().GetPlanMatch(id, page, pagesize);
            call.enqueue(new Callback<GetPlanMatchResultVO>() {
                @Override
                public void onResponse(Call<GetPlanMatchResultVO> call, Response<GetPlanMatchResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetPlanMatchResultVO data = response.body();
                        CoolLogTrace.e("GetPlanMatch", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            setData(data.getResult());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetPlanMatchResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void PlanInvite(final Context context, int id, PlanInviteBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanInviteResultVO> call = CoolHttpUrl.getApi().getService().PlanInvite(id, body);
            call.enqueue(new Callback<PlanInviteResultVO>() {
                @Override
                public void onResponse(Call<PlanInviteResultVO> call, Response<PlanInviteResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanInviteResultVO data = response.body();
                        CoolLogTrace.e("PlanInvite", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (swp != null) {
                                swp.setRefreshing(false);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanInviteResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
