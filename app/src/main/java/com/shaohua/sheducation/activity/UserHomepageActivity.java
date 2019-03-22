package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.CoolRecycleViewLoadMoreListener;
import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetUserInfoVO;
import com.shaohua.sheducation.resultbean.GetUserPlansVO;
import com.shaohua.sheducation.view.CircleProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomepageActivity extends SHBaseActivity {

    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.img_gender)
    ImageView imgGender;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_degree)
    TextView tvDegree;
    @Bind(R.id.tv_qq)
    TextView tvQq;
    @Bind(R.id.fl_tag)
    CoolTagFlowLayout flTag;
    @Bind(R.id.tv_all_nums)
    TextView tvAllNums;
    @Bind(R.id.rcv_plans)
    RecyclerView rcvPlans;

    private CoolCommonRecycleviewAdapter<GetUserPlansVO.ResultBean.DataBean> adapter;
    private List<GetUserPlansVO.ResultBean.DataBean> mDatas;
    private LinearLayoutManager linearLayoutManager;
    private CoolRecycleViewLoadMoreListener coolRecycleViewLoadMoreListener;

    private TagAdapter<GetUserInfoVO.ResultBean.LabelsBean> adapter_tag;
    private List<GetUserInfoVO.ResultBean.LabelsBean> mDatas_tag;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_homepage);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        findViews();
    }

    private void findViews() {
        if (id == 0) {
            finish();
        }
        linearLayoutManager = new LinearLayoutManager(UserHomepageActivity.this, LinearLayoutManager.VERTICAL, false);
        rcvPlans.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetUserPlansVO.ResultBean.DataBean>(mDatas,
                UserHomepageActivity.this, R.layout.item_userhome_plan) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                ImageView img_pic = holder.getView(R.id.img_pic);
                TextView tv_title = holder.getView(R.id.tv_title);
                CircleProgressView cp_percent = holder.getView(R.id.cp_percent);
                CoolCircleImageView img_person_one = holder.getView(R.id.img_person_one);
                CoolCircleImageView img_person_two = holder.getView(R.id.img_person_two);

                CoolGlideUtil.urlInto(UserHomepageActivity.this, mDatas.get(position).getImage(), img_pic);
                tv_title.setText(mDatas.get(position).getName());
                cp_percent.setProgress(mDatas.get(position).getCompletePercent());
//                if (mDatas.get(position).getMembers() != null && mDatas.get(position).getMembers().size() > 0) {
//                    if (mDatas.get(position).getMembers().size() == 1) {
//                        CoolGlideUtil.urlInto(UserHomepageActivity.this, mDatas.get(position).getMembers().get(0).getAvatar(), img_person_one);
//                        img_person_one.setVisibility(View.VISIBLE);
//                        img_person_two.setVisibility(View.INVISIBLE);
//                    } else if (mDatas.get(position).getMembers().size() == 2) {
//                        CoolGlideUtil.urlInto(UserHomepageActivity.this, mDatas.get(position).getMembers().get(0).getAvatar(), img_person_one);
//                        CoolGlideUtil.urlInto(UserHomepageActivity.this, mDatas.get(position).getMembers().get(1).getAvatar(), img_person_two);
//                        img_person_one.setVisibility(View.VISIBLE);
//                        img_person_two.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    img_person_one.setVisibility(View.INVISIBLE);
//                    img_person_two.setVisibility(View.INVISIBLE);
//                }

            }
        };
        rcvPlans.setAdapter(adapter);

        adapter_tag = new TagAdapter<GetUserInfoVO.ResultBean.LabelsBean>(mDatas_tag) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetUserInfoVO.ResultBean.LabelsBean s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag_homepage,
                        flTag, false);
                tv.setTextColor(getResources().getColor(R.color.common_color));
                tv.setText(mDatas_tag.get(position).getName());
                return tv;
            }
        };
        flTag.setAdapter(adapter_tag);

        coolRecycleViewLoadMoreListener = new CoolRecycleViewLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore() {
                getPlanList(false);
                coolRecycleViewLoadMoreListener.isLoading = true;
            }

            @Override
            public void onScrollMore() {

            }
        };
        rcvPlans.addOnScrollListener(coolRecycleViewLoadMoreListener);
        GetUserInfo(UserHomepageActivity.this, id);
        getPlanList(true);
    }

    int page = 1;

    private void getPlanList(boolean refresh) {
        if (refresh) {
            page = 1;
        } else {
            page++;
        }
        GetUserPlans(UserHomepageActivity.this, id, page, 15);

    }

    private void setData(GetUserInfoVO.ResultBean resultBean) {
        CoolGlideUtil.urlInto(UserHomepageActivity.this, resultBean.getAvatar(), imgHead);
        if (resultBean.getGender() == 1) {
            imgGender.setImageResource(R.mipmap.icon_women);
            tvGender.setText("女");
        } else {
            imgGender.setImageResource(R.mipmap.icon_man);
            tvGender.setText("男");
        }
        tvName.setText(resultBean.getNickName());
        if (resultBean.getEducation() == 1) {
            tvDegree.setText("高中");
        } else if (resultBean.getEducation() == 2) {
            tvDegree.setText("专科");
        } else if (resultBean.getEducation() == 3) {
            tvDegree.setText("本科");
        } else if (resultBean.getEducation() == 4) {
            tvDegree.setText("硕士");
        } else if (resultBean.getEducation() == 5) {
            tvDegree.setText("博士");
        } else {
            tvDegree.setText("其他");
        }

        if (resultBean.getAge() == 1) {
            tvAge.setText("18以下");
        } else if (resultBean.getAge() == 2) {
            tvAge.setText("18～25岁");
        } else if (resultBean.getAge() == 3) {
            tvAge.setText("25～35岁");
        } else if (resultBean.getAge() == 4) {
            tvAge.setText("35岁以上");
        }

        if (resultBean.getLabels() != null && resultBean.getLabels().size() > 0) {

            mDatas_tag = resultBean.getLabels();
            adapter_tag.setmTagDatas(mDatas_tag);
            adapter_tag.notifyDataChanged();
        }

    }

    private void setmDatas(GetUserPlansVO.ResultBean resultBean) {
        if (resultBean.getData() != null && resultBean.getData().size() > 0) {
            tvAllNums.setText("(共" + resultBean.getData().size() + "个)");
            if (page == 1) {
                mDatas = resultBean.getData();

            } else {
                for (int i = 0; i < resultBean.getData().size(); i++) {
                    mDatas.add(resultBean.getData().get(i));
                }
            }

            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
            imgClose.setFocusable(true);
            imgClose.setFocusableInTouchMode(true);
            imgClose.requestFocus();

            if (resultBean.getCount() > mDatas.size()) {
                coolRecycleViewLoadMoreListener.isLoading = false;
            } else {
                coolRecycleViewLoadMoreListener.isLoading = true;
            }
        } else {
            if (page == 1) {
                tvAllNums.setText("(共0个)");
            }
            coolRecycleViewLoadMoreListener.isLoading = true;
        }

    }

    @OnClick({R.id.img_close, R.id.img_head, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.img_head:
                break;
            case R.id.tv_add:
                break;
        }
    }

    private void GetUserInfo(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserInfoVO> call = CoolHttpUrl.getApi().getService().GetUserInfo(id);
            call.enqueue(new Callback<GetUserInfoVO>() {
                @Override
                public void onResponse(Call<GetUserInfoVO> call, Response<GetUserInfoVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetUserInfoVO data = response.body();
                        CoolLogTrace.e("GetUserInfo", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetUserInfoVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void GetUserPlans(final Context context, int id, int page, int pagesize) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserPlansVO> call = CoolHttpUrl.getApi().getService().GetUserPlans(id, page, pagesize);
            call.enqueue(new Callback<GetUserPlansVO>() {
                @Override
                public void onResponse(Call<GetUserPlansVO> call, Response<GetUserPlansVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetUserPlansVO data = response.body();
                        CoolLogTrace.e("GetUserPlans", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetUserPlansVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
