package com.example.zhihudaily;

import android.view.View;

import com.example.zhihudaily.bean.CommentsInfo;

public interface CommentPagerListener {
    void onCommentItemClick(View v, CommentsInfo.Comment comment);
}