package com.turntableview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HARRY on 2019/1/7 0007.
 */

public class TurntableView extends View {

    private Integer mPanNum;
    private String[] mNamesStrs;
    private String[] mIconsStrs;
    private int mWid;
    private int mHei;
    private int mCenterX;
    private int mCenterY;
    private float mOffsetAngle;
    private int mRadius;
    private Paint mPaint = new Paint();
    private int mColorRed;
    private int mColorGreen;
    private int mScreenHeight;
    private int mScreenWidth;
    private float mPercentage;
    private List<Bitmap> mBitmaps = new ArrayList<>();
    private GestureDetectorCompat mDetector;
    private int mCurrentAngle = 0;
    private long mDuration = 2000;

    public TurntableView(Context context) {
        this(context, null);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurntableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TurntableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mDetector = new GestureDetectorCompat(context, new TurntableGestureListener());
        mPaint.setAntiAlias(true);

        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;

        mColorRed = context.getResources().getColor(R.color.red);
        mColorGreen = context.getResources().getColor(R.color.green);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TurntableView);
        if (array != null) {
            mPanNum = array.getInteger(R.styleable.TurntableView_pannum, 8);
            int namesArray = array.getResourceId(R.styleable.TurntableView_names, R.array.names);
            int iconsArray = array.getResourceId(R.styleable.TurntableView_icons, R.array.icons);
            mPercentage = array.getFloat(R.styleable.TurntableView_percentage, (float) 0.75);
            mNamesStrs = context.getResources().getStringArray(namesArray);
            mIconsStrs = context.getResources().getStringArray(iconsArray);

            List<Integer> iconLists = new ArrayList<>();
            for (int i = 0; i < mIconsStrs.length; i++) {
                iconLists.add(context.getResources().getIdentifier(mIconsStrs[i], "mipmap", context.getPackageName()));
            }
            for (int i = 0; i < iconLists.size(); i++) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), iconLists.get(i));
                mBitmaps.add(bitmap);
            }
            array.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWid = w;
        mHei = h;

        mCenterX = mWid / 2;
        mCenterY = mCenterX;
        mRadius = mWid / 2;

        mOffsetAngle = (float) 360 / (float) mPanNum;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWid = (int) ((float) mScreenWidth * mPercentage);
        setMeasuredDimension(viewWid, viewWid);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆的背景颜色
        drawBackground(canvas);

        //画图片
        drawImage(canvas);

        //画文本
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(30);
        RectF rectF = new RectF(0, 0, mWid, mHei);

        Paint.FontMetrics fm = mPaint.getFontMetrics();
        float textHeight = fm.bottom - fm.top;

        float startAngle = mCurrentAngle;
        for (int i = 0; i < mNamesStrs.length; i++) {
            Path path = new Path();
            path.addArc(rectF, startAngle, mOffsetAngle);
            canvas.drawTextOnPath(mNamesStrs[i], path, 0, textHeight + 10, mPaint);
            startAngle = startAngle + mOffsetAngle;
        }
    }

    private void drawImage(Canvas canvas) {
        float radian = mCurrentAngle + mOffsetAngle / (float) 2;
        float imageOffset = (float) mRadius / (float) 7;
        for (int i = 0; i < mBitmaps.size(); i++) {
//        Math.toRadians  是为了提高计算精度
            float x = (float) (mCenterX + (float) mRadius * (float) 0.6 * Math.cos(Math.toRadians(radian)));
            float y = (float) (mCenterY + (float) mRadius * (float) 0.6 * Math.sin(Math.toRadians(radian)));

            RectF rectF = new RectF(x - imageOffset, y - imageOffset, x + imageOffset, y + imageOffset);
            canvas.drawBitmap(mBitmaps.get(i), null, rectF, mPaint);
            radian = radian + mOffsetAngle;
        }
    }

    private void drawBackground(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);

        RectF rectF = new RectF(0, 0, mWid, mHei);

        int angle = mCurrentAngle;
        for (int i = 0; i < mPanNum; i++) {
            if (i % 2 == 0) {
                mPaint.setColor(mColorRed);
                canvas.drawArc(rectF, angle, mOffsetAngle, true, mPaint);
            } else {
                mPaint.setColor(mColorGreen);
                canvas.drawArc(rectF, angle, mOffsetAngle, true, mPaint);
            }
            angle = (int) (angle + mOffsetAngle);
        }
    }

    private void setRotate(int rotation) {
        mCurrentAngle = (rotation % 360 + 360) % 360;
//        ViewCompat.postInvalidateOnAnimation(this);
        invalidate();
//        postInvalidate();
    }

    public void startRotate(int position) {
        if (position >= 0 && position < mPanNum) {
            //指定位置
            setScrollToPosition(position);
        } else {
            //随机
            int random = getRandom(0, mPanNum);
            setScrollToPosition(random);
        }
    }

    public void startRotate() {
        //随机
        int random = getRandom(0, mPanNum);
        setScrollToPosition(random);
    }

    public int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    private void setScrollToPosition(int position) {
        float entAngle = 270 - mOffsetAngle * ((float) position + (float) 0.5);
        if (entAngle < mCurrentAngle) {
            entAngle = entAngle + 4 * 360;
        } else {
            entAngle = entAngle + 3 * 360;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(mCurrentAngle, entAngle);
        animator.setDuration(mDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                System.out.println("animatedValue:" + animatedValue);
                setRotate((int) animatedValue);
            }
        });

        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = mDetector.onTouchEvent(event);
//        if (consume) {
////            getParent().getParent().requestDisallowInterceptTouchEvent(true);
//            return true;
//        } else {
//            return super.onTouchEvent(event);
//        }

        return consume;
    }

    private class TurntableGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            System.out.println("distanceX:" + distanceX + ",distanceX" + distanceY);

            float distance = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            System.out.println("velocityX:" + velocityX + ",velocityY" + velocityY);
            return true;
        }
    }

    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            return dx > 0 ? 'r' : 'l';
        } else {
            //Y轴移动
            return dy > 0 ? 'b' : 't';
        }
    }

}
