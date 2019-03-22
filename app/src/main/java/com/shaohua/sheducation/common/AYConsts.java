package com.shaohua.sheducation.common;

/**
 * Created by lizhichuan on 17/1/27.
 */

public class AYConsts {
    public static final String NETWOEKERROR = "网络不给力,请稍后重试...";


    public static final String SINA_APP_KEY = "1384500957";
    public static final String SINA_APP_SECERT = "18586e26cb590d47577d939c68a41808";
    public static final String SINA_APP_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final String QQ_APP_ID = "1106233177";
    public static final String QQ_APP_KEY = "2fYNfviIhGsFPYi0";
    public static final String QQ_SCOPE = "get_user_info,add_t";

    public static final String WX_KEY = "wxd574efaef8998cc7";
    public static final String WX_SECRET = "928b631e486c3984ff82d12cafe3a7ce";
    public static final String WEIXIN_SCOPE = "snsapi_userinfo"; //这个值提示没有权限
    public static final String WEIXIN_STATE = "wechat_sdk_demo_test";


    public static final String SIGN = "9f55f6f57738b5b523fc92762bfef721";


    public interface SearchType {
        String type_product = "product";
        String type_artist = "artist";
    }

    public static String appId = "aiyi";
    public static String appKey = "DQNLKRFQTKXPCRTXFIYG";
    public static String UPImageUrl = "https://artmkt-web.b0.upaiyun.com/";

}
