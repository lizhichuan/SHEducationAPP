package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.FriendAcceptReultVO;
import com.shaohua.sheducation.resultbean.GetFriendNewResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFriendsInviteActivity extends SHBaseActivity {

    @Bind(R.id.rcv_invites)
    RecyclerView rcvInvites;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private LinearLayoutManager linearLayoutManager;
    private CoolCommonRecycleviewAdapter<GetFriendNewResultVO.ResultBean.DataBean> adapter;
    private List<GetFriendNewResultVO.ResultBean.DataBean> mDatas;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;

    private int page = 1;

    private int selectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_new_friends_invite);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("新好友");
        linearLayoutManager = new LinearLayoutManager(this);
        rcvInvites.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetFriendNewResultVO.ResultBean.DataBean>(NewFriendsInviteActivity.this,
                mDatas, R.layout.item_classmate_new_friends) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_ok = holder.getView(R.id.tv_ok);
                TextView tv_status = holder.getView(R.id.tv_status);

                CoolGlideUtil.urlInto(NewFriendsInviteActivity.this, mDatas.get(position).getAvatar(), img_head);
                tv_name.setText(mDatas.get(position).getNickName());

                if (mDatas.get(position).getFriendStatus() == 4) {
                    tv_status.setVisibility(View.VISIBLE);
                    tv_ok.setVisibility(View.GONE);
                } else {
                    tv_status.setVisibility(View.GONE);
                    tv_ok.setVisibility(View.VISIBLE);
                }

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        FriendAccept(NewFriendsInviteActivity.this, mDatas.get(position).getId());
                    }
                });

            }
        };
        rcvInvites.setAdapter(adapter);
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
        rcvInvites.addOnScrollListener(coolRecycleViewLoadMoreListener);
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
        GetFriendNew(NewFriendsInviteActivity.this, page, 15);
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    public void setData(GetFriendNewResultVO.ResultBean getFriendNewVO) {
        swp.setRefreshing(false);
        if (getFriendNewVO.getData() != null && getFriendNewVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getFriendNewVO.getData();
            } else {
                for (int i = 0; i < getFriendNewVO.getData().size(); i++) {
                    mDatas.add(getFriendNewVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();

            if (getFriendNewVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {

        }
    }


    private void FriendAccept(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<FriendAcceptReultVO> call = CoolHttpUrl.getApi().getService().FriendAccept(id);
            call.enqueue(new Callback<FriendAcceptReultVO>() {
                @Override
                public void onResponse(Call<FriendAcceptReultVO> call, Response<FriendAcceptReultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        FriendAcceptReultVO data = response.body();
                        CoolLogTrace.e("FriendAccept", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            mDatas.get(selectPosition).setFriendStatus(4);
                            adapter.notifyDataSetChanged();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<FriendAcceptReultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void GetFriendNew(final Context context, int page, int pageSize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetFriendNewResultVO> call = CoolHttpUrl.getApi().getService().GetFriendNew(page, pageSize);
            call.enqueue(new Callback<GetFriendNewResultVO>() {
                @Override
                public void onResponse(Call<GetFriendNewResultVO> call, Response<GetFriendNewResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetFriendNewResultVO data = response.body();
                        CoolLogTrace.e("GetFriendMy", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetFriendNewResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

}
