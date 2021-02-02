package com.oscar.widgets.floatDialog.I;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public interface IFloatDialogTransform {
    /**
     * AbsFloatDialog变形类
     * @param dialog 变形目标
     * @param root   AbsFloatDialog根view，其实与第一个参数等价，可以强转
     * @param main   mainView对象，就是layoutId持有的view对象
     * @param wm     window manager
     * @param lp     window manager的layoutparams
     */
    void transform(Context dialog, View root,View main,WindowManager wm, WindowManager.LayoutParams lp);
}
