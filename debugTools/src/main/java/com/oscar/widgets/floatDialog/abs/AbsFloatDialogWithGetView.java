package com.oscar.widgets.floatDialog.abs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.oscar.widgets.I.IDialogGetView;
import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;

public abstract class AbsFloatDialogWithGetView extends AbsFloatDialog implements IDialogGetView {

    protected AbsFloatDialogWithGetView(@NonNull Context context, int layoutId, IFloatDialogTransform transform) {
        super(context,layoutId,transform);
    }
    protected AbsFloatDialogWithGetView(@NonNull Context context,int layoutId,int gravity,IFloatDialogTransform transform) {
        super(context,layoutId,gravity,transform);
    }
    protected AbsFloatDialogWithGetView(@NonNull Context context, int layoutId, int gravity, WindowManager.LayoutParams lp,IFloatDialogTransform transform) {
        super(context,layoutId,gravity,lp,transform);
    }
    @Override
    public Bitmap getDialogView() {
        return getViewBitmap(getRootView());
    }
    protected Bitmap getViewBitmap(View view) {
        Bitmap root = Bitmap.createBitmap(getLayoutParams().width,getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(root);
        draw(canvas);
        return root;
    }
}
