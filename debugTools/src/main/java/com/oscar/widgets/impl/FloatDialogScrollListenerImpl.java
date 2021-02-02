package com.oscar.widgets.impl;

import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;
import com.oscar.widgets.model.CoordinateDirection;
import com.oscar.widgets.utils.CoordinateHelper;


/**
 * 控件支持滑动变换位置
 */
public class FloatDialogScrollListenerImpl implements View.OnTouchListener{
    private static final String TAG = FloatDialogScrollListenerImpl.class.getName();
    /**
     * 标识正向滑动对应的坐标值是正还是负
     */
    private CoordinateDirection derection;
    private float x;
    private float y;
    private float mTouchStartX;
    private float mTouchStartY;
    private float mStartX;
    private float mStartY;
    private WindowManager wm;
    private WindowManager.LayoutParams layoutParams;
    public FloatDialogScrollListenerImpl(View view, WindowManager wm, WindowManager.LayoutParams lp) {
        setWm(wm);
        setLayoutParams(lp);
        setDerection(((AbsFloatDialog)view).getCoordinateDirection());
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        AbsFloatDialog dialog = (AbsFloatDialog) v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setmTouchStartX(getLayoutParams().x);
                setmTouchStartY(getLayoutParams().y);
                setmStartX(event.getRawX());
                setmStartY(event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX;
                float moveY;
                setX(event.getRawX());
                setY(event.getRawY());
                moveX = getX() - getmStartX();
                moveY = getY()- getmStartY();
                getLayoutParams().x = (int) (getmTouchStartX()+moveX*getDerection().getXDirection());
                getLayoutParams().y = (int) (getmTouchStartY()+moveY*getDerection().getYDirection());
                if (getLayoutParams().x > dialog.getCoordinateDirection().getMaxX())
                    getLayoutParams().x = dialog.getCoordinateDirection().getMaxX();
                if (getLayoutParams().x < dialog.getCoordinateDirection().getMinX())
                    getLayoutParams().x = dialog.getCoordinateDirection().getMinX();
                if (getLayoutParams().y > dialog.getCoordinateDirection().getMaxY())
                    getLayoutParams().y = dialog.getCoordinateDirection().getMaxY();
                if (getLayoutParams().y < dialog.getCoordinateDirection().getMinY())
                    getLayoutParams().y = dialog.getCoordinateDirection().getMinY();

                getWm().updateViewLayout(v,getLayoutParams());
                return true;
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        return false;
    }

    public CoordinateDirection getDerection() {
        return derection;
    }

    public void setDerection(CoordinateDirection derection) {
        this.derection = derection;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getmTouchStartX() {
        return mTouchStartX;
    }

    public void setmTouchStartX(float mTouchStartX) {
        this.mTouchStartX = mTouchStartX;
    }

    public float getmTouchStartY() {
        return mTouchStartY;
    }

    public void setmTouchStartY(float mTouchStartY) {
        this.mTouchStartY = mTouchStartY;
    }

    public float getmStartX() {
        return mStartX;
    }

    public void setmStartX(float mStartX) {
        this.mStartX = mStartX;
    }

    public float getmStartY() {
        return mStartY;
    }

    public void setmStartY(float mStartY) {
        this.mStartY = mStartY;
    }

    public WindowManager getWm() {
        return wm;
    }

    public void setWm(WindowManager wm) {
        this.wm = wm;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        return layoutParams;
    }

    public void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }
}
