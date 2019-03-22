package com.njcool.lzccommon.view.rooler;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class CoolTextHintView extends TextView implements CoolHintView {
	private int length;
	
	public CoolTextHintView(Context context){
		super(context);
	}
	
	public CoolTextHintView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void initView(int length, int gravity) {
		this.length = length;
		setTextColor(Color.WHITE);
		switch (gravity) {
		case 0:
			setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
			break;
		case 1:
			setGravity(Gravity.CENTER);
			break;
		case 2:
			setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
			break;
		}

        setCurrent(0);
	}

	@Override
	public void setCurrent(int current) {
		setText(current+1+"/"+ length);
	}
}
