package com.shaohua.sheducation.fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.activity.ClassmateSetActivity;
import com.shaohua.sheducation.activity.UserHomepageActivity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.FriendInviteResultVO;
import com.shaohua.sheducation.resultbean.GetUserListResultVO;
import com.shaohua.sheducation.resultbean.GetUserRecommendResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

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

public class ClassmateRecommendFragment extends BaseFragment {

    @Bind(R.id.rcv_hot)
    RecyclerView rcvHot;
    @Bind(R.id.tv_filter)
    TextView tvFilter;
    @Bind(R.id.rcv_all)
    RecyclerView rcvAll;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetUserRecommendResultVO.ResultBean> mAdapterHot;
    private List<GetUserRecommendResultVO.ResultBean> mDatasHot;

    private CoolCommonRecycleviewAdapter<GetUserListResultVO.ResultBean.DataBean> mAdapterAll;
    private List<GetUserListResultVO.ResultBean.DataBean> mDatasAll;

    private LinearLayoutManager linearLayoutManagerHot;
    private LinearLayoutManager linearLayoutManagerAll;


    private int selectPosition = 0;
    private boolean hot = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManagerHot = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvHot.setLayoutManager(linearLayoutManagerHot);
        mAdapterHot = new CoolCommonRecycleviewAdapter<GetUserRecommendResultVO.ResultBean>(mDatasHot, getActivity(),
                R.layout.item_classmate_recommend) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                ImageView img_gender = holder.getView(R.id.img_gender);
                ImageView img_invite = holder.getView(R.id.img_invite);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_distance = holder.getView(R.id.tv_distance);
                TextView tv_status = holder.getView(R.id.tv_status);
                RecyclerView rcv_tags = holder.getView(R.id.rcv_tags);
                LinearLayoutManager li = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(mDatasHot.get(position).getLabels(), getActivity(), R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int subposition) {
                        TextView tv_tag = holder.getView(R.id.tv_tag);
                        tv_tag.setText(mDatasHot.get(position).getLabels().get(subposition).getName());
                    }

                });

                CoolGlideUtil.urlInto(getActivity(), mDatasHot.get(position).getAvatar(), img_head);
                tv_name.setText(mDatasHot.get(position).getNickName());
                if (mDatasHot.get(position).getGender() == 1) {
                    img_gender.setImageResource(R.mipmap.icon_women);
                } else {
                    img_gender.setImageResource(R.mipmap.icon_man);
                }
                //关系：0不是好友，1推荐(用户列表忽略该类型)，2发送邀请，3受邀，4互相好友 ,
                if (mDatasHot.get(position).getFriendStatus() == 0) {
                    img_invite.setVisibility(View.VISIBLE);
                    tv_status.setVisibility(View.GONE);

                } else if (mDatasHot.get(position).getFriendStatus() == 1) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.GONE);
                } else if (mDatasHot.get(position).getFriendStatus() == 2) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.VISIBLE);
                } else if (mDatasHot.get(position).getFriendStatus() == 3) {
                    img_invite.setVisibility(View.VISIBLE);
                    tv_status.setVisibility(View.GONE);
                } else if (mDatasHot.get(position).getFriendStatus() == 4) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.GONE);
                }
                img_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        hot = true;
                        FriendInvite(getActivity(), mDatasAll.get(position).getId());
                    }
                });
            }
        };
        rcvHot.setAdapter(mAdapterHot);

        linearLayoutManagerAll = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvAll.setLayoutManager(linearLayoutManagerAll);
        mAdapterAll = new CoolCommonRecycleviewAdapter<GetUserListResultVO.ResultBean.DataBean>(mDatasAll, getActivity(),
                R.layout.item_classmate_recommend) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                ImageView img_gender = holder.getView(R.id.img_gender);
                ImageView img_invite = holder.getView(R.id.img_invite);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_distance = holder.getView(R.id.tv_distance);
                TextView tv_status = holder.getView(R.id.tv_status);
                RecyclerView rcv_tags = holder.getView(R.id.rcv_tags);
                LinearLayoutManager li = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(mDatasAll.get(position).getLabels(),
                        getActivity(), R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int subposition) {
                        TextView tv_tag = holder.getView(R.id.tv_tag);
                        tv_tag.setText(mDatasAll.get(position).getLabels().get(subposition).getName());
                    }

                });

                CoolGlideUtil.urlInto(getActivity(), mDatasAll.get(position).getAvatar(), img_head);
                tv_name.setText(mDatasAll.get(position).getNickName());
                if (mDatasAll.get(position).getGender() == 1) {
                    img_gender.setImageResource(R.mipmap.icon_women);
                } else {
                    img_gender.setImageResource(R.mipmap.icon_man);
                }
                //关系：0不是好友，1推荐(用户列表忽略该类型)，2发送邀请，3受邀，4互相好友 ,
                if (mDatasAll.get(position).getFriendStatus() == 0) {
                    img_invite.setVisibility(View.VISIBLE);
                    tv_status.setVisibility(View.GONE);

                } else if (mDatasAll.get(position).getFriendStatus() == 1) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.GONE);
                } else if (mDatasAll.get(position).getFriendStatus() == 2) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.VISIBLE);
                } else if (mDatasAll.get(position).getFriendStatus() == 3) {
                    img_invite.setVisibility(View.VISIBLE);
                    tv_status.setVisibility(View.GONE);
                } else if (mDatasAll.get(position).getFriendStatus() == 4) {
                    img_invite.setVisibility(View.GONE);
                    tv_status.setVisibility(View.GONE);
                }

                img_invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        hot = false;
                        FriendInvite(getActivity(), mDatasAll.get(position).getId());
                    }
                });

            }
        };
        rcvAll.setAdapter(mAdapterAll);
        rcvAll.setNestedScrollingEnabled(false);
        rcvHot.setNestedScrollingEnabled(false);
