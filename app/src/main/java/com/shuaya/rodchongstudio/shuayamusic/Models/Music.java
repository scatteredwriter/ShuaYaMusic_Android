package com.shuaya.rodchongstudio.shuayamusic.models;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class Music extends BaseMusic {

    private PayBean pay;
    private PreviewBean preview;
    private List<SingerBean> singer;

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

    public List<SingerBean> getSinger() {
        return singer;
    }

    public void setSinger(List<SingerBean> singer) {
        this.singer = singer;
    }
}
