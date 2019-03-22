package com.shaohua.sheducation.common;

import android.content.Context;

import com.njcool.lzccommon.network.common.GsonUtil;
import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.request.MilestoneClockBody;
import com.shaohua.sheducation.resultbean.MilestoneClockVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chuan on 2017/11/24.
 */

public class Public {


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
}
