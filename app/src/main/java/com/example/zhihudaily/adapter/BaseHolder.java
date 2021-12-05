package com.example.zhihudaily.adapter;

import android.view.View;


abstract public class BaseHolder<T> {
    public View view;
    protected T datas;

    protected BaseHolder(T t){
        datas = t;
    }

    //加载界面并初始化
    protected abstract void initView();
    //刷新界面，设置holder内容
    protected abstract void refreshView(int pos);

    public View getContentView(){
        return view;
    }

}
