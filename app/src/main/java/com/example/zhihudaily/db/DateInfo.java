package com.example.zhihudaily.db;

import org.litepal.crud.LitePalSupport;

import java.util.zip.Inflater;


public class DateInfo extends LitePalSupport {
    private String pos;
    private String date;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
