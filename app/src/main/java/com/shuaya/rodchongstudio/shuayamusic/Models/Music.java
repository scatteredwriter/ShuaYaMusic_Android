package com.shuaya.rodchongstudio.shuayamusic.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class Music {

    private String albumid;
    private String albummid;
    private String albumname;
    private String alertid;
    private int interval;
    private int isonly;
    private PayBean pay;
    private PreviewBean preview;
    private List<SingerBean> singer;
    private int size128;
    private int size320;
    private int sizeape;
    private int sizeflac;
    private int sizeogg;
    private String songid;
    private String songmid;
    private String songname;
    private int stream;

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getAlertid() {
        return alertid;
    }

    public void setAlertid(String alertid) {
        this.alertid = alertid;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getIsonly() {
        return isonly;
    }

    public void setIsonly(int isonly) {
        this.isonly = isonly;
    }

    public PayBean getPay() {
        return pay;
    }

    public void setPay(PayBean pay) {
        this.pay = pay;
    }

    public PreviewBean getPreview() {
        return preview;
    }

    public void setPreview(PreviewBean preview) {
        this.preview = preview;
    }

    public int getSize128() {
        return size128;
    }

    public void setSize128(int size128) {
        this.size128 = size128;
    }

    public int getSize320() {
        return size320;
    }

    public void setSize320(int size320) {
        this.size320 = size320;
    }

    public int getSizeape() {
        return sizeape;
    }

    public void setSizeape(int sizeape) {
        this.sizeape = sizeape;
    }

    public int getSizeflac() {
        return sizeflac;
    }

    public void setSizeflac(int sizeflac) {
        this.sizeflac = sizeflac;
    }

    public int getSizeogg() {
        return sizeogg;
    }

    public void setSizeogg(int sizeogg) {
        this.sizeogg = sizeogg;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public int getStream() {
        return stream;
    }

    public void setStream(int stream) {
        this.stream = stream;
    }

    public List<SingerBean> getSinger() {
        return singer;
    }

    public void setSinger(List<SingerBean> singer) {
        this.singer = singer;
    }
}
