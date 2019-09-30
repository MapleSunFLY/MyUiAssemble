package com.fly.viewlibrary.sortlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.fly.viewlibrary.R;
import com.fly.viewlibrary.utils.ViewUtils;

/**
 * 包    名 : com.fly.viewlibrary.sortlist
 * 作    者 : FLY
 * 创建时间 : 2019/9/18
 * 描述:
 */
public class WaveSideBar extends View {

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int choose = -1;// 选中
    private Paint paint = new Paint();

    private TextView mTextDialog;

    private int textColor;
    private int textSize;
    private int textSelectorColor;
    private int selectedBackground;

    private int mHeightPixels;

    public WaveSideBar(Context context) {
        this(context, null);
    }

    public WaveSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveSideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getWindowInfo(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveSideBar);
        textColor = typedArray.getColor(R.styleable.WaveSideBar_wsbTextColor, Color.BLACK);
        textSize = typedArray.getDimensionPixelSize(R.styleable.WaveSideBar_wsbTextSize, ViewUtils.dip2px(getContext(), 14));
        textSelectorColor = typedArray.getColor(R.styleable.WaveSideBar_wsbSelectedTextColor, Color.GREEN);
        selectedBackground = typedArray.getResourceId(R.styleable.WaveSideBar_wsbSelectedBackground, R.drawable.wsb_selected_bg);
        typedArray.recycle();
    }


    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色.
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            paint.setColor(textColor);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(textSize);
            // 选中的状态
            if (i == choose) {
                paint.setColor(textSelectorColor);
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            //（整个高度/2-字母的高度*字母的数量/2）+字母的高度*第几个+字母高度
            float yPos = (height / 2 - singleHeight * b.length / 2) + singleHeight * i + singleHeight;//绘制相对于y坐标的中间
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();// 重置画笔
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        int singleHeight = getHeight() / b.length;// 获取每一个字母的高度

        //整个屏幕像素的一半  字母集合对应的像素高度的一半
        final int c = (int) ((y - mHeightPixels / 2 + (mHeightPixels / b.length * b.length / 2)) / (singleHeight * b.length) * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (selectedBackground != 0) {
                    setBackgroundResource(selectedBackground);
                } else {
                    setBackgroundDrawable(new ColorDrawable(0x00000000));
                }
                setAlpha((float) 0.7);
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(b[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(b[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
    }

    /**
     * 设置有数据对应的字母索引，并重绘
     *
     * @param b
     */
    public void setLetter(String[] b) {
        this.b = b;
        requestLayout();
        invalidate();
    }

    /**
     * 获取设备的屏幕信息
     *
     * @return
     */
    private void getWindowInfo(Context context) {
        // DisplayMetrics类用于屏幕的描述信息
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // 获取屏幕像素高度 px
        int height = displayMetrics.heightPixels;
        //获取状态栏高度
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        mHeightPixels = height - result - ViewUtils.dip2px(context, 48);//控件的高度=屏幕高度-状态栏高度-导航栏高度
    }

}


