package com.shuaya.rodchongstudio.shuayamusic.models.AlbumModels;

import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;

import java.util.List;

/**
 * Created by RodChong on 2016/12/21.
 */

public class Album {

    private String album_id;
    private String album_mid;
    private String album_name;
    private String public_time;
    private String singer_id;
    private String singer_mid;
    private String singer_name;
    private String week;
    private String year;
    private List<SingerBean> singers;

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_mid() {
        return album_mid;
    }

    public void setAlbum_mid(String album_mid) {
        this.album_mid = album_mid;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }

    public String getSinger_id() {
        return singer_id;
    }

    public void setSinger_id(String singer_id) {
        this.singer_id = singer_id;
    }

    public String getSinger_mid() {
        return singer_mid;
    }

    public void setSinger_mid(String singer_mid) {
        this.singer_mid = singer_mid;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<SingerBean> getSingers() {
        return singers;
    }

    public void setSingers(List<SingerBean> singers) {
        this.singers = singers;
    }
}
