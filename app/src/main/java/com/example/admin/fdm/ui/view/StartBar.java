package com.example.admin.fdm.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.admin.fdm.R;


/**
 * Created by fushuang on 2017/10/10.
 */

public class StartBar extends View {

    private int mHeight;
    private int mWidth;
    private int startNum=5;
    private int rating=2;
    private int spacing=0;
    private Bitmap grayStart;
    private Bitmap yellowStart;
    private Paint paint;
    private RectF rectF;
    private Rect rect;
    private boolean canStart=false;


    public StartBar(Context context) {
        super(context);
    }

    public StartBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StartBar);
        canStart = ta.getBoolean(R.styleable.StartBar_can_start, false);
        init();
    }

    public void setRating(int rating) {
        this.rating = rating;
        postInvalidate();
    }

    private void init() {
        grayStart = BitmapFactory.decodeResource(getResources(), R.drawable.gray_start);
        yellowStart = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_start);
        rect = new Rect(0, 0, grayStart.getWidth(), grayStart.getHeight());
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        spacing= (int) (mWidth*0.2/(startNum-1));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canStart){
            return false;
        }
        float x = event.getX();
        float v = x / mWidth;
        if (v<0.2){
            rating=1;
        }else if (v<0.4){
            rating=2;
        }else if (v<0.6){
            rating=3;
        }else if (v<0.8){
            rating=4;
        }else {
            rating=5;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < startNum; i++) {
            rectF = new RectF((float) (mWidth*0.8 / startNum * i+spacing*(i)), 0, (float) (mWidth*0.8 / startNum * (i + 1)+i+spacing*(i)), mHeight);
            canvas.drawBitmap(grayStart,rect,rectF,paint);
        }

        canvas.save();
        canvas.clipRect(0,0,mWidth*rating/startNum,mHeight);
        for (int i = 0; i < startNum; i++) {
            rectF = new RectF((float) (mWidth*0.8 / startNum * i+spacing*(i)), 0,(float) (mWidth*0.8 / startNum * (i + 1)+i+spacing*(i)), mHeight);
            canvas.drawBitmap(yellowStart,rect,rectF,paint);
        }

        canvas.restore();
    }


}
