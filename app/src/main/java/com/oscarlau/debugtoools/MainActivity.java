package com.oscarlau.debugtoools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oscar.debugtools.adapter.AnalyseAdapter;
import com.oscar.debugtools.annotation.DebugWarnning;
import com.oscar.debugtools.impl.DebugToolsAnalyst;
import com.oscar.debugtools.impl.DebugToolsManager;

@DebugWarnning
public class MainActivity extends AppCompatActivity {
    @DebugWarnning
    private int testCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DebugToolsManager.getInstance().setAdapter(
                new AnalyseAdapter()
                        .setContext(this)
                        .setScanPath("/sdcard/aura/lib/") //此处为扫描目录，插件调试一般使用aura目录，不设置为apk目录
                        .setScanPreFix("com.jd.lib")  //扫描关键字，提升扫描效率
                        .setAnalyst(new DebugToolsAnalyst()));
        DebugToolsManager.getInstance().show();
    }

    @DebugWarnning
    private void testCase1(){

    }
    @DebugWarnning
    private void testCase2(){

    }
    @DebugWarnning
    private void testCase3(){

    }
    @DebugWarnning
    private void testCase4(){

    }
    @DebugWarnning
    private void testCase5(){

    }
    @DebugWarnning
    private void testCase6(){

    }

}