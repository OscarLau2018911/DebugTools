package com.oscar.widgets.I;

import android.view.animation.Animation;

import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;

public interface IFloatDialogAnimation {
    /**
     * AbsFloatDialog的动画对象，attach或者dettach之类的时候可以定制动画
     * @param dialog
     */
    void animate(AbsFloatDialog dialog, Animation.AnimationListener animationListener);
}
