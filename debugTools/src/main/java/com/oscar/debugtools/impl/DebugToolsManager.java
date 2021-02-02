package com.oscar.debugtools.impl;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.oscar.debugtools.I.IDebugTools;
import com.oscar.debugtools.I.IDebugToolsAnalyst;
import com.oscar.debugtools.adapter.AnalyseAdapter;

import java.lang.reflect.Method;
import java.util.List;

import static com.oscar.debugtools.impl.DebugToolsAnalyst.ANALYSE_BY_ANNOTATION;

/**
 * author:liudeyu on 2021/2/1
 */
public class DebugToolsManager implements IDebugTools {
    private AnalyseAdapter adapter;
    private static DebugWarnningDialog dialog;
    private static DebugToolsManager instance;

    private DebugToolsManager(){

    }

    public static DebugToolsManager getInstance(){
        if (instance == null){
            instance = new DebugToolsManager();
        }
        return instance;
    }

    @Override
    public void setAdapter(AnalyseAdapter adap) {
        this.adapter = adap;
    }

    @Override
    public void show() {
        if (dialog == null){
            dialog = new DebugWarnningDialog(this.adapter.getContext());
        }
        if (dialog != null){
            dialog.show();
        }
        if (this.adapter != null && this.adapter.getAnalyst() != null){
            this.adapter.getAnalyst().setAdapter(adapter);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //暂时只支持ANALYSE_BY_ANNOTATION模式
                    dialog.bindData(adapter.getAnalyst().getAnnotationApi());
                }
            }).start();
        }
    }

    @Override
    public void dismiss() {
        if (dialog != null){
            dialog.dismiss();
        }
    }
}
