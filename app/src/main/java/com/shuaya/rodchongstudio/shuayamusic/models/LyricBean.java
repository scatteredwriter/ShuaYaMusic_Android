package com.shuaya.rodchongstudio.shuayamusic.models;

/**
 * Created by RodChong on 2017/1/3.
 */

public class LyricBean {

    private int code;
    private String lyric;
    private int retcode;
    private int subcode;
    private String trans;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getLyric() {
        return lyric;
    }

    public void setRetcode(int code) {
        this.retcode = code;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setSubcode(int code) {
        this.subcode = code;
    }

    public int getSubcode() {
        return subcode;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

}
