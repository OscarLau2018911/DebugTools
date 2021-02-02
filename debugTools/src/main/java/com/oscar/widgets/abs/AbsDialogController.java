package com.oscar.widgets.abs;

import com.oscar.widgets.I.IDialogActionListener;
import com.oscar.widgets.I.IDialogControllerSelfDismissListener;
import com.oscar.widgets.I.IDialogTransformAnimation;

public abstract class AbsDialogController implements IDialogActionListener {
    private IDialogControllerSelfDismissListener dialogControllerSelfDismissListener;
    public abstract void run();
    public abstract void stop();
    protected void selfDismiss() {
        if (dialogControllerSelfDismissListener != null) {
            dialogControllerSelfDismissListener.onDialogControllerDismiss();
        }
    }

    public void setDialogControllerSelfDismissListener(IDialogControllerSelfDismissListener dialogControllerSelfDismissListener) {
        this.dialogControllerSelfDismissListener = dialogControllerSelfDismissListener;
    }
}
