package com.njcool.lzccommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

import com.njcool.lzccommon.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Created by chuan on 2017/7/24.
 */

public class CoolPayPsdInputView extends EditText {

    private Context mContext;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;


    private float cX;


    /**
     * 实心圆的半径
     */
    private int radius = 10;
    /**
     * view的高度
     */
    private int height;
    private int width;

    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;
    /**
     * 最大输入位数
     */
    private int maxCount = 6;
    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColor = Color.GRAY;

    /**
     * 分割线的颜色
     */
    private int borderColor = Color.GRAY;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;

    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = Color.GRAY;
    private RectF rectF = new RectF();

    private int psdType = 0;
    private final static int psdType_weChat = 0;
    private final static int psdType_bottomLine = 1;

    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;
    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;

    /**
     * 需要对比的密码  一般为上次输入的
     */
//    private String mComparePassword = null;

    private onPasswordListener mListener;

    public CoolPayPsdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CoolPayPsdInputView);
        maxCount = typedArray.getInt(R.styleable.CoolPayPsdInputView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.CoolPayPsdInputView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.CoolPayPsdInputView_bottomLineColor, bottomLineColor);
        radius = typedArray.getDimensionPixelOffset(R.styleable.CoolPayPsdInputView_radius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.CoolPayPsdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.CoolPayPsdInputView_divideLineColor, divideLineColor);
        psdType = typedArray.getInt(R.styleable.CoolPayPsdInputView_psdType, psdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.CoolPayPsdInputView_rectAngle, rectAngle);

        typedArray.recycle();
    }

//    public String getmComparePassword() {
//        return mComparePassword;
//    }
//
//    public void setmComparePassword(String mComparePassword) {
//        this.mComparePassword = mComparePassword;
//    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
//        super.onDraw(canvas);

        switch (psdType) {
            case psdType_weChat:
                drawWeChatBorder(canvas);
                break;
            case psdType_bottomLine:
                drawBottomBorder(canvas);
                break;
        }

        drawPsdCircle(canvas);
    }


    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }

    }


    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {

        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPsdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    radius,
                    circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        textLength = text.toString().length();
//
//        if (textLength == maxCount) {
//            mListener.onEqual(getPasswordString());
//        }

//        if (mComparePassword != null && textLength == maxCount) {
//            if (TextUtils.equals(mComparePassword, getPasswordString())) {
//                mListener.onEqual(getPasswordString());
//                System.out.println(mComparePassword + "--" + getPasswordString());
//            } else {
//                mListener.onDifference();
//                System.out.println(mComparePassword + "--" + getPasswordString());
//            }
//        }
        invalidate();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPasswordString() {
        return getText().toString().trim();
    }

    /**
     * 密码比较监听
     */
    public interface onPasswordListener {
//        void onDifference();

        void onEqual(String psd);
    }

    public void setComparePassword(onPasswordListener listener) {
        mListener = listener;
    }
}
