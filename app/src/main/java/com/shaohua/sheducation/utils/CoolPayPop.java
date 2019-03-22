package com.shaohua.sheducation.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shaohua.sheducation.R;


/**
 * Created by chuan on 2017/7/4.
 */

public class CoolPayPop {

    public CoolPayPop() {
    }

    private PopupWindow pop;

    private PopClickListener popClickListener;

    public PopClickListener getPopClickListener() {
        return popClickListener;
    }

    public void setPopClickListener(PopClickListener popClickListener) {
        this.popClickListener = popClickListener;
    }

    public void ShowPop(Activity activity, View rootView, int gravity, int image, String title, String tips) {
        View view = activity.getLayoutInflater().inflate(R.layout.pop_pay_result, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        ImageView img_result = (ImageView) view.findViewById(R.id.img_result);
        img_result.setImageResource(image);
        TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
        TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        if (!TextUtils.isEmpty(title)) {
            tv_result.setText(title);
            tv_result.setVisibility(View.VISIBLE);
        } else {
            tv_result.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(tips)) {
            tv_tips.setText(tips);
            tv_tips.setVisibility(View.VISIBLE);
        } else {
            tv_tips.setVisibility(View.GONE);
        }
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();

            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popClickListener.onConfirm();
                pop.dismiss();
            }
        });

        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(rootView, gravity, 0, 0);
    }


    public interface PopClickListener {
        public void onConfirm();

    }

}
