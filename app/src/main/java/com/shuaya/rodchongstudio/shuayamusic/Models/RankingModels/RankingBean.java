package com.shuaya.rodchongstudio.shuayamusic.Models.RankingModels;

import java.util.List;

/**
 * Created by RodChong on 2016/12/20.
 */

public class RankingBean {

    private int color;
    private int comment_num;
    private int cur_song_num;
    private String date;
    private int song_begin;
    private TopinfoBean topinfo;
    private int total_song_num;
    private String update_time;
    private List<RankingMusic> songlist;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getCur_song_num() {
        return cur_song_num;
    }

    public void setCur_song_num(int cur_song_num) {
        this.cur_song_num = cur_song_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSong_begin() {
        return song_begin;
    }

    public void setSong_begin(int song_begin) {
        this.song_begin = song_begin;
    }

    public TopinfoBean getTopinfo() {
        return topinfo;
    }

    public void setTopinfo(TopinfoBean topinfo) {
        this.topinfo = topinfo;
    }

    public int getTotal_song_num() {
        return total_song_num;
    }

    public void setTotal_song_num(int total_song_num) {
        this.total_song_num = total_song_num;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<RankingMusic> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<RankingMusic> songlist) {
        this.songlist = songlist;
    }
}
