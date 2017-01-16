package com.shuaya.rodchongstudio.shuayamusic.models.singersongmodels;

import com.google.gson.annotations.SerializedName;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.models.PayBean;
import com.shuaya.rodchongstudio.shuayamusic.models.PreviewBean;
import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;

import java.util.List;

/**
 * Created by RodChong on 2017/1/15.
 */

public class SingerMusic extends Music {

    private String albumdesc;
    private int belongCD;
    private int cdIdx;
    private String label;
    private int msgid;
    private int rate;
    private int size5_1;
    private String songorig;
    private int songtype;
    private String strMediaMid;
    @SerializedName("switch")
    private int switchX;
    private int type;
    private String vid;

    public String getAlbumdesc() {
        return albumdesc;
    }

    public void setAlbumdesc(String albumdesc) {
        this.albumdesc = albumdesc;
    }

    public int getBelongCD() {
        return belongCD;
    }

    public void setBelongCD(int belongCD) {
        this.belongCD = belongCD;
    }

    public int getCdIdx() {
        return cdIdx;
    }

    public void setCdIdx(int cdIdx) {
        this.cdIdx = cdIdx;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getSize5_1() {
        return size5_1;
    }

    public void setSize5_1(int size5_1) {
        this.size5_1 = size5_1;
    }

    public String getSongorig() {
        return songorig;
    }

    public void setSongorig(String songorig) {
        this.songorig = songorig;
    }

    public int getSongtype() {
        return songtype;
    }

    public void setSongtype(int songtype) {
        this.songtype = songtype;
    }

    public String getStrMediaMid() {
        return strMediaMid;
    }

    public void setStrMediaMid(String strMediaMid) {
        this.strMediaMid = strMediaMid;
    }

    public int getSwitchX() {
        return switchX;
    }

    public void setSwitchX(int switchX) {
        this.switchX = switchX;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

}
