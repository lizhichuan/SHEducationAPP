package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.njcool.lzccommon.common.CoolPhotoPop;
import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.utils.PickUtil;
import com.njcool.lzccommon.view.photo.model.InvokeParam;
import com.njcool.lzccommon.view.photo.model.TContextWrap;
import com.njcool.lzccommon.view.photo.model.TResult;
import com.njcool.lzccommon.view.photo.permission.InvokeListener;
import com.njcool.lzccommon.view.photo.permission.PermissionManager;
import com.njcool.lzccommon.view.photo.permission.TakePhotoInvocationHandler;
import com.njcool.lzccommon.view.photo.ui.CoolPhotoOptions;
import com.njcool.lzccommon.view.photo.ui.TakePhoto;
import com.njcool.lzccommon.view.photo.ui.TakePhotoImpl;
import com.njcool.lzccommon.view.pickview.TimePickerView;
import com.njcool.lzccommon.view.round.CoolRoundAngleImageView;
import com.njcool.lzccommon.vo.TypeBean;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.PlanCreateBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;
import com.shaohua.sheducation.resultbean.PlanCreateResultVO;
import com.shaohua.sheducation.resultbean.FailVO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlanActivity extends SHBaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.img_pic)
    CoolRoundAngleImageView imgPic;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.tv_destination)
    EditText tvDestination;
    @Bind(R.id.tv_check_time)
    TextView tvCheckTime;
    @Bind(R.id.et_detail)
    EditText etDetail;
    @Bind(R.id.rdbt_single)
    RadioButton rdbtSingle;
    @Bind(R.id.rdbt_mult)
    RadioButton rdbtMult;
    @Bind(R.id.rdbt_support)
    RadioButton rdbtSupport;
    @Bind(R.id.rdbt_unsupport)
    RadioButton rdbtUnsupport;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.cb_agree)
    CheckBox cbAgree;
    @Bind(R.id.tv_rights)
    TextView tvRights;
    @Bind(R.id.activity_create_plan)
    ConstraintLayout activityCreatePlan;

    private static final String TAG = PersonalInfoActivity.class.getName();
    @Bind(R.id.et_type)
    EditText etType;
    @Bind(R.id.linear_type_modify)
    LinearLayout linearTypeModify;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private PlanCreateBody planCreateRequest = new PlanCreateBody();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_create_plan);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("创建计划");
    }

    @Override
    public void onEventMainThread(FailVO failVO) {
        super.onEventMainThread(failVO);
    }


    @OnClick({R.id.img_pic, R.id.tv_type, R.id.tv_check_time, R.id.tv_confirm, R.id.tv_rights})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_pic:
                CoolPhotoPop coolPhotoPop = new CoolPhotoPop();
                CoolPhotoOptions coolPhotoOptions = new CoolPhotoOptions();
                coolPhotoOptions.setCropTool(false);
                coolPhotoPop.ShowPop(CreatePlanActivity.this, activityCreatePlan, Gravity.BOTTOM,
                        getTakePhoto(), coolPhotoOptions);
                break;
            case R.id.tv_type:
                GetSystemLabels(CreatePlanActivity.this);
                break;
            case R.id.tv_check_time:
                PickUtil.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new PickUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvCheckTime.setText(date);
                    }
                });
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(tvType.getText().toString())) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "计划类型未选择");
                    return;
                }
                if (tvType.getText().toString().equalsIgnoreCase("其他")) {
                    if (TextUtils.isEmpty(etType.getText().toString())) {
                        CoolPublicMethod.Toast(CreatePlanActivity.this, "计划类型未输入");
                        return;
                    }

                }
                if (TextUtils.isEmpty(etTitle.getText().toString())) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "计划标题未输入");
                    return;
                }
                if (TextUtils.isEmpty(tvDestination.getText().toString())) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "计划目标未选择");
                    return;
                }
                if (TextUtils.isEmpty(tvCheckTime.getText().toString())) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "考试时间未选择");
                    return;
                }
                if (TextUtils.isEmpty(etDetail.getText().toString())) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "计划描述未输入");
                    return;
                }
                if (TextUtils.isEmpty(url)) {
                    CoolPublicMethod.Toast(CreatePlanActivity.this, "封面图片未上传");
                    return;
                }


                planCreateRequest.setName(etTitle.getText().toString());
                planCreateRequest.setCustomLabel(etType.getText().toString());
                planCreateRequest.setPlanMode(rdbtSingle.isChecked() ? 1 : 2);
                planCreateRequest.setDepositMode(rdbtSupport.isChecked() ? 1 : 0);
                planCreateRequest.setTestDest(tvDestination.getText().toString());
                planCreateRequest.setTestTime(tvCheckTime.getText().toString());
                planCreateRequest.setDescription(etDetail.getText().toString());
                planCreateRequest.setImage(url);
                PlanCreate(CreatePlanActivity.this, planCreateRequest);
                break;
            case R.id.tv_rights:
                break;
        }
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
        new Thread() {
            @Override
            public void run() {
                super.run();
                update(result.getImage().getCompressPath());
                //上传图片
            }
        }.start();
