package com.shaohua.sheducation.fragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.common.CoolCommonPop;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.activity.EditPlanStoreActivity;
import com.shaohua.sheducation.activity.PayMoneyActivity;
import com.shaohua.sheducation.activity.PlanDetailsActivity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.FailVO;
import com.shaohua.sheducation.resultbean.GetMilestonesConfirmResultVO;
import com.shaohua.sheducation.resultbean.GetMilestonesResultVO;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.MilestonesRefuseResultVO;
import com.shaohua.sheducation.resultbean.MilestonesSubmitResultVO;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chuan on 2017/11/6.
 */

public class PlanStoreFragment extends BaseFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    @Bind(R.id.rcv_message)
    RecyclerView rcvMessage;
    @Bind(R.id.swp)
    CoolSwipeRefreshLayout swp;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.tv_status)
    TextView tvStatus;
    @Bind(R.id.activity_stores)
    LinearLayout activityStores;
    @Bind(R.id.tv_agree)
    TextView tvAgree;
    @Bind(R.id.tv_unagree)
    TextView tvUnagree;
    @Bind(R.id.linear_store_deal)
    LinearLayout linearStoreDeal;

    private CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean> adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<GetMilestonesResultVO.ResultBean> mDatas;
    private GetPlanInfoResultVO.ResultBean mgetPlanInfoVO;
    private int id = 0;


    public static Fragment getInstance(Bundle bundle) {
        PlanStoreFragment fragment = new PlanStoreFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_store, container, false);
        ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvMessage.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean>(mDatas, getActivity(), R.layout.item_plan_store) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_add_new = holder.getView(R.id.tv_add_new);
                CardView cv_plan_store = holder.getView(R.id.cv_plan_store);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
                if (PlanDetailsActivity.PlanMeCreate) {
                    if (position == mDatas.size() - 1) {
                        cv_plan_store.setVisibility(View.GONE);
                        tv_add_new.setVisibility(View.VISIBLE);
                    } else {
                        cv_plan_store.setVisibility(View.VISIBLE);
                        tv_add_new.setVisibility(View.GONE);
                        tv_title.setText(mDatas.get(position).getName());
                        tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas.get(position).getFinishTime()));
                    }
                } else {
                    tv_add_new.setVisibility(View.GONE);
                    cv_plan_store.setVisibility(View.VISIBLE);
                    tv_title.setText(mDatas.get(position).getName());
                    tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas.get(position).getFinishTime()));
                }

                tv_add_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), EditPlanStoreActivity.class)
                                .putExtra("id", id));
                    }
                });

            }
        };
        rcvMessage.setAdapter(adapter);
        swp.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMilestones(getActivity(), id);
            }
        });
        adapter.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (PlanDetailsActivity.PlanMeCreate) {
                    Intent intent = new Intent(getActivity(), EditPlanStoreActivity.class);
                    intent.putExtra("store", mDatas.get(position));
                    intent.putExtra("id", mDatas.get(position).getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        if (PlanDetailsActivity.PlanMeCreate) {
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = getArguments().getInt("id");
        GetMilestones(getActivity(), id);
        if (PlanDetailsActivity.CreaterBean.getMilestoneStatus() == 0) {
            if (!PlanDetailsActivity.PlanMeCreate) {
                tvStatus.setText("正在等待" + PlanDetailsActivity.CreaterBean.getNickName() + "提交里程碑");
                tvSubmit.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
            } else {
                tvStatus.setVisibility(View.GONE);
                tvSubmit.setVisibility(View.VISIBLE);
            }
        } else if (PlanDetailsActivity.CreaterBean.getMilestoneStatus() == 1) {
            if (PlanDetailsActivity.PlanMeCreate) {
                if (PlanDetailsActivity.MemberBean.getMilestoneStatus() != 3) {
                    tvSubmit.setVisibility(View.GONE);
                    tvStatus.setVisibility(View.VISIBLE);
                    tvStatus.setText("已提交,正在等待" + PlanDetailsActivity.MemberBean.getNickName() + "确认里程碑");
                } else {
                    //对方拒绝
                    CoolPublicMethod.Toast(getActivity(), "对方拒绝了里程碑，请重新修改提交");
                    tvStatus.setVisibility(View.GONE);
                    tvSubmit.setVisibility(View.VISIBLE);
                }
            } else {
                if (PlanDetailsActivity.MemberBean.getMilestoneStatus() != 3) {
                    tvSubmit.setVisibility(View.GONE);
                    tvStatus.setVisibility(View.GONE);
                    linearStoreDeal.setVisibility(View.VISIBLE);
                } else {
                    //已拒绝
                    tvStatus.setText("正在等待" + PlanDetailsActivity.CreaterBean.getNickName() + "提交里程碑");
                    tvSubmit.setVisibility(View.GONE);
                    tvStatus.setVisibility(View.VISIBLE);
                    linearStoreDeal.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
    }

    @Subscribe
    public void onEventMainThread(GetPlanInfoResultVO.ResultBean getPlanInfoVO) {
        mgetPlanInfoVO = getPlanInfoVO;
        if (mgetPlanInfoVO.getStatus() == 3) {
            if (PlanDetailsActivity.CreaterBean.getMilestoneStatus() == 1) {
                CoolPublicMethod.Toast(getActivity(), "对方已提交里程碑，等待您的确认");
            }
        }
        if (mgetPlanInfoVO.getStatus() == 4) {
            if (PlanDetailsActivity.MemberBean.getMilestoneStatus() == 3) {
                CoolPublicMethod.Toast(getActivity(), "对方未同意您的里程碑\\n原因:" + PlanDetailsActivity.MemberBean.getRefuseReason());
            }
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_submit, R.id.tv_agree, R.id.tv_unagree})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_submit:

                CoolCommonPop coolCommonPop_Clear_im = new CoolCommonPop();
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    coolCommonPop_Clear_im.ShowPop(getActivity(), activityStores
                            , Gravity.CENTER, "提示", "里程碑提交后不能更改，请确定是否立即提交 ？");
                } else {
                    coolCommonPop_Clear_im.ShowPop(getActivity(), activityStores
                            , Gravity.CENTER, "提示", "里程碑对方确认后不能更改，请确定是否立即提交?");
                }

                coolCommonPop_Clear_im.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        MilestonesSubmit(getActivity(), id);

                    }

                    @Override
                    public void onCancel() {

                    }
                });


                break;
            case R.id.tv_agree:
                CoolCommonPop coolCommonPop = new CoolCommonPop();
                coolCommonPop.ShowPop(getActivity(), activityStores
                        , Gravity.CENTER, "提示", "里程碑同意后不能修改，是否同意？");
                coolCommonPop.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        GetMilestonesConfirm(getActivity(), id);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.tv_unagree:
                initPop();
                break;
        }
    }

    private PopupWindow pop;

    public void initPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_refuse_store, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        final EditText et_des = (EditText) view.findViewById(R.id.et_des);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MilestonesRefuse(getActivity(), id);
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(activityStores, Gravity.CENTER, 0, 0);
    }


    private void GetMilestones(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetMilestonesResultVO> call = CoolHttpUrl.getApi().getService().GetMilestones(id);
            call.enqueue(new Callback<GetMilestonesResultVO>() {
                @Override
                public void onResponse(Call<GetMilestonesResultVO> call, Response<GetMilestonesResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (swp != null) {
                        swp.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetMilestonesResultVO data = response.body();
                        CoolLogTrace.e("GetMilestones", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (data.getResult() != null && data.getResult().size() > 0) {
                                mDatas = data.getResult();
                                if (PlanDetailsActivity.PlanMeCreate) {
                                    mDatas.add(new GetMilestonesResultVO.ResultBean());
                                }
                                adapter.setmDatas(mDatas);
                                adapter.notifyDataSetChanged();
                            } else {
                                mDatas = new ArrayList<>();
                                if (PlanDetailsActivity.PlanMeCreate) {
                                    mDatas.add(new GetMilestonesResultVO.ResultBean());
                                }
                                adapter.setmDatas(mDatas);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetMilestonesResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void GetMilestonesConfirm(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetMilestonesConfirmResultVO> call = CoolHttpUrl.getApi().getService().GetMilestonesConfirm(id);
            call.enqueue(new Callback<GetMilestonesConfirmResultVO>() {
                @Override
                public void onResponse(Call<GetMilestonesConfirmResultVO> call, Response<GetMilestonesConfirmResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetMilestonesConfirmResultVO data = response.body();
                        CoolLogTrace.e("GetMilestones", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (mgetPlanInfoVO.getDepositMode() == 0) {
                                //非押金模式，开始打卡
                                PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                            } else {
                                //非押金模式，开始打卡
                                PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                                //押金模式，需要支付押金
                                startActivity(new Intent(getActivity(), PayMoneyActivity.class)
                                        .putExtra("plan", mgetPlanInfoVO));

                            }
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetMilestonesConfirmResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void MilestonesRefuse(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestonesRefuseResultVO> call = CoolHttpUrl.getApi().getService().MilestonesRefuse(id);
            call.enqueue(new Callback<MilestonesRefuseResultVO>() {
                @Override
                public void onResponse(Call<MilestonesRefuseResultVO> call, Response<MilestonesRefuseResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestonesRefuseResultVO data = response.body();
                        CoolLogTrace.e("MilestonesRefuse", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestonesRefuseResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void MilestonesSubmit(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestonesSubmitResultVO> call = CoolHttpUrl.getApi().getService().MilestonesSubmit(id);
            call.enqueue(new Callback<MilestonesSubmitResultVO>() {
                @Override
                public void onResponse(Call<MilestonesSubmitResultVO> call, Response<MilestonesSubmitResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestonesSubmitResultVO data = response.body();
                        CoolLogTrace.e("MilestonesSubmit", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(getActivity(), "提交成功，请等待审核");
                            tvSubmit.setVisibility(View.GONE);

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestonesSubmitResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
