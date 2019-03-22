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
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.activity.FilterPlanActivity;
import com.shaohua.sheducation.activity.PlanDetails2Activity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.FailVO;
import com.shaohua.sheducation.resultbean.FilterPlanBean;
import com.shaohua.sheducation.resultbean.GetPlanListResultVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by chuan on 2017/11/6.
 */

public class HomeAllFragment extends BaseFragment {

    @Bind(R.id.tv_filter)
    TextView tvFilter;
    @Bind(R.id.rcv_home_all)
    RecyclerView rcvHomeAll;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;


    private CoolCommonRecycleviewAdapter<GetPlanListResultVO.ResultBean.DataBean> adapter;
    private List<GetPlanListResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;

    private int page = 1;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;
    private FilterPlanBean filterPlanBean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_all, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvHomeAll.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanListResultVO.ResultBean.DataBean>(mDatas, getActivity(), R.layout.item_home_recommend_plan) {
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
        rcvHomeAll.setAdapter(adapter);
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
        rcvHomeAll.addOnScrollListener(coolRecycleViewLoadMoreListener);
        labels = CoolSPUtil.getDataFromLoacl(getActivity(), "labels").toString();
        getList(true);
        return view;
    }

    private int planMode = 0;
    private int depositMode = -1;
    private int status = -1;
    private String labels = "";

    private void getList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }

        GetPlanList(getActivity(), labels, planMode, depositMode, status, page);
    }


    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }

    public void setmDatas(GetPlanListResultVO.ResultBean getPlanListVO) {
        swp.setRefreshing(false);
        if (getPlanListVO.getData() != null && getPlanListVO.getData().size() > 0) {

            if (page == 1) {
                mDatas = getPlanListVO.getData();
            } else {
                for (int i = 0; i < getPlanListVO.getData().size(); i++) {
                    mDatas.add(getPlanListVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();

            if (getPlanListVO.getCount() > mDatas.size()) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_filter)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), FilterPlanActivity.class), 999);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 999:
                    filterPlanBean = (FilterPlanBean) data.getSerializableExtra("filter");
                    planMode = filterPlanBean.getPlanMode();
                    depositMode = filterPlanBean.getDepositMode();
                    status = filterPlanBean.getStatus();
                    if (filterPlanBean.getGetSystemLabelsVO() != null) {
                        labels = filterPlanBean.getGetSystemLabelsVO().getId() + "";
                    }
                    getList(true);
                    break;
                default:
                    break;
            }
        }
    }

    private void GetPlanList(final Context context, String labels,
                             int planMode, int depositMode,
                             int status, int page) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanListResultVO> call = CoolHttpUrl.getApi().getService().GetPlanList(labels, "",
                    planMode, depositMode, status, page, 15);
            call.enqueue(new Callback<GetPlanListResultVO>() {
                @Override
                public void onResponse(Call<GetPlanListResultVO> call, Response<GetPlanListResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetPlanListResultVO data = response.body();
                        CoolLogTrace.e("GetPlanList", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetPlanListResultVO> call, Throwable t) {
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
