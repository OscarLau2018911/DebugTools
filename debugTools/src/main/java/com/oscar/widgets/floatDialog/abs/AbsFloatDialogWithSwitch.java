package com.oscar.widgets.floatDialog.abs;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

import com.oscar.widgets.I.IDialogActionListener;
import com.oscar.widgets.I.IDialogSwitch;
import com.oscar.widgets.I.IDialogTransformAnimation;
import com.oscar.widgets.abs.AbsDialogActionAdapter;
import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;

import java.util.Observable;
import java.util.Observer;

public abstract class AbsFloatDialogWithSwitch extends AbsFloatDialogWithGetView implements IDialogSwitch{

    private static final String TAG = AbsFloatDialogWithSwitch.class.getName();
    private AbsDialogActionAdapter adapter;
    private IDialogActionListener listener;

    protected AbsFloatDialogWithSwitch(@NonNull Context context, int layoutId, IFloatDialogTransform transform) {
        super(context, layoutId, transform);
    }

    protected AbsFloatDialogWithSwitch(@NonNull Context context, int layoutId, int gravity, IFloatDialogTransform transform) {
        super(context, layoutId, gravity, transform);
    }

    protected AbsFloatDialogWithSwitch(@NonNull Context context, int layoutId, int gravity, WindowManager.LayoutParams lp, IFloatDialogTransform transform) {
        super(context, layoutId, gravity, lp, transform);
    }

    public IDialogActionListener getActionListener() {
        return listener;
    }

    public AbsFloatDialogWithSwitch setActionListener(IDialogActionListener listener) {
        this.listener = listener;
        return this;
    }

    public AbsDialogActionAdapter getAdapter() {
        return adapter;
    }

    public AbsFloatDialogWithSwitch setAdapter(AbsDialogActionAdapter adapter) {
        this.adapter = adapter;
        return this;
    }
}
