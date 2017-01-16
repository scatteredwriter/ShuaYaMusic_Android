package com.shuaya.rodchongstudio.shuayamusic.models.singeralbummodels;

import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;

import java.util.List;

/**
 * Created by RodChong on 2017/1/16.
 */

public class SingerAlbum {

    private String Fattribute_5;
    private String Ftype;
    private String albumID;
    private String albumMID;
    private String albumName;
    private String albumtype;
    private String company;
    private String desc;
    private String lan;
    private String listen_count;
    private String pubTime;
    private String score;
    private int shoufa;
    private String singerID;
    private String singerMID;
    private String singerName;
    private int type;
    private List<SingerBean> singers;

    public String getFattribute_5() {
        return Fattribute_5;
    }

    public void setFattribute_5(String Fattribute_5) {
        this.Fattribute_5 = Fattribute_5;
    }

    public String getFtype() {
        return Ftype;
    }

    public void setFtype(String Ftype) {
        this.Ftype = Ftype;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumMID() {
        return albumMID;
    }

    public void setAlbumMID(String albumMID) {
        this.albumMID = albumMID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumtype() {
        return albumtype;
    }

    public void setAlbumtype(String albumtype) {
        this.albumtype = albumtype;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getListen_count() {
        return listen_count;
    }

    public void setListen_count(String listen_count) {
        this.listen_count = listen_count;
    }

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getShoufa() {
        return shoufa;
    }

    public void setShoufa(int shoufa) {
        this.shoufa = shoufa;
    }

    public String getSingerID() {
        return singerID;
    }

    public void setSingerID(String singerID) {
        this.singerID = singerID;
    }

    public String getSingerMID() {
        return singerMID;
    }

    public void setSingerMID(String singerMID) {
        this.singerMID = singerMID;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SingerBean> getSingers() {
        return singers;
    }

    public void setSingers(List<SingerBean> singers) {
        this.singers = singers;
    }
}
