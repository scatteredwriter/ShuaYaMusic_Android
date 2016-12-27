package com.shuaya.rodchongstudio.shuayamusic.models.SearchModels;

import com.shuaya.rodchongstudio.shuayamusic.models.Music;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class SongBean {

    private int curnum;
    private int curpage;
    private int totalnum;
    private List<Music> list;

    public int getCurnum() {
        return curnum;
    }

    public void setCurnum(int curnum) {
        this.curnum = curnum;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public List<Music> getList() {
        return list;
    }

    public void setList(List<Music> list) {
        this.list = list;
    }
}
