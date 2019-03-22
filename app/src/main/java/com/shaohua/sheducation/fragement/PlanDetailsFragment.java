package com.shaohua.sheducation.fragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.common.CoolCommonPop;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.activity.PayMoneyActivity;
import com.shaohua.sheducation.activity.PlanDetailsActivity;
import com.shaohua.sheducation.activity.RecommendPairPersonActivity;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.PlanAcceptBody;
import com.shaohua.sheducation.request.PlanJoinBody;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.GetUserIdsResultVO;
import com.shaohua.sheducation.resultbean.GetUserInfoVO;
import com.shaohua.sheducation.resultbean.PlanAcceptVO;
import com.shaohua.sheducation.resultbean.PlanExitVO;
import com.shaohua.sheducation.resultbean.PlanFinishVO;
import com.shaohua.sheducation.resultbean.PlanJoinVO;
import com.shaohua.sheducation.resultbean.PlanOverVO;
import com.shaohua.sheducation.resultbean.PlanRefuseVO;

import org.greenrobot.eventbus.Subscribe;

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

public class PlanDetailsFragment extends BaseFragment {

    @Bind(R.id.tv_check_time)
    TextView tvCheckTime;
    @Bind(R.id.tv_plan_type)
    TextView tvPlanType;
    @Bind(R.id.tv_plan_mode)
    TextView tvPlanMode;
    @Bind(R.id.tv_plan_money)
    TextView tvPlanMoney;
    @Bind(R.id.tv_plan_destination)
    TextView tvPlanDestination;
    @Bind(R.id.tv_plan_id)
    TextView tvPlanId;
    @Bind(R.id.tv_person_nums)
    TextView tvPersonNums;
    @Bind(R.id.img_head_start)
    CoolCircleImageView imgHeadStart;
    @Bind(R.id.tv_start_name)
    TextView tvStartName;
    @Bind(R.id.img_head_me)
    CoolCircleImageView imgHeadMe;
    @Bind(R.id.tv_me_name)
    TextView tvMeName;
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.tv_refuse)
    TextView tvRefuse;
    @Bind(R.id.tv_agree)
    TextView tvAgree;
    @Bind(R.id.tv_start_status)
    TextView tvStartStatus;
    @Bind(R.id.tv_me_status)
    TextView tvMeStatus;
    @Bind(R.id.linear_join)
    LinearLayout linearJoin;
    @Bind(R.id.linear_join_deal)
    LinearLayout linearJoinDeal;
    @Bind(R.id.tv_exit_plan)
    TextView tvExitPlan;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.linear_plan_deal)
    LinearLayout linearPlanDeal;
    @Bind(R.id.tv_person_invite)
    TextView tvPersonInvite;
    @Bind(R.id.tv_person_more)
    TextView tvPersonMore;
    @Bind(R.id.rcv_persons)
    RecyclerView rcvPersons;
    @Bind(R.id.rel_creater)
    RelativeLayout relCreater;
    @Bind(R.id.img_line)
    ImageView imgLine;
    @Bind(R.id.rel_invited)
    RelativeLayout relInvited;
    @Bind(R.id.linear_invites)
    LinearLayout linearInvites;
    @Bind(R.id.activity_plan_details)
    NestedScrollView activityPlanDetails;

    private CoolCommonRecycleviewAdapter<GetPlanInfoResultVO.ResultBean.InvitesBean> adapter;
    private List<GetPlanInfoResultVO.ResultBean.InvitesBean> mDatas;
    private List<GetUserIdsResultVO.ResultBean> mDatas_persons;
    private LinearLayoutManager linearLayoutManager;
    private GetPlanInfoResultVO.ResultBean mgetPlanInfoVO;

    private PopupWindow pop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_details, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvPersons.setLayoutManager(linearLayoutManager);
        adapter = new CoolCommonRecycleviewAdapter<GetPlanInfoResultVO.ResultBean.InvitesBean>(mDatas, getActivity(),
                R.layout.item_classmate_recommend) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                CoolCircleImageView img_head = holder.getView(R.id.img_head);
                ImageView img_gender = holder.getView(R.id.img_gender);
                ImageView img_invite = holder.getView(R.id.img_invite);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_distance = holder.getView(R.id.tv_distance);
                TextView tv_status = holder.getView(R.id.tv_status);
                RecyclerView rcv_tags = holder.getView(R.id.rcv_tags);
                img_invite.setVisibility(View.GONE);
                tv_status.setVisibility(View.VISIBLE);
                LinearLayoutManager li = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(mDatas_persons.get(position).getLabels(),
                        getActivity(), R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int subposition) {
                        TextView tv_tag = holder.getView(R.id.tv_tag);
                        tv_tag.setText(mDatas_persons.get(position).getLabels().get(subposition).getName());
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                    }
                });

                CoolGlideUtil.urlInto(getActivity(), mDatas_persons.get(position).getAvatar(), img_head);
                tv_name.setText(mDatas_persons.get(position).getNickName());
                if (mDatas_persons.get(position).getGender() == 1) {
                    img_gender.setImageResource(R.mipmap.icon_women);
                } else {
                    img_gender.setImageResource(R.mipmap.icon_man);
                }

                if (mDatas.get(position).getStatus() == 1) {
                    tv_status.setText("已邀请");
                } else if (mDatas.get(position).getStatus() == 2) {
                    tv_status.setText("已接受");
                } else if (mDatas.get(position).getStatus() == 3) {
                    tv_status.setText("已拒绝");
                }
            }
        };
        rcvPersons.setAdapter(adapter);

        return view;
    }

    @Subscribe
    public void onEventMainThread(GetUserInfoVO.ResultBean getPlanInfoVO) {
        tvStartName.setText(getPlanInfoVO.getNickName());
        CoolGlideUtil.urlInto(getActivity(), getPlanInfoVO.getAvatar(), imgHeadStart);
    }

    @Subscribe
    public void onEventMainThread(GetPlanInfoResultVO.ResultBean getPlanInfoVO) {
        mgetPlanInfoVO = getPlanInfoVO;
        if (mgetPlanInfoVO.getUserId() == Integer.valueOf(CoolSPUtil.getDataFromLoacl(getActivity(), "uid").toString())) {
            PlanDetailsActivity.PlanMeCreate = true;
        } else {
            PlanDetailsActivity.PlanMeCreate = false;
        }
        tvCheckTime.setText(CoolPublicMethod.timeStamp2DateYMD(getPlanInfoVO.getTestTime()));
        tvPlanType.setText(getPlanInfoVO.getLabel().getName());
        tvPlanMode.setText(getPlanInfoVO.getPlanMode() == 1 ? "单人模式" : "双人模式");
        tvPlanMoney.setText(getPlanInfoVO.getDepositMode() == 0 ? "不支持" : "支持");
        tvPlanDestination.setText(getPlanInfoVO.getTestDest());
        tvPlanId.setText(getPlanInfoVO.getId() + "");

        if (mgetPlanInfoVO.getPlanMode() == 1) {
            linearInvites.setVisibility(View.GONE);
        } else {
            linearInvites.setVisibility(View.VISIBLE);
            if (getPlanInfoVO.getInvites() != null && getPlanInfoVO.getInvites().size() > 0) {
                tvPersonInvite.setText("已邀请同学(" + getPlanInfoVO.getInvites().size() + ")");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < getPlanInfoVO.getInvites().size(); i++) {
                    if (CoolSPUtil.getDataFromLoacl(getActivity(), "uid").toString()
                            .equalsIgnoreCase(getPlanInfoVO.getInvites().get(i).getInvitedId() + "")) {
                        PlanDetailsActivity.PlanMeInvited = true;
                    }
                    if (i == 0) {
                        sb.append(getPlanInfoVO.getInvites().get(i).getInvitedId());
                    } else {
                        sb.append(",").append(getPlanInfoVO.getInvites().get(i).getInvitedId());
                    }
                }

                GetUserIds(getActivity(), sb.toString());
            }
            mDatas = getPlanInfoVO.getInvites();
            adapter.setmDatas(mDatas);
            adapter.notifyDataSetChanged();
        }


        if (getPlanInfoVO.getMembers() != null && getPlanInfoVO.getMembers().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < getPlanInfoVO.getMembers().size(); i++) {
                if (CoolSPUtil.getDataFromLoacl(getActivity(), "uid").toString()
                        .equalsIgnoreCase(getPlanInfoVO.getMembers().get(i).getUserId() + "")) {
                    PlanDetailsActivity.PlanMeMember = true;
                }
                if (i == 0) {
                    sb.append(getPlanInfoVO.getMembers().get(i).getUserId());
                } else {
                    sb.append(",").append(getPlanInfoVO.getMembers().get(i).getUserId());
                }
            }


            GetUserMemberIds(getActivity(), sb.toString());

        } else {

        }

        //计划状态：1待匹配，2已匹配，3待开始，4支付中，5已开始，6已完成,7已结束
        switch (getPlanInfoVO.getStatus()) {
            case 1:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                    linearJoinDeal.setVisibility(View.GONE);
                    linearPlanDeal.setVisibility(View.GONE);
                } else {
                    if (PlanDetailsActivity.PlanMeInvited) {
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.VISIBLE);
                    } else {
                        linearJoin.setVisibility(View.VISIBLE);
                        linearJoinDeal.setVisibility(View.GONE);
                    }
                    linearPlanDeal.setVisibility(View.GONE);
                }
                break;
            case 2:
                linearInvites.setVisibility(View.GONE);
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearPlanDeal.setVisibility(View.VISIBLE);
                    linearJoin.setVisibility(View.GONE);
                    linearJoinDeal.setVisibility(View.GONE);
                } else {
                    if (PlanDetailsActivity.PlanMeMember) {
                        if (mgetPlanInfoVO.getDepositMode() == 0) {
                            tvPay.setVisibility(View.GONE);
                        } else {
                            tvPay.setVisibility(View.VISIBLE);
                        }
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    } else {
                        linearPlanDeal.setVisibility(View.GONE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    }

                }

                break;
            case 3:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                } else {

                }

                break;
            case 4:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                } else {

                }

                break;
            case 5:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                } else {

                }

                break;
            case 6:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                } else {

                }

                break;
            case 7:
                if (PlanDetailsActivity.PlanMeCreate) {
                    linearJoin.setVisibility(View.GONE);
                } else {

                }

                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_join, R.id.tv_refuse, R.id.tv_agree,
            R.id.tv_exit_plan, R.id.tv_pay, R.id.tv_person_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_join:
                PlanJoinBody body = new PlanJoinBody();
                body.setTestDest(mgetPlanInfoVO.getTestDest());
                PlanJoin(getActivity(), mgetPlanInfoVO.getId(), body);
                break;
            case R.id.tv_refuse:
                CoolCommonPop coolCommonPop = new CoolCommonPop();
                coolCommonPop.ShowPop(getActivity(), activityPlanDetails
                        , Gravity.CENTER, "提示", "确定拒绝邀请吗?");
                coolCommonPop.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        PlanRefuse(getActivity(), mgetPlanInfoVO.getId());
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                break;
            case R.id.tv_agree:
                CoolCommonPop coolCommonPop_Clear_im = new CoolCommonPop();
                if (mgetPlanInfoVO.getDepositMode() == 0) {
                    coolCommonPop_Clear_im.ShowPop(getActivity(), activityPlanDetails
                            , Gravity.CENTER, "提示", "加入后双方将共同制定里程碑,相互监督共同完成计划,确定立即加入吗?");
                } else {
                    coolCommonPop_Clear_im.ShowPop(getActivity(), activityPlanDetails
                            , Gravity.CENTER, "提示", "加入后双方将共同制定里程碑并开启押金模式,相互监督共同完成计划,确定立即加入吗?");
                }

                coolCommonPop_Clear_im.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        initPop();
                    }

                    @Override
                    public void onCancel() {

                    }
                });


                break;
            case R.id.tv_exit_plan:
                PlanExit(getActivity(), mgetPlanInfoVO.getId());
                break;
            case R.id.tv_pay:
                startActivity(new Intent(getActivity(), PayMoneyActivity.class)
                        .putExtra("plan", mgetPlanInfoVO));
                break;
            case R.id.tv_person_more:
                Intent intent = new Intent(getActivity(), RecommendPairPersonActivity.class);
                intent.putExtra("id", mgetPlanInfoVO.getId());
                startActivity(intent);
                break;
        }
    }


    public void initPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_confirm_join, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        final EditText et_des = (EditText) view.findViewById(R.id.et_des);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanAcceptBody body2 = new PlanAcceptBody();
                body2.setTestDest(mgetPlanInfoVO.getTestDest());
                PlanAccept(getActivity(), body2, mgetPlanInfoVO.getId());
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanAcceptBody body2 = new PlanAcceptBody();
                if (TextUtils.isEmpty(et_des.getText().toString())) {
                    body2.setTestDest(mgetPlanInfoVO.getTestDest());
                } else {
                    body2.setTestDest(et_des.getText().toString());
                }
                PlanAccept(getActivity(), body2, mgetPlanInfoVO.getId());
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(activityPlanDetails, Gravity.CENTER, 0, 0);
    }


    private void GetUserIds(final Context context, String users) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserIdsResultVO> call = CoolHttpUrl.getApi().getService().GetUserIds(users);
            call.enqueue(new Callback<GetUserIdsResultVO>() {
                @Override
                public void onResponse(Call<GetUserIdsResultVO> call, Response<GetUserIdsResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetUserIdsResultVO data = response.body();
                        CoolLogTrace.e("GetUserIds", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            mDatas_persons = data.getResult();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetUserIdsResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void GetUserMemberIds(final Context context, String users) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetUserIdsResultVO> call = CoolHttpUrl.getApi().getService().GetUserIds(users);
            System.out.println("url= " + call.request().toString());
            call.enqueue(new Callback<GetUserIdsResultVO>() {
                @Override
                public void onResponse(Call<GetUserIdsResultVO> call, Response<GetUserIdsResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetUserIdsResultVO data = response.body();
                        CoolLogTrace.e("GetUserMemberIds", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (mgetPlanInfoVO.getPlanMode() == 2) {
                                imgLine.setVisibility(View.VISIBLE);
                                relInvited.setVisibility(View.VISIBLE);
                                if (mgetPlanInfoVO.getUserId() ==
                                        Integer.valueOf(CoolSPUtil.getDataFromLoacl(getActivity(), "uid").toString())) {
                                    for (int i = 0; i < mgetPlanInfoVO.getMembers().size(); i++) {
                                        if (mgetPlanInfoVO.getUserId() == mgetPlanInfoVO.getMembers().get(i).getUserId()) {
                                            PlanDetailsActivity.CreaterBean.setNickName(data.getResult().get(i).getNickName());
                                            PlanDetailsActivity.CreaterBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(getActivity(), data.getResult().get(i).getAvatar(), imgHeadStart);
                                            tvStartName.setText(data.getResult().get(i).getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (mgetPlanInfoVO.getMembers().get(i).getPayStatus() == 2) {
                                                    tvStartStatus.setText("待支付押金");
                                                } else {
                                                    tvStartStatus.setText("已支付押金");
                                                }
                                            }

                                        } else {
                                            PlanDetailsActivity.MemberBean.setNickName(data.getResult().get(i).getNickName());
                                            PlanDetailsActivity.MemberBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(getActivity(), data.getResult().get(i).getAvatar(), imgHeadMe);
                                            tvMeName.setText(data.getResult().get(i).getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (mgetPlanInfoVO.getMembers().get(i).getPayStatus() == 2) {
                                                    tvMeStatus.setText("待支付押金");
                                                } else {
                                                    tvMeStatus.setText("已支付押金");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < mgetPlanInfoVO.getMembers().size(); i++) {
                                        if (Integer.valueOf(CoolSPUtil.getDataFromLoacl(getActivity(), "uid").toString())
                                                == data.getResult().get(i).getId()) {
                                            PlanDetailsActivity.MemberBean.setNickName(data.getResult().get(i).getNickName());
                                            PlanDetailsActivity.MemberBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(getActivity(), data.getResult().get(i).getAvatar(), imgHeadMe);
                                            tvMeName.setText(data.getResult().get(i).getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (mgetPlanInfoVO.getMembers().get(i).getPayStatus() == 2) {
                                                    tvMeStatus.setText("待支付押金");
                                                } else {
                                                    tvMeStatus.setText("已支付押金");
                                                }
                                            }
                                        } else {
                                            PlanDetailsActivity.CreaterBean.setNickName(data.getResult().get(i).getNickName());
                                            PlanDetailsActivity.CreaterBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(getActivity(), data.getResult().get(i).getAvatar(), imgHeadStart);
                                            tvStartName.setText(data.getResult().get(i).getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (mgetPlanInfoVO.getMembers().get(i).getPayStatus() == 2) {
                                                    tvStartStatus.setText("待支付押金");
                                                } else {
                                                    tvStartStatus.setText("已支付押金");
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                imgLine.setVisibility(View.GONE);
                                relInvited.setVisibility(View.GONE);
                                PlanDetailsActivity.CreaterBean.setNickName(data.getResult().get(0).getNickName());
                                PlanDetailsActivity.CreaterBean.setAvatar(data.getResult().get(0).getAvatar());
                                CoolGlideUtil.urlInto(getActivity(), data.getResult().get(0).getAvatar(), imgHeadStart);
                                tvStartName.setText(data.getResult().get(0).getNickName());
                                if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                    if (mgetPlanInfoVO.getMembers().get(0).getPayStatus() == 2) {
                                        tvStartStatus.setText("待支付押金");
                                    } else {
                                        tvStartStatus.setText("已支付押金");
                                    }
                                }
                            }
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetUserIdsResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    /**
     * 接受邀请
     *
     * @param context
     * @param body
     * @param id
     */
    private void PlanAccept(final Context context, PlanAcceptBody body, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanAcceptVO> call = CoolHttpUrl.getApi().getService().PlanAccept(id, body);
            call.enqueue(new Callback<PlanAcceptVO>() {
                @Override
                public void onResponse(Call<PlanAcceptVO> call, Response<PlanAcceptVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanAcceptVO data = response.body();
                        CoolLogTrace.e("PlanAccept", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanAcceptVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    /**
     * 拒绝接收邀请
     *
     * @param context
     * @param id
     */
    private void PlanRefuse(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanRefuseVO> call = CoolHttpUrl.getApi().getService().PlanRefuse(id);
            call.enqueue(new Callback<PlanRefuseVO>() {
                @Override
                public void onResponse(Call<PlanRefuseVO> call, Response<PlanRefuseVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanRefuseVO data = response.body();
                        CoolLogTrace.e("PlanRefuse", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanRefuseVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    /**
     * 退出计划
     *
     * @param context
     * @param id
     */
    private void PlanExit(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanExitVO> call = CoolHttpUrl.getApi().getService().PlanExit(id);
            call.enqueue(new Callback<PlanExitVO>() {
                @Override
                public void onResponse(Call<PlanExitVO> call, Response<PlanExitVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanExitVO data = response.body();
                        CoolLogTrace.e("PlanExit", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanExitVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    /**
     * 完成计划
     *
     * @param context
     * @param id
     */
    private void PlanFinish(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanFinishVO> call = CoolHttpUrl.getApi().getService().PlanFinish(id);
            call.enqueue(new Callback<PlanFinishVO>() {
                @Override
                public void onResponse(Call<PlanFinishVO> call, Response<PlanFinishVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanFinishVO data = response.body();
                        CoolLogTrace.e("PlanFinish", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanFinishVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    /**
     * 主动加入计划
     *
     * @param context
     * @param id
     */
    private void PlanJoin(final Context context, int id, PlanJoinBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanJoinVO> call = CoolHttpUrl.getApi().getService().PlanJoin(id, body);
            call.enqueue(new Callback<PlanJoinVO>() {
                @Override
                public void onResponse(Call<PlanJoinVO> call, Response<PlanJoinVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanJoinVO data = response.body();
                        CoolLogTrace.e("PlanJoin", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanJoinVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    /**
     * 结束计划
     *
     * @param context
     * @param id
     */
    private void PlanOver(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanOverVO> call = CoolHttpUrl.getApi().getService().PlanOver(id);
            call.enqueue(new Callback<PlanOverVO>() {
                @Override
                public void onResponse(Call<PlanOverVO> call, Response<PlanOverVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanOverVO data = response.body();
                        CoolLogTrace.e("PlanOver", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            PlanDetailsActivity.GetPlanInfo(getActivity(), mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanOverVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

}
