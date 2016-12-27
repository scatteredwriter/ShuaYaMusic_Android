package com.shuaya.rodchongstudio.shuayamusic.models.SearchModels;

/**
 * Created by RodChong on 2016/12/19.
 */

public class ZhidaBean {
    /**
     * albumnum : 30
     * singerid : 4558
     * singermid : 0025NhlN2yWrP4
     * singername : 周杰伦
     * songnum : 333
     * type : 2
     */

    private int albumnum;
    private int singerid;
    private String singermid;
    private String singername;
    private int songnum;
    private int type;

    public int getAlbumnum() {
        return albumnum;
    }

    public void setAlbumnum(int albumnum) {
        this.albumnum = albumnum;
    }

    public int getSingerid() {
        return singerid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public String getSingermid() {
        return singermid;
    }

    public void setSingermid(String singermid) {
        this.singermid = singermid;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public int getSongnum() {
        return songnum;
    }

    public void setSongnum(int songnum) {
        this.songnum = songnum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
