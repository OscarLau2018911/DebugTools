package com.oscar.debugtools.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.textservice.TextInfo;

import com.oscar.debugtools.I.IDebugToolsAnalyst;
import com.oscar.debugtools.adapter.AnalyseAdapter;
import com.oscar.debugtools.annotation.DebugWarnning;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * author:liudeyu on 2021/2/1
 */
public class DebugToolsAnalyst implements IDebugToolsAnalyst {
    public static final int ANALYSE_BY_ANNOTATION = 0;  //计算注解函数的数量，每一处调试代码的地方都注解
    public static final int ANALYSE_BY_ANNOTAION_API = 1;//计算注解函数api使用的数量，适用于统一管理一个调试类
    private AnalyseAdapter adapter;

    public DebugToolsAnalyst(){
    }
    @Override
    public void setAdapter(AnalyseAdapter adapter){
        this.adapter = adapter;
    }
    @Override
    public long analyseAnnotationNumber() {
        long num = 0;
        try {
            List<Object> anObjs = scanPackageAnnotation();
            num = anObjs.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public List<Object> getAnnotationApi() {
        try {
            return scanPackageAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //扫描包中包含所有注解的字段，类， 方法
    private List<Object> scanPackageAnnotation() throws Exception{
        if (adapter == null || adapter.getContext() == null){
            throw new Exception("no adapter | context has been set!");
        }
        List<Object> annotationBeans = new ArrayList<>();
        String className = "";
        Class clazz = null;
        Field[] allFields = null;
        Method[] allMethods = null;
        List<DexFile> dexFiles = getDexFilesFromPath(adapter.getScanPath());
        for (DexFile df:
             dexFiles) {
            //获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            Enumeration<String> enumeration = df.entries();
            while (enumeration.hasMoreElements()) {
                className = (String) enumeration.nextElement();
                if (className != null) {
                    //如果扫描前缀不为空，则过滤不匹配的名称
                    if (!TextUtils.isEmpty(adapter.getScanPreFix()) && !className.contains(adapter.getScanPreFix())){
                        continue;
                    }
                    Log.d("DebugTools","扫描"+className);
                    clazz = Class.forName(className);
                    clazz.getFields();
                    if (clazz.isAnnotationPresent(DebugWarnning.class)){
                        Log.d("DebugTools","扫描到使用DebugWarnning注解的class:"+clazz.getName());
                        annotationBeans.add(clazz);
                    }
//                    clazz.getDeclaredFields()
                    allFields = clazz.getDeclaredFields();
                    allMethods = clazz.getDeclaredMethods();
                    for (Field field:
                            allFields) {
                        if (field.isAnnotationPresent(DebugWarnning.class)){
                            Log.d("DebugTools","扫描到使用DebugWarnning注解的field:"+field.getName());
                            annotationBeans.add(field);
                        }
                    }
                    for (Method method : allMethods) {
                        if (method.isAnnotationPresent(DebugWarnning.class)) {
                            Log.d("DebugTools","扫描到使用DebugWarnning注解的method:"+method.getName());
                            annotationBeans.add(method);
                        }
                    }
                }
            }
            df.close();
        }
        return annotationBeans;

    }

    private List<DexFile> getDexFilesFromPath(String path) throws IOException {
        List<DexFile> dexFiles = new ArrayList<>();
        //通过DexFile查找当前的APK中可执行文件
        if (TextUtils.isEmpty(path)) {
            dexFiles.add(new DexFile(adapter.getContext().getPackageCodePath()));
        }else{
            File dir = new File(path);
            if (dir.isDirectory()){
                File[] files = dir.listFiles();
                for (File file:
                        files) {
                    dexFiles.add(new DexFile(file));
                }
            }else{
                dexFiles.add(new DexFile(dir));
            }
        }
        return dexFiles;
    }
}
