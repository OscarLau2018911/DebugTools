package com.oscar.debugtools.impl;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.oscar.debugtools.R;
import com.oscar.debugtools.adapter.ListViewAdapter;
import com.oscar.debugtools.transform.FullScreenTransform;
import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;
import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;
import com.oscar.widgets.utils.AppUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author:liudeyu on 2021/2/2
 */
public class DebugDataListDialog extends AbsFloatDialog {
    private ListView lv_class;
    private ListView lv_method;
    private ListView lv_field;
    private Button btn_close;
    protected DebugDataListDialog(@NonNull final Context context) {
        super(context, R.layout.layout_debug_data_list_dialog, Gravity.CENTER, null);
        lv_class = findViewById(R.id.lv_debug_data_for_class);
        lv_method = findViewById(R.id.lv_debug_data_for_method);
        lv_field = findViewById(R.id.lv_debug_data_for_field);
        btn_close = findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void bindData(List<Object> debugData){
        if (debugData != null && debugData.size() > 0){
            show();
            //切换全屏
            gotoTransform(new FullScreenTransform(getContex()));
            List<Object> classList = new ArrayList<>();
            List<Object> methodList = new ArrayList<>();
            List<Object> fieldList = new ArrayList<>();

            for (Object obj:
                 debugData) {
                if (obj instanceof Class){
                    classList.add(obj);
                }else if (obj instanceof Method){
                    methodList.add(obj);
                }else if (obj instanceof Field){
                    fieldList.add(obj);
                }
            }
            ListViewAdapter classAdapter = new ListViewAdapter(getContex(),classList);
            ListViewAdapter methodAdapter = new ListViewAdapter(getContex(),methodList);
            ListViewAdapter fieldAdapter = new ListViewAdapter(getContex(),fieldList);
            lv_class.setAdapter(classAdapter);
            lv_method.setAdapter(methodAdapter);
            lv_field.setAdapter(fieldAdapter);
        }
    }
}
