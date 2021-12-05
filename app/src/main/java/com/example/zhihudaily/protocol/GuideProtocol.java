package com.example.zhihudaily.protocol;

import android.util.Log;

import com.example.zhihudaily.Utils.FileUtils;
import com.example.zhihudaily.Utils.HttpUtils;
import com.example.zhihudaily.Utils.LogUtils;
import com.example.zhihudaily.bean.GuideInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import okhttp3.Request;
import okhttp3.Response;


public class GuideProtocol extends BaseProtocol<GuideInfo> {
    private static final String GUIDE_URL = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
    private static final String GUIDE_DIR = "guide";
    private static final String TAG = "GuideProtocol";

    @Override
    protected GuideInfo paserJsonData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String guideName = jsonObject.getString("text");
            String guidePicUrl = jsonObject.getString("img");
            GuideInfo guideInfo = new GuideInfo(guideName, guidePicUrl);
            Log.e(TAG, guideInfo.toString());
            return guideInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getCacheDir() {
        return GUIDE_DIR;
    }

    @Override
    protected String getUrl() {
        return GUIDE_URL;
    }
}
