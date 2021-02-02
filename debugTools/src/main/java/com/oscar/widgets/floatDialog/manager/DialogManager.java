package com.oscar.widgets.floatDialog.manager;


import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;

import java.util.Stack;

public class DialogManager {
    private static DialogManager instance = null;
    private Stack<AbsFloatDialog> dialogs;
    private DialogManager() {
        setDialogs(new Stack<AbsFloatDialog>());
    }
    public static DialogManager getInstance() {
        if (instance == null) {
            instance = new DialogManager();
        }
        return instance;
    }

    public boolean isTop(AbsFloatDialog floatDialog) {
        try {
            AbsFloatDialog top = getDialogs().lastElement();
            if (floatDialog.hashCode() == top.hashCode()) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e) {
            return false;
        }
    }

    public void remove(AbsFloatDialog floatDialog) {
        if (getDialogs().contains(floatDialog)) {
            getDialogs().remove(floatDialog);
        }
    }

    public void push(AbsFloatDialog floatDialog) {
        getDialogs().push(floatDialog);
    }

    public Stack<AbsFloatDialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Stack<AbsFloatDialog> dialogs) {
        this.dialogs = dialogs;
    }
}
