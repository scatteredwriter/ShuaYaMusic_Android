package com.shuaya.rodchongstudio.shuayamusic.models.lyricmodels;

/**
 * Created by RodChong on 2017/1/3.
 */

public class LyricLine {

    private String content;
    private long start_time;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getStart_time() {
        return start_time;
    }
}
