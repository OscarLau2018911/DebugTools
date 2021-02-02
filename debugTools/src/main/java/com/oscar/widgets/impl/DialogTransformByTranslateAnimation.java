package com.oscar.widgets.impl;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.oscar.widgets.I.IDialogTransformAnimation;
import com.oscar.widgets.abs.AbsDialogActionAdapter;
import com.oscar.widgets.floatDialog.abs.AbsFloatDialogWithSwitch;
import com.oscar.widgets.utils.CoordinateHelper;

import java.util.Observable;
import java.util.Observer;

public class DialogTransformByTranslateAnimation implements IDialogTransformAnimation, Observer {
    private static final String TAG = "coordinate";
    private WindowManager.LayoutParams toLp;
    private AbsFloatDialogWithSwitch from;
    private AbsFloatDialogWithSwitch to;
    //from的screen绝对坐标
    private int[] fromP;
    //to位于from位置的wm坐标系坐标
    Point wm_for_to_at_from;
    /*终点与起点的坐标差*/
    private Point toPReal;
    private Animation.AnimationListener animationListener;

    @Override
    public void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b) {

    }

    @Override
    public void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b, AbsDialogActionAdapter adapter) {

    }

    /**
     * 从A--->B，500ms的过程中，A的位置跟大小进行变化，B的透明度增加，A的透明度降低，动画完成之后，恢复数据
     * 获取两个dialog的绝对位置，之后根据A的gravity计算A的相对位移，结合A B的透明度形成动画
     *
     * @param a
     * @param b
     */
    @Override
    public void animateFromAtoB(AbsFloatDialogWithSwitch a, AbsFloatDialogWithSwitch b, AbsDialogActionAdapter adapter,Animation.AnimationListener animationListener) {
        setFrom(a);
        setTo(b);
        setAnimationListener(animationListener);
        //检测窗口创建，不然直接获取属性会有问题
        getTo().addAttachObserver(DialogTransformByTranslateAnimation.this);
        //如果to窗口从来没创建过，将无法直接使用，所以先偷偷摸摸创建下，之后再做位移动画
   //     this.to.setAlpha(0);
        getTo().getLayoutParams().alpha = 0;
        getTo().showMe(adapter);
        setFromP(new int[2]);
        setToPReal(new Point());
        setWm_for_to_at_from(new Point());
        setToLp(new WindowManager.LayoutParams());
    }

    @Override
    public void update(Observable o, Object arg) {
        getTo().removeAttachObserver(DialogTransformByTranslateAnimation.this);
        //to页面已经加载好，但是alpha是0，先获取to位于wm坐标系中的位置
        getToLp().copyFrom(getTo().getLayoutParams());
        //获取from屏幕绝对位置
        getFrom().getLocationOnScreen(getFromP());
        //计算to在from位置处于wm坐标系中的位置
        Point from_screen = new Point(getFromP()[0],getFromP()[1]);
        setWm_for_to_at_from(CoordinateHelper.getWmCoordinateByScreen(this.to.getCoordinateDirection(),from_screen,new Point(to.getWidth(),to.getHeight())));
        //让to在from位置显示，并且alpha设置为1可见
        getTo().getLayoutParams().alpha = 1;
        getTo().getLayoutParams().x = getWm_for_to_at_from().x;
        getTo().getLayoutParams().y = getWm_for_to_at_from().y;
        getTo().setLayoutParams(getTo().getLayoutParams());
        //计算从from位置到to位置的坐标差
        getToPReal().x = getToLp().x-getWm_for_to_at_from().x;
        getToPReal().y = getToLp().y-getWm_for_to_at_from().y;
        /**
         * 1000ms内从百分之0线性增加到百分之百，相应计算对应的x，y，透明度及窗口大小形成动画
         */
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                getTo().getLayoutParams().x = (int) (getWm_for_to_at_from().x+getToPReal().x*percent);
                getTo().getLayoutParams().y = (int) (getWm_for_to_at_from().y+getToPReal().y*percent);
                getTo().setLayoutParams(getTo().getLayoutParams());
                if (percent == 1)
                {
                    if (getAnimationListener() != null)
                        getAnimationListener().onAnimationEnd(null);
                }
            }
        });
        if (getAnimationListener() != null) {
            getAnimationListener().onAnimationStart(null);
        }
        animator.start();
    }

    public WindowManager.LayoutParams getToLp() {
        return toLp;
    }

    public void setToLp(WindowManager.LayoutParams toLp) {
        this.toLp = toLp;
    }

    public AbsFloatDialogWithSwitch getFrom() {
        return from;
    }

    public void setFrom(AbsFloatDialogWithSwitch from) {
        this.from = from;
    }

    public AbsFloatDialogWithSwitch getTo() {
        return to;
    }

    public void setTo(AbsFloatDialogWithSwitch to) {
        this.to = to;
    }

    public int[] getFromP() {
        return fromP;
    }

    public void setFromP(int[] fromP) {
        this.fromP = fromP;
    }

    public Point getWm_for_to_at_from() {
        return wm_for_to_at_from;
    }

    public void setWm_for_to_at_from(Point wm_for_to_at_from) {
        this.wm_for_to_at_from = wm_for_to_at_from;
    }

    public Point getToPReal() {
        return toPReal;
    }

    public void setToPReal(Point toPReal) {
        this.toPReal = toPReal;
    }

    public Animation.AnimationListener getAnimationListener() {
        return animationListener;
    }

    public void setAnimationListener(Animation.AnimationListener animationListener) {
        this.animationListener = animationListener;
    }
}
