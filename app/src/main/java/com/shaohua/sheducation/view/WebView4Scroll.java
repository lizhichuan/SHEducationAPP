package com.shaohua.sheducation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by chuan on 2017/9/26.
 */

public class WebView4Scroll extends WebView {

    public WebView4Scroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getScrollY() <= 0) {
                    this.scrollTo(0, 1);
                }
                break;
            case MotionEvent.ACTION_UP:
//                if(this.getScrollY() == 0)
//                this.scrollTo(0,-1);
                break;
        }
        return super.onTouchEvent(event);
    }
}