//        swp.setColorSchemeColors(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserList(true);
            }
        });

        mAdapterAll.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), UserHomepageActivity.class)
                        .putExtra("id", mDatasAll.get(position).getId()));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mAdapterHot.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), UserHomepageActivity.class)
                        .putExtra("id", mDatasHot.get(position).getId()));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        GetUserRecommend(getActivity(), CoolSPUtil.getDataFromLoacl(getActivity(), "labels"));
        getUserList(true);
        return view;
    }

    String distance;
    int education;
    int gender;
    int age;
    int page = 1;

    private void getUserList(boolean refresh) {
        GetUserList(getActivity(), CoolSPUtil.getDataFromLoacl(getActivity(), "labels"), distance,
                education, gender, age, page, 15);
    }


    public void setDate(GetUserListResultVO.ResultBean getUserListVO) {
        swp.setRefreshing(false);
        if (getUserListVO.getData() != null && getUserListVO.getData().size() > 0) {
            if (page == 1) {
                mDatasAll = getUserListVO.getData();
            } else {
                for (int i = 0; i < getUserListVO.getData().size(); i++) {
                    mDatasAll.add(getUserListVO.getData().get(i));
                }
            }
            mAdapterAll.setmDatas(mDatasAll);
            mAdapterAll.notifyDataSetChanged();

            if (getUserListVO.getCount() > mDatasAll.size()) {
                //更多
            } else {
                //无更多
            }
        } else {

        }

    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_filter)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ClassmateSetActivity.class));
    }


    private void FriendInvite(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<FriendInviteResultVO> call = CoolHttpUrl.getApi().getService().FriendInvite(id);
            call.enqueue(new Callback<FriendInviteResultVO>() {
                @Override
                public void onResponse(Call<FriendInviteResultVO> call, Response<FriendInviteResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        FriendInviteResultVO data = response.body();
                        CoolLogTrace.e("FriendInvite", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (hot) {
                                mDatasHot.get(selectPosition).setFriendStatus(2);
                                mAdapterHot.notifyDataSetChanged();
                            } else {
                                mDatasAll.get(selectPosition).setFriendStatus(2);
                                mAdapterAll.notifyDataSetChanged();
                            }
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<FriendInviteResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void GetUserList(final Context context, String labels, String distance,
                             int education, int gender,
                             int age, int page,
                             int pageSize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserListResultVO> call = CoolHttpUrl.getApi().getService().GetUserList(labels, distance,
                    education, gender, age, page, pageSize);
            call.enqueue(new Callback<GetUserListResultVO>() {
                @Override
                public void onResponse(Call<GetUserListResultVO> call, Response<GetUserListResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetUserListResultVO data = response.body();
                        CoolLogTrace.e("GetUserList", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            setDate(data.getResult());
                        } else {

                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetUserListResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void GetUserRecommend(final Context context, String labels) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserRecommendResultVO> call = CoolHttpUrl.getApi().getService().GetUserRecommend(labels);
            call.enqueue(new Callback<GetUserRecommendResultVO>() {
                @Override
                public void onResponse(Call<GetUserRecommendResultVO> call, Response<GetUserRecommendResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetUserRecommendResultVO data = response.body();
                        CoolLogTrace.e("GetUserRecommend", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (data.getResult() != null && data.getResult().size() > 0) {
                                mDatasHot = data.getResult();
                                mAdapterHot.setmDatas(mDatasHot);
                                mAdapterHot.notifyDataSetChanged();
                            }
                        } else {

                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetUserRecommendResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

}

