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
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.activity.CourseDetailsActivity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetStudyFavoriteListResultVO;
import com.shaohua.sheducation.resultbean.StudyCollectCancelResultVO;
import com.shaohua.sheducation.resultbean.StudyLikeResultVO;
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

public class CourseFavoriteFragment extends BaseFragment {


    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;
    @Bind(R.id.rcv_course)
    RecyclerView rcvCourse;

    private CoolCommonRecycleviewAdapter<GetStudyFavoriteListResultVO.ResultBean.DataBean> adapter;
    private List<GetStudyFavoriteListResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;

    private int page = 1;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;
    private int selectPosition = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_all, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvCourse.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetStudyFavoriteListResultVO.ResultBean.DataBean>(mDatas,
                getActivity(), R.layout.item_course_all) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolRoundAngleImageView img_pic = holder.getView(R.id.img_pic);
                TextView tv_tag = holder.getView(R.id.tv_tag);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_coins = holder.getView(R.id.tv_coins);
                LinearLayout linear_good = holder.getView(R.id.linear_good);
                LinearLayout linear_favorite = holder.getView(R.id.linear_favorite);
                CheckBox cb_good = holder.getView(R.id.cb_good);
                CheckBox cb_favorite = holder.getView(R.id.cb_favorite);
                CoolGlideUtil.urlInto(getActivity(), mDatas.get(position).getImage(), img_pic);

                tv_title.setText(mDatas.get(position).getName());
                tv_coins.setText(mDatas.get(position).getCoins() + "");
                cb_good.setText("  " + mDatas.get(position).getLikeCount() + "");
                cb_favorite.setText("  " + mDatas.get(position).getCollectCount() + "");
                if (mDatas.get(position).getCollectStatus() == 1) {
                    cb_favorite.setChecked(true);
                } else {
                    cb_favorite.setChecked(false);
                }

                if (mDatas.get(position).getLikeStatus() == 1) {
                    cb_good.setChecked(true);
                } else {
                    cb_good.setChecked(false);
                }

                linear_good.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDatas.get(position).getLikeStatus() == 0) {
                            selectPosition = position;
                            StudyLike(getActivity(), mDatas.get(position).getId());
                        }
                    }
                });

                linear_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        if (mDatas.get(position).getCollectStatus() == 0) {

                        } else {
                            StudyCollectCancel(getActivity(), mDatas.get(position).getId());
                        }
                    }
                });
            }
        };
        rcvCourse.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getActivity(), CourseDetailsActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetStudyList(true);
            }
        });
        coolRecycleViewLoadMoreListener = new CoolRecycleViewLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                coolRecycleViewLoadMoreListener.isLoading = true;
                GetStudyList(false);
            }

            @Override
            public void onScrollMore() {

            }
        };
        rcvCourse.addOnScrollListener(coolRecycleViewLoadMoreListener);
        GetStudyList(true);
        return view;
    }

    private void GetStudyList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetStudyFavoriteList(getActivity(), CoolSPUtil.getDataFromLoacl(getActivity(), "labels"), "", page, 15);
    }


    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
//        GetStudyList(true);
    }


    public void setData(GetStudyFavoriteListResultVO.ResultBean getStudyListVO) {
        swp.setRefreshing(false);
        if (getStudyListVO.getData() != null && getStudyListVO.getData().size() > 0) {
            if (page == 1) {
                mDatas = getStudyListVO.getData();
            } else {
                for (int i = 0; i < getStudyListVO.getData().size(); i++) {
                    mDatas.add(getStudyListVO.getData().get(i));
                }
            }
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            if (getStudyListVO.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                CoolPublicMethod.Toast(getActivity(), "暂无数据");
            } else {
                CoolPublicMethod.Toast(getActivity(), "无更多数据");
            }
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void GetStudyFavoriteList(final Context context, String labels, String q, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetStudyFavoriteListResultVO> call = CoolHttpUrl.getApi().getService().GetStudyFavoriteList(labels, q, page, pagesize);
            call.enqueue(new Callback<GetStudyFavoriteListResultVO>() {
                @Override
                public void onResponse(Call<GetStudyFavoriteListResultVO> call, Response<GetStudyFavoriteListResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetStudyFavoriteListResultVO data = response.body();
                        CoolLogTrace.e("GetStudyFavoriteList", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetStudyFavoriteListResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void StudyLike(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<StudyLikeResultVO> call = CoolHttpUrl.getApi().getService().StudyLike(id);
            call.enqueue(new Callback<StudyLikeResultVO>() {
                @Override
                public void onResponse(Call<StudyLikeResultVO> call, Response<StudyLikeResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        StudyLikeResultVO data = response.body();
                        CoolLogTrace.e("StudyLike", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (swp != null) {
                                swp.setRefreshing(false);
                            }
                            mDatas.get(selectPosition).setLikeStatus(1);
                            adapter.notifyDataSetChanged();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<StudyLikeResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void StudyCollectCancel(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<StudyCollectCancelResultVO> call = CoolHttpUrl.getApi().getService().StudyCollectCancel(id);
            call.enqueue(new Callback<StudyCollectCancelResultVO>() {
                @Override
                public void onResponse(Call<StudyCollectCancelResultVO> call, Response<StudyCollectCancelResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        StudyCollectCancelResultVO data = response.body();
                        CoolLogTrace.e("StudyCollectCancel", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (swp != null) {
                                swp.setRefreshing(false);
                            }
                            mDatas.get(selectPosition).setCollectStatus(0);
                            adapter.notifyDataSetChanged();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<StudyCollectCancelResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
