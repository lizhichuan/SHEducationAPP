package com.shaohua.sheducation.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import com.njcool.lzccommon.utils.CoolLogTrace;
import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.utils.HttpUtil;
import com.shaohua.sheducation.activity.SHBaseActivity;
import com.shaohua.sheducation.common.AYConsts;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.tencent.mm.opensdk.constants.ConstantsAPI.COMMAND_SENDAUTH;
import static com.tencent.mm.opensdk.constants.ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX;

/**
 * Created by chuan on 2017/7/26.
 */

public class WXEntryActivity extends SHBaseActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        api = WXAPIFactory.createWXAPI(this, AYConsts.WX_KEY, false);
        api.handleIntent(getIntent(), WXEntryActivity.this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, WXEntryActivity.this);
    }

    // 微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq arg0) {
        // Intent intent = new Intent(WXEntryActivity.this,
        // MonitorDetailsActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivity(intent);
        // finish();
    }

    // 发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        Log.d("wxpay", "onPayFinish,errCode=" + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            EventBus.getDefault().post(new WXPayCodeVO(resp.errCode));
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
//                    CoolPublicMethod.Toast(WXEntryActivity.this, "成功");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 取消分享
//                    CoolPublicMethod.Toast(WXEntryActivity.this, "取消");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// // 分享失败
//                    CoolPublicMethod.Toast(WXEntryActivity.this, "失败");
                    break;
                default:
//                    CoolPublicMethod.Toast(WXEntryActivity.this, "未知错误");
                    break;
            }
        } else if (resp.getType() == COMMAND_SENDAUTH) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) resp).code;
                    String state = ((SendAuth.Resp) resp).state;
//                    CoolPublicMethod.Toast(WXEntryActivity.this, "成功");
                    // 根据code获取access_token接口
                    getTokenWithCode(code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 取消分享
                    CoolPublicMethod.Toast(WXEntryActivity.this, "取消");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// // 分享失败
                    CoolPublicMethod.Toast(WXEntryActivity.this, "失败");
                    break;
                default:
                    CoolPublicMethod.Toast(WXEntryActivity.this, "未知错误");
                    break;
            }
        } else if (resp.getType() == COMMAND_SENDMESSAGE_TO_WX) {
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) resp).code;
                    String state = ((SendAuth.Resp) resp).state;
                    CoolPublicMethod.Toast(WXEntryActivity.this, "微信分享成功");
                    // 根据code获取access_token接口
//                getTokenWithCode(code);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:// 取消分享
                    CoolPublicMethod.Toast(WXEntryActivity.this, "微信取消分享");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:// // 分享失败
                    CoolPublicMethod.Toast(WXEntryActivity.this, "微信分享失败");
                    break;
                default:
                    CoolPublicMethod.Toast(WXEntryActivity.this, "微信未知错误");
                    break;
            }
        } else {
            CoolPublicMethod.Toast(WXEntryActivity.this, resp.errCode + "");
        }

        this.finish();
    }

    private void getTokenWithCode(String code) {
        String path = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + AYConsts.WX_KEY
                + "&secret="
                + AYConsts.WX_SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";
        CoolLogTrace.i("getTokenWithCode", "getTokenWithCode", " getAccess_token：" + path);

        String result = HttpUtil.executeHttpGet(path);
        System.out.println("result= " + result);
        if (TextUtils.isEmpty(result)) {
            CoolPublicMethod.Toast(WXEntryActivity.this, "登录失败，请重新登录");
        } else {
//            WXLoginVO wxLoginVO = new Gson().fromJson(result, WXLoginVO.class);
//            EventBus.getDefault().post(new ThirdLoginBean(wxLoginVO.getOpenid(),"1"));
        }

//        CoolHttpUtil.get(path, null, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                CoolPublicMethod.hideProgressDialog();
//                CoolLogTrace.i(" failure ", "getTokenWithCode", responseString);
//                CoolPublicMethod.Toast(WXEntryActivity.this, responseString);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                CoolPublicMethod.hideProgressDialog();
//                CoolLogTrace.i(" onSuccess ", "getTokenWithCode", responseString);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(responseString);
//                    String openid = jsonObject.getString("openid").toString().trim();
//                    String access_token = jsonObject.getString("access_token").toString().trim();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
