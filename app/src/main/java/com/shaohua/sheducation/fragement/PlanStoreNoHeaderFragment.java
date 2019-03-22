package com.shaohua.sheducation.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chuan on 2017/11/6.
 */

public class PlanStoreNoHeaderFragment extends BaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    @Bind(R.id.rcv_message)
    RecyclerView rcvMessage;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.linear_submit)
    LinearLayout linearSubmit;
    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.linear_wait)
    LinearLayout linearWait;
    @Bind(R.id.tv_unagree)
    TextView tvUnagree;
    @Bind(R.id.tv_agree)
    TextView tvAgree;
    @Bind(R.id.linear_agree_deal)
    LinearLayout linearAgreeDeal;

    private CoolCommonRecycleviewAdapter<String> adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<String> mDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_store_no_header, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvMessage.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<String>(mDatas, getActivity(), R.layout.item_plan_store) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_add_new = holder.getView(R.id.tv_add_new);
                CardView cv_plan_store = holder.getView(R.id.cv_plan_store);
                if (position == mDatas.size() - 1) {
                    cv_plan_store.setVisibility(View.GONE);
                    tv_add_new.setVisibility(View.VISIBLE);
                } else {
                    cv_plan_store.setVisibility(View.VISIBLE);
                    tv_add_new.setVisibility(View.GONE);
                }
            }
        };
        rcvMessage.setAdapter(adapter);
        setmDatas();
        return view;
    }


    private void setmDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("");
        }
        adapter.setmDatas(mDatas);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_submit, R.id.tv_unagree, R.id.tv_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                break;
            case R.id.tv_unagree:
                break;
            case R.id.tv_agree:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
