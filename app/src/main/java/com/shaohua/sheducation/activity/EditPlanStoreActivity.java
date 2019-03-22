package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.PickUtil;
import com.njcool.lzccommon.view.pickview.TimePickerView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.MilestoneAddBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetMilestonesResultVO;
import com.shaohua.sheducation.resultbean.MilestoneAddResultVO;
import com.shaohua.sheducation.resultbean.MilestoneEditResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPlanStoreActivity extends SHBaseActivity {

    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.et_content)
    EditText etContent;

    private int id = 0;
    private GetMilestonesResultVO.ResultBean store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_edit_plan_store);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        store = (GetMilestonesResultVO.ResultBean) getIntent().getSerializableExtra("store");
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("编辑里程碑");
        if (id == 0) {
            finish();
        }
        if (store != null) {
            etContent.setText(store.getName());
            tvTime.setText(CoolPublicMethod.timeStamp2DateYMD(store.getFinishTime()));
        }

    }

    @OnClick({R.id.tv_time, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                PickUtil.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY,
                        "yyyy-MM-dd", new PickUtil.TimerPickerCallBack() {
                            @Override
                            public void onTimeSelect(String date) {
                                tvTime.setText(date);
                            }
                        });
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    CoolPublicMethod.Toast(EditPlanStoreActivity.this, "请输入里程碑内容");
                    return;
                }
                if (TextUtils.isEmpty(tvTime.getText().toString())) {
                    CoolPublicMethod.Toast(EditPlanStoreActivity.this, "请选择时间");
                    return;
                }
                MilestoneAddBody milestoneRequest = new MilestoneAddBody();
                milestoneRequest.setPlanId(id);
                milestoneRequest.setFinishTime(tvTime.getText().toString());
                milestoneRequest.setName(etContent.getText().toString());
                if (store != null) {
                    MilestoneEdit(EditPlanStoreActivity.this, milestoneRequest);
                } else {
                    MilestoneAdd(EditPlanStoreActivity.this, milestoneRequest);

                }
                break;
        }
    }

    private void MilestoneAdd(final Context context, MilestoneAddBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneAddResultVO> call = CoolHttpUrl.getApi().getService().MilestoneAdd(body);
            call.enqueue(new Callback<MilestoneAddResultVO>() {
                @Override
                public void onResponse(Call<MilestoneAddResultVO> call, Response<MilestoneAddResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneAddResultVO data = response.body();
                        CoolLogTrace.e("MilestoneAdd", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneAddResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void MilestoneEdit(final Context context, MilestoneAddBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<MilestoneEditResultVO> call = CoolHttpUrl.getApi().getService().MilestoneEdit(body);
            call.enqueue(new Callback<MilestoneEditResultVO>() {
                @Override
                public void onResponse(Call<MilestoneEditResultVO> call, Response<MilestoneEditResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        MilestoneEditResultVO data = response.body();
                        CoolLogTrace.e("MilestoneEdit", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<MilestoneEditResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }



}
