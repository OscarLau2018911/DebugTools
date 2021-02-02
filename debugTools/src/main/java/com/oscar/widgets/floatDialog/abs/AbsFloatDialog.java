package com.oscar.widgets.floatDialog.abs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Region;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.oscar.widgets.I.IFloatDialogAnimation;
import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;
import com.oscar.widgets.floatDialog.manager.DialogManager;
import com.oscar.widgets.impl.FloatDialogScrollListenerImpl;
import com.oscar.widgets.model.CoordinateDirection;
import com.oscar.widgets.utils.AppUtils;
import com.oscar.widgets.utils.ClickUtils;
import com.oscar.widgets.utils.CoordinateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import butterknife.ButterKnife;

/**
 * jd定制悬浮框基类
 * auth:liudeyu
 */
public abstract class AbsFloatDialog extends  LinearLayout{
    private static final String TAG = AbsFloatDialog.class.getName();
    /**
     * 上下文对象
     */
    private Context iContex;
    /**
     * window manager
     */
    private WindowManager wm;
    /**
     * wm的参数类
     */
    private WindowManager.LayoutParams layoutParams;
    /**
     * 此悬浮框分为三层框架，最下层为wm分配的view，之后是AbsFloatDialog层，最上层是布局layout层，也就是mainView对象持有的对象view
     */
    private View mainView;
    /**
     * 悬浮框的变形回调，方便窗口在不同的情况下的变形需求
     */
    private IFloatDialogTransform transform;
    /**
     * 是否在wm窗口中显示的标志，false表示未显示，true表示已经绑定到wm中显示
     */
    private boolean attach = false;
    /**
     * 实现触摸操作接口，默认为窗口拖动
     */
    private FloatDialogScrollListenerImpl touchMove = null;
    /**
     * 点击判断辅助类
     */
    private ClickUtils.Builder builder;
    /**
     * 窗口范围
     */
    private Region region;
    /**
     * 是否支持窗口拖动
     */
    private boolean isTouchMoveAble = true;
    /**
     * 是否支持窗口外点击dismiss
     */
    private boolean isClickOutSideDismiss = false;
    /**
     * 是否支持点击置顶功能
     */
    private boolean isClickOnTop = true;

    /**
     * 窗口attach观察对象
     */
    private List<Observer> attachObserver = new ArrayList<>();
    /**
     * 窗口dettach观察对象
     */
    private List<Observer> dettachObserver = new ArrayList<>();

    /**
     * 窗口attach动画
     */
    private IFloatDialogAnimation attachAnimation;
    /**
     * 窗口dettach动画
     */
    private IFloatDialogAnimation dettachAnimation;
    /**
     * 窗口坐标属性累，包括窗口拖动的上下左右阀值，对应的圆心位置
     */
    private CoordinateDirection coordinateDirection;

    /**
     * 全局AbsFloatDialog堆栈管理类，用来管理窗口置顶等数据
     */
    private DialogManager dialogManager = DialogManager.getInstance();

