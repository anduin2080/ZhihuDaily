package com.example.zhihudaily.protocol;

import com.example.zhihudaily.bean.NewsBarInfo;
import com.example.zhihudaily.bean.ThemeInfo;
import com.google.gson.Gson;


public class ThemeProtocol extends BaseProtocol<ThemeInfo> {

    private String HOMEURL = "http://news-at.zhihu.com/api/4/themes";
    private String HOMECACHE = "theme";

    @Override
    protected ThemeInfo paserJsonData(String json) {
        Gson gson = new Gson();
        ThemeInfo Info = gson.fromJson(json, ThemeInfo.class);
        return Info;
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
