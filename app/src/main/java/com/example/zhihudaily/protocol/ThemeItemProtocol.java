package com.example.zhihudaily.protocol;

import com.example.zhihudaily.Utils.LogUtils;
import com.example.zhihudaily.bean.ThemeInfo;
import com.example.zhihudaily.bean.ThemeItemInfo;
import com.google.gson.Gson;

public class ThemeItemProtocol extends BaseProtocol<ThemeItemInfo> {

    private String HOMEURL = "http://news-at.zhihu.com/api/4/theme/";
    private String HOMECACHE = "themeItem";

    public ThemeItemProtocol(String id) {
        HOMEURL+=id;
        HOMECACHE+=id;
    }

    @Override
    protected ThemeItemInfo paserJsonData(String json) {
        Gson gson = new Gson();
        ThemeItemInfo Info = gson.fromJson(json, ThemeItemInfo.class);
        LogUtils.e("paserJsonData" + Info.toString());
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
