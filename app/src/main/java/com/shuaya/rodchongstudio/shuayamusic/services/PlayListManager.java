package com.shuaya.rodchongstudio.shuayamusic.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayList;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayMode;

import java.util.List;
import java.util.Random;

import utils.Application;

/**
 * Created by RodChong on 2016/12/30.
 */

public class PlayListManager {

    final private static String SharedPreferencesName = "PlayListManagerSP";
    final private static String PlayModeString = "PlayMode";

    private PlayMode playMode;
    private Random random;

    public PlayListManager() {
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
        playMode = PlayMode.values()[sp.getInt(PlayModeString, PlayMode.Shuffle.ordinal())];
        random = new Random();
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void ChangedPlayMode(PlayMode playmode) {
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PlayModeString, playmode.ordinal());
        editor.apply();
        this.playMode = playmode;
    }

    public Music NextMusic(Music current_music) {
        List<Music> list = PlayList.DefaultPlayList();
        if (list != null && (!list.isEmpty())) {
            int index = 0;
            switch (playMode) {
                case Shuffle:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            index = random.nextInt(list.size() - 1);
                            if (index == i) //如果还是和当前歌曲相同
                                while (index == i) index = random.nextInt(list.size() - 1);
                            break;
                        }
                    }
                    return list.get(index);
                case RepeatOne:
                    return current_music;
                case RepeatList:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            if ((list.size() - 1) == i) //到达最后一首
                                index = 0; //跳转到第一首
                            else
                                index = i + 1; //跳转到下一首
                            break;
                        }
                    }
                    return list.get(index);
                case Order:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            index = i + 1; //跳转到下一首
                            if ((list.size() - 1) == i) //到达最后一首
                                return null; //不再播放
                            break;
                        }
                    }
                    return list.get(index);
            }
        }
        return null;
    }

    public Music PreviousMusic(Music current_music) {
        List<Music> list = PlayList.DefaultPlayList();
        if (list != null && (!list.isEmpty())) {
            int index = 0;
            switch (playMode) {
                case Shuffle:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            index = random.nextInt(list.size() - 1);
                            if (index == i) //如果还是和当前歌曲相同
                                while (index == i) index = random.nextInt(list.size() - 1);
                            break;
                        }
                    }
                    return list.get(index);
                case RepeatOne:
                    return current_music;
                case RepeatList:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            if (0 == i) //到达第一首
                                index = (list.size() - 1); //跳转到最后一首
                            else
                                index = i - 1; //跳转到前一首
                            break;
                        }
                    }
                    return list.get(index);
                case Order:
                    for (int i = 0; i < list.size(); i++) {
                        if (current_music.getSongid().equals(list.get(i).getSongid())) {
                            index = i - 1; //跳转到前一首
                            if (0 == i) //到达第一首
                                return null; //不再播放
                            break;
                        }
                    }
                    return list.get(index);
            }
        }
        return null;
    }

}
