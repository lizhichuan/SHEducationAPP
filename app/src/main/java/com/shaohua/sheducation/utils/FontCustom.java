package com.shaohua.sheducation.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by chuan on 2017/9/25.
 */

public class FontCustom {
    // fongUrl是自定义字体分类的名称
    private static String fongUrl = "myfont.TTF";
    private static String fongUrl2 = "center.ttf";
    //Typeface是字体，这里我们创建一个对象
    private static Typeface tf;

    /**
     * 设置字体
     */
    public static Typeface setFont(Context context)
    {
        if (tf == null)
        {
            //给它设置你传入的自定义字体文件，再返回回来
            tf = Typeface.createFromAsset(context.getAssets(),fongUrl);
        }
        return tf;
    }

    /**
     * 设置字体
     */
    public static Typeface setFont2(Context context) {
        if (tf == null) {
            //给它设置你传入的自定义字体文件，再返回回来
            tf = Typeface.createFromAsset(context.getAssets(), fongUrl2);
        }
        return tf;
    }
}
