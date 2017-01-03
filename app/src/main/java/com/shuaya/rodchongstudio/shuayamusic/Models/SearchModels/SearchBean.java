package com.shuaya.rodchongstudio.shuayamusic.models.searchmodels;

/**
 * Created by RodChong on 2016/12/19.
 */

public class SearchBean {

    private int code;
    private DataBean data;
    private String message;
    private String notice;
    private int subcode;
    private long time;
    private String tips;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setSubcode(int subcode) {
        this.subcode = subcode;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
