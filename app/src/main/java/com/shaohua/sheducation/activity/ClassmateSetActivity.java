package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.FriendSettingBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.FriendSettingResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassmateSetActivity extends SHBaseActivity {

    @Bind(R.id.cb_agree)
    CheckBox cbAgree;
    @Bind(R.id.fl_type)
    CoolTagFlowLayout flType;
    @Bind(R.id.fl_time)
    CoolTagFlowLayout flTime;
    @Bind(R.id.fl_distance)
    CoolTagFlowLayout flDistance;

    private TagAdapter<String> adapter_type;
    private List<String> mDatas_type;
    private TagAdapter<String> adapter_time;
    private List<String> mDatas_time;
    private TagAdapter<String> adapter_distance;
    private List<String> mDatas_distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_classmate_set);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("同学设置");
        mDatas_type = new ArrayList<>();
        mDatas_type.add("英语");
        mDatas_type.add("计算机");
        mDatas_type.add("韩语");
        mDatas_type.add("日语");
        mDatas_type.add("考研");
        mDatas_type.add("设计");
        mDatas_type.add("音乐");

        mDatas_time = new ArrayList<>();
        mDatas_time.add("不限");
        mDatas_time.add("1月内");
        mDatas_time.add("3月内");
        mDatas_time.add("本年内");

        mDatas_distance = new ArrayList<>();
        mDatas_distance.add("不限");
        mDatas_distance.add("500m内");
        mDatas_distance.add("2km");
        mDatas_distance.add("5km");
        mDatas_distance.add("8km");


        adapter_type = new TagAdapter<String>(mDatas_type) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_tag_circle,
                        flType, false);
                tv.setText(mDatas_type.get(position));
                return tv;
            }
        };
        flType.setAdapter(adapter_type);

        flType.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {

                }


            }
        });

        adapter_time = new TagAdapter<String>(mDatas_time) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_tag_circle,
                        flTime, false);
                tv.setText(mDatas_time.get(position));
                return tv;
            }
        };
        flTime.setAdapter(adapter_time);
        flTime.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {

                }

            }
        });

        adapter_distance = new TagAdapter<String>(mDatas_distance) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_tag_circle,
                        flDistance, false);
                tv.setText(mDatas_distance.get(position));
                return tv;
            }
        };
        flDistance.setAdapter(adapter_distance);
        flDistance.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {

                }
                if (selectPosSet.size() == 0) {

                }
            }
        });
    }

    private void FriendSetting(final Context context, FriendSettingBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<FriendSettingResultVO> call = CoolHttpUrl.getApi().getService().FriendSetting(body);
            call.enqueue(new Callback<FriendSettingResultVO>() {
                @Override
                public void onResponse(Call<FriendSettingResultVO> call, Response<FriendSettingResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        FriendSettingResultVO data = response.body();
                        CoolLogTrace.e("FriendSetting", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<FriendSettingResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
