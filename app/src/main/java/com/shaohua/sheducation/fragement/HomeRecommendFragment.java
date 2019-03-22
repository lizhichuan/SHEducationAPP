package com.shaohua.sheducation.fragement;

import android.content.Context;
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
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetPlanRecommendResultVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chuan on 2017/11/6.
 */

public class HomeRecommendFragment extends BaseFragment {

    @Bind(R.id.rcv_home_recommend)
    RecyclerView rcvHomeRecommend;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetPlanRecommendResultVO.ResultBean.DataBean> adapter;
    private List<GetPlanRecommendResultVO.ResultBean.DataBean> mDatas;
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
        View view = inflater.inflate(R.layout.fragment_home_recommend, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvHomeRecommend.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanRecommendResultVO.ResultBean.DataBean>(mDatas,
                getActivity(), R.layout.item_home_recommend_plan) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                ImageView img_pic = holder.getView(R.id.img_pic);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);

                CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getImage(), img_pic);
                tv_title.setText(mDatas.get(position).getName());
                tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas.get(position).getTestTime()));
            }
        };
        rcvHomeRecommend.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                startActivity(new Intent(getActivity(), PlanDetails2Activity.class)
//                        .putExtra("id", mDatas.get(position).getId()));
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
                coolRecycleViewLoadMoreListener.isLoading = true;
                getList(false);
            }

            @Override
            public void onScrollMore() {

            }
        };
        rcvHomeRecommend.addOnScrollListener(coolRecycleViewLoadMoreListener);
        getList(true);
        return view;
    }

    private void getList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetPlanRecommend(getActivity(), page, 15, CoolSPUtil.getDataFromLoacl(getActivity(), "labels"));

    }

    private void setmDatas(GetPlanRecommendResultVO.ResultBean getPlanRecommendVO) {
        if (getPlanRecommendVO.getData() != null && getPlanRecommendVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getPlanRecommendVO.getData();
            } else {
                mDatas.remove(mDatas.size() - 1);
                for (int i = 0; i < getPlanRecommendVO.getData().size(); i++) {
                    mDatas.add(getPlanRecommendVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();

            if (getPlanRecommendVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                mDatas = null;
                adapter.setmDatas(mDatas);
                adapter.notifyDataSetChanged();
            }
            coolRecycleViewLoadMoreListener.isLoading = true;
        }

    }

    private void GetPlanRecommend(final Context context, final int page, int pagesize, String labels) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanRecommendResultVO> call = CoolHttpUrl.getApi().getService().GetPlanRecommend(labels, page, pagesize);
            call.enqueue(new Callback<GetPlanRecommendResultVO>() {
                @Override
                public void onResponse(Call<GetPlanRecommendResultVO> call, Response<GetPlanRecommendResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetPlanRecommendResultVO data = response.body();
                        CoolLogTrace.e("GetPlanRecommend", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetPlanRecommendResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
