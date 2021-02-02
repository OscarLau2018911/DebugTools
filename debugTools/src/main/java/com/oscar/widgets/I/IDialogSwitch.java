package com.oscar.widgets.I;

import android.graphics.Point;

import com.oscar.widgets.abs.AbsDialogActionAdapter;

import java.io.PipedOutputStream;

public interface IDialogSwitch {
    /**
     * 获取dialog别民
     * @return
     */
    String getSwitchName();

    /**
     * 显示当前switch dialog
     * @param adapter  窗口间的数据传递对象
     */
    void showMe(AbsDialogActionAdapter adapter);
}
