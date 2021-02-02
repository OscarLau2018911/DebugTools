package com.oscar.debugtools.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.oscar.debugtools.R;
import com.oscar.widgets.floatDialog.I.IFloatDialogTransform;
import com.oscar.widgets.floatDialog.abs.AbsFloatDialog;

import java.util.List;

/**
 * author:liudeyu on 2021/2/1
 */
public class DebugWarnningDialog extends AbsFloatDialog implements View.OnClickListener {
    private TextView warnningMsg;
    private WarnningDialogHandler handler;
    private List<Object> debugData;
    private DebugDataListDialog listData;
    class WarnningDialogHandler extends Handler {
        public WarnningDialogHandler(Looper looper){
            super(looper);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                setWarnningMsg((String) msg.obj);
            }else if (msg.what == 2){
                listData.bindData(debugData);
            }
        }
    }

    public DebugWarnningDialog(@NonNull Context context) {
        super(context, R.layout.layout_debug_warnning_dialog, Gravity.BOTTOM|Gravity.LEFT,null);
        warnningMsg = findViewById(R.id.tv_warnning_msg);
        warnningMsg.setTextColor(Color.RED);
        setOnClickListener(this);
        warnningMsg.setOnClickListener(this);
        if (handler == null){
            handler = new WarnningDialogHandler(context.getMainLooper());
        }
    }

    public void bindData(List<Object> data){
        this.debugData = data;
        if (debugData != null && debugData.size() > 0){
            sendMsg("发现"+data.size()+"处调试代码,记得删除!!!");
            show();
        }
    }

    private void sendMsg(String msg){
        Message message = new Message();
        message.what = 1;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void setWarnningMsg(String msg){
        warnningMsg.setText(msg);
    }

    @Override
    public void onClick(View v) {
        if (debugData != null && debugData.size() > 0){
            if (listData == null){
                listData = new DebugDataListDialog(getContex());
            }
            handler.sendEmptyMessage(2);
        }
    }
}
