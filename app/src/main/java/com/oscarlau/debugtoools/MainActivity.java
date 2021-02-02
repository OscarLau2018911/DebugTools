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
                        .setScanPreFix("com.oscarlau")
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