package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.MyMessageReadBody;
import com.shaohua.sheducation.resultbean.FailVO;
import com.shaohua.sheducation.resultbean.GetMyMessageResultVO;
import com.shaohua.sheducation.resultbean.MyMessageReadVO;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends SHBaseActivity {

    @Bind(R.id.rcv_messages)
    RecyclerView rcvMessages;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetMyMessageResultVO.ResultBean.DataBean> adapter;
    private List<GetMyMessageResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_message);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("我的消息");
        linearLayoutManager = new LinearLayoutManager(this);
        rcvMessages.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetMyMessageResultVO.ResultBean.DataBean>(mDatas,
                MessageActivity.this, R.layout.item_messages) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_red_point = holder.getView(R.id.tv_red_point);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
                TextView tv_content = holder.getView(R.id.tv_content);
                if (mDatas.get(position).getStatus() == 0) {
                    tv_red_point.setVisibility(View.VISIBLE);
                } else {
                    tv_red_point.setVisibility(View.INVISIBLE);
                }
                tv_title.setText(mDatas.get(position).getTitle());
                tv_content.setText(mDatas.get(position).getContent());
            }
        };
        rcvMessages.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mDatas.get(position).setStatus(1);
                adapter.notifyDataSetChanged();

                List<Integer> ids = new ArrayList<>();
                ids.add(mDatas.get(position).getId());
                MyMessageReadBody body = new MyMessageReadBody();
                body.setIds(ids);
                MyMessageRead(MessageActivity.this, body);

                Intent intent = new Intent(MessageActivity.this, MessageDetailsActivity.class);
                intent.putExtra("message", mDatas.get(position));
                startActivity(intent);
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
        rcvMessages.addOnScrollListener(coolRecycleViewLoadMoreListener);
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
        GetMyMessage(MessageActivity.this, -1, page, 15);
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
    }


    public void setData(GetMyMessageResultVO.ResultBean getMyMessageVO) {
        swp.setRefreshing(false);
        if (getMyMessageVO.getData() != null && getMyMessageVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getMyMessageVO.getData();
            } else {
                for (int i = 0; i < getMyMessageVO.getData().size(); i++) {
                    mDatas.add(getMyMessageVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            if (getMyMessageVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            coolRecycleViewLoadMoreListener.isLoading = true;
        }
    }


    private void GetMyMessage(final Context context, int status, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetMyMessageResultVO> call = CoolHttpUrl.getApi().getService().GetMyMessage(status, page, pagesize);
            call.enqueue(new Callback<GetMyMessageResultVO>() {
                @Override
                public void onResponse(Call<GetMyMessageResultVO> call, Response<GetMyMessageResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetMyMessageResultVO data = response.body();
                        CoolLogTrace.e("GetMyMessage", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetMyMessageResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void MyMessageRead(final Context context, MyMessageReadBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MyMessageReadVO> call = CoolHttpUrl.getApi().getService().MyMessageRead(body);
            call.enqueue(new Callback<MyMessageReadVO>() {
                @Override
                public void onResponse(Call<MyMessageReadVO> call, Response<MyMessageReadVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MyMessageReadVO data = response.body();
                        CoolLogTrace.e("MyMessageRead", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MyMessageReadVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
