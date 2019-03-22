package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.njcool.lzccommon.adapter.CoolCommonRecycleviewAdapter;
import com.njcool.lzccommon.adapter.CoolOnItemClickListener;
import com.njcool.lzccommon.adapter.CoolRecycleViewHolder;
import com.njcool.lzccommon.common.CoolCommonPop;
import com.njcool.lzccommon.common.CoolPhotoPop;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.fresh.CoolSwipeRefreshLayout;
import com.njcool.lzccommon.view.photo.model.InvokeParam;
import com.njcool.lzccommon.view.photo.model.TContextWrap;
import com.njcool.lzccommon.view.photo.model.TResult;
import com.njcool.lzccommon.view.photo.permission.InvokeListener;
import com.njcool.lzccommon.view.photo.permission.PermissionManager;
import com.njcool.lzccommon.view.photo.permission.TakePhotoInvocationHandler;
import com.njcool.lzccommon.view.photo.ui.CoolPhotoOptions;
import com.njcool.lzccommon.view.photo.ui.TakePhoto;
import com.njcool.lzccommon.view.photo.ui.TakePhotoImpl;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.MilestoneAlarmBody;
import com.shaohua.sheducation.request.MilestoneClockBody;
import com.shaohua.sheducation.request.MilestoneRemindBody;
import com.shaohua.sheducation.request.PlanAcceptBody;
import com.shaohua.sheducation.request.PlanJoinBody;
import com.shaohua.sheducation.resultbean.GetMilestonesConfirmResultVO;
import com.shaohua.sheducation.resultbean.GetMilestonesResultVO;
import com.shaohua.sheducation.resultbean.GetPlanInfoResultVO;
import com.shaohua.sheducation.resultbean.GetUserIdsResultVO;
import com.shaohua.sheducation.resultbean.GetUserInfoVO;
import com.shaohua.sheducation.resultbean.MilestoneAlarmVO;
import com.shaohua.sheducation.resultbean.MilestoneClockVO;
import com.shaohua.sheducation.resultbean.MilestoneDeleteVO;
import com.shaohua.sheducation.resultbean.MilestoneRemindVO;
import com.shaohua.sheducation.resultbean.MilestonesRefuseResultVO;
import com.shaohua.sheducation.resultbean.MilestonesSubmitResultVO;
import com.shaohua.sheducation.resultbean.PlanAcceptVO;
import com.shaohua.sheducation.resultbean.PlanExitVO;
import com.shaohua.sheducation.resultbean.PlanFinishVO;
import com.shaohua.sheducation.resultbean.PlanJoinVO;
import com.shaohua.sheducation.resultbean.PlanMemberVO;
import com.shaohua.sheducation.resultbean.PlanOverVO;
import com.shaohua.sheducation.resultbean.PlanRefuseVO;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanDetails2Activity extends SHBaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_help)
    ImageView imgHelp;
    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_summary)
    TextView tvSummary;
    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_details)
    TextView tvDetails;
    @Bind(R.id.tv_stores)
    TextView tvStores;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
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
    @Bind(R.id.tv_person_invite)
    TextView tvPersonInvite;
    @Bind(R.id.tv_person_more)
    TextView tvPersonMore;
    @Bind(R.id.rcv_persons)
    RecyclerView rcvPersons;
    @Bind(R.id.linear_invites)
    LinearLayout linearInvites;
    @Bind(R.id.tv_person_nums)
    TextView tvPersonNums;
    @Bind(R.id.img_head_start)
    CoolCircleImageView imgHeadStart;
    @Bind(R.id.tv_start_name)
    TextView tvStartName;
    @Bind(R.id.tv_start_status)
    TextView tvStartStatus;
    @Bind(R.id.rel_creater)
    RelativeLayout relCreater;
    @Bind(R.id.img_line)
    ImageView imgLine;
    @Bind(R.id.img_head_me)
    CoolCircleImageView imgHeadMe;
    @Bind(R.id.tv_me_name)
    TextView tvMeName;
    @Bind(R.id.tv_me_status)
    TextView tvMeStatus;
    @Bind(R.id.rel_invited)
    RelativeLayout relInvited;
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.linear_join)
    LinearLayout linearJoin;
    @Bind(R.id.tv_refuse)
    TextView tvRefuse;
    @Bind(R.id.tv_agree)
    TextView tvAgree;
    @Bind(R.id.linear_join_deal)
    LinearLayout linearJoinDeal;
    @Bind(R.id.tv_exit_plan)
    TextView tvExitPlan;
    @Bind(R.id.tv_pay)
    TextView tvPay;
    @Bind(R.id.linear_plan_deal)
    LinearLayout linearPlanDeal;
    @Bind(R.id.nest_plan_details)
    NestedScrollView nestPlanDetails;
    @Bind(R.id.rcv_store_waiting)
    RecyclerView rcvStoreWaiting;
    @Bind(R.id.swp_store_waiting)
    CoolSwipeRefreshLayout swpStoreWaiting;
    @Bind(R.id.tv_submit_store)
    TextView tvSubmitStore;
    @Bind(R.id.linear_submit_store)
    LinearLayout linearSubmitStore;
    @Bind(R.id.img_store_head)
    CoolCircleImageView imgStoreHead;
    @Bind(R.id.tv_store_name)
    TextView tvStoreName;
    @Bind(R.id.linear_wait_status)
    LinearLayout linearWaitStatus;
    @Bind(R.id.tv_unagree_store)
    TextView tvUnagreeStore;
    @Bind(R.id.tv_agree_store)
    TextView tvAgreeStore;
    @Bind(R.id.linear_agree_deal)
    LinearLayout linearAgreeDeal;
    @Bind(R.id.linear_store_waiting)
    LinearLayout linearStoreWaiting;
    @Bind(R.id.tv_start_nums)
    TextView tvStartNums;
    @Bind(R.id.tv_start_days)
    TextView tvStartDays;
    @Bind(R.id.img_head_start_dealing)
    ImageView imgHeadStartDealing;
    @Bind(R.id.tv_me_nums)
    TextView tvMeNums;
    @Bind(R.id.tv_me_days)
    TextView tvMeDays;
    @Bind(R.id.img_head_me_dealing)
    ImageView imgHeadMeDealing;
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
    @Bind(R.id.nest_store_dealing)
    NestedScrollView nestStoreDealing;
    @Bind(R.id.tv_store_status)
    TextView tvStoreStatus;
    @Bind(R.id.rel_store_person_one)
    RelativeLayout relStorePersonOne;
    @Bind(R.id.rel_store_person_two)
    RelativeLayout relStorePersonTwo;


    private int id = 0;
    private GetPlanInfoResultVO.ResultBean mgetPlanInfoVO;
    private PopupWindow pop;

    private CoolCommonRecycleviewAdapter<GetPlanInfoResultVO.ResultBean.InvitesBean> adapter_invites;
    private List<GetPlanInfoResultVO.ResultBean.InvitesBean> mDatas_invites;
    private List<GetUserIdsResultVO.ResultBean> mDatas_persons;
    private LinearLayoutManager linearLayoutManager_invites;


    private CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean> adapter_store_waiting;
    private LinearLayoutManager linearLayoutManager_store_waiting;
    private List<GetMilestonesResultVO.ResultBean> mDatas_store_waiting;


    private CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean> adapter_unfinish;
    private LinearLayoutManager linearLayoutManager_unfinish;
    private List<GetMilestonesResultVO.ResultBean> mDatas_unfinish;
    private CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean> adapter_finish;
    private LinearLayoutManager linearLayoutManager_finish;
    private List<GetMilestonesResultVO.ResultBean> mDatas_finish;


    /**
     * 计划创建者信息
     */
    public PlanMemberVO CreaterBean = new PlanMemberVO();
    /**
     * 计划成员信息
     */
    public PlanMemberVO MemberBean = new PlanMemberVO();

    /**
     * 我的角色，0 不相干  1 创建者  2  协作者
     */
    public int planMeRole = 0;


    private static final String TAG = PlanDetails2Activity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details2);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        findViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void findViews() {
        if (id == 0) {
            finish();
        }
        //详情
        linearLayoutManager_invites = new LinearLayoutManager(PlanDetails2Activity.this, LinearLayoutManager.VERTICAL, false);
        rcvPersons.setLayoutManager(linearLayoutManager_invites);
        adapter_invites = new CoolCommonRecycleviewAdapter<GetPlanInfoResultVO.ResultBean.InvitesBean>(mDatas_invites, PlanDetails2Activity.this,
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
                LinearLayoutManager li = new LinearLayoutManager(PlanDetails2Activity.this, LinearLayoutManager.HORIZONTAL, false);
                rcv_tags.setLayoutManager(li);
                rcv_tags.setAdapter(new CoolCommonRecycleviewAdapter(mDatas_persons.get(position).getLabels(),
                        PlanDetails2Activity.this, R.layout.item_classmate_tag) {
                    @Override
                    protected void onBindView(CoolRecycleViewHolder holder, int subposition) {
                        TextView tv_tag = holder.getView(R.id.tv_tag);
                        tv_tag.setText(mDatas_persons.get(position).getLabels().get(subposition).getName());
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                    }
                });

                CoolGlideUtil.urlInto(PlanDetails2Activity.this, mDatas_persons.get(position).getAvatar(), img_head);
                tv_name.setText(mDatas_persons.get(position).getNickName());
                if (mDatas_persons.get(position).getGender() == 1) {
                    img_gender.setImageResource(R.mipmap.icon_women);
                } else {
                    img_gender.setImageResource(R.mipmap.icon_man);
                }

                if (mDatas_invites.get(position).getStatus() == 1) {
                    tv_status.setText("已邀请");
                } else if (mDatas_invites.get(position).getStatus() == 2) {
                    tv_status.setText("已接受");
                } else if (mDatas_invites.get(position).getStatus() == 3) {
                    tv_status.setText("已拒绝");
                }
            }
        };
        rcvPersons.setAdapter(adapter_invites);


        //里程碑

        linearLayoutManager_store_waiting = new LinearLayoutManager(PlanDetails2Activity.this, LinearLayoutManager.VERTICAL, false);
        rcvStoreWaiting.setLayoutManager(linearLayoutManager_store_waiting);
        adapter_store_waiting = new CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean>(mDatas_store_waiting, PlanDetails2Activity.this, R.layout.item_plan_store) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_add_new = holder.getView(R.id.tv_add_new);
                CardView cv_plan_store = holder.getView(R.id.cv_plan_store);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
                if (planMeRole == 1) {
                    if (position == mDatas_store_waiting.size() - 1) {
                        cv_plan_store.setVisibility(View.GONE);
                        tv_add_new.setVisibility(View.VISIBLE);
                    } else {
                        cv_plan_store.setVisibility(View.VISIBLE);
                        tv_add_new.setVisibility(View.GONE);
                        tv_title.setText(mDatas_store_waiting.get(position).getName());
                        tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas_store_waiting.get(position).getFinishTime()));
                    }
                } else {
                    tv_add_new.setVisibility(View.GONE);
                    cv_plan_store.setVisibility(View.VISIBLE);
                    tv_title.setText(mDatas_store_waiting.get(position).getName());
                    tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas_store_waiting.get(position).getFinishTime()));
                }

                tv_add_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PlanDetails2Activity.this, EditPlanStoreActivity.class)
                                .putExtra("id", id));
                    }
                });

            }
        };
        rcvStoreWaiting.setAdapter(adapter_store_waiting);
        swpStoreWaiting.setOnRefreshListener(new CoolSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMilestones(PlanDetails2Activity.this, id);
            }
        });
        adapter_store_waiting.setOnItemClickListener(new CoolOnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (planMeRole == 1) {
                    Intent intent = new Intent(PlanDetails2Activity.this, EditPlanStoreActivity.class);
                    intent.putExtra("store", mDatas_store_waiting.get(position));
                    intent.putExtra("id", mDatas_store_waiting.get(position).getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });


        linearLayoutManager_unfinish = new LinearLayoutManager(PlanDetails2Activity.this, LinearLayoutManager.VERTICAL, false);
        rcvUnfinish.setLayoutManager(linearLayoutManager_unfinish);
        adapter_unfinish = new CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean>(mDatas_unfinish, PlanDetails2Activity.this,
                R.layout.item_plan_store_dealing) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, final int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
                CoolCircleImageView img_person_one = holder.getView(R.id.img_person_one);
                CoolCircleImageView img_person_two = holder.getView(R.id.img_person_two);
                ImageView img_open = holder.getView(R.id.img_open);
                LinearLayout linear_cards = holder.getView(R.id.linear_cards);
                LinearLayout linear_creater = holder.getView(R.id.linear_creater);
                LinearLayout linear_member = holder.getView(R.id.linear_member);
                ImageView img_camere = holder.getView(R.id.img_camere);
                ImageView img_me_pic = holder.getView(R.id.img_me_pic);
                ImageView img_user_pic = holder.getView(R.id.img_user_pic);
                ImageView img_notice = holder.getView(R.id.img_notice);
                CoolCircleImageView img_head_one = holder.getView(R.id.img_head_one);
                CoolCircleImageView img_head_two = holder.getView(R.id.img_head_two);
                TextView tv_one_status = holder.getView(R.id.tv_one_status);
                TextView tv_two_status = holder.getView(R.id.tv_two_status);

                tv_title.setText(mDatas_unfinish.get(position).getName());
                tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas_unfinish.get(position).getFinishTime()));
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    img_person_two.setVisibility(View.GONE);
                    linear_member.setVisibility(View.GONE);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_one);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_one);
                    if (mDatas_unfinish.get(position).getMy() != null && mDatas_unfinish.get(position).getMy().getStatus() == 1) {
                        //已打卡
                        tv_one_status.setText("已打卡");
                        img_camere.setVisibility(View.GONE);
                        img_me_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, mDatas_unfinish.get(position).getMy().getImage(), img_me_pic);
                    } else {
                        //未打卡
                        img_camere.setVisibility(View.VISIBLE);
                        img_me_pic.setVisibility(View.GONE);
                        tv_one_status.setText("未打卡");
                        img_camere.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                } else {
                    img_person_two.setVisibility(View.VISIBLE);
                    linear_member.setVisibility(View.VISIBLE);
                    if (planMeRole == 1) {
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_person_two);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_head_two);
                    } else {
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_person_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_two);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_head_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_two);
                    }
                    if (mDatas_unfinish.get(position).getMy() != null && mDatas_unfinish.get(position).getMy().getStatus() == 1) {
                        //已打卡
                        tv_one_status.setText("已打卡");
                        img_camere.setVisibility(View.GONE);
                        img_me_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this,
                                mDatas_unfinish.get(position).getMy().getImage(), img_me_pic);
                    } else {
                        //未打卡
                        img_camere.setVisibility(View.VISIBLE);
                        img_me_pic.setVisibility(View.GONE);
                        tv_one_status.setText("未打卡");

                        img_camere.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CoolPhotoPop coolPhotoPop = new CoolPhotoPop();
                                CoolPhotoOptions coolPhotoOptions = new CoolPhotoOptions();
                                coolPhotoOptions.setCropTool(false);
                                coolPhotoPop.ShowPop(PlanDetails2Activity.this, nestStoreDealing, Gravity.BOTTOM,
                                        getTakePhoto(), coolPhotoOptions);
                            }
                        });

                    }

                    if (mDatas_unfinish.get(position).getOther() != null && mDatas_unfinish.get(position).getOther().getStatus() == 1) {
                        //已打卡
                        tv_two_status.setText("已打卡");
                        img_notice.setVisibility(View.GONE);
                        img_user_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this,
                                mDatas_unfinish.get(position).getOther().getImage(), img_user_pic);
                    } else {
                        //未打卡
                        img_notice.setVisibility(View.VISIBLE);
                        img_user_pic.setVisibility(View.GONE);
                        tv_two_status.setText("未打卡");
                        img_notice.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MilestoneRemindBody body = new MilestoneRemindBody();
                                body.setUserId(mDatas_unfinish.get(position).getOther().getUserId());
                                MilestoneRemind(PlanDetails2Activity.this, id, body);
                            }
                        });
                    }

                }
            }
        };
        rcvUnfinish.setAdapter(adapter_unfinish);
        linearLayoutManager_finish = new LinearLayoutManager(PlanDetails2Activity.this, LinearLayoutManager.VERTICAL, false);
        rcvFinish.setLayoutManager(linearLayoutManager_finish);
        adapter_finish = new CoolCommonRecycleviewAdapter<GetMilestonesResultVO.ResultBean>(mDatas_finish, PlanDetails2Activity.this,
                R.layout.item_plan_store_dealing) {
            @Override
            protected void onBindView(CoolRecycleViewHolder holder, int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_time = holder.getView(R.id.tv_time);
                CoolCircleImageView img_person_one = holder.getView(R.id.img_person_one);
                CoolCircleImageView img_person_two = holder.getView(R.id.img_person_two);
                ImageView img_open = holder.getView(R.id.img_open);
                LinearLayout linear_cards = holder.getView(R.id.linear_cards);
                LinearLayout linear_creater = holder.getView(R.id.linear_creater);
                LinearLayout linear_member = holder.getView(R.id.linear_member);
                ImageView img_camere = holder.getView(R.id.img_camere);
                ImageView img_me_pic = holder.getView(R.id.img_me_pic);
                ImageView img_user_pic = holder.getView(R.id.img_user_pic);
                ImageView img_notice = holder.getView(R.id.img_notice);
                CoolCircleImageView img_head_one = holder.getView(R.id.img_head_one);
                CoolCircleImageView img_head_two = holder.getView(R.id.img_head_two);
                TextView tv_one_status = holder.getView(R.id.tv_one_status);
                TextView tv_two_status = holder.getView(R.id.tv_two_status);
                tv_title.setText(mDatas_finish.get(position).getName());
                tv_time.setText(CoolPublicMethod.timeStamp2DateYMD(mDatas_finish.get(position).getFinishTime()));
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    img_person_two.setVisibility(View.GONE);
                    linear_member.setVisibility(View.GONE);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_one);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_one);
                    if (mDatas_finish.get(position).getMy() != null && mDatas_finish.get(position).getMy().getStatus() == 1) {
                        //已打卡
                        tv_one_status.setText("已打卡");
                        img_camere.setVisibility(View.GONE);
                        img_me_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, mDatas_finish.get(position).getMy().getImage(), img_me_pic);
                    } else {
                        //未打卡
                        img_camere.setVisibility(View.VISIBLE);
                        img_me_pic.setVisibility(View.GONE);
                        tv_one_status.setText("未打卡");
                    }
                } else {
                    img_person_two.setVisibility(View.VISIBLE);
                    linear_member.setVisibility(View.VISIBLE);
                    if (planMeRole == 1) {
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_person_two);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_head_two);
                    } else {
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_person_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_person_two);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), img_head_one);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), img_head_two);
                    }

                    if (mDatas_finish.get(position).getMy() != null && mDatas_finish.get(position).getMy().getStatus() == 1) {
                        //已打卡
                        tv_one_status.setText("已打卡");
                        img_camere.setVisibility(View.GONE);
                        img_me_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this,
                                mDatas_finish.get(position).getMy().getImage(), img_me_pic);
                    } else {
                        //未打卡
                        img_camere.setVisibility(View.VISIBLE);
                        img_me_pic.setVisibility(View.GONE);
                        tv_one_status.setText("未打卡");
                    }

                    if (mDatas_finish.get(position).getOther() != null && mDatas_finish.get(position).getOther().getStatus() == 1) {
                        //已打卡
                        tv_two_status.setText("已打卡");
                        img_notice.setVisibility(View.GONE);
                        img_user_pic.setVisibility(View.VISIBLE);
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this,
                                mDatas_finish.get(position).getOther().getImage(), img_user_pic);
                    } else {
                        //未打卡
                        img_notice.setVisibility(View.VISIBLE);
                        img_user_pic.setVisibility(View.GONE);
                        tv_two_status.setText("未打卡");
                    }

                }
            }
        };
        rcvFinish.setAdapter(adapter_finish);


        GetPlanInfo(PlanDetails2Activity.this, id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                default:
                    getTakePhoto().onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(final TResult result) {
        Log.i(TAG, "takeSuccess：" + result.getImage().getCompressPath());
//        CoolGlideUtil.FileInto(PersonalInfoActivity.this, result.getImage().getCompressPath(), imgHead);

        new Thread() {
            @Override
            public void run() {
                super.run();
                update(result.getImage().getCompressPath());
                //上传图片
            }
        }.start();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.i(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        Log.i(TAG, getResources().getString(R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    private OSS oss;

    private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAIGbMsEyoWIMfc";
    private static final String accessKeySecret = "Tb4020rc8wwJSiDRJxDmXM9owrFCoJ";

    private static final String testBucket = "shaohua-edu-all-files";
    private static String uploadObject = "";
    private String url = "";

    private void update(final String uploadFilePath) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        new Thread(new Runnable() {
            @Override
            public void run() {
                resumableUpload(uploadFilePath);
            }
        }).start();
    }

    // 异步断点上传，不设置记录保存路径，只在本次上传内做断点续传
    public void resumableUpload(String uploadFilePath) {
        url = "";
        uploadObject = "head_" + CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this, "uid").toString()
                + "_" + System.currentTimeMillis() + ".jpg";
        // 创建断点上传请求
        ResumableUploadRequest request = new ResumableUploadRequest(testBucket, uploadObject, uploadFilePath);
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                Log.d("resumableUpload", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        // 异步调用断点上传
        OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                CoolPublicMethod.hideProgressDialog();
                Log.d("resumableUpload", "success!");
                url = "http://shaohua-edu-all-files.oss-cn-beijing.aliyuncs.com/" + uploadObject;
                Log.d("resumableUpload", url);
                Message message = new Message();
                message.what = 999;
                message.obj = url;
                handlerDealImage.sendMessage(message);
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                url = "";
                CoolPublicMethod.Toast(PlanDetails2Activity.this, "图片上传失败，请重新选择上传");
            }
        });

        resumableTask.waitUntilFinished();
    }

    private Handler handlerDealImage = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {

                case 999:
                    url = (String) msg.obj;
                    //打卡
                    MilestoneClockBody body = new MilestoneClockBody();
                    body.setImage(url);
                    MilestoneClock(PlanDetails2Activity.this, id, body);

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    private void setData(GetPlanInfoResultVO.ResultBean getPlanInfoVO) {
        mgetPlanInfoVO = getPlanInfoVO;

        if (getPlanInfoVO.getUserId() == Integer.valueOf(CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this,
                "uid").toString())) {
            planMeRole = 1;
        } else {
            for (int i = 0; i < getPlanInfoVO.getInvites().size(); i++) {
                if (CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this, "uid").toString()
                        .equalsIgnoreCase(getPlanInfoVO.getInvites().get(i).getInvitedId() + "")) {
                    planMeRole = 2;
                }
            }
            for (int i = 0; i < getPlanInfoVO.getMembers().size(); i++) {
                if (CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this, "uid").toString()
                        .equalsIgnoreCase(getPlanInfoVO.getMembers().get(i).getUserId() + "")) {
                    planMeRole = 2;
                }
            }
        }
        for (int i = 0; i < getPlanInfoVO.getMembers().size(); i++) {
            if (getPlanInfoVO.getUserId() == getPlanInfoVO.getMembers().get(i).getUserId()) {
                CreaterBean.setDepositMoney(getPlanInfoVO.getMembers().get(i).getDepositMoney());
                CreaterBean.setFinishStatus(getPlanInfoVO.getMembers().get(i).getFinishStatus());
                CreaterBean.setId(getPlanInfoVO.getMembers().get(i).getId());
                CreaterBean.setMilestoneFinishNum(getPlanInfoVO.getMembers().get(i).getMilestoneFinishNum());
                CreaterBean.setMilestoneNum(getPlanInfoVO.getMembers().get(i).getMilestoneNum());
                CreaterBean.setPayStatus(getPlanInfoVO.getMembers().get(i).getPayStatus());
                CreaterBean.setMilestoneStatus(getPlanInfoVO.getMembers().get(i).getMilestoneStatus());
                CreaterBean.setPlanId(getPlanInfoVO.getMembers().get(i).getPlanId());
                CreaterBean.setRefuseReason(getPlanInfoVO.getMembers().get(i).getRefuseReason());
                CreaterBean.setStatus(getPlanInfoVO.getMembers().get(i).getStatus());
                CreaterBean.setTestDest(getPlanInfoVO.getMembers().get(i).getTestDest());
                CreaterBean.setUserId(getPlanInfoVO.getMembers().get(i).getUserId());
            } else {
                MemberBean.setDepositMoney(getPlanInfoVO.getMembers().get(i).getDepositMoney());
                MemberBean.setFinishStatus(getPlanInfoVO.getMembers().get(i).getFinishStatus());
                MemberBean.setId(getPlanInfoVO.getMembers().get(i).getId());
                MemberBean.setMilestoneFinishNum(getPlanInfoVO.getMembers().get(i).getMilestoneFinishNum());
                MemberBean.setMilestoneNum(getPlanInfoVO.getMembers().get(i).getMilestoneNum());
                MemberBean.setPayStatus(getPlanInfoVO.getMembers().get(i).getPayStatus());
                MemberBean.setMilestoneStatus(getPlanInfoVO.getMembers().get(i).getMilestoneStatus());
                MemberBean.setPlanId(getPlanInfoVO.getMembers().get(i).getPlanId());
                MemberBean.setRefuseReason(getPlanInfoVO.getMembers().get(i).getRefuseReason());
                MemberBean.setStatus(getPlanInfoVO.getMembers().get(i).getStatus());
                MemberBean.setTestDest(getPlanInfoVO.getMembers().get(i).getTestDest());
                MemberBean.setUserId(getPlanInfoVO.getMembers().get(i).getUserId());
            }
        }

        CoolGlideUtil.urlInto(PlanDetails2Activity.this, getPlanInfoVO.getImage(), imgPic);
        tvTitle.setText(getPlanInfoVO.getName());
        tvSummary.setText(getPlanInfoVO.getDescription());

        tvCheckTime.setText(CoolPublicMethod.timeStamp2DateYMD(getPlanInfoVO.getTestTime()));
        tvPlanType.setText(getPlanInfoVO.getLabel().getName());
        tvPlanMode.setText(getPlanInfoVO.getPlanMode() == 1 ? "单人模式" : "双人模式");
        tvPlanMoney.setText(getPlanInfoVO.getDepositMode() == 0 ? "不支持" : "支持");
        tvPlanDestination.setText(getPlanInfoVO.getTestDest());
        tvPlanId.setText(getPlanInfoVO.getId() + "");


        if (mgetPlanInfoVO.getStatus() == 1) {

        } else {
            linearInvites.setVisibility(View.GONE);
        }


        //处理成员
        if (getPlanInfoVO.getMembers() != null && getPlanInfoVO.getMembers().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < getPlanInfoVO.getMembers().size(); i++) {
                if (i == 0) {
                    sb.append(getPlanInfoVO.getMembers().get(i).getUserId());
                } else {
                    sb.append(",").append(getPlanInfoVO.getMembers().get(i).getUserId());
                }
            }

            GetUserMemberIds(PlanDetails2Activity.this, sb.toString());

        }

        //计划状态：1待匹配，2已匹配，3待开始，4支付中，5已开始，6已完成,7已结束
        switch (getPlanInfoVO.getStatus()) {
            case 1:
                tvStores.setEnabled(false);
                //待匹配时展示已邀请人员列表
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    linearInvites.setVisibility(View.GONE);
                } else {
                    linearInvites.setVisibility(View.VISIBLE);
                    if (getPlanInfoVO.getInvites() != null && getPlanInfoVO.getInvites().size() > 0) {
                        tvPersonInvite.setText("已邀请同学(" + getPlanInfoVO.getInvites().size() + ")");
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < getPlanInfoVO.getInvites().size(); i++) {
                            if (i == 0) {
                                sb.append(getPlanInfoVO.getInvites().get(i).getInvitedId());
                            } else {
                                sb.append(",").append(getPlanInfoVO.getInvites().get(i).getInvitedId());
                            }
                        }
                        GetUserIds(PlanDetails2Activity.this, sb.toString());
                    }
                    mDatas_invites = getPlanInfoVO.getInvites();
                    adapter_invites.setmDatas(mDatas_invites);
                    adapter_invites.notifyDataSetChanged();
                }

                if (planMeRole == 1) {
                    linearJoin.setVisibility(View.GONE);
                    linearJoinDeal.setVisibility(View.GONE);
                    linearPlanDeal.setVisibility(View.GONE);
                } else if (planMeRole == 2) {
                    linearJoin.setVisibility(View.GONE);
                    linearJoinDeal.setVisibility(View.VISIBLE);
                    linearPlanDeal.setVisibility(View.GONE);
                } else {
                    if (mgetPlanInfoVO.getPlanMode() == 1) {
                        linearJoin.setVisibility(View.GONE);
                    } else {
                        linearJoin.setVisibility(View.VISIBLE);
                    }
                    linearJoinDeal.setVisibility(View.GONE);
                    linearPlanDeal.setVisibility(View.GONE);
                }
                break;
            case 2:
            case 3:
                if (planMeRole == 1 || planMeRole == 2) {
                    tvStores.setEnabled(true);
                } else {
                    tvStores.setEnabled(false);
                }

                linearInvites.setVisibility(View.GONE);
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    if (planMeRole == 1) {
                        if (mgetPlanInfoVO.getDepositMode() == 1) {
                            linearPlanDeal.setVisibility(View.VISIBLE);
                            tvPay.setVisibility(View.GONE);
                        } else {
                            linearPlanDeal.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (planMeRole == 1) {
                        linearPlanDeal.setVisibility(View.GONE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    } else if (planMeRole == 2) {
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    } else {
                        linearPlanDeal.setVisibility(View.GONE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    }
                }

                break;
            case 4:
                if (planMeRole == 1 || planMeRole == 2) {
                    tvStores.setEnabled(true);
                } else {
                    tvStores.setEnabled(false);
                }
                linearInvites.setVisibility(View.GONE);
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    if (planMeRole == 1) {
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        if (mgetPlanInfoVO.getDepositMode() == 1) {
                            tvPay.setVisibility(View.VISIBLE);
                        } else {
                            tvPay.setVisibility(View.GONE);
                        }
                    } else {
                        linearPlanDeal.setVisibility(View.GONE);
                    }
                } else {
                    if (planMeRole == 1) {
                        linearJoin.setVisibility(View.GONE);
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        if (mgetPlanInfoVO.getDepositMode() == 1) {
                            tvPay.setVisibility(View.VISIBLE);
                        } else {
                            tvPay.setVisibility(View.GONE);
                        }
                    } else if (planMeRole == 2) {
                        linearJoin.setVisibility(View.GONE);
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        if (mgetPlanInfoVO.getDepositMode() == 1) {
                            tvPay.setVisibility(View.VISIBLE);
                        } else {
                            tvPay.setVisibility(View.GONE);
                        }
                    } else {
                        linearPlanDeal.setVisibility(View.GONE);
                        linearJoin.setVisibility(View.GONE);
                        linearJoinDeal.setVisibility(View.GONE);
                    }
                }

                break;
            case 5:
                if (planMeRole == 1 || planMeRole == 2) {
                    tvStores.setEnabled(true);
                } else {
                    tvStores.setEnabled(false);
                }
                linearInvites.setVisibility(View.GONE);
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    if (planMeRole == 1) {
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);
                    } else {
                        linearPlanDeal.setVisibility(View.GONE);
                    }
                } else {
                    if (planMeRole == 1) {
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);
                    } else if (planMeRole == 2) {
                        linearPlanDeal.setVisibility(View.VISIBLE);
                        tvPay.setVisibility(View.GONE);
                    } else {

                    }
                }

                break;
            case 6:
                if (planMeRole == 1 || planMeRole == 2) {
                    tvStores.setEnabled(true);
                } else {
                    tvStores.setEnabled(false);
                }
                linearInvites.setVisibility(View.GONE);
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    if (planMeRole == 1) {

                    } else {

                    }

                } else {
                    if (planMeRole == 1) {

                    } else if (planMeRole == 2) {

                    } else {

                    }
                }

                break;
            case 7:
                if (planMeRole == 1 || planMeRole == 2) {
                    tvStores.setEnabled(true);
                } else {
                    tvStores.setEnabled(false);
                }
                linearInvites.setVisibility(View.GONE);

                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    if (planMeRole == 1) {

                    } else {

                    }

                } else {
                    if (planMeRole == 1) {

                    } else if (planMeRole == 2) {

                    } else {

                    }
                }
                break;
            default:
                break;
        }
    }

    private void setStoreStatus() {
        GetMilestones(PlanDetails2Activity.this, id);
        if (mgetPlanInfoVO.getStatus() == 1 || mgetPlanInfoVO.getStatus() == 2 || mgetPlanInfoVO.getStatus() == 3 ||
                mgetPlanInfoVO.getStatus() == 4) {
            linearStoreWaiting.setVisibility(View.VISIBLE);
            nestStoreDealing.setVisibility(View.GONE);
        } else {
            linearStoreWaiting.setVisibility(View.GONE);
            nestStoreDealing.setVisibility(View.VISIBLE);
            if (mgetPlanInfoVO.getPlanMode() == 1) {
                relStorePersonTwo.setVisibility(View.GONE);
                if (planMeRole == 1) {
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHeadStartDealing);
                } else {
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgHeadStartDealing);

                }
            } else {
                relStorePersonTwo.setVisibility(View.VISIBLE);
                if (planMeRole == 1) {
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHeadStartDealing);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgHeadMeDealing);
                } else {
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgHeadStartDealing);
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHeadMeDealing);

                }
            }
        }
        if (mgetPlanInfoVO.getStatus() == 2) {
            if (CreaterBean.getMilestoneStatus() == 0) {
                if (planMeRole != 1) {
                    CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgStoreHead);
                    tvStoreName.setText(CreaterBean.getNickName());
                    tvStoreStatus.setText("提交里程碑");
                    linearSubmitStore.setVisibility(View.GONE);
                    linearWaitStatus.setVisibility(View.VISIBLE);
                    linearAgreeDeal.setVisibility(View.GONE);
                } else {
                    linearAgreeDeal.setVisibility(View.GONE);
                    linearWaitStatus.setVisibility(View.GONE);
                    linearSubmitStore.setVisibility(View.VISIBLE);
                }
            } else if (CreaterBean.getMilestoneStatus() == 1) {
                if (planMeRole == 1) {
                    if (MemberBean.getMilestoneStatus() != 3) {
                        linearSubmitStore.setVisibility(View.GONE);
                        linearWaitStatus.setVisibility(View.VISIBLE);
                        linearAgreeDeal.setVisibility(View.GONE);
                        tvStoreName.setText(MemberBean.getNickName());
                        tvStoreStatus.setText("确认里程碑");
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgStoreHead);
                    } else {
                        //对方拒绝
                        CoolPublicMethod.Toast(PlanDetails2Activity.this, "对方拒绝了里程碑，请重新修改提交");
                        linearAgreeDeal.setVisibility(View.GONE);
                        linearWaitStatus.setVisibility(View.GONE);
                        linearSubmitStore.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (MemberBean.getMilestoneStatus() != 3) {
                        linearSubmitStore.setVisibility(View.GONE);
                        linearWaitStatus.setVisibility(View.GONE);
                        linearAgreeDeal.setVisibility(View.VISIBLE);
                    } else {
                        //已拒绝
                        CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgStoreHead);
                        tvStoreName.setText(CreaterBean.getNickName());
                        tvStoreStatus.setText("提交里程碑");
                        linearSubmitStore.setVisibility(View.GONE);
                        linearWaitStatus.setVisibility(View.VISIBLE);
                        linearAgreeDeal.setVisibility(View.GONE);
                    }
                }
            }
        } else {
            linearSubmitStore.setVisibility(View.GONE);
            linearWaitStatus.setVisibility(View.GONE);
            linearAgreeDeal.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.img_help, R.id.tv_details, R.id.tv_stores, R.id.tv_person_more, R.id.tv_join, R.id.tv_refuse, R.id.tv_agree, R.id.tv_exit_plan, R.id.tv_pay, R.id.tv_submit_store, R.id.tv_unagree_store, R.id.tv_agree_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_help:
                break;
            case R.id.tv_details:
                nestPlanDetails.setVisibility(View.VISIBLE);
                linearStoreWaiting.setVisibility(View.GONE);
                nestStoreDealing.setVisibility(View.GONE);
                break;
            case R.id.tv_stores:
                nestPlanDetails.setVisibility(View.GONE);
                setStoreStatus();
                break;
            case R.id.tv_person_more:
                Intent intent = new Intent(PlanDetails2Activity.this, RecommendPairPersonActivity.class);
                intent.putExtra("id", mgetPlanInfoVO.getId());
                startActivity(intent);
                break;
            case R.id.tv_join:
                PlanJoinBody body = new PlanJoinBody();
                body.setTestDest(mgetPlanInfoVO.getTestDest());
                PlanJoin(PlanDetails2Activity.this, mgetPlanInfoVO.getId(), body);
                break;
            case R.id.tv_refuse:
                CoolCommonPop coolCommonPop = new CoolCommonPop();
                coolCommonPop.ShowPop(PlanDetails2Activity.this, nestPlanDetails
                        , Gravity.CENTER, "提示", "确定拒绝邀请吗?");
                coolCommonPop.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        PlanRefuse(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.tv_agree:
                CoolCommonPop coolCommonPop_agree = new CoolCommonPop();
                if (mgetPlanInfoVO.getDepositMode() == 0) {
                    coolCommonPop_agree.ShowPop(PlanDetails2Activity.this, nestPlanDetails
                            , Gravity.CENTER, "提示", "加入后双方将共同制定里程碑,相互监督共同完成计划,确定立即加入吗?");
                } else {
                    coolCommonPop_agree.ShowPop(PlanDetails2Activity.this, nestPlanDetails
                            , Gravity.CENTER, "提示", "加入后双方将共同制定里程碑并开启押金模式,相互监督共同完成计划,确定立即加入吗?");
                }

                coolCommonPop_agree.setPopClickListener(new CoolCommonPop.PopClickListener() {
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
                PlanExit(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
                break;
            case R.id.tv_pay:
                startActivity(new Intent(PlanDetails2Activity.this, PayMoneyActivity.class)
                        .putExtra("plan", mgetPlanInfoVO));
                break;
            case R.id.tv_submit_store:
                CoolCommonPop coolCommonPop_submit_store = new CoolCommonPop();
                if (mgetPlanInfoVO.getPlanMode() == 1) {
                    coolCommonPop_submit_store.ShowPop(PlanDetails2Activity.this, linearStoreWaiting
                            , Gravity.CENTER, "提示", "里程碑提交后不能更改，请确定是否立即提交 ？");
                } else {
                    coolCommonPop_submit_store.ShowPop(PlanDetails2Activity.this, linearStoreWaiting
                            , Gravity.CENTER, "提示", "里程碑对方确认后不能更改，请确定是否立即提交?");
                }

                coolCommonPop_submit_store.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        MilestonesSubmit(PlanDetails2Activity.this, id);

                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.tv_unagree_store:
                CoolCommonPop coolCommonPop_agree_store = new CoolCommonPop();
                coolCommonPop_agree_store.ShowPop(PlanDetails2Activity.this, linearStoreWaiting
                        , Gravity.CENTER, "提示", "里程碑同意后不能修改，是否同意？");
                coolCommonPop_agree_store.setPopClickListener(new CoolCommonPop.PopClickListener() {
                    @Override
                    public void onConfirm() {
                        GetMilestonesConfirm(PlanDetails2Activity.this, id);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                break;
            case R.id.tv_agree_store:
                initPopForRefuseStore();
                break;
        }
    }


    public void GetPlanInfo(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetPlanInfoResultVO> call = CoolHttpUrl.getApi().getService().GetPlanInfo(id);
            call.enqueue(new Callback<GetPlanInfoResultVO>() {
                @Override
                public void onResponse(Call<GetPlanInfoResultVO> call, Response<GetPlanInfoResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetPlanInfoResultVO data = response.body();
                        CoolLogTrace.e("GetPlanInfo", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetPlanInfoResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
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
                            EventBus.getDefault().post(data.getResult());
                            CoolGlideUtil.urlInto(PlanDetails2Activity.this, data.getResult().getAvatar(), imgHead);
                            tvName.setText(data.getResult().getNickName());
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
                PlanAccept(PlanDetails2Activity.this, body2, mgetPlanInfoVO.getId());
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
                PlanAccept(PlanDetails2Activity.this, body2, mgetPlanInfoVO.getId());
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(nestPlanDetails, Gravity.CENTER, 0, 0);
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
                                        Integer.valueOf(CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this, "uid").toString())) {
                                    for (int i = 0; i < mgetPlanInfoVO.getMembers().size(); i++) {
                                        if (mgetPlanInfoVO.getUserId() == mgetPlanInfoVO.getMembers().get(i).getUserId()) {
                                            CreaterBean.setNickName(data.getResult().get(i).getNickName());
                                            CreaterBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(PlanDetails2Activity.this,
                                                    CreaterBean.getAvatar(), imgHeadStart);
                                            tvStartName.setText(CreaterBean.getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (CreaterBean.getPayStatus() == 2) {
                                                    tvStartStatus.setText("待支付押金");
                                                } else {
                                                    tvStartStatus.setText("已支付押金");
                                                }
                                            }

                                        } else {
                                            MemberBean.setNickName(data.getResult().get(i).getNickName());
                                            MemberBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgHeadMe);
                                            tvMeName.setText(MemberBean.getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (MemberBean.getPayStatus() == 2) {
                                                    tvMeStatus.setText("待支付押金");
                                                } else {
                                                    tvMeStatus.setText("已支付押金");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    for (int i = 0; i < mgetPlanInfoVO.getMembers().size(); i++) {
                                        if (Integer.valueOf(CoolSPUtil.getDataFromLoacl(PlanDetails2Activity.this, "uid").toString())
                                                == data.getResult().get(i).getId()) {
                                            MemberBean.setNickName(data.getResult().get(i).getNickName());
                                            MemberBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(PlanDetails2Activity.this, MemberBean.getAvatar(), imgHeadMe);
                                            tvMeName.setText(MemberBean.getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (MemberBean.getPayStatus() == 2) {
                                                    tvMeStatus.setText("待支付押金");
                                                } else {
                                                    tvMeStatus.setText("已支付押金");
                                                }
                                            }
                                        } else {
                                            CreaterBean.setNickName(data.getResult().get(i).getNickName());
                                            CreaterBean.setAvatar(data.getResult().get(i).getAvatar());
                                            CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHeadStart);
                                            tvStartName.setText(CreaterBean.getNickName());
                                            if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                                if (CreaterBean.getPayStatus() == 2) {
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
                                CreaterBean.setNickName(data.getResult().get(0).getNickName());
                                CreaterBean.setAvatar(data.getResult().get(0).getAvatar());
                                CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHeadStart);
                                tvStartName.setText(CreaterBean.getNickName());
                                if (mgetPlanInfoVO.getDepositMode() == 1 && mgetPlanInfoVO.getStatus() == 4) {
                                    if (CreaterBean.getPayStatus() == 2) {
                                        tvStartStatus.setText("待支付押金");
                                    } else {
                                        tvStartStatus.setText("已支付押金");
                                    }
                                }
                            }
                            CoolGlideUtil.urlInto(PlanDetails2Activity.this, CreaterBean.getAvatar(), imgHead);
                            tvName.setText(CreaterBean.getNickName());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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
                            GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
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


    public void initPopForRefuseStore() {
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
                MilestonesRefuse(PlanDetails2Activity.this, id);
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(linearStoreWaiting, Gravity.CENTER, 0, 0);
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
                    if (swpStoreWaiting != null) {
                        swpStoreWaiting.setRefreshing(false);
                    }
                    if (response.isSuccessful()) {
                        GetMilestonesResultVO data = response.body();
                        CoolLogTrace.e("GetMilestones", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (data.getResult() != null && data.getResult().size() > 0) {
                                if (mgetPlanInfoVO.getStatus() > 4) {
                                    mDatas_unfinish = new ArrayList<>();
                                    mDatas_finish = new ArrayList<>();
                                    for (int i = 0; i < data.getResult().size(); i++) {
                                        if (mgetPlanInfoVO.getPlanMode() == 1) {
                                            if (data.getResult().get(i).getMy() != null &&
                                                    data.getResult().get(i).getMy().getStatus() == 1) {
                                                mDatas_finish.add(data.getResult().get(i));
                                            } else {
                                                mDatas_unfinish.add(data.getResult().get(i));
                                            }
                                        } else {
                                            if (data.getResult().get(i).getMy() != null &&
                                                    data.getResult().get(i).getOther() != null &&
                                                    data.getResult().get(i).getMy().getStatus() == 1 &&
                                                    data.getResult().get(i).getOther().getStatus() == 1) {
                                                mDatas_finish.add(data.getResult().get(i));
                                            } else {
                                                mDatas_unfinish.add(data.getResult().get(i));

                                            }
                                        }
                                    }
                                    adapter_finish.setmDatas(mDatas_finish);
                                    adapter_finish.notifyDataSetChanged();
                                    tvFinishNums.setText("已完成" + mDatas_finish.size() + "个");
                                    tvAllNumsTwo.setText("(共" + data.getResult().size() + "个)");
                                    if (mgetPlanInfoVO.getPlanMode() == 1) {
                                        tvStartNums.setText(mDatas_finish.size() + "");
                                    } else {
                                        if (planMeRole == 1) {

                                        }
                                    }

                                    adapter_unfinish.setmDatas(mDatas_unfinish);
                                    adapter_unfinish.notifyDataSetChanged();
                                    tvUnfinishNums.setText("未完成" + mDatas_unfinish.size() + "个");
                                    tvAllNums.setText("(共" + data.getResult().size() + "个)");

                                } else {
                                    mDatas_store_waiting = data.getResult();
                                    if (planMeRole == 1) {
                                        mDatas_store_waiting.add(new GetMilestonesResultVO.ResultBean());
                                    }
                                    adapter_store_waiting.setmDatas(mDatas_store_waiting);
                                    adapter_store_waiting.notifyDataSetChanged();
                                }

                            } else {
                                if (mgetPlanInfoVO.getStatus() > 4) {

                                } else {
                                    mDatas_store_waiting = new ArrayList<>();
                                    if (planMeRole == 1) {
                                        mDatas_store_waiting.add(new GetMilestonesResultVO.ResultBean());
                                    }
                                    adapter_store_waiting.setmDatas(mDatas_store_waiting);
                                    adapter_store_waiting.notifyDataSetChanged();
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
                                GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
                            } else {
                                //非押金模式，开始打卡
                                GetPlanInfo(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
                                //押金模式，需要支付押金
                                startActivity(new Intent(PlanDetails2Activity.this, PayMoneyActivity.class)
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
                            CoolPublicMethod.Toast(PlanDetails2Activity.this, "提交成功，请等待审核");
                            tvSubmitStore.setVisibility(View.GONE);

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


    private void MilestoneClock(final Context context, int id, MilestoneClockBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneClockVO> call = CoolHttpUrl.getApi().getService().MilestoneClock(id, body);
            call.enqueue(new Callback<MilestoneClockVO>() {
                @Override
                public void onResponse(Call<MilestoneClockVO> call, Response<MilestoneClockVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneClockVO data = response.body();
                        CoolLogTrace.e("MilestoneClock", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            GetMilestones(PlanDetails2Activity.this, mgetPlanInfoVO.getId());
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneClockVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void MilestoneRemind(final Context context, int id, MilestoneRemindBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneRemindVO> call = CoolHttpUrl.getApi().getService().MilestoneRemind(id, body);
            call.enqueue(new Callback<MilestoneRemindVO>() {
                @Override
                public void onResponse(Call<MilestoneRemindVO> call, Response<MilestoneRemindVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneRemindVO data = response.body();
                        CoolLogTrace.e("MilestoneRemind", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(PlanDetails2Activity.this, "提醒成功");
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneRemindVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void MilestoneAlarm(final Context context, int id, MilestoneAlarmBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneAlarmVO> call = CoolHttpUrl.getApi().getService().MilestoneAlarm(id, body);
            call.enqueue(new Callback<MilestoneAlarmVO>() {
                @Override
                public void onResponse(Call<MilestoneAlarmVO> call, Response<MilestoneAlarmVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneAlarmVO data = response.body();
                        CoolLogTrace.e("MilestoneAlarm", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneAlarmVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void MilestoneDelete(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneDeleteVO> call = CoolHttpUrl.getApi().getService().MilestoneDelete(id);
            call.enqueue(new Callback<MilestoneDeleteVO>() {
                @Override
                public void onResponse(Call<MilestoneDeleteVO> call, Response<MilestoneDeleteVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneDeleteVO data = response.body();
                        CoolLogTrace.e("MilestoneDelete", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneDeleteVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
