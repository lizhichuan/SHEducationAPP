package com.shaohua.sheducation.fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shaohua.sheducation.activity.CreatePlanActivity;
import com.shaohua.sheducation.activity.InviteMeJoinPlanActivity;
import com.shaohua.sheducation.activity.PlanDetails2Activity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetPlanInvitedResultVO;
import com.shaohua.sheducation.resultbean.GetPlanMyResultVO;
import com.shaohua.sheducation.view.CircleProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chuan on 2017/11/6.
 */

public class HomeMeFragment extends BaseFragment {


    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;
    @Bind(R.id.tv_create)
    TextView tvCreate;
    @Bind(R.id.tv_no_plan)
    LinearLayout tvNoPlan;
    @Bind(R.id.rcv_home_me)
    RecyclerView rcvHomeMe;
    @Bind(R.id.tv_person_nums)
    TextView tvPersonNums;
    @Bind(R.id.img_invite_one)
    CoolCircleImageView imgInviteOne;
    @Bind(R.id.img_invite_two)
    CoolCircleImageView imgInviteTwo;
    @Bind(R.id.linear_new_invite)
    LinearLayout linearNewInvite;

    private CoolCommonRecycleviewAdapter<GetPlanMyResultVO.ResultBean.DataBean> adapter;
    private List<GetPlanMyResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;

    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_me, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvHomeMe.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanMyResultVO.ResultBean.DataBean>(mDatas, getActivity(), R.layout.item_home_me_plan) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                CardView cv_home_me = holder.getView(R.id.cv_home_me);
                TextView tv_add_new = holder.getView(R.id.tv_add_new);
                ImageView img_pic = holder.getView(R.id.img_pic);
                TextView tv_title = holder.getView(R.id.tv_title);
                CircleProgressView cp_percent = holder.getView(R.id.cp_percent);
                CoolCircleImageView img_person_one = holder.getView(R.id.img_person_one);
                CoolCircleImageView img_person_two = holder.getView(R.id.img_person_two);


                if (position == mDatas.size() - 1) {
                    tv_add_new.setVisibility(View.VISIBLE);
                    cv_home_me.setVisibility(View.GONE);
                } else {
                    tv_add_new.setVisibility(View.GONE);
                    cv_home_me.setVisibility(View.VISIBLE);
                }
                tv_add_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), CreatePlanActivity.class));
                    }
                });

                CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getImage(), img_pic);
                tv_title.setText(mDatas.get(position).getName());
                cp_percent.setProgress(mDatas.get(position).getCompletePercent());
                if (mDatas.get(position).getMembers() != null && mDatas.get(position).getMembers().size() > 0) {
                    if (mDatas.get(position).getMembers().size() == 1) {
                        CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getMembers().get(0).getAvatar(), img_person_one);
                        img_person_one.setVisibility(View.VISIBLE);
                        img_person_two.setVisibility(View.INVISIBLE);
                    } else if (mDatas.get(position).getMembers().size() == 2) {
                        CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getMembers().get(0).getAvatar(), img_person_one);
                        CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getMembers().get(1).getAvatar(), img_person_two);
                        img_person_one.setVisibility(View.VISIBLE);
                        img_person_two.setVisibility(View.VISIBLE);
                    }
                } else {
                    img_person_one.setVisibility(View.INVISIBLE);
                    img_person_two.setVisibility(View.INVISIBLE);
                }
            }
        };
        rcvHomeMe.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), PlanDetails2Activity.class)
                        .putExtra("id", mDatas.get(position).getId()));
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
        rcvHomeMe.addOnScrollListener(coolRecycleViewLoadMoreListener);
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList(true);
            }
        });
        getList(true);
        return view;
    }

    private void getList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetPlanMy(getActivity(), page, 15, -1);

    }

    @Override
    public void onResume() {
        super.onResume();
        GetPlanInvited(getActivity());
    }

    private void setmDatas(GetPlanMyResultVO.ResultBean getPlanMyVO) {
        if (getPlanMyVO.getData() != null && getPlanMyVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getPlanMyVO.getData();
                mDatas.add(new GetPlanMyResultVO.ResultBean.DataBean());
            } else {
                mDatas.remove(mDatas.size() - 1);
                for (int i = 0; i < getPlanMyVO.getData().size(); i++) {
                    mDatas.add(getPlanMyVO.getData().get(i));
                }
                mDatas.add(new GetPlanMyResultVO.ResultBean.DataBean());
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();

            if (getPlanMyVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            coolRecycleViewLoadMoreListener.isLoading = true;
        }

        if (mDatas == null || mDatas.size() == 0) {
            tvNoPlan.setVisibility(View.VISIBLE);
            swp.setVisibility(View.GONE);
        } else {
            tvNoPlan.setVisibility(View.GONE);
            swp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.linear_new_invite, R.id.tv_create})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linear_new_invite:
                startActivity(new Intent(getActivity(), InviteMeJoinPlanActivity.class));
                break;
            case R.id.tv_create:
                startActivity(new Intent(getActivity(), CreatePlanActivity.class));
                break;
        }
    }

    private void GetPlanMy(final Context context, final int page, int pagesize, int status) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanMyResultVO> call = CoolHttpUrl.getApi().getService().GetPlanMy(status, page, pagesize);
            call.enqueue(new Callback<GetPlanMyResultVO>() {
                @Override
                public void onResponse(Call<GetPlanMyResultVO> call, Response<GetPlanMyResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetPlanMyResultVO data = response.body();
                        CoolLogTrace.e("GetPlanMy", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetPlanMyResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void GetPlanInvited(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanInvitedResultVO> call = CoolHttpUrl.getApi().getService().GetPlanInvited(1, 15);
            call.enqueue(new Callback<GetPlanInvitedResultVO>() {
                @Override
                public void onResponse(Call<GetPlanInvitedResultVO> call, Response<GetPlanInvitedResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetPlanInvitedResultVO data = response.body();
                        CoolLogTrace.e("GetPlanInvited", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (data.getResult().getCount() != 0) {
                                linearNewInvite.setVisibility(View.VISIBLE);
                                tvPersonNums.setText(data.getResult().getCount() + "位");
                                if (data.getResult().getData() != null && data.getResult().getData().size() >= 2) {
                                    CoolGlideUtil.urlInto(getActivity(), data.getResult().getData().get(0).getInvite().getAvatar(), imgInviteOne);
                                    CoolGlideUtil.urlInto(getActivity(), data.getResult().getData().get(1).getInvite().getAvatar(), imgInviteTwo);
                                    imgInviteTwo.setVisibility(View.VISIBLE);
                                    imgInviteOne.setVisibility(View.VISIBLE);
                                } else if (data.getResult().getData() != null && data.getResult().getData().size() == 1) {
                                    CoolGlideUtil.urlInto(getActivity(), data.getResult().getData().get(0).getInvite().getAvatar(), imgInviteOne);
                                    imgInviteTwo.setVisibility(View.INVISIBLE);
                                    imgInviteOne.setVisibility(View.VISIBLE);
                                }
                            } else {
                                linearNewInvite.setVisibility(View.GONE);
                            }
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
