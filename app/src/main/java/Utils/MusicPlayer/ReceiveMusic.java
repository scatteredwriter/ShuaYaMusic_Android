package Utils.MusicPlayer;

/**
 * Created by RodChong on 2016/12/27.
 */

import com.shuaya.rodchongstudio.shuayamusic.Models.Music;

public abstract interface ReceiveMusic {
    public void GetMusic(Music music, int duration);

    public void ProgressChanged(int progress);

    public void PlayingCompleted();
}
