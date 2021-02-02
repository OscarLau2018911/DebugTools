package com.oscar.widgets.animation;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;
import com.oscar.widgets.utils.CoordinateHelper;

public class DialogAnimationFactory {
    /**
     *
     * @param dialog  待移动的dialog
     * @param A    位置A
     * @param isScreenA  位置A的坐标是screen绝对坐标还是wm相对坐标
     * @param B    位置B
     * @param isScreenB  位置B的坐标是screen绝对坐标还是wm相对坐标
     * @param duration   动画时长
     * @param listener   监听回调，但是只能监听结束事件
     * @return
     */
    public static ValueAnimator getAnimationTranslateFromAToB(final AbsFloatDialog dialog, final Point A, boolean isScreenA, Point B, boolean isScreenB, long duration, final Animation.AnimationListener listener) {
        Point fromA;
        Point toB;
        final Point toPReal;
        //先计算wm坐标
        if (isScreenA) {
            fromA = CoordinateHelper.getWmCoordinateByScreen(dialog.getCoordinateDirection(), A, new Point(dialog.getWidth(), dialog.getHeight()));
        } else {
            fromA = A;
        }
        if (isScreenB) {
            toB = CoordinateHelper.getWmCoordinateByScreen(dialog.getCoordinateDirection(), B, new Point(dialog.getWidth(), dialog.getHeight()));
        } else{
            toB = B;
        }
        toPReal = new Point(toB.x-fromA.x,toB.y-fromA.y);
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                dialog.getLayoutParams().x = (int) (A.x+toPReal.x*percent);
                dialog.getLayoutParams().y = (int) (A.y+toPReal.y*percent);
                dialog.setLayoutParams(dialog.getLayoutParams());
                if (percent == 1) {
                    if (listener != null) {
                        listener.onAnimationEnd(null);
                    }
                }
            }
        });
        return animator;
    }


}
