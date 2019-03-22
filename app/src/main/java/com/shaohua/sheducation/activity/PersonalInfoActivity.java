package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.njcool.lzccommon.vo.TypeBean;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.EditMyInfoBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.common.UserPref;
import com.shaohua.sheducation.resultbean.EditMyInfoResultVO;
import com.shaohua.sheducation.resultbean.GetMyInfoResultVO;
import com.shaohua.sheducation.resultbean.UserVO;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalInfoActivity extends SHBaseActivity implements TakePhoto.TakeResultListener, InvokeListener {

    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_degree)
    TextView tvDegree;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_rights)
    TextView tvRights;
    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    private final int NICKNAME = 0;
    @Bind(R.id.activity_personal)
    LinearLayout activityPersonal;


    private static final String TAG = PersonalInfoActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private UserVO userVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_personal_info);
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
        setmTopTitle("个人资料");
        GetMyInfo(PersonalInfoActivity.this);
    }


    public void setData(UserVO getMyInfoVO) {
        UserPref.saveUserInfo(getMyInfoVO);
        userVO = getMyInfoVO;
        CoolSPUtil.insertDataToLoacl(PersonalInfoActivity.this, "uid", getMyInfoVO.getId() + "");
        CoolGlideUtil.urlInto(PersonalInfoActivity.this, getMyInfoVO.getAvatar(), imgHead);
        tvNickname.setText(getMyInfoVO.getNickName());
        if (getMyInfoVO.getAge() == 1) {
            tvAge.setText("18以下");
        } else if (getMyInfoVO.getAge() == 2) {
            tvAge.setText("18～25岁");
        } else if (getMyInfoVO.getAge() == 3) {
            tvAge.setText("25～35岁");
        } else if (getMyInfoVO.getAge() == 4) {
            tvAge.setText("35岁以上 ");
        }
        if (getMyInfoVO.getEducation() == 1) {
            tvDegree.setText("高中");
        } else if (getMyInfoVO.getEducation() == 2) {
            tvDegree.setText("专科");
        } else if (getMyInfoVO.getEducation() == 3) {
            tvDegree.setText("本科");
        } else if (getMyInfoVO.getEducation() == 4) {
            tvDegree.setText("硕士");
        } else if (getMyInfoVO.getEducation() == 5) {
            tvDegree.setText("博士");
        } else if (getMyInfoVO.getEducation() == 6) {
            tvDegree.setText("其他");
        }

        if (getMyInfoVO.getPrivacyOptions() == 0) {
            tvRights.setText("全部可见");
        } else if (getMyInfoVO.getPrivacyOptions() == 1) {
            tvRights.setText("好友可见");
        } else if (getMyInfoVO.getPrivacyOptions() == 2) {
            tvRights.setText("自己可见");
        }

        if (getMyInfoVO.getGender() == 1) {
            tvSex.setText("女");
        } else {
            tvSex.setText("男");
        }

    }


    @OnClick({R.id.tv_nickname, R.id.tv_sex, R.id.tv_degree, R.id.tv_age, R.id.tv_rights, R.id.img_head, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nickname:
                startActivityForResult(new Intent(PersonalInfoActivity.this, InputTextActivity.class), NICKNAME);
                break;
            case R.id.tv_sex:
                final ArrayList<TypeBean> mList = new ArrayList<TypeBean>();
                // 单项选择
                mList.add(new TypeBean(0, "女", "1"));
                mList.add(new TypeBean(1, "男", "2"));
                PickUtil.alertBottomWheelOption(PersonalInfoActivity.this, mList, new PickUtil.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvSex.setText(mList.get(postion).getName());
                        userVO.setGender(Integer.valueOf(mList.get(postion).getType()));
                    }
                });
                break;
            case R.id.tv_degree:
                final ArrayList<TypeBean> mList3 = new ArrayList<TypeBean>();
                // 单项选择
                mList3.add(new TypeBean(0, "高中", "1"));
                mList3.add(new TypeBean(1, "专科", "2"));
                mList3.add(new TypeBean(2, "本科", "3"));
                mList3.add(new TypeBean(3, "硕士", "4"));
                mList3.add(new TypeBean(4, "博士", "5"));
                mList3.add(new TypeBean(5, "其他", "6"));
                PickUtil.alertBottomWheelOption(PersonalInfoActivity.this, mList3, new PickUtil.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvDegree.setText(mList3.get(postion).getName());
                        userVO.setEducation(Integer.valueOf(mList3.get(postion).getType()));
                    }
                });
                break;
            case R.id.tv_age:
                final ArrayList<TypeBean> mList2 = new ArrayList<TypeBean>();
                // 单项选择
                mList2.add(new TypeBean(0, "18以下", "1"));
                mList2.add(new TypeBean(1, "18～25岁", "2"));
                mList2.add(new TypeBean(2, "25～35岁", "3"));
                mList2.add(new TypeBean(3, "35岁以上", "4"));
                PickUtil.alertBottomWheelOption(PersonalInfoActivity.this, mList2, new PickUtil.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvAge.setText(mList2.get(postion).getName());
                        userVO.setAge(Integer.valueOf(mList2.get(postion).getType()));
                    }
                });
                break;
            case R.id.tv_rights:
                final ArrayList<TypeBean> mList4 = new ArrayList<TypeBean>();
                // 单项选择
                mList4.add(new TypeBean(0, "全部可见", "0"));
                mList4.add(new TypeBean(1, "好友可见", "1"));
                mList4.add(new TypeBean(2, "仅自己可见", "2"));
                PickUtil.alertBottomWheelOption(PersonalInfoActivity.this, mList4, new PickUtil.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvRights.setText(mList4.get(postion).getName());
                        userVO.setPrivacyOptions(Integer.valueOf(mList4.get(postion).getType()));
                    }
                });
                break;
            case R.id.img_head:
                CoolPhotoPop coolPhotoPop = new CoolPhotoPop();
                CoolPhotoOptions coolPhotoOptions = new CoolPhotoOptions();
                coolPhotoOptions.setCropTool(false);
                coolPhotoPop.ShowPop(PersonalInfoActivity.this, activityPersonal, Gravity.BOTTOM,
                        getTakePhoto(), coolPhotoOptions);
                break;
            case R.id.tv_confirm:
                EditMyInfoBody editMyInfoRequest = new EditMyInfoBody();
                editMyInfoRequest.setAge(userVO.getAge());
                editMyInfoRequest.setAvatar(userVO.getAvatar());
                editMyInfoRequest.setEducation(userVO.getEducation());
                editMyInfoRequest.setGender(userVO.getGender());
                editMyInfoRequest.setNickName(userVO.getNickName());
                editMyInfoRequest.setPrivacyOptions(userVO.getPrivacyOptions());
                EditMyInfo(PersonalInfoActivity.this, editMyInfoRequest);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case NICKNAME:
                    tvNickname.setText(data.getStringExtra("content"));
                    userVO.setNickName(data.getStringExtra("content"));
                    break;
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
        uploadObject = "head_" + CoolSPUtil.getDataFromLoacl(PersonalInfoActivity.this, "uid").toString()
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
                CoolPublicMethod.Toast(PersonalInfoActivity.this, "图片上传失败，请重新选择上传");
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
                    CoolGlideUtil.urlInto(PersonalInfoActivity.this, url, imgHead);
                    userVO.setAvatar(url);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };


    private void GetMyInfo(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetMyInfoResultVO> call = CoolHttpUrl.getApi().getService().GetMyInfo();
            call.enqueue(new Callback<GetMyInfoResultVO>() {
                @Override
                public void onResponse(Call<GetMyInfoResultVO> call, Response<GetMyInfoResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetMyInfoResultVO data = response.body();
                        CoolLogTrace.e("GetMyInfo", "", GsonUtil.gson().toJson(data).toString());
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
                public void onFailure(Call<GetMyInfoResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void EditMyInfo(final Context context, EditMyInfoBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<EditMyInfoResultVO> call = CoolHttpUrl.getApi().getService().EditMyInfo(body);
            call.enqueue(new Callback<EditMyInfoResultVO>() {
                @Override
                public void onResponse(Call<EditMyInfoResultVO> call, Response<EditMyInfoResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        EditMyInfoResultVO data = response.body();
                        CoolLogTrace.e("EditMyInfo", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            finish();
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<EditMyInfoResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
