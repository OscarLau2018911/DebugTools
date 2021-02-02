package com.oscar.debugtools.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.oscar.debugtools.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * author:liudeyu on 2021/2/2
 */
public class ListViewAdapter implements ListAdapter {
    private List<Object> data;
    private Context mContext;

    public ListViewAdapter(Context context,List<Object> data){
        this.mContext = context;
        this.data = data;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return data != null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data != null?data.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_listview_item, null);
        }
        TextView tv_item  = convertView.findViewById(R.id.tv_item);
        Object obj = data.get(position);
        String msg = "";
        if (obj instanceof Class){
            msg = ((Class)obj).getName();
        }else if (obj instanceof Method){
            msg = ((Method)obj).getDeclaringClass().getName()+"->"+((Method)obj).getName();
        }else if (obj instanceof Field){
            msg = ((Field)obj).getDeclaringClass().getName()+"->"+((Field)obj).getName();
        }
        tv_item.setText(msg);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
