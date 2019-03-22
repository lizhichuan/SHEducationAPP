package com.shaohua.sheducation.fragement;

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
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chuan on 2017/11/6.
 */

public class FlowDetailsCoinsFragment extends BaseFragment {


    @Bind(R.id.rcv_details)
    RecyclerView rcvDetails;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;
    private CoolCommonRecycleviewAdapter<String> adapter;
    private List<String> mDatas;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flow_details, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvDetails.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<String>(mDatas, getActivity(), R.layout.item_flow_detail) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_tag = holder.getView(R.id.tv_tag);
                TextView tv_nums = holder.getView(R.id.tv_nums);
                TextView tv_info = holder.getView(R.id.tv_info);
                TextView tv_time = holder.getView(R.id.tv_time);
            }
        };
        rcvDetails.setAdapter(adapter);

        setmDatas();
        return view;
    }

    private void setmDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas.add("");
        }
        adapter.setmDatas(mDatas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
