package com.example.zhihudaily.protocol;

import android.text.format.DateFormat;

import com.example.zhihudaily.Utils.LogUtils;
import com.example.zhihudaily.bean.CommentsInfo;
import com.example.zhihudaily.bean.NewsBarInfo;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommentProtocol extends BaseProtocol<CommentsInfo> {

    private String HOMECACHE;
    private String HOMEURL;

    public CommentProtocol(String id, boolean isGetLongCommentFlag) {
        if (isGetLongCommentFlag) {
            HOMECACHE = "longcomment" + id;
            HOMEURL = "http://news-at.zhihu.com/api/4/story/" + id + "/long-comments";
        } else {
            HOMECACHE = "shortcomment" + id;
            HOMEURL = "http://news-at.zhihu.com/api/4/story/" + id + "/short-comments";
        }
    }

    @Override
    protected CommentsInfo paserJsonData(String json) {
        Gson gson = new Gson();
        CommentsInfo cInfo = gson.fromJson(json, CommentsInfo.class);
        if (cInfo.getComments().size() > 0) {
            DateFormat dateFormat = new DateFormat();
            Calendar calendar = Calendar.getInstance();
            for(CommentsInfo.Comment c : cInfo.getComments()){
                calendar.setTimeInMillis(Long.valueOf(c.getTime()));
                String date = dateFormat.format("MM-dd hh:mm",calendar.getTimeInMillis()).toString();
                c.setTime(date);
            }
        }
        return cInfo;
    }

    @Override
    protected String getCacheDir() {
        return HOMECACHE;
    }

    @Override
    protected String getUrl() {
        LogUtils.e(HOMEURL);
        return HOMEURL;
    }
}
