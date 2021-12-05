package com.example.zhihudaily.protocol;

import com.example.zhihudaily.bean.NewsBarInfo;
import com.google.gson.Gson;


public class NewsBarProtocol extends BaseProtocol<NewsBarInfo> {

    private String HOMEURL = "http://news-at.zhihu.com/api/4/story-extra/";
    private String HOMECACHE;

    public NewsBarProtocol(String id){
        HOMECACHE ="extra"+id;
        HOMEURL+=id;
    }

    @Override
    protected NewsBarInfo paserJsonData(String json) {
        Gson gson = new Gson();
        NewsBarInfo barInfo = gson.fromJson(json, NewsBarInfo.class);
        return barInfo;
    }

    @Override
    protected String getCacheDir() {
        return HOMECACHE;
    }

    @Override
    protected String getUrl() {
        return HOMEURL;
    }
}
