package com.shaohua.sheducation.common;

import android.content.Context;
import android.content.Intent;

import com.njcool.lzccommon.common.PersistentCookieStore;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.shaohua.sheducation.activity.LoginActivity;
import com.shaohua.sheducation.resultbean.FailVO;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by chuan on 2017/11/15.
 */

public class SHHttpUtil {
    public static final int RIGHT_SUCCESS = 0;


    /**/
    //返回码处理
    public static void resultCode(Context mContext, int code, String result) {
        if (code == (RIGHT_SUCCESS)) {
            CoolPublicMethod.Toast(mContext, "返回数据有误");
        } else {
            if (101 == code) {
                PersistentCookieStore persistentCookieStore = new PersistentCookieStore(mContext);
                persistentCookieStore.removeAll();
                CoolPublicMethod.Toast(mContext, "token过期，请重新登录");
                CoolSPUtil.clearDataFromLoacl(mContext);
                CoolSPUtil.insertDataToLoacl(mContext, "firstin", "true");
                mContext.startActivity(new Intent(mContext, LoginActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            EventBus.getDefault().post(new FailVO(true));
            CoolPublicMethod.Toast(mContext, code + "-" + result);
        }
    }
}
