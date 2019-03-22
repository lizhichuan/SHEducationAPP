package com.shaohua.sheducation.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.shaohua.sheducation.R;
import com.shaohua.sheducation.js.AndroidtoJs;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 笔记详情
 *
 * @author chuan
 */
public class CommonWebViewActivity extends SHBaseActivity {

    @Bind(R.id.webview)
    WebView webview;
//    @Bind(R.id.swp)
//    CoolSwipeRefreshLayout swp;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_common_webview);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        findViews();
    }

    @Override
    protected void onClickLeft(View v) {
        finish();
        super.onClickLeft(v);
    }


    private void findViews() {
        if (TextUtils.isEmpty(url)) {
            url = "http://www.baidu.com";
        }
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        System.out.println("url= " + url);
        CoolPublicMethod.setWebView(CommonWebViewActivity.this, webview);
        webview.addJavascriptInterface(new AndroidtoJs(), "app");//AndroidtoJS类对象映射到js的test对象

        // 设置进度条
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                // Log.e(TAG, "progress = " + newProgress);
                if (newProgress == 100) {
                    // 隐藏进度条
//                    if (swp != null) {
//                        swp.setRefreshing(false);
//                    }
                    CoolPublicMethod.hideProgressDialog();
                } else {

                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setmTopTitle(title);
            }
        });
//        swp.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
//        swp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                webview.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webview.loadUrl(url);
//                    }
//                });
//            }
//        });
//
//        // 设置子视图是否允许滚动到顶部
//        swp.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
//                return webview.getScrollY() > 0;
//            }
//        });
        webview.loadUrl(url);
    }


}
