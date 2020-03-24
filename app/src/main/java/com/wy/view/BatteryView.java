package com.wy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.wy.battery.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2020/3/18 0019.
 */
public class BatteryView extends View {
    ArrayList<PointBean> pointBeans = new ArrayList<>();
    private int mPower = 100;
    private boolean mIsCharge = false;
    private int orientation;
    private int battery_width;
    private int battery_height;
    int battery_head_width = 3;
    int battery_head_height;
    int line_width = 2;
    int battery_inside_margin = 2;
    int lightning_width;
    int lightning_height;

    Paint framePaint, frameBattery, lightningPaint, whitePaint;


    public BatteryView(Context context) {
        super(context);
        initPoint();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Battery);
        orientation = typedArray.getInt(R.styleable.Battery_batteryOrientation, 0);
        mPower = typedArray.getInt(R.styleable.Battery_batteryPower, 100);

        /**
         * recycle() :官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
         * 在TypedArray后调用recycle主要是为了缓存。当recycle被调用后，这就说明这个对象从现在可以被重用了。
         *TypedArray 内部持有部分数组，它们缓存在Resources类中的静态字段中，这样就不用每次使用前都需要分配内存。
         */
        typedArray.recycle();

        initPoint();
    }

    private void initPoint() {
        // 外框颜色
        framePaint = new Paint();
        framePaint.setColor(Color.BLACK);
        framePaint.setStrokeWidth(line_width);
        framePaint.setAntiAlias(true);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeJoin(Paint.Join.ROUND);
        framePaint.setStrokeCap(Paint.Cap.ROUND);

        //电量颜色
        frameBattery = new Paint();
        frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_000000));
        frameBattery.setStrokeWidth(line_width);
        frameBattery.setAntiAlias(true);
        frameBattery.setAlpha(0);
        frameBattery.setStyle(Paint.Style.FILL);
        frameBattery.setStrokeJoin(Paint.Join.ROUND);
        frameBattery.setStrokeCap(Paint.Cap.ROUND);

        // 充电图标颜色
        lightningPaint = new Paint();
        lightningPaint.setColor(Color.BLACK);
        lightningPaint.setAntiAlias(true);
        lightningPaint.setStyle(Paint.Style.FILL);
        lightningPaint.setStrokeJoin(Paint.Join.ROUND);
        lightningPaint.setStrokeCap(Paint.Cap.ROUND);

        // 充电图标边框颜色
        whitePaint = new Paint();
        whitePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_D5F4FF));
        whitePaint.setAntiAlias(true);
        whitePaint.setStrokeWidth(1);
        whitePaint.setAlpha(0);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeJoin(Paint.Join.ROUND);
        whitePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对View上的內容进行测量后得到的View內容占据的宽度

        pointBeans.clear();
        if (orientation == 0) {
            // 水平方向电池
            battery_height = getMeasuredHeight();
            battery_head_height = (int) (battery_height * 0.5);
            battery_head_width = (int) (battery_head_height * 0.4);
            battery_width = getMeasuredWidth() - battery_head_width;

            lightning_height = (int) (battery_height - 2 * battery_inside_margin);
            lightning_width = lightning_height * 3 / 5;

            int topX = battery_width / 2;
            int topY = battery_height / 2 - lightning_height / 2;
            int offset = lightning_width / 4;

            PointBean pointBean0 = new PointBean();
            pointBean0.setX(topX + offset);
            pointBean0.setY(topY);
            pointBeans.add(pointBean0);

            int leftX = battery_width / 2 - lightning_width / 2;
            int leftY = battery_height / 2;
            PointBean pointBean1 = new PointBean();
            pointBean1.setX(leftX);
            pointBean1.setY(leftY + offset);
            pointBeans.add(pointBean1);

            int centerX = battery_width / 2;
            int centerY = battery_height / 2;
            PointBean pointBean2 = new PointBean();
            pointBean2.setX(centerX - offset / 2);
            pointBean2.setY(centerY + offset / 2);
            pointBeans.add(pointBean2);

            int bottomX = topX;
            int bottomY = topY + lightning_height;
            PointBean pointBean3 = new PointBean();
            pointBean3.setX(bottomX - offset);
            pointBean3.setY(bottomY);
            pointBeans.add(pointBean3);

            int rightX = leftX + lightning_width;
            int rightY = leftY;
            PointBean pointBean4 = new PointBean();
            pointBean4.setX(rightX);
            pointBean4.setY(rightY - offset);
            pointBeans.add(pointBean4);

            PointBean pointBean5 = new PointBean();
            pointBean5.setX(centerX + offset / 2);
            pointBean5.setY(centerY - offset / 2);
            pointBeans.add(pointBean5);
        } else {
            // 垂直方向电池
            battery_width = getMeasuredWidth();
            battery_head_width = (int) (battery_width * 0.5);
            battery_head_height = (int) (battery_head_width * 0.4);
            battery_height = getMeasuredHeight() - battery_head_height;
            lightning_height = battery_height * 3 / 5;
            lightning_width = battery_width / 2;

            int topX = battery_width / 2;
            int topY = battery_height / 2 - lightning_height / 2 + battery_head_height / 2 + battery_inside_margin * 2;
            int offset = lightning_width / 3;

            PointBean pointBean0 = new PointBean();
            pointBean0.setX(topX + offset / 2);
            pointBean0.setY(topY);
            pointBeans.add(pointBean0);

            int leftX = battery_width / 2 - lightning_width / 2;
            int leftY = battery_height / 2 + battery_head_height / 2 + battery_inside_margin;
            PointBean pointBean1 = new PointBean();
            pointBean1.setX(leftX);
            pointBean1.setY(leftY + offset);
            pointBeans.add(pointBean1);

            int centerX = battery_width / 2;
            int centerY = battery_height / 2 + battery_head_height / 2 + battery_inside_margin;
            PointBean pointBean2 = new PointBean();
            pointBean2.setX(centerX - offset / 4);
            pointBean2.setY(centerY + offset / 4);
            pointBeans.add(pointBean2);

            int bottomX = topX;
            int bottomY = topY + lightning_height + battery_head_height / 2 + battery_inside_margin;
            PointBean pointBean3 = new PointBean();
            pointBean3.setX(bottomX - offset / 2);
            pointBean3.setY(bottomY);
            pointBeans.add(pointBean3);

            int rightX = leftX + lightning_width;
            int rightY = leftY;
            PointBean pointBean4 = new PointBean();
            pointBean4.setX(rightX);
            pointBean4.setY(rightY - offset);
            pointBeans.add(pointBean4);

            PointBean pointBean5 = new PointBean();
            pointBean5.setX(centerX + offset / 4);
            pointBean5.setY(centerY - offset / 4);
            pointBeans.add(pointBean5);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPower < 25) {
            frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_F25E5E));
            //lightningPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_F25E5E));
        } else if (mPower < 50) {
            frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_FFBA57));
            // lightningPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_FFBA57));
        } else if (mPower < 75) {
            frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_89D146));
            // lightningPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_89D146));
        } else {
            if (mIsCharge) {
                frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_89D146));
            } else {
                frameBattery.setColor(ContextCompat.getColor(getContext(), R.color.color_49A6F6));
            }
        }

        if (orientation == 0) {
            drawHorizontalBattery(canvas);
        } else {
            drawVerticalBattery(canvas);
        }
    }

    private void drawHorizontalBattery(Canvas canvas) {
        int battery_left = 0;
        int battery_top = 0;

        //先画外框
        framePaint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF(battery_left, battery_top, battery_left + battery_width, battery_top + battery_height);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(rect, 3, 3, framePaint);
        } else {
            canvas.drawRect(rect, framePaint);
        }

        //画电池头
        framePaint.setStyle(Paint.Style.FILL);
        int h_left = battery_left + battery_width;
        int h_top = battery_top + battery_height / 2 - battery_head_height / 2;
        int h_right = h_left + battery_head_width;
        int h_bottom = h_top + battery_head_height;
        RectF rect2 = new RectF(h_left, h_top, h_right, h_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(rect2, 3, 3, framePaint);
        } else {
            canvas.drawRect(rect2, framePaint);
        }


        float power_percent = mPower / 100.0f;
        //画电量
        if (power_percent != 0) {
            int p_left = battery_left + battery_inside_margin;
            int p_top = battery_top + battery_inside_margin;
            int p_right = p_left + (int) ((battery_width - battery_inside_margin * 2) * power_percent);
            int p_bottom = p_top + battery_height - battery_inside_margin * 2;
            RectF rect3 = new RectF(p_left, p_top, p_right, p_bottom);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(rect3, 3, 3, frameBattery);
            } else {
                canvas.drawRect(rect3, frameBattery);
            }
        }

        // 画充电模式闪电
        if (mIsCharge) {
            drawCharge(canvas);
        }
    }

    private void drawVerticalBattery(Canvas canvas) {
        int battery_left = 0;
        int battery_top = 0;
        int battery_h = battery_height - battery_inside_margin * 4;

        //画电池头
        framePaint.setStyle(Paint.Style.FILL);
        int h_left = battery_width / 2 - battery_head_width / 2;
        int h_top = battery_top;
        int h_right = h_left + battery_head_width;
        int h_bottom = h_top + battery_head_height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(h_left, h_top, h_right, h_bottom, 3, 3, framePaint);
        } else {
            Rect rect3 = new Rect(h_left, h_top, h_right, h_bottom);
            canvas.drawRect(rect3, framePaint);
        }

        //先画外框
        framePaint.setStyle(Paint.Style.STROKE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RectF rect = new RectF(battery_left, battery_top + battery_head_height, battery_left + battery_width, battery_head_height + battery_height);
            canvas.drawRoundRect(rect, 3, 3, framePaint);
        } else {
            Rect rect = new Rect(battery_left, battery_top + battery_head_height, battery_left + battery_width, battery_head_height + battery_height);
            canvas.drawRect(rect, framePaint);
        }


        float power_percent = mPower / 100.0f;
        framePaint.setStyle(Paint.Style.FILL);
        //画电量
        if (power_percent != 0) {
            int p_left = battery_left + battery_inside_margin * 2;
            int p_top = (int) (battery_head_height + battery_inside_margin * 2 + battery_h - (((battery_h) * power_percent)));
            int p_right = battery_width - battery_inside_margin * 2;
            int p_bottom = battery_head_height + battery_height - battery_inside_margin * 2;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RectF rect = new RectF(p_left, p_top, p_right, p_bottom);
                canvas.drawRoundRect(rect, 3, 3, frameBattery);
            } else {
                Rect rect = new Rect(p_left, p_top, p_right, p_bottom);
                canvas.drawRect(rect, frameBattery);
            }

        }

        // 画充电模式闪电
        if (mIsCharge) {
            drawCharge(canvas);
        }
    }

    private void drawCharge(Canvas canvas) {
        PointBean fristPoint;
        Path path = new Path();
        for (int i = 0; i < pointBeans.size(); i++) {
            if (i == 0) {
                fristPoint = pointBeans.get(0);
                path.moveTo(fristPoint.getX(), fristPoint.getY());
            }
            path.lineTo(pointBeans.get(i).getX(), pointBeans.get(i).getY());//右下角
        }

        if (pointBeans.size() >= 3) {
            path.close();//闭合图形
        }
        canvas.drawPath(path, lightningPaint);

        PointBean fristPoint2;
        Path path2 = new Path();
        for (int i = 0; i < pointBeans.size(); i++) {
            if (i == 0) {
                fristPoint2 = pointBeans.get(0);
                path2.moveTo(fristPoint2.getX(), fristPoint2.getY());
            }
            path2.lineTo(pointBeans.get(i).getX(), pointBeans.get(i).getY());//右下角
        }

        if (pointBeans.size() >= 3) {
            path2.close();//闭合图形
        }
        if (orientation == 0) {
            whitePaint.setStrokeWidth(1);
        } else {
            whitePaint.setStrokeWidth(2);
        }
        canvas.drawPath(path2, whitePaint);
    }

    public void setPower(int power, boolean isCharge) {
        mPower = power;
        if (mPower < 0) {
            mPower = 0;
        }
        mIsCharge = isCharge;
        invalidate();
    }

    public class PointBean {
        private float x;
        private float y;

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }
}
