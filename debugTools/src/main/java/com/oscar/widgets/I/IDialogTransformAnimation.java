package com.oscar.widgets.I;


import android.view.animation.Animation;

import com.oscar.widgets.abs.AbsDialogActionAdapter;
import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;
import com.oscar.widgets.floatDialog.abs.AbsFloatDialogWithSwitch;

public interface IDialogTransformAnimation {
    /**
     * 从窗口A到窗口B的动画
     * @param a
     * @param b
     */
    void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b);

    /**
     * 从窗口A到窗口B的动画
     * @param a
     * @param b
     * @param adapter 数据对象
     */
    void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b, AbsDialogActionAdapter adapter);

    /**
     * 从窗口A到窗口B的动画
     * @param a
     * @param b
     * @param adapter  数据对象
     * @param animationListener 动画监听对象
     */
    void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b, AbsDialogActionAdapter adapter, Animation.AnimationListener animationListener);
}
