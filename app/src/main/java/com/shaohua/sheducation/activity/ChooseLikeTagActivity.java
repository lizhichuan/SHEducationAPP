package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLikeTagActivity extends SHBaseActivity {

    @Bind(R.id.fl_type)
    CoolTagFlowLayout flType;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_choose_like_tag);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {

        adapter = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_choose_like_tag,
                        flType, false);
                tv.setText(mDatas.get(position).getName());
                return tv;
            }
        };
        flType.setAdapter(adapter);
        adapter.setSelectedList();
        flType.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                StringBuffer sb = new StringBuffer();
                for (Integer i : selectPosSet) {
                    if (i == 0) {
                        sb.append(mDatas.get(i).getId());
                    } else {
                        sb.append(",").append(mDatas.get(i).getId());
                    }
                }
                CoolSPUtil.insertDataToLoacl(ChooseLikeTagActivity.this, "labels",
                        sb.toString());
                tvConfirm.setText("选好了(" + selectPosSet.size() + "/5)");

            }
        });
        GetSystemLabels(ChooseLikeTagActivity.this);
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


    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        startActivity(MainActivity.class);
        finish();
    }

}