    /**
     * attch与dettach锁
     */
    private boolean lock = false;
    /**
     *
     * @param context
     * @param layoutId
     * @param transform
     */
    protected AbsFloatDialog(@NonNull Context context,int layoutId,IFloatDialogTransform transform) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        initContext(context,layoutId,Gravity.CENTER,transform);
    }

    /**
     *
     * @param context
     * @param layoutId
     * @param gravity   只支持LEFT RIGHT TOP BOTTOM CENTER
     * @param transform
     */
    protected AbsFloatDialog(@NonNull Context context,int layoutId,int gravity,IFloatDialogTransform transform) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        initContext(context,layoutId,gravity,transform);
    }

    /**
     *
     * @param context
     * @param layoutId
     * @param gravity  只支持LEFT RIGHT TOP BOTTOM CENTER
     * @param lp
     * @param transform
     */
    protected AbsFloatDialog(@NonNull Context context, int layoutId, int gravity, WindowManager.LayoutParams lp,IFloatDialogTransform transform) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        initContext(context,layoutId,gravity,transform);
    }

    private void initContext(Context context, int layoutId,int gravity, IFloatDialogTransform transform) {
        setContex(context);
        setWindowManager((WindowManager) getContex().getSystemService(Context.WINDOW_SERVICE));
        measureRootViewLayoutParam(context,layoutId,gravity);
        if (transform != null) {
            transform.transform(getContex(), getRootView(), getMainView(), getWindowManager(), getLayoutParams());
            updateLayoutParams();
        }
        ButterKnife.bind(getRootView(),getRootView());
    }

    private void measureRootViewLayoutParam(Context context, int layoutId,int gravity) {
        setMainView(LayoutInflater.from(context).inflate(layoutId, (ViewGroup) getRootView(),false));
        addView(getMainView());
        Point lcd = new Point();
        AppUtils.getScreenSize(context,lcd);
        int w = View.MeasureSpec.makeMeasureSpec(lcd.x, MeasureSpec.AT_MOST);
        int h = View.MeasureSpec.makeMeasureSpec(lcd.y, MeasureSpec.AT_MOST);
        measure(w, h);
        setLayoutParams(new WindowManager.LayoutParams());
        initWmLayoutparams(getLayoutParams(),gravity);
        freshCoordinate(context);
    }

    private void initWmLayoutparams(WindowManager.LayoutParams lp,int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        lp.format = PixelFormat.RGBA_8888;
        lp.gravity = gravity;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        lp.width = getMeasuredWidth();
        lp.height = getMeasuredHeight();
    }

    /**
     * 更新坐标数据
     */
    public void freshCoordinate() {
        Point lcd = new Point();
        AppUtils.getScreenSize(getContex(),lcd);
        setCoordinateDirection(CoordinateHelper.getCoordinateDirection(getLayoutParams(),lcd,new Point(getLayoutParams().width,getLayoutParams().height)));
        setTouchMove(new FloatDialogScrollListenerImpl(getRootView(),getWindowManager(),getLayoutParams()));
    }

    /**
     *
     * @param context
     */
    public void freshCoordinate(Context context) {
        Point lcd = new Point();
        AppUtils.getScreenSize(context,lcd);
        setCoordinateDirection(CoordinateHelper.getCoordinateDirection(getLayoutParams(),lcd,new Point(getLayoutParams().width,getLayoutParams().height)));
        setTouchMove(new FloatDialogScrollListenerImpl(getRootView(),getWindowManager(),getLayoutParams()));
    }

    public void restoreTransform() {
        if (getTransform() != null) {
            getTransform().transform(getContex(), getRootView(), getMainView(), getWindowManager(), getLayoutParams());
        }
        updateLayoutParams();
    }
    public void gotoTransform(IFloatDialogTransform transform) {
        transform.transform(getContex(),getRootView(),getMainView(),getWindowManager(),getLayoutParams());
        updateLayoutParams();
    }

    public void show() {
        freshCoordinate();
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        attachToWm();
    }
    /**
     * 显示在屏幕绝对坐标系位置
     * @param screen
     */
    public void showAtScreen(Point screen) {
        //防止在dismiss的状态下旋转屏幕，导致坐标异常
        freshCoordinate();
        Point wm = CoordinateHelper.getWmCoordinateByScreen(getCoordinateDirection(), screen, new Point(getLayoutParams().width, getLayoutParams().height));
        getLayoutParams().x = wm.x;
        getLayoutParams().y = wm.y;
        if (!isShowing()) {
            show();
        } else {
            updateLayoutParams();
        }
    }

    /**
     * 显示在windowmanager.layoutparam坐标系位置layoutParams
     * @param screen
     */
    public void showAtWm(Point screen) {
        freshCoordinate();
        getLayoutParams().x = screen.x;
        getLayoutParams().y = screen.y;
        if (!isShowing()) {
            show();
        } else {
            updateLayoutParams();
        }
    }

    public void dismiss() {
        dettachFromWm();
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    private void broadcastAttachObserver() {
        for (int i = 0;i<getAttachObserver().size();i++) {
            getAttachObserver().get(i).update(null, isShowing());
        }
    }
    private void broadcastDettachObserver() {
        for (int i = 0;i<getDettachObserver().size();i++) {
            getDettachObserver().get(i).update(null, isShowing());
        }
    }
    private void attachToWm() {
        if (!isAttach() && !isLock()) {
            setLock(true);
            getWindowManager().addView(getRootView(), getLayoutParams());
            setAttach(true);
            if (getAttachAnimation() != null) {
                getAttachAnimation().animate((AbsFloatDialog) getRootView(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        getDialogManager().push((AbsFloatDialog) getRootView());
                        freshCoordinate();
                        registerConfigChangeReceiver(getContex());
                        setLock(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                getDialogManager().push((AbsFloatDialog) getRootView());
                freshCoordinate();
                registerConfigChangeReceiver(getContex());
                setLock(false);
            }
        }
        getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                broadcastAttachObserver();
            }
        });
    }

    private void dettachFromWm() {
        if (isAttach() && !isLock()) {
            setLock(true);
            if (getDettachAnimation() != null) {
                getDettachAnimation().animate((AbsFloatDialog) getRootView(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        getWindowManager().removeView(getRootView());
                        setAttach(false);
                        getDialogManager().remove((AbsFloatDialog) getRootView());
                        unregisterConfigChangeReceiver(getContex());
                        setLock(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            } else {
                getWindowManager().removeView(getRootView());
                setAttach(false);
                getDialogManager().remove((AbsFloatDialog) getRootView());
                unregisterConfigChangeReceiver(getContex());
                setLock(false);
            }
        }
        broadcastDettachObserver();
    }


    public void fillBackGroud(int color) {
        if (isShown()) {
            fillBackGroud(color, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        }else {
            try {
                throw new Exception("fillBackGroud must be taken after dialog show()!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 支持背景填充
     * @param color 填充颜色
     * @param width 背景宽度，MATCH_PARENT或者具体像素都可以
     * @param height 高度
     * @param gravity 主view相对于背景的对齐方式
     */
    public void fillBackGroud(int color, int width, int height, int gravity) {
        if (isShown()) {
            getLayoutParams().width = width;
            getLayoutParams().height = height;
            setLayoutParams(new LayoutParams(width, height));
            setBackgroundColor(color);
            setGravity(gravity);
            updateLayoutParams();
        } else {
            try {
                throw new Exception("fillBackGroud must be taken after dialog show()!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //创建核心view的判断区域，收到的任何事件，先此层处理，处理完了之后酌情给到上层
        if (getRegion() == null) {
            setRegion(new Region(getRootView().getLeft(), getRootView().getTop(), getRootView().getRight(), getRootView().getBottom()));
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                builder = new ClickUtils.Builder();
                builder.setStartTime(System.currentTimeMillis());
                builder.setStartP(new Point((int)event.getRawX(),(int)event.getRawY()));
                if (isTouchMoveAble() && getTouchMove() != null) {
                    return getTouchMove().onTouch(getRootView(), event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchMoveAble() && getTouchMove() != null) {
                    return getTouchMove().onTouch(getRootView(), event);
                }
                break;
            case MotionEvent.ACTION_UP:
                builder.setEndTime(System.currentTimeMillis());
                builder.setEndP(new Point((int)event.getRawX(),(int)event.getRawY()));
                if (builder.create().isClick()) {
                    if (!getRegion().contains((int)event.getX(),(int)event.getY())) {
//                        if (isClickOutSideDismiss()) {
//                            dismiss();
//                            return true;
//                        }
                    }
                    if (isClickOnTop()) {
                        showOnTop();
                        return true;
                    }
                }
                if (isTouchMoveAble() && getTouchMove() != null) {
                    return getTouchMove().onTouch(getRootView(), event);
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
                if (isClickOutSideDismiss()) {
                    dismiss();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void showOnTop() {
        if (!getDialogManager().isTop((AbsFloatDialog) getRootView())) {
            dettachFromWm();
            attachToWm();
        }
    }


    private void updateLayoutParams() {
        if (isAttach()) {
            getWindowManager().updateViewLayout(getRootView(), getLayoutParams());
            freshCoordinate();
            setRegion(null);
        }
    }
    private void registerConfigChangeReceiver(Context context){
        IntentFilter configChangeFilter = new IntentFilter();
        configChangeFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        context.registerReceiver(mConfigChangeReceiver, configChangeFilter);
    }

    private void unregisterConfigChangeReceiver(Context context){
        context.unregisterReceiver(mConfigChangeReceiver);
    }

    private BroadcastReceiver mConfigChangeReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            freshCoordinate();
        }
    };

    public Context getContex() {
        return this.iContex;
    }

    private void setContex(Context contex) {
        this.iContex = contex;
    }


    public IFloatDialogTransform getTransform() {
        return this.transform;
    }

    public void setTransform(IFloatDialogTransform transform) {
        this.transform = transform;
    }

    public FloatDialogScrollListenerImpl getTouchMove() {
        return this.touchMove;
    }

    public void setTouchMove(FloatDialogScrollListenerImpl touchMove) {
        this.touchMove = touchMove;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        return this.layoutParams;
    }

    public void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        updateLayoutParams();
    }

    public boolean isTouchMoveAble() {
        return this.isTouchMoveAble;
    }

    public void setTouchMoveAble(boolean move) {
        this.isTouchMoveAble = move;
    }

    private void setAttach(boolean flag) {
        this.attach = flag;
    }
    private boolean isAttach() {
        return this.attach;
    }
    public boolean isShowing() {
        return this.attach;
    }
    public View getRootView() {
        return this;
    }

    public View getMainView() {
        return this.mainView;
    }

    private void setMainView(View mainView) {
        this.mainView = mainView;
    }

    public void addAttachObserver(Observer observer) {
        this.attachObserver.add(observer);
    }
    public void removeAttachObserver(Observer observer) {
        this.attachObserver.remove(observer);
    }

    public void addDettachObserver(Observer observer) {
        this.dettachObserver.add(observer);
    }
    public void removeDettachObserver(Observer observer) {
        this.dettachObserver.remove(observer);
    }

    public void setAttachAnimation(IFloatDialogAnimation attachAnimation) {
        this.attachAnimation = attachAnimation;
    }

    public void setDettachAnimation(IFloatDialogAnimation dettachAnimation) {
        this.dettachAnimation = dettachAnimation;
    }

    public IFloatDialogAnimation getAttachAnimation() {
        return this.attachAnimation;
    }

    public IFloatDialogAnimation getDettachAnimation() {
        return this.dettachAnimation;
    }

    public CoordinateDirection getCoordinateDirection() {
        return this.coordinateDirection;
    }
    public void setCoordinateDirection(CoordinateDirection coordinateDirection) {
        this.coordinateDirection = coordinateDirection;
    }
    public boolean isClickOnTop() {
        return this.isClickOnTop;
    }

    public void setClickOnTop(boolean clickOnTop) {
        this.isClickOnTop = clickOnTop;
    }
    public List<Observer> getAttachObserver() {
        return this.attachObserver;
    }

    public void setAttachObserver(List<Observer> attachObserver) {
        this.attachObserver = attachObserver;
    }

    public List<Observer> getDettachObserver() {
        return this.dettachObserver;
    }

    public void setDettachObserver(List<Observer> dettachObserver) {
        this.dettachObserver = dettachObserver;
    }

    public DialogManager getDialogManager() {
        return this.dialogManager;
    }

    public void setDialogManager(DialogManager dialogManager) {
        this.dialogManager = dialogManager;
    }

    public WindowManager getWindowManager() {
        return this.wm;
    }

    private void setWindowManager(WindowManager wm) {
        this.wm = wm;
    }

    private Region getRegion() {
        return this.region;
    }

    private void setRegion(Region region) {
        this.region = region;
    }


    public void setClickOutSideDismissAble(boolean flag) {
        this.isClickOutSideDismiss = flag;
    }
    public boolean isClickOutSideDismiss() {
        return this.isClickOutSideDismiss;
    }

    private boolean isLock() {
        return lock;
    }

    private void setLock(boolean lock) {
        this.lock = lock;
    }
}
