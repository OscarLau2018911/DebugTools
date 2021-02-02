package com.oscar.debugtools.I;

import com.oscar.debugtools.adapter.AnalyseAdapter;

import java.lang.reflect.Method;
import java.util.List;

/**
 * author:liudeyu on 2021/2/1
 */
public interface IDebugToolsAnalyst {
    void setAdapter(AnalyseAdapter adapter);
    long analyseAnnotationNumber();//分析使用注解的接口数量
    List<Object> getAnnotationApi();//获取使用注解的列表
}
