package com.shuaya.rodchongstudio.shuayamusic.models.lyricmodels;

import java.util.List;

/**
 * Created by RodChong on 2017/1/3.
 */

public class Lyric {

    private List<LyricLine> lyric;
    private String title;
    private String singer;
    private String album;
    private String by;
    private int offset;

    public void setLyric(List<LyricLine> lines) {
        this.lyric = lines;
    }

    public List<LyricLine> getLyric() {
        return lyric;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSinger() {
        return singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum() {
        return album;
    }

    public void setBy(String title) {
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

}
