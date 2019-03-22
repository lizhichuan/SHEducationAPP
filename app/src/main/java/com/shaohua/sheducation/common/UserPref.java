package com.shaohua.sheducation.common;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.CoolSPUtil;
import com.shaohua.sheducation.activity.LoginActivity;
import com.shaohua.sheducation.app.SHApplication;
import com.shaohua.sheducation.resultbean.UserVO;

/**
 * Created by chuan on 2017/11/16.
 */

public class UserPref {
    public static UserVO readUserInfo() {
        String json = CoolSPUtil.getDataFromLoacl(SHApplication.getInstance(), "user");
        if (TextUtils.isEmpty(json)) {
            return new UserVO();
        }
        try {
            return new Gson().fromJson(json, UserVO.class);
        } catch (JsonSyntaxException exception) {
            CoolPublicMethod.Toast(SHApplication.getInstance(), "请重新进入登录");
            CoolSPUtil.clearDataFromLoacl(SHApplication.getInstance());
            CoolSPUtil.insertDataToLoacl(SHApplication.getInstance(), "firstin", "true");
            SHApplication.getInstance().startActivity(new Intent(SHApplication.getInstance(), LoginActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            return null;
        }

    }


    public static void saveUserInfo(UserVO user) {
        String json = new Gson().toJson(user);
        CoolSPUtil.insertDataToLoacl(SHApplication.getInstance(), "user", json);
    }


    public static void removeUserInfoPref() {
        CoolSPUtil.insertDataToLoacl(SHApplication.getInstance(), "user", "");
    }


}
