package com.sagarmakhija1994.dgnadmin.util.squreview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@SuppressLint("AppCompatCustomView")
public class SquareLinearWidth extends LinearLayout {
    public SquareLinearWidth(Context context) {
        super(context);
    }
    public SquareLinearWidth(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLinearWidth(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int Actulewidth = getMeasuredHeight();
        int width =(int)(Actulewidth*(100.0f/100.0f));

        int height = width;
        setMeasuredDimension(width , height);
    }
}