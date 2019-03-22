package com.shaohua.sheducation.request;

/**
 * Created by chuan on 2017/11/25.
 */

public class AppSettingBody {

    /**
     * appPush : 0
     * friendPush : 0
     */

    private int appPush;
    private int friendPush;

    public int getAppPush() {
        return appPush;
    }

    public void setAppPush(int appPush) {
        this.appPush = appPush;
    }

    public int getFriendPush() {
        return friendPush;
    }

    public void setFriendPush(int friendPush) {
        this.friendPush = friendPush;
    }
}
