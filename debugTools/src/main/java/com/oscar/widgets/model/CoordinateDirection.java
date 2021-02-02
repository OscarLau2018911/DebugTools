package com.oscar.widgets.model;

import android.graphics.Picture;
import android.graphics.Point;
import android.view.WindowManager;

import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;

/**
 * wm的layoutparams与屏幕坐标系对应关系类
 * 根据Gravity的区别，将wm的坐标原点分为9个位置，
 * 不同位置对应的屏幕坐标与wm坐标情况不一样
 * wm坐标只能支持LEFT,RIGHT,TOP,BOTTOM,CENTER几个Gravity
 * x y表示相对于原点的距离，所以需要考虑view的宽高
     1(L|T)********8(T)************7(R|T)
     *                             *
     *                             *
     *                             *
     *                             *
     *                             *
     *                             *
     2(L)          9(CENTER)       6(R)
     *                             *
     *                             *
     *                             *
     *                             *
     *                             *
     *                             *
     3(L|B)*********4(B)***********5(R|B)
 */
public class CoordinateDirection {
    /*LEFT&TOP*/
    public static final int POINT1 = 1;
    /*LEFT*/
    public static final int POINT2 = 2;
    /*LEFT&BOTTOM*/
    public static final int POINT3 = 3;
    /*BOTTOM*/
    public static final int POINT4 = 4;
    /*RIGHT&BOTTOM*/
    public static final int POINT5 = 5;
    /*RIGHT*/
    public static final int POINT6 = 6;
    /*RIGHT&TOP*/
    public static final int POINT7 = 7;
    /*TOP*/
    public static final int POINT8 = 8;
    /*CENTER*/
    public static final int POINT9 = 9;
    /**
     * x y坐标的变化正常
     */
    public static final int FORWARD = 1;
    /**
     * x y坐标的变化相反
     */
    public static final int REVERSE = -1;
    /**
     * 坐标永远为正
     */
    public static final int ABSOLUTE = 0;
    /**
     * 坐标正负均可
     */
    public static final int ASNORMAL = 1;
    /**
     * 屏幕screen坐标与控件宽高有关
     */
    public static final boolean DEPENS = true;
    /**
     * 屏幕screen坐标与控件宽高无关
     */
    public static final boolean NO_DEPENDS = false;
    /**
     * 坐标位置类型
     */
    private int type;
    /**
     * x坐标方向类型
     */
    private int x_d;
    /**
     * y坐标方向类型
     */
    private int y_d;
    /**
     * x坐标值类型
     */
    private int x_type;
    /**
     * y坐标值类型
     */
    private int y_type;
    /**
     * 中心点坐标
     */
    private Point center;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    public CoordinateDirection() {
        type = POINT1;
        x_d = FORWARD;
        y_d = FORWARD;
        x_type = ABSOLUTE;
        y_type = ABSOLUTE;
        center = new Point(0,0);
    }

    public int getXDirection() {
        return x_d;
    }

    public CoordinateDirection setXDirection(int x) {
        this.x_d = x;
        return this;
    }

    public int getYDirection() {
        return y_d;
    }

    public CoordinateDirection setYDirection(int y) {
        this.y_d = y;
        return this;
    }

    public int getCoordinateType() {
        return type;
    }

    public CoordinateDirection setCoordinateType(int type) {
        this.type = type;
        switch (this.type) {
            case POINT1:
                x_d = FORWARD;
                y_d = FORWARD;
                x_type = ABSOLUTE;
                y_type = ABSOLUTE;
                break;
            case POINT2:
                x_d = FORWARD;
                y_d = FORWARD;
                x_type = ABSOLUTE;
                y_type = ASNORMAL;
                break;
            case POINT3:
                x_d = FORWARD;
                y_d = REVERSE;
                x_type = ABSOLUTE;
                y_type = ABSOLUTE;
                break;
            case POINT4:
                x_d = FORWARD;
                y_d = FORWARD;
                x_type = ASNORMAL;
                y_type = ABSOLUTE;
                break;
            case POINT5:
                x_d = REVERSE;
                y_d = REVERSE;
                x_type = ABSOLUTE;
                y_type = ABSOLUTE;
                break;
            case POINT6:
                x_d = REVERSE;
                y_d = FORWARD;
                x_type = ABSOLUTE;
                y_type = ASNORMAL;
                break;
            case POINT7:
                x_d = REVERSE;
                y_d = FORWARD;
                x_type = ABSOLUTE;
                y_type = ABSOLUTE;
                break;
            case POINT8:
                x_d = FORWARD;
                y_d = FORWARD;
                x_type = ASNORMAL;
                y_type = ABSOLUTE;
                break;
            case POINT9:
                x_d = FORWARD;
                y_d = FORWARD;
                x_type = ASNORMAL;
                y_type = ASNORMAL;
                break;
        }
        return this;
    }

    public int getXValuetype() {
        return x_type;
    }

    public CoordinateDirection setXValuetype(int x_type) {
        this.x_type = x_type;
        return this;
    }

    public int getYValuetype() {
        return y_type;
    }

    public CoordinateDirection setYValuetype(int y_type) {
        this.y_type = y_type;
        return this;
    }

    public Point getCenterCoordinate() {
        return center;
    }

    public CoordinateDirection setCenterCoordinate(Point center) {
        this.center = center;
        return this;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    @Override
    public String toString() {
        return "CoordinateDirection{" +
                "type=" + type +
                ", x_d=" + x_d +
                ", y_d=" + y_d +
                ", x_type=" + x_type +
                ", y_type=" + y_type +
                ", center=" + center +
                ", maxX=" + maxX +
                ", minX=" + minX +
                ", maxY=" + maxY +
                ", minY=" + minY +
                '}';
    }
}
