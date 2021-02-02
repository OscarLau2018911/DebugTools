package com.oscar.widgets.I;

import com.oscar.widgets.abs.AbsDialogActionAdapter;

public interface IDialogActionListener {
    /**
     * dialog执行动作接口，此接口用于所有dialog之间传递数据
     * @param adapter
     */
    void action(AbsDialogActionAdapter adapter);
}
