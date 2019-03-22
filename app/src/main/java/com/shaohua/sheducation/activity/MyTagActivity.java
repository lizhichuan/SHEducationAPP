package com.shaohua.sheducation.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.flowlayout.CoolFlowLayout;
import com.njcool.lzccommon.view.flowlayout.CoolTagFlowLayout;
import com.njcool.lzccommon.view.flowlayout.TagAdapter;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.request.LabelCustomBody;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.resultbean.GetSystemLabelsResultVO;
import com.shaohua.sheducation.resultbean.LabelCancelResultVO;
import com.shaohua.sheducation.resultbean.LabelCustomResultVO;
import com.shaohua.sheducation.resultbean.LabelSelectResultVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTagActivity extends SHBaseActivity {

    @Bind(R.id.tv_my_nums)
    TextView tvMyNums;
    @Bind(R.id.fl_selected)
    CoolTagFlowLayout flSelected;
    @Bind(R.id.fl_type)
    CoolTagFlowLayout flType;
    @Bind(R.id.fl_other)
    CoolTagFlowLayout flOther;
    @Bind(R.id.fl_modify)
    CoolTagFlowLayout flModify;
    @Bind(R.id.activity_my_tag)
    ConstraintLayout activityMyTag;

    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter_selected;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas_selected;
    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter_type;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas_type;
    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter_other;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas_other;
    private TagAdapter<GetSystemLabelsResultVO.ResultBean> adapter_modify;
    private List<GetSystemLabelsResultVO.ResultBean> mDatas_modify;


    private PopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_my_tag);
        ButterKnife.bind(this);
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }

    private void findViews() {
        setmTopTitle("我的标签");
        mDatas_selected = new ArrayList<>();
        mDatas_selected.add(null);
        mDatas_selected.add(null);
        mDatas_selected.add(null);

        adapter_selected = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas_selected) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView view = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flSelected, false);
                view.setText(mDatas_selected.get(position).getName());
                if (TextUtils.isEmpty(mDatas_selected.get(position).getName())) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
                return view;
            }
        };
        flSelected.setAdapter(adapter_selected);

        adapter_type = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas_type) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView view = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flType, false);
                view.setText(mDatas_type.get(position).getName());
                return view;
            }
        };
        flType.setAdapter(adapter_type);

        flType.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(0, mDatas_type.get(i));
                    adapter_selected.notifyDataChanged();
                }
                if (selectPosSet.size() == 0) {
                    mDatas_selected.set(0, null);
                    adapter_selected.notifyDataChanged();
                }

            }
        });

        adapter_other = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas_other) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {
                TextView view = (TextView) getLayoutInflater().inflate(R.layout.item_filter_tag,
                        flOther, false);
                view.setText(mDatas_other.get(position).getName());
                return view;
            }
        };
        flOther.setAdapter(adapter_other);
        flOther.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    mDatas_selected.set(1, mDatas_other.get(i));
                    adapter_selected.notifyDataChanged();
                    LabelSelect(MyTagActivity.this, mDatas_other.get(i).getId());
                }
                if (selectPosSet.size() == 0) {
                    LabelCancel(MyTagActivity.this, mDatas_other.get(1).getId());
                    mDatas_selected.set(1, null);
                    adapter_selected.notifyDataChanged();
                }
            }
        });

        adapter_modify = new TagAdapter<GetSystemLabelsResultVO.ResultBean>(mDatas_modify) {
            @Override
            public View getView(CoolFlowLayout parent, int position, GetSystemLabelsResultVO.ResultBean s) {

                View view = getLayoutInflater().inflate(R.layout.item_filter_modify,
                        flModify, false);
                TextView tv_tag = (TextView) view.findViewById(R.id.tv_tag);
                TextView tv_add = (TextView) view.findViewById(R.id.tv_add);
                if (position == 0) {
                    tv_tag.setVisibility(View.GONE);
                    tv_add.setVisibility(View.VISIBLE);
                } else {
                    tv_tag.setVisibility(View.VISIBLE);
                    tv_add.setVisibility(View.GONE);
                }
                tv_tag.setText(mDatas_modify.get(position).getName());
                tv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initGenderPop();
                    }
                });
                return view;
            }
        };
        flModify.setAdapter(adapter_modify);
        flModify.setOnSelectListener(new CoolTagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                for (Integer i : selectPosSet) {
                    if (i != 0) {
                        mDatas_selected.set(2, mDatas_modify.get(i));
                        adapter_selected.notifyDataChanged();
                    }
                }
                if (selectPosSet.size() == 0) {
                    mDatas_selected.set(2, null);
                    adapter_selected.notifyDataChanged();
                }
            }
        });

        GetSystemLabels(MyTagActivity.this);
        GetMyLabels(MyTagActivity.this);
    }



    public void initGenderPop() {
        View view = getLayoutInflater().inflate(R.layout.pop_add_tag, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        final EditText et_tag = (EditText) view.findViewById(R.id.et_tag);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();

            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_tag.getText().toString())) {
                    pop.dismiss();
                } else {
                    LabelCustomBody labelCustomRequest = new LabelCustomBody();
                    labelCustomRequest.setName(et_tag.getText().toString());
                    LabelCustom(MyTagActivity.this, labelCustomRequest);
//                    mDatas_modify.add(et_tag.getText().toString());
//                    adapter_modify.notifyDataChanged();
                    pop.dismiss();
                }
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(activityMyTag, Gravity.CENTER, 0, 0);
    }

    private List<GetSystemLabelsResultVO.ResultBean> mDatas;

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
                                mDatas_type = new ArrayList<>();
                                mDatas_other = new ArrayList<>();

                                for (int i = 0; i < mDatas.size(); i++) {
                                    if (mDatas.get(i).getType() == 0) {
                                        mDatas_type.add(mDatas.get(i));
                                        adapter_type.setmTagDatas(mDatas_type);
                                        adapter_type.notifyDataChanged();
                                    } else if (mDatas.get(i).getType() == 1) {
                                        mDatas_other.add(mDatas.get(i));
                                        adapter_other.setmTagDatas(mDatas_other);
                                        adapter_other.notifyDataChanged();
                                    } else if (mDatas.get(i).getType() == 2) {

                                    }
                                }
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

    /**
     * 获取系统标签
     *
     * @param context
     */
    public void GetMyLabels(final Context context) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<GetSystemLabelsResultVO> call = CoolHttpUrl.getApi().getService().GetMyLabels();
            call.enqueue(new Callback<GetSystemLabelsResultVO>() {
                @Override
                public void onResponse(Call<GetSystemLabelsResultVO> call, Response<GetSystemLabelsResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        GetSystemLabelsResultVO result = response.body();
                        CoolLogTrace.e("GetSystemLabels", "", GsonUtil.gson().toJson(result).toString());
                        if (result.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {
                            if (result.getResult() != null && result.getResult().size() > 0) {
                                mDatas_modify = result.getResult();
                                mDatas_modify.add(0, null);
                                adapter_modify.setmTagDatas(mDatas_modify);
                                adapter_modify.notifyDataChanged();
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


    private void LabelSelect(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<LabelSelectResultVO> call = CoolHttpUrl.getApi().getService().LabelSelect(id);
            call.enqueue(new Callback<LabelSelectResultVO>() {
                @Override
                public void onResponse(Call<LabelSelectResultVO> call, Response<LabelSelectResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        LabelSelectResultVO data = response.body();
                        CoolLogTrace.e("LabelSelect", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<LabelSelectResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void LabelCancel(final Context context, int id) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<LabelCancelResultVO> call = CoolHttpUrl.getApi().getService().LabelCancel(id);
            call.enqueue(new Callback<LabelCancelResultVO>() {
                @Override
                public void onResponse(Call<LabelCancelResultVO> call, Response<LabelCancelResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        LabelCancelResultVO data = response.body();
                        CoolLogTrace.e("LabelCancel", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<LabelCancelResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }

    private void LabelCustom(final Context context, LabelCustomBody body) {
        if (!CoolPublicMethod.isNetworkAvailable(context)) {
            CoolPublicMethod.Toast(context, AYConsts.NETWOEKERROR);
            return;
        } else {
            Call<LabelCustomResultVO> call = CoolHttpUrl.getApi().getService().LabelCustom(body);
            call.enqueue(new Callback<LabelCustomResultVO>() {
                @Override
                public void onResponse(Call<LabelCustomResultVO> call, Response<LabelCustomResultVO> response) {
                    CoolPublicMethod.hideProgressDialog();
                    if (response.isSuccessful()) {
                        LabelCustomResultVO data = response.body();
                        CoolLogTrace.e("LabelCustom", "", GsonUtil.gson().toJson(data).toString());
                        if (data.getStatus() == SHHttpUtil.RIGHT_SUCCESS) {

                        } else {
                            SHHttpUtil.resultCode(context, data.getStatus(), data.getMessage());
                        }
                    } else {
                        SHHttpUtil.resultCode(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<LabelCustomResultVO> call, Throwable t) {
                    CoolPublicMethod.hideProgressDialog();
                    CoolPublicMethod.Toast(context, ConstsUrl.Failed);
                }
            });
        }
    }
}
