package com.example.testproject;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MaskImageView extends androidx.appcompat.widget.AppCompatImageView {

    private Point mTouch;
    private Point originalColor;
    private Point matchColor1;
    private Point matchColor2;
    private Bitmap mMarker;
    private Bitmap Marker;
    private Bitmap Cursor;
    private int dynSize;
    private int cursorX;
    private Paint paint = new Paint();
    private int MaskType = 0;
    private int theoryNum = 1;
    // Java constructor
    public MaskImageView(Context context) {
        super(context);
        init();
    }
    // XML constructor
    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTouch = new Point (-1, -1);
        originalColor = new Point (-1, -1);
        matchColor1 = new Point (-1, -1);
        matchColor2 = new Point (-1, -1);
    }

    public void setMaskType (int mode) {
        MaskType = mode;
    }

    public void setMarker(int cols, int rows, boolean markerColor) {
        // int dynSize = 0;
        if (cols > rows) {
            dynSize = cols / 10;
        } else {
            dynSize = rows / 10;
        }
        if (markerColor) {
            mMarker = BitmapFactory.decodeResource(this.getResources(), R.drawable.cross_dark);
        } else {
            mMarker = BitmapFactory.decodeResource(this.getResources(), R.drawable.cross_light);
        }
        Marker = Bitmap.createScaledBitmap(mMarker, dynSize, dynSize, false);
    }

    public void setColor(int x, int y, int num) {
        // int dynSize = 0;
        if (num == 0) {
            originalColor.x = x;
            originalColor.y = y;
        } else if (num == 1) {
            matchColor1.x = x;
            matchColor1.y = y;
        } else if (num == 2) {
            matchColor2.x = x;
            matchColor2.y = y;
        }
    }

    public void setCursor(double lightness, int view_height, int view_width) {
        Cursor = BitmapFactory.decodeResource(this.getResources(), R.drawable.cursor);
        Cursor = Bitmap.createScaledBitmap(Cursor, (int) (100.0 / 120.0 * view_height),
                view_height, false);
        cursorX = (int) (view_width / 70.0 * (- 64 * lightness + 67) - 50.0 / 120.0 * view_height);
    }

    public void setTheoryNum (int mode) {
        theoryNum = mode;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Capture a reference to each touch for drawing
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mTouch.set((int) event.getX(), (int) event.getY());
            // mTouches.add(new Point((int) event.getX(), (int) event.getY()));
            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    protected void onDraw(Canvas c) {
        // Let the image be drawn first
        super.onDraw(c);
        // Draw your custom points here
        // Paint paint = new Paint();
        if (MaskType == 0) { // 0: display touch coordinate on image
            if (mTouch.x >= 0) {
                c.drawBitmap(Marker, mTouch.x - (int) (0.5 * dynSize),
                        mTouch.y - (int) (0.5 * dynSize), paint);
            }
        } else if (MaskType == 1) { // 1: display color wheel mask
            c.drawCircle(originalColor.x, originalColor.y, 20, paint);
            paint.setColor(Color.GRAY);
            c.drawCircle(originalColor.x, originalColor.y, 10, paint);
        } else if (MaskType == 2) { // 2: display cursor of lightness bar
            c.drawBitmap(Cursor, cursorX, 0, paint);
        } else if (MaskType == 3) { // 3: display color wheel mask of different matching theory
            if (theoryNum == 1) {
                // draw line
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(5);
                c.drawLine(originalColor.x, originalColor.y, matchColor1.x, matchColor1.y, paint);
                // draw point
                paint.setColor(Color.BLACK);
                c.drawCircle(originalColor.x, originalColor.y, 15, paint);
                c.drawCircle(matchColor1.x, matchColor1.y, 9, paint);
                paint.setColor(Color.GRAY);
                c.drawCircle(originalColor.x, originalColor.y, 7, paint);
                c.drawCircle(matchColor1.x, matchColor1.y, 4, paint);
            } else if (theoryNum == 2) {
                c.drawCircle(originalColor.x, originalColor.y, 15, paint);
                paint.setColor(Color.WHITE);
                c.drawCircle(matchColor1.x, matchColor1.y, 8, paint);
            } else if (theoryNum == 3 || theoryNum == 4) {
                // draw line
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(5);
                c.drawLine(originalColor.x, originalColor.y, matchColor1.x, matchColor1.y, paint);
                c.drawLine(originalColor.x, originalColor.y, matchColor2.x, matchColor2.y, paint);
                if (theoryNum == 4)
                    c.drawLine(matchColor1.x, matchColor1.y, matchColor2.x, matchColor2.y, paint);
                // draw point
                paint.setColor(Color.BLACK);
                c.drawCircle(originalColor.x, originalColor.y, 15, paint);
                c.drawCircle(matchColor1.x, matchColor1.y, 9, paint);
                c.drawCircle(matchColor2.x, matchColor2.y, 9, paint);
                paint.setColor(Color.GRAY);
                c.drawCircle(originalColor.x, originalColor.y, 7, paint);
                c.drawCircle(matchColor1.x, matchColor1.y, 4, paint);
                c.drawCircle(matchColor2.x, matchColor2.y, 4, paint);
            }
        }
    }

    public void ColorWheelMask(Canvas c) {

    }

}
