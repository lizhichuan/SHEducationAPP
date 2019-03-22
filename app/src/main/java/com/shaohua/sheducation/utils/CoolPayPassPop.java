package com.shaohua.sheducation.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.njcool.lzccommon.utils.CoolPublicMethod;
import com.njcool.lzccommon.view.CoolPayPsdInputView;
import com.shaohua.sheducation.R;

/**
 * Created by chuan on 2017/7/4.
 */

public class CoolPayPassPop {

    public CoolPayPassPop() {
    }

    private PopupWindow pop;

    private PopClickListener popClickListener;

    public PopClickListener getPopClickListener() {
        return popClickListener;
    }

    public void setPopClickListener(PopClickListener popClickListener) {
        this.popClickListener = popClickListener;
    }

    public void ShowPop(final Activity activity, View rootView, int gravity) {
        View view = activity.getLayoutInflater().inflate(R.layout.pop_pay_password, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        ImageView img_close = (ImageView) view.findViewById(R.id.img_close);
        CoolPayPsdInputView pass_input_one = (CoolPayPsdInputView) view.findViewById(R.id.pass_input_one);
        pass_input_one.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    pop.dismiss();
                    CoolPublicMethod.hintKbTwo(activity);
                    popClickListener.onConfirm(s.toString());

                }
            }
        });
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                popClickListener.onCancel();

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                popClickListener.onCancel();

            }
        });


        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(rootView, gravity, 0, 0);
    }


    public interface PopClickListener {
        public void onConfirm(String s);

        public void onCancel();
    }

}
