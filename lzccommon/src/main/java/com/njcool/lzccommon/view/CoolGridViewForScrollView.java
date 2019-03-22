package com.njcool.lzccommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lizhichuan on 16/3/30.
 */
public class CoolGridViewForScrollView extends GridView {
    public CoolGridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoolGridViewForScrollView(Context context) {
        super(context);
    }

    public CoolGridViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
