package com.shuaya.rodchongstudio.shuayamusic.musicplayer;

import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;

import java.util.ArrayList;
import java.util.List;

import utils.CastMusicClass;
import utils.DatabaseHelper;

/**
 * Created by RodChong on 2016/12/30.
 */

public class PlayList {

    public static List<Music> DefaultPlayList() {
        List<BaseMusic> baseMusicList = DatabaseHelper.GetAllMusicItems();
        if (baseMusicList != null && (!baseMusicList.isEmpty())) {
            List<Music> musicList = new ArrayList<>();
            for (BaseMusic item : baseMusicList
                    ) {
                Music music = CastMusicClass.BaseMusicToMusic(item);
                musicList.add(music);
            }
            return musicList;
        }
        return null;
    }

}
