package com.oscar.debugtools.transform;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;
import com.oscar.widgets.utils.AppUtils;

/**
 * author:liudeyu on 2021/2/2
 */
public class FullScreenTransform implements IFloatDialogTransform {
    private Context mContext;
    public FullScreenTransform(Context context){
        this.mContext = context;
    }
    @Override
    public void transform(Context dialog, View root, View main, WindowManager wm, WindowManager.LayoutParams lp) {
        Point lcd = new Point();
        AppUtils.getScreenSize(mContext,lcd);
        lp.width = lcd.x;
        lp.height = lcd.y;
    }
}
