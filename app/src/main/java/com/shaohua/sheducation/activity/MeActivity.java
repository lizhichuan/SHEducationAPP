package com.shaohua.sheducation.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.njcool.lzccommon.view.round.CoolCircleImageView;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.common.AYConsts;
import com.shaohua.sheducation.common.ConstsUrl;
import com.shaohua.sheducation.common.CoolHttpUrl;
import com.shaohua.sheducation.common.SHHttpUtil;
import com.shaohua.sheducation.common.UserPref;
import com.shaohua.sheducation.resultbean.GetMyInfoResultVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeActivity extends SHBaseActivity {

    @Bind(R.id.img_head)
    CoolCircleImageView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_id)
    TextView tvId;
    @Bind(R.id.linear_qrcode)
    LinearLayout linearQrcode;
    @Bind(R.id.img_message)
    ImageView imgMessage;
    @Bind(R.id.tv_nums)
    TextView tvNums;
    @Bind(R.id.linear_message)
    LinearLayout linearMessage;
    @Bind(R.id.linear_wallat)
    LinearLayout linearWallat;
    @Bind(R.id.linear_rewards)
    LinearLayout linearRewards;
    @Bind(R.id.linear_personal)
    LinearLayout linearPersonal;
    @Bind(R.id.linear_my_tag)
    LinearLayout linearMyTag;
    @Bind(R.id.linear_invite)
    LinearLayout linearInvite;
    @Bind(R.id.linear_help)
    LinearLayout linearHelp;
    @Bind(R.id.linear_set)
    LinearLayout linearSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);
        findViews();
    }

    private void findViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetMyInfo(MeActivity.this);
    }


    @OnClick({R.id.img_head, R.id.tv_name, R.id.linear_qrcode, R.id.linear_message, R.id.linear_wallat, R.id.linear_rewards, R.id.linear_personal, R.id.linear_my_tag, R.id.linear_invite, R.id.linear_help, R.id.linear_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                startActivity(PersonalInfoActivity.class);
                break;
            case R.id.tv_name:
                break;
            case R.id.linear_qrcode:
                startActivity(CaptureActivity.class);
                break;
            case R.id.linear_message:
                startActivity(MessageActivity.class);
                break;
            case R.id.linear_wallat:
                startActivity(MyWallatActivity.class);
                break;
            case R.id.linear_rewards:
                startActivity(MyMedalActivity.class);
                break;
            case R.id.linear_personal:
                startActivity(PersonalInfoActivity.class);
                break;
            case R.id.linear_my_tag:
                startActivity(MyTagActivity.class);
                break;
            case R.id.linear_invite:
                break;
            case R.id.linear_help:
                break;
            case R.id.linear_set:
                startActivity(SettingActivity.class);
                break;
        }
    }

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
                            UserPref.saveUserInfo(data.getResult());
                            CoolSPUtil.insertDataToLoacl(MeActivity.this, "uid", data.getResult().getId() + "");
                            CoolGlideUtil.urlInto(MeActivity.this, data.getResult().getAvatar(), imgHead);
                            tvId.setText("ID:" + data.getResult().getId() + "");
                            tvName.setText(data.getResult().getNickName());
                            if (data.getResult().getMessageNum() > 0) {
                                tvNums.setText(data.getResult().getMessageNum() + "");
                                tvNums.setVisibility(View.VISIBLE);
                            } else {
                                tvNums.setVisibility(View.INVISIBLE);
                            }
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
}
