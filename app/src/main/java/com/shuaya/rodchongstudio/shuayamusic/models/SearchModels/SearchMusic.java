package com.shuaya.rodchongstudio.shuayamusic.models.searchmodels;

import com.google.gson.annotations.SerializedName;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;

import java.util.List;

/**
 * Created by RodChong on 2016/12/28.
 */

public class SearchMusic extends Music {

    private String albumname_hilight;
    private int chinesesinger;
    private String docid;
    private String format;
    private String lyric;
    private String lyric_hilight;
    private int msgid;
    private long nt;
    private String pubtime;
    private int pure;
    private String songname_hilight;
    private String songurl;
    @SerializedName("switch")
    private int switchX;
    private int t;
    private int tag;
    private int type;
    private int ver;
    private String vid;
    private String albumtransname;
    private String albumtransname_hilight;
    private List<?> grp;

    public String getAlbumname_hilight() {
        return albumname_hilight;
    }

    public void setAlbumname_hilight(String albumname_hilight) {
        this.albumname_hilight = albumname_hilight;
    }

    public int getChinesesinger() {
        return chinesesinger;
    }

    public void setChinesesinger(int chinesesinger) {
        this.chinesesinger = chinesesinger;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getLyric_hilight() {
        return lyric_hilight;
    }

    public void setLyric_hilight(String lyric_hilight) {
        this.lyric_hilight = lyric_hilight;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public long getNt() {
        return nt;
    }

    public void setNt(long nt) {
        this.nt = nt;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public int getPure() {
        return pure;
    }

    public void setPure(int pure) {
        this.pure = pure;
    }

    public String getSongname_hilight() {
        return songname_hilight;
    }

    public void setSongname_hilight(String songname_hilight) {
        this.songname_hilight = songname_hilight;
    }

    public String getSongurl() {
        return songurl;
    }

    public void setSongurl(String songurl) {
        this.songurl = songurl;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAlbumtransname() {
        return albumtransname;
    }

    public void setAlbumtransname(String albumtransname) {
        this.albumtransname = albumtransname;
    }

    public String getAlbumtransname_hilight() {
        return albumtransname_hilight;
    }

    public void setAlbumtransname_hilight(String albumtransname_hilight) {
        this.albumtransname_hilight = albumtransname_hilight;
    }

    public List<?> getGrp() {
        return grp;
    }

    public void setGrp(List<?> grp) {
        this.grp = grp;
    }
}
