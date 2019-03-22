package com.shaohua.sheducation.fragement;

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
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.shaohua.sheducation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chuan on 2017/11/6.
 */

public class PlanStoreDealingFragment extends BaseFragment {


    @Bind(R.id.tv_start_nums)
    TextView tvStartNums;
    @Bind(R.id.tv_start_days)
    TextView tvStartDays;
    @Bind(R.id.img_head_start)
    ImageView imgHeadStart;
    @Bind(R.id.tv_me_nums)
    TextView tvMeNums;
    @Bind(R.id.tv_me_days)
    TextView tvMeDays;
    @Bind(R.id.img_head_me)
    ImageView imgHeadMe;
    @Bind(R.id.tv_unfinish_nums)
    TextView tvUnfinishNums;
    @Bind(R.id.tv_all_nums)
    TextView tvAllNums;
    @Bind(R.id.rcv_unfinish)
    RecyclerView rcvUnfinish;
    @Bind(R.id.tv_finish_nums)
    TextView tvFinishNums;
    @Bind(R.id.tv_all_nums_two)
    TextView tvAllNumsTwo;
    @Bind(R.id.rcv_finish)
    RecyclerView rcvFinish;


    private CoolCommonRecycleviewAdapter<String> adapter_unfinish;
    private LinearLayoutManager linearLayoutManager_unfinish;
    private List<String> mDatas_unfinish;
    private CoolCommonRecycleviewAdapter<String> adapter_finish;
    private LinearLayoutManager linearLayoutManager_finish;
    private List<String> mDatas_finish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_store_dealing, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager_unfinish = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvUnfinish.setLayoutManager(linearLayoutManager_unfinish);
        adapter_unfinish = new CoolCommonRecycleviewAdapter<String>(mDatas_unfinish, getActivity(),
                R.layout.item_plan_store_dealing) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {

            }
        };
        rcvUnfinish.setAdapter(adapter_unfinish);
        linearLayoutManager_finish = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvFinish.setLayoutManager(linearLayoutManager_finish);
        adapter_finish = new CoolCommonRecycleviewAdapter<String>(mDatas_finish, getActivity(),
                R.layout.item_plan_store_dealing) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {

            }
        };
        rcvFinish.setAdapter(adapter_finish);
        setmDatas();
        setmDatas2();
        return view;
    }


    private void setmDatas() {
        mDatas_unfinish = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas_unfinish.add("");
        }
        adapter_unfinish.setmDatas(mDatas_unfinish);
        adapter_unfinish.notifyDataSetChanged();
    }

    private void setmDatas2() {
        mDatas_finish = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas_finish.add("");
        }
        adapter_finish.setmDatas(mDatas_finish);
        adapter_finish.notifyDataSetChanged();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
