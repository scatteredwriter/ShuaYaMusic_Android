package com.shuaya.rodchongstudio.shuayamusic.Models.SearchModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class DataBean {

    private String keyword;
    private int priority;
    private SemanticBean semantic;
    private SongBean song;
    private long totaltime;
    private ZhidaBean zhida;
    private List<?> qc;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public SemanticBean getSemantic() {
        return semantic;
    }

    public void setSemantic(SemanticBean semantic) {
        this.semantic = semantic;
    }

    public SongBean getSong() {
        return song;
    }

    public void setSong(SongBean song) {
        this.song = song;
    }

    public long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(long totaltime) {
        this.totaltime = totaltime;
    }

    public ZhidaBean getZhida() {
        return zhida;
    }

    public void setZhida(ZhidaBean zhida) {
        this.zhida = zhida;
    }

    public List<?> getQc() {
        return qc;
    }

    public void setQc(List<?> qc) {
        this.qc = qc;
    }
}
