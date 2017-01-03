package com.shuaya.rodchongstudio.shuayamusic.models.rankingmodels;

import com.google.gson.annotations.SerializedName;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;

/**
 * Created by RodChong on 2016/12/20.
 */

public class RankingMusic {

    private String Franking_value;
    private String cur_count;
    private DataBean data;
    private String in_count;
    private String mb;
    private String old_count;
    private SingerBean singer2;
    private VidBean vid;

    public String getFranking_value() {
        return Franking_value;
    }

    public void setFranking_value(String Franking_value) {
        this.Franking_value = Franking_value;
    }

    public String getCur_count() {
        return cur_count;
    }

    public void setCur_count(String cur_count) {
        this.cur_count = cur_count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getIn_count() {
        return in_count;
    }

    public void setIn_count(String in_count) {
        this.in_count = in_count;
    }

    public String getMb() {
        return mb;
    }

    public void setMb(String mb) {
        this.mb = mb;
    }

    public String getOld_count() {
        return old_count;
    }

    public void setOld_count(String old_count) {
        this.old_count = old_count;
    }

    public SingerBean getSinger2() {
        return singer2;
    }

    public void setSinger2(SingerBean singer2) {
        this.singer2 = singer2;
    }

    public VidBean getVid() {
        return vid;
    }

    public void setVid(VidBean vid) {
        this.vid = vid;
    }

    public static class DataBean extends Music {

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

        public int getswitchX() {
            return switchX;
        }

        public void setswitchX(int switchX) {
            this.switchX = type;
        }

        public int getType() {
            return type;
        }

        public void setType(int switchX) {
            this.switchX = type;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }
    }

    public static class VidBean {
        /**
         * Fmv_id : 1064826
         * Fstatus : 1
         * Fvid : m0022arrzr8
         */

        private String Fmv_id;
        private String Fstatus;
        private String Fvid;

        public String getFmv_id() {
            return Fmv_id;
        }

        public void setFmv_id(String Fmv_id) {
            this.Fmv_id = Fmv_id;
        }

        public String getFstatus() {
            return Fstatus;
        }

        public void setFstatus(String Fstatus) {
            this.Fstatus = Fstatus;
        }

        public String getFvid() {
            return Fvid;
        }

        public void setFvid(String Fvid) {
            this.Fvid = Fvid;
        }
    }
}
