package com.oscar.widgets.utils;

import android.graphics.Picture;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;
import com.oscar.widgets.model.CoordinateDirection;

/**
 * 处理WM的x y坐标系与屏幕坐标系的帮助类
 */
public class CoordinateHelper {
    private static final String TAG = AbsFloatDialog.class.getName();
    /**
     * 获取的分辨率与物理分辨率px的比值，VHUB是3840*2160，获取的是1920*1080
     */
    private static final int densityScale = 2;

    public static Point initCoordinate(CoordinateDirection direction,Point lcd,Point view) {
        Point center = new Point();
        switch (direction.getCoordinateType()) {
            case CoordinateDirection.POINT1:
                center.x = 0;
                center.y = 0;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT2:
                center.x = 0;
                center.y = lcd.y/2;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y/2-view.y/2);
                direction.setMinY(view.y/2-lcd.y/2);
                break;
            case CoordinateDirection.POINT3:
                center.x = 0;
                center.y = lcd.y;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT4:
                center.x = lcd.x/2;
                center.y = lcd.y;
                direction.setMaxX(lcd.x/2-view.x);
                direction.setMinX(view.x-lcd.x/2);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT5:
                center.x = lcd.x;
                center.y = lcd.y;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT6:
                center.x = lcd.x;
                center.y = lcd.y/2;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y/2-view.y/2);
                direction.setMinY(view.y/2-lcd.y/2);
                break;
            case CoordinateDirection.POINT7:
                center.x = lcd.x;
                center.y = 0;
                direction.setMaxX(lcd.x-view.x);
                direction.setMinX(0);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT8:
                center.x = lcd.x/2;
                center.y = 0;
                direction.setMaxX(lcd.x/2-view.x/2);
                direction.setMinX(view.x/2-lcd.x/2);
                direction.setMaxY(lcd.y-view.y);
                direction.setMinY(0);
                break;
            case CoordinateDirection.POINT9:
                center.x = lcd.x/2;
                center.y = lcd.y/2;
                direction.setMaxX(lcd.x/2-view.x/2);
                direction.setMinX(view.x/2-lcd.x/2);
                direction.setMaxY(lcd.y/2-view.y/2);
                direction.setMinY(view.y/2-lcd.y/2);
                break;
        }
        return center;
    }

    /**
     *
     * @param layoutParams
     * @param lcd
     * @return
     */
    public static CoordinateDirection getCoordinateDirection(WindowManager.LayoutParams layoutParams,Point lcd,Point view) {
        CoordinateDirection coordinateDirection = new CoordinateDirection();
        int gravity = layoutParams.gravity;

        if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
            if ((gravity & Gravity.TOP) == Gravity.TOP) {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT1);
            } else if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT3);
            } else {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT2);
            }
        } else if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
            if ((gravity & Gravity.TOP) == Gravity.TOP) {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT7);
            } else if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT5);
            } else {
                coordinateDirection.setCoordinateType(CoordinateDirection.POINT6);
            }
        } else if ((gravity & Gravity.TOP) == Gravity.TOP) {
            coordinateDirection.setCoordinateType(CoordinateDirection.POINT8);
        } else if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
            coordinateDirection.setCoordinateType(CoordinateDirection.POINT4);
        } else {
            coordinateDirection.setCoordinateType(CoordinateDirection.POINT9);
        }
        coordinateDirection.setCenterCoordinate(initCoordinate(coordinateDirection,lcd,view));
        return coordinateDirection;
    }


    /**
     *
     * @param coordinateDirection
     * @param screen  屏幕绝对位置
     * @param view   view宽高
     * @return
     */
    public static Point getWmCoordinateByScreen(CoordinateDirection coordinateDirection,Point screen,Point view) {
        Point wm = new Point();
        Point screen_new = new Point(screen.x*densityScale,screen.y*densityScale);
        Point center = coordinateDirection.getCenterCoordinate();
        switch (coordinateDirection.getCoordinateType()) {
            case CoordinateDirection.POINT1:
                wm.x = screen_new.x - center.x;
                wm.y = screen_new.y - center.y;
                break;
            case CoordinateDirection.POINT2:
                wm.x = screen_new.x - center.x;
                wm.y = (screen_new.y + view.y/2)-center.y;
                break;
            case CoordinateDirection.POINT3:
                wm.x = screen_new.x - center.x;
                wm.y = center.y-screen_new.y -view.y;
                break;
            case CoordinateDirection.POINT4:
                wm.x = (screen_new.x + view.x/2)-center.x;
                wm.y = center.y-screen_new.y -view.y;
                break;
            case CoordinateDirection.POINT5:
                wm.x = center.x - screen_new.x-view.x;
                wm.y = center.y-screen_new.y -view.y;
                break;
            case CoordinateDirection.POINT6:
                wm.x = center.x - screen_new.x-view.x;
                wm.y = (screen_new.y + view.y/2)-center.y;
                break;
            case CoordinateDirection.POINT7:
                wm.x = center.x - screen_new.x-view.x;
                wm.y = screen_new.y -center.y;
                break;
            case CoordinateDirection.POINT8:
                wm.x = (screen_new.x + view.x/2)-center.x;
                wm.y = screen_new.y -center.y;
                break;
            case CoordinateDirection.POINT9:
                wm.x = (screen_new.x + view.x/2)-center.x;
                wm.y = (screen_new.y + view.y/2)-center.y;
                break;
        }
        return wm;
    }

    /**
     *
     * @param coordinateDirection
     * @param wm  wm位置
     * @param view   view宽高
     * @return
     */
    public static Point getScreenCoordinateByWm(CoordinateDirection coordinateDirection,Point wm,Point view) {
        Point screen = new Point();
        Point center = coordinateDirection.getCenterCoordinate();
        switch (coordinateDirection.getCoordinateType()) {
            case CoordinateDirection.POINT1:
                screen.x = wm.x + center.x;
                screen.y = wm.y + center.y;
                break;
            case CoordinateDirection.POINT2:
                screen.x = wm.x+center.x;
                screen.y = wm.y+center.y-view.y/2;
                break;
            case CoordinateDirection.POINT3:
                screen.x = wm.x + center.x;
                center.y = wm.y+screen.y+view.y;
                break;
            case CoordinateDirection.POINT4:
                screen.x = wm.x + center.x - view.x/2;
                center.y = wm.y+view.y+screen.y;
                break;
            case CoordinateDirection.POINT5:
                center.x = wm.x+screen.x+view.x;
                center.y = wm.y+screen.y +view.y;
                break;
            case CoordinateDirection.POINT6:
                center.x = wm.x+screen.x+view.x;
                screen.y = wm.y+center.y-view.y/2;
                break;
            case CoordinateDirection.POINT7:
                center.x = wm.x+screen.x+view.x;
                screen.y = wm.y+center.y;
                break;
            case CoordinateDirection.POINT8:
                screen.x = wm.x+center.x-view.x/2;
                screen.y = wm.y+center.y;
                break;
            case CoordinateDirection.POINT9:
                screen.x = wm.x+center.x-view.x/2;
                screen.y = wm.y+center.y-view.y/2;
                break;
        }
        screen.x = screen.x/densityScale;
        screen.y = screen.y/densityScale;
        return  screen;
    }
}
