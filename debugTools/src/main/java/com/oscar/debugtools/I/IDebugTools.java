package com.oscar.debugtools.I;

import com.oscar.debugtools.adapter.AnalyseAdapter;

/**
 * author:liudeyu on 2021/2/1
 */
public interface IDebugTools {
    void setAdapter(AnalyseAdapter adapter);
    void show();
    void dismiss();
}
