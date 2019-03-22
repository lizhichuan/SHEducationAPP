package com.shaohua.sheducation.fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shaohua.sheducation.resultbean.GetFriendMyResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chuan on 2017/11/6.
 */

public class ClassmateFriendsFragment extends BaseFragment {


    @Bind(R.id.rcv_friends)
    RecyclerView rcvFriends;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetFriendMyResultVO.ResultBean.DataBean> adapter;
    private List<GetFriendMyResultVO.ResultBean.DataBean> mDatas;
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
        View view = inflater.inflate(R.layout.fragment_classmate_friends, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvFriends.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetFriendMyResultVO.ResultBean.DataBean>(getActivity(), mDatas, R.layout.item_classmate_friends) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                TextView tv_name = holder.getView(R.id.tv_name);

                CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getAvatar(), img_head);
                tv_name.setText(mDatas.get(position).getNickName());
            }
        };
        rcvFriends.setAdapter(adapter);
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
        rcvFriends.addOnScrollListener(coolRecycleViewLoadMoreListener);
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
        GetFriendMy(getActivity(), page, 15);
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    public void setData(GetFriendMyResultVO.ResultBean getFriendMyVO) {
        swp.setRefreshing(false);
        if (getFriendMyVO.getData() != null && getFriendMyVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getFriendMyVO.getData();
            } else {
                for (int i = 0; i < getFriendMyVO.getData().size(); i++) {
                    mDatas.add(getFriendMyVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            if (getFriendMyVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                CoolPublicMethod.Toast(getActivity(), "暂无相关数据");
            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void GetFriendMy(final Context context, int page, int pageSize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetFriendMyResultVO> call = CoolHttpUrl.getApi().getService().GetFriendMy(page, pageSize);
            call.enqueue(new Callback<GetFriendMyResultVO>() {
                @Override
                public void onResponse(Call<GetFriendMyResultVO> call, Response<GetFriendMyResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetFriendMyResultVO data = response.body();
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
                public void onFailure(Call<GetFriendMyResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
