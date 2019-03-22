package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetStudySmartResultVO;
import com.shaohua.sheducation.resultbean.StudyCollectCancelResultVO;
import com.shaohua.sheducation.resultbean.StudyCollectResultVO;
import com.shaohua.sheducation.resultbean.StudyLikeResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntelligentLibraryActivity extends SHBaseActivity {

    @Bind(R.id.et_key)
    EditText etKey;
    @Bind(R.id.tv_filter)
    TextView tvFilter;
    @Bind(R.id.rcv_courses)
    RecyclerView rcvCourses;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;

    private CoolCommonRecycleviewAdapter<GetStudySmartResultVO.ResultBean.DataBean> adapter;
    private List<GetStudySmartResultVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;
    private int selectPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_intelligent_library);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    @Override
    protected void onRightClick(View v) {
        super.onRightClick(v);
        startActivity(SubscribeCourseActivity.class);
    }

    private void findViews() {
        setmTopTitle("智能库");
        setTvRight("订资料");
        setmTvRightVisible(1);
        linearLayoutManager = new LinearLayoutManager(IntelligentLibraryActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvCourses.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetStudySmartResultVO.ResultBean.DataBean>(mDatas, IntelligentLibraryActivity.this,
                R.layout.item_course_all) {
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
                CoolGlideUtil.urlInto(IntelligentLibraryActivity.this, mDatas.get(position).getImage(), img_pic);

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
                            StudyLike(IntelligentLibraryActivity.this, mDatas.get(position).getId());
                        }
                    }
                });

                linear_favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPosition = position;
                        if (mDatas.get(position).getCollectStatus() == 0) {
                            StudyCollect(IntelligentLibraryActivity.this, mDatas.get(position).getId());
                        } else {
                            StudyCollectCancel(IntelligentLibraryActivity.this, mDatas.get(position).getId());
                        }
                    }
                });

            }
        };
        rcvCourses.setAdapter(adapter);
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(IntelligentLibraryActivity.this, CourseDetailsActivity.class));
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
        rcvCourses.addOnScrollListener(coolRecycleViewLoadMoreListener);
        GetStudyList(true);
    }

    private void GetStudyList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetStudySmart(IntelligentLibraryActivity.this,
                CoolSPUtil.getDataFromLoacl(IntelligentLibraryActivity.this,
                        "labels"), "", page, 15);
    }


    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
        if (swp != null) {
            swp.setRefreshing(false);
        }
//        GetStudyList(true);
    }


    public void setData(GetStudySmartResultVO.ResultBean getStudyListVO) {
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
                CoolPublicMethod.Toast(IntelligentLibraryActivity.this, "暂无数据");
            } else {
                CoolPublicMethod.Toast(IntelligentLibraryActivity.this, "无更多数据");
            }
        }

    }


    @OnClick(R.id.tv_filter)
    public void onViewClicked() {
    }

    private void GetStudySmart(final Context context, String labels, String q, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetStudySmartResultVO> call = CoolHttpUrl.getApi().getService().GetStudySmart(labels, q, page, pagesize);
            call.enqueue(new Callback<GetStudySmartResultVO>() {
                @Override
                public void onResponse(Call<GetStudySmartResultVO> call, Response<GetStudySmartResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetStudySmartResultVO data = response.body();
                        CoolLogTrace.e("GetStudySmart", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetStudySmartResultVO> call, Throwable t) {
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

    private void StudyCollect(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<StudyCollectResultVO> call = CoolHttpUrl.getApi().getService().StudyCollect(id);
            call.enqueue(new Callback<StudyCollectResultVO>() {
                @Override
                public void onResponse(Call<StudyCollectResultVO> call, Response<StudyCollectResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        StudyCollectResultVO data = response.body();
                        CoolLogTrace.e("StudyLike", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (swp != null) {
                                swp.setRefreshing(false);
                            }
                            mDatas.get(selectPosition).setCollectStatus(1);
                            adapter.notifyDataSetChanged();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<StudyCollectResultVO> call, Throwable t) {
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
