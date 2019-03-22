package com.njcool.lzccommon.view.rooler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.njcool.lzccommon.utils.CoolPublicMethod;


/**
 * Created by Mr.Jude on 2016/1/10.
 */
public class CoolColorPointHintView extends CoolShapeHintView {
    private int focusColor;
    private int normalColor;

    public CoolColorPointHintView(Context context, int focusColor, int normalColor) {
        super(context);
        this.focusColor = focusColor;
        this.normalColor = normalColor;
    }

    @Override
    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(focusColor);
        dot_focus.setCornerRadius(CoolPublicMethod.dip2px(getContext(), 4));
        dot_focus.setSize(CoolPublicMethod.dip2px(getContext(), 6), CoolPublicMethod.dip2px(getContext(), 6));
        return dot_focus;
    }

    @Override
    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(normalColor);
        dot_normal.setCornerRadius(CoolPublicMethod.dip2px(getContext(), 4));
        dot_normal.setSize(CoolPublicMethod.dip2px(getContext(), 6), CoolPublicMethod.dip2px(getContext(), 6));
        return dot_normal;
    }
}
