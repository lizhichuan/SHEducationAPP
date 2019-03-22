package com.shaohua.sheducation.js;

import android.webkit.JavascriptInterface;

/**
 * Created by chuan on 2017/10/14.
 */

public class AndroidtoJs extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void openDetail(String id) {
//        Intent intent = new Intent(App.getInstance(), ProductDetailsActivity.class);
//        intent.putExtra("id", id);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        App.getInstance().startActivity(intent);
    }
}
