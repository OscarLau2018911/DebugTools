package com.oscar.debugtools.adapter;

import android.content.Context;

import com.oscar.debugtools.I.IDebugToolsAnalyst;

import static com.oscar.debugtools.impl.DebugToolsAnalyst.ANALYSE_BY_ANNOTATION;

/**
 * author:liudeyu on 2021/2/1
 */
public class AnalyseAdapter {
    private Context mContext;
    private int analyseMode = ANALYSE_BY_ANNOTATION;
    private IDebugToolsAnalyst analyst;
    private String scanPath;//因为存在热加载的情况，所以需要设置扫描位置
    private String scanPreFix;//扫描的前缀，默认为包名，需要自定义

    public int getAnalyseMode() {
        return analyseMode;
    }

    public AnalyseAdapter setAnalyseMode(int analyseMode) {
        this.analyseMode = analyseMode;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public AnalyseAdapter setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public IDebugToolsAnalyst getAnalyst() {
        return analyst;
    }

    public AnalyseAdapter setAnalyst(IDebugToolsAnalyst analyst) {
        this.analyst = analyst;
        return this;
    }

    public String getScanPreFix() {
        return scanPreFix;
    }

    public AnalyseAdapter setScanPreFix(String scanPreFix) {
        this.scanPreFix = scanPreFix;
        return this;
    }

    public String getScanPath() {
        return scanPath;
    }

    public AnalyseAdapter setScanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }
}