//        CoolGlideUtil.FileInto(CreatePlanActivity.this, result.getImage().getCompressPath(), imgPic);

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
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(5);
        conf.setMaxErrorRetry(2);
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        new Thread(new Runnable() {
            @Override
            public void run() {
                resumableUpload(uploadFilePath);
            }
        }).start();
    }

    /**
     * 异步断点上传，不设置记录保存路径，只在本次上传内做断点续传
     */

    public void resumableUpload(String uploadFilePath) {
        url = "";
        uploadObject = "plan_" + CoolSPUtil.getDataFromLoacl(CreatePlanActivity.this, "uid").toString()
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
                CoolPublicMethod.Toast(CreatePlanActivity.this, "图片上传失败，请重新选择上传");
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
                    CoolGlideUtil.urlInto(CreatePlanActivity.this, url, imgPic);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    /**
     * 获取系统标签
     *
     * @param context
     */
    public void GetSystemLabels(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetSystemLabelsResultVO> call = CoolHttpUrl.getApi().getService().GetSystemLabels();
            call.enqueue(new Callback<GetSystemLabelsResultVO>() {
                @Override
                public void onResponse(Call<GetSystemLabelsResultVO> call, Response<GetSystemLabelsResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetSystemLabelsResultVO result = response.body();
                        CoolLogTrace.e("GetSystemLabels", "", GsonUtil.gson().toJson(result).toString());
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (result.getResult() != null && result.getResult().size() > 0) {
                                final ArrayList<TypeBean> mList2 = new ArrayList<TypeBean>();
                                // 单项选择
                                for (int i = 0; i < result.getResult().size(); i++) {
                                    mList2.add(new TypeBean(result.getResult().get(i).getId(),
                                            result.getResult().get(i).getName(), "" + result.getResult().get(i).getType()));
                                }
                                PickUtil.alertBottomWheelOption(CreatePlanActivity.this, mList2, new PickUtil.OnWheelViewClick() {
                                    @Override
                                    public void onClick(View view, int postion) {
                                        tvType.setText(mList2.get(postion).getName());
                                        if (mList2.get(postion).getName().equalsIgnoreCase("其他")) {
                                            planCreateRequest.setLabelId(-1);
                                            linearTypeModify.setVisibility(View.VISIBLE);
                                        } else {
                                            planCreateRequest.setLabelId(mList2.get(postion).getId());
                                            linearTypeModify.setVisibility(View.GONE);
                                            etType.setText("");

                                        }

                                    }
                                });
                            }
                        } else {
                            SHHttpUtil.resultCode(context, response.code(), response.message());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetSystemLabelsResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }


    private void PlanCreate(final Context context, PlanCreateBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<PlanCreateResultVO> call = CoolHttpUrl.getApi().getService().PlanCreate(body);
            call.enqueue(new Callback<PlanCreateResultVO>() {
                @Override
                public void onResponse(Call<PlanCreateResultVO> call, Response<PlanCreateResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        PlanCreateResultVO data = response.body();
                        CoolLogTrace.e("PlanCreate", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            CoolPublicMethod.Toast(CreatePlanActivity.this, "创建成功");
                            Intent intent = new Intent();

                            if (rdbtSingle.isChecked()) {
                                intent.setClass(CreatePlanActivity.this,
                                        PlanDetails2Activity.class);
                            } else {
                                intent.setClass(CreatePlanActivity.this,
                                        RecommendPairPersonActivity.class);
                            }
                            intent.putExtra("id", data.getResult().getId());
                            startActivity(intent);
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<PlanCreateResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
