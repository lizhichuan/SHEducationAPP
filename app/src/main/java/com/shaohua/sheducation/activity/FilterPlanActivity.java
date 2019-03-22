package com.shaohua.sheducation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;
import com.shaohua.sheducation.resultbean.FilterPlanBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterPlanActivity extends SHBaseActivity {

    @Bind(R.id.fl_selected)
    CoolTagFlowLayout flSelected;
    @Bind(R.id.fl_type)
    CoolTagFlowLayout flType;
    @Bind(R.id.fl_mode)
    CoolTagFlowLayout flMode;
    @Bind(R.id.fl_money)
    CoolTagFlowLayout flMoney;
    @Bind(R.id.fl_status)
    CoolTagFlowLayout flStatus;

    private TagAdapter<String> adapter_selected;
    private List<String> mDatas_selected;
    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter_type;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas_type;
    private TagAdapter<String> adapter_mode;
    private List<String> mDatas_mode;
    private TagAdapter<String> adapter_money;
    private List<String> mDatas_money;
    private TagAdapter<String> adapter_status;
    private List<String> mDatas_status;

    private FilterPlanBean filterPlanBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_filter_plan);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    @Override
    protected void onRightClick(View v) {
        super.onRightClick(v);
        Intent intent = new Intent();
        intent.putExtra("filter", filterPlanBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void findViews() {
        setmTopTitle("筛选");
        setTvRight("完成");
        setmTvRightVisible(1);
        filterPlanBean = new FilterPlanBean();
        mDatas_selected = new ArrayList<>();
        mDatas_selected.add("");
        mDatas_selected.add("");
        mDatas_selected.add("");
        mDatas_selected.add("");


        mDatas_mode = new ArrayList<>();
        mDatas_mode.add("不限");
        mDatas_mode.add("单人");
        mDatas_mode.add("多人");

        mDatas_money = new ArrayList<>();
        mDatas_money.add("不限");
        mDatas_money.add("支持");
        mDatas_money.add("不支持");

        mDatas_status = new ArrayList<>();
        mDatas_status.add("不限");
        mDatas_status.add("已匹配");
        mDatas_status.add("待匹配");

        adapter_selected = new TagAdapter<String>(mDatas_selected) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag_selected,
                        flSelected, false);
                tv.setTextColor(getResources().getColor(R.color.common_color));
                tv.setText(mDatas_selected.get(position));
                if (TextUtils.isEmpty(mDatas_selected.get(position))) {
                    tv.setVisibility(View.GONE);
                } else {
                    tv.setVisibility(View.VISIBLE);
                }
                return tv;
            }
        };
        flSelected.setAdapter(adapter_selected);

        adapter_type = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas_type) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flType, false);
                tv.setText(mDatas_type.get(position).getName());
                return tv;
            }
        };
        flType.setAdapter(adapter_type);

        flType.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(0, mDatas_type.get(i).getName());
                    adapter_selected.notifyDataChanged();
                    filterPlanBean.setGetSystemLabelsVO(mDatas_type.get(i));
                }
                if (selectPosSet.size() == 0) {
                    mDatas_selected.set(0, "");
                    filterPlanBean.setGetSystemLabelsVO(null);
                    adapter_selected.notifyDataChanged();
                }

            }
        });

        adapter_mode = new TagAdapter<String>(mDatas_mode) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flMode, false);
                tv.setText(mDatas_mode.get(position));
                return tv;
            }
        };
        flMode.setAdapter(adapter_mode);
        flMode.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(1, mDatas_mode.get(i));
                    adapter_selected.notifyDataChanged();
                    if (i == 0) {
                        filterPlanBean.setPlanMode(0);
                    } else if (i == 1) {
                        filterPlanBean.setPlanMode(1);
                    } else {
                        filterPlanBean.setPlanMode(2);
                    }
                }
                if (selectPosSet.size() == 0) {
                    mDatas_selected.set(1, "");
                    filterPlanBean.setPlanMode(-1);
                    adapter_selected.notifyDataChanged();
                }
            }
        });

        adapter_money = new TagAdapter<String>(mDatas_money) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flMoney, false);
                tv.setText(mDatas_money.get(position));
                return tv;
            }
        };
        flMoney.setAdapter(adapter_money);
        flMoney.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(2, mDatas_money.get(i));
                    adapter_selected.notifyDataChanged();
                    if (i == 0) {
                        filterPlanBean.setDepositMode(-1);
                    } else if (i == 1) {
                        filterPlanBean.setDepositMode(0);
                    } else {
                        filterPlanBean.setDepositMode(1);
                    }
                }
                if (selectPosSet.size() == 0) {
                    filterPlanBean.setDepositMode(-1);
                    mDatas_selected.set(2, "");
                    adapter_selected.notifyDataChanged();
                }
            }
        });

        adapter_status = new TagAdapter<String>(mDatas_status) {
            @Override
            public View getView(CoolFlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flStatus, false);
                tv.setText(mDatas_status.get(position));
                return tv;
            }
        };
        flStatus.setAdapter(adapter_status);
        flStatus.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(3, mDatas_status.get(i));
                    adapter_selected.notifyDataChanged();
                    if (i == 0) {
                        filterPlanBean.setStatus(-1);
                    } else if (i == 1) {
                        filterPlanBean.setDepositMode(2);
                    } else {
                        filterPlanBean.setDepositMode(1);
                    }
                }
                if (selectPosSet.size() == 0) {
                    filterPlanBean.setStatus(-1);
                    mDatas_selected.set(3, "");
                    adapter_selected.notifyDataChanged();
                }
            }
        });
        GetSystemLabels(FilterPlanActivity.this);
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
                                mDatas_type = result.getResult();
                                adapter_type.setmTagDatas(mDatas_type);
                                adapter_type.notifyDataChanged();
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
