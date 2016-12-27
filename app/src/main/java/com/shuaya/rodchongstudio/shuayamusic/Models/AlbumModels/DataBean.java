package com.shuaya.rodchongstudio.shuayamusic.models.AlbumModels;

import java.util.List;

/**
 * Created by RodChong on 2016/12/21.
 */

public class DataBean {

    private int page;
    private int pagesize;
    private int sum;
    private List<Album> albumlist;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<Album> getAlbumlist() {
        return albumlist;
    }

    public void setAlbumlist(List<Album> albumlist) {
        this.albumlist = albumlist;
    }
}
