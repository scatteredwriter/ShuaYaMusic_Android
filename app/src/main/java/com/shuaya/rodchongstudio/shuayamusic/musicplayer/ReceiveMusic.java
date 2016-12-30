package com.shuaya.rodchongstudio.shuayamusic.musicplayer;

/**
 * Created by RodChong on 2016/12/27.
 */

import com.shuaya.rodchongstudio.shuayamusic.models.Music;

public abstract interface ReceiveMusic {
    public void GetMusic(Music music, int duration, int current_position);

    public void ProgressChanged(int progress);

    public void PlayingCompleted();

    public void Paused(int status);
}
