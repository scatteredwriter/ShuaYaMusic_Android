package Utils.MusicPlayer;

/**
 * Created by RodChong on 2016/12/27.
 */

import Utils.Application;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

import com.shuaya.rodchongstudio.shuayamusic.Models.Music;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MusicPlayer {

    public static MusicPlayer Instance = new MusicPlayer();
    private Music current_music;
    private MediaPlayer mediaPlayer;
    private List<ReceiveMusic> receiveMusics;

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            if (!MusicPlayer.this.mediaPlayer.isPlaying())
                return;
            Iterator localIterator = MusicPlayer.this.receiveMusics.iterator();
            while (localIterator.hasNext())
                ((ReceiveMusic) localIterator.next()).ProgressChanged(MusicPlayer.this.mediaPlayer.getCurrentPosition());
            MusicPlayer.this.handler.postDelayed(this, 1000L);
        }
    };

    public MusicPlayer() {
        mediaPlayer = new MediaPlayer();
        receiveMusics = new ArrayList();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
            }
        });
    }

    public void StartPlaying(final Music music) {
        String api = Application.getContext().getString(R.string.music_online_api).replace("{0}", music.getSongmid());
        try {
            this.mediaPlayer.reset();
            this.mediaPlayer.setDataSource(api);
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer paramAnonymousMediaPlayer) {
                    paramAnonymousMediaPlayer.start();
                    current_music = music;
                    NotifyMusicChanged();
                    NotifyProgressChanged();
                }
            });
            return;
        } catch (Exception localException) {
        }
    }

    private void NotifyMusicChanged() {
        Iterator localIterator = this.receiveMusics.iterator();
        while (localIterator.hasNext())
            ((ReceiveMusic) localIterator.next()).GetMusic(this.current_music, this.mediaPlayer.getDuration());
    }

    private void NotifyProgressChanged() {
        this.handler.postDelayed(this.runnable, 1000L);
    }

    private void NotifyPlayingCompleted(){
        Iterator localIterator = this.receiveMusics.iterator();
        while (localIterator.hasNext())
            ((ReceiveMusic) localIterator.next()).GetMusic(this.current_music, this.mediaPlayer.getDuration());
    }

    public void AddMusicChangedListener(ReceiveMusic paramReceiveMusic) {
        this.receiveMusics.add(paramReceiveMusic);
    }

    public Music GetCurrentMusic() {
        return this.current_music;
    }

    public boolean Pause() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
            return true;
        }
        return false;
    }

    public boolean Start() {
        if (!this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.start();
            return true;
        }
        return false;
    }

}
