package com.shuaya.rodchongstudio.shuayamusic.models.AlbumModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RodChong on 2016/12/21.
 */

public class AlbumBean {

    private int code;
    private int subcode;
    private String message;
    @SerializedName("default")
    private int defaultX;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(int defaultX) {
        this.defaultX = defaultX;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
}