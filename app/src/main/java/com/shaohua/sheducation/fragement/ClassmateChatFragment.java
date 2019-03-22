package com.shaohua.sheducation.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chuan on 2017/11/6.
 */

public class ClassmateChatFragment extends BaseFragment {


    @Bind(R.id.rcv_conversation)
    RecyclerView rcvConversation;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classmate, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
