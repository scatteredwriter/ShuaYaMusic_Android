package com.shuaya.rodchongstudio.shuayamusic.models.searchmodels;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class SemanticBean {
    /**
     * curnum : 0
     * curpage : 1
     * list : []
     * totalnum : 0
     */

    private int curnum;
    private int curpage;
    private int totalnum;
    private List<?> list;

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

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
