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
import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.request.StudySubscribeBody;
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;
import com.shaohua.sheducation.resultbean.StudySubscribeResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeCourseActivity extends SHBaseActivity {

    @Bind(R.id.fl_type)
    CoolTagFlowLayout flType;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas;
    private List<Integer> labels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_subscribe_course);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("订资料");
        adapter = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flType, false);
                tv.setText(mDatas.get(position).getName());
                return tv;
            }
        };
        flType.setAdapter(adapter);
        flType.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                labels = new ArrayList<>();
                for (Integer i : selectPosSet) {
                    labels.add(i, mDatas.get(i).getId());
                }
            }
        });
        GetSystemLabels(SubscribeCourseActivity.this);
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            CoolPublicMethod.Toast(SubscribeCourseActivity.this, "请输入资料名称");
            return;
        }
        if (labels != null && labels.size() > 0) {
            StudySubscribeBody body = new StudySubscribeBody();
            body.setLabels(labels);
            body.setQ(etName.getText().toString());
            StudySubscribe(SubscribeCourseActivity.this, body);
        } else {
            CoolPublicMethod.Toast(SubscribeCourseActivity.this, "请选择资料类别");
            return;
        }

    }

    private void StudySubscribe(final Context context, StudySubscribeBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<StudySubscribeResultVO> call = CoolHttpUrl.getApi().getService().StudySubscribe(body);
            call.enqueue(new Callback<StudySubscribeResultVO>() {
                @Override
                public void onResponse(Call<StudySubscribeResultVO> call, Response<StudySubscribeResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        StudySubscribeResultVO data = response.body();
                        CoolLogTrace.e("StudySubscribe", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            //todo 订阅资料成功
                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<StudySubscribeResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

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
                                mDatas = result.getResult();
                                adapter.setmTagDatas(mDatas);
                                adapter.notifyDataChanged();
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

}
