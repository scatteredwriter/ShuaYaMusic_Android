package com.shuaya.rodchongstudio.shuayamusic.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Application;

import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayMode;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayState;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.ReceiveMusic;

/**
 * Created by RodChong on 2016/12/29.
 */

public class MusicService extends Service {

    private Music current_music;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private PlayState playState;
    private PlayListManager playListManager;
    private List<ReceiveMusic> receiveMusics;

    private MusicServiceBinder binder = new MusicServiceBinder();

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        public void run() {
            if (!MusicService.this.mediaPlayer.isPlaying())
                return;
            Iterator localIterator = MusicService.this.receiveMusics.iterator();
            while (localIterator.hasNext())
                ((ReceiveMusic) localIterator.next()).ProgressChanged(MusicService.this.mediaPlayer.getCurrentPosition());
            handler.postDelayed(this, 1000L);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        playListManager = new PlayListManager();
        receiveMusics = new ArrayList();
        binder = new MusicServiceBinder();
        playState = PlayState.NOSTATR;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binder.PlayNext();
            }
        });
    }

    private void NotifyMusicChanged() {
        Iterator localIterator = this.receiveMusics.iterator();
        while (localIterator.hasNext())
            ((ReceiveMusic) localIterator.next()).GetMusic(this.current_music, this.mediaPlayer.getDuration(), this.mediaPlayer.getCurrentPosition());
    }

    private void NotifyProgressChanged() {
        handler.postDelayed(this.runnable, 1000L);
    }

    private void PlayCompleted() {
        mediaPlayer.reset();
        playState = PlayState.COMPLETED;
        handler.removeCallbacks(runnable);
        Iterator localIterator = receiveMusics.iterator();
        while (localIterator.hasNext())
            ((ReceiveMusic) localIterator.next()).PlayingCompleted();
    }

    @Override
    public void onDestroy() {

        mediaPlayer.stop();
        mediaPlayer.release();

        super.onDestroy();
    }

    public class MusicServiceBinder extends Binder implements AudioManager.OnAudioFocusChangeListener {

        public void StartPlaying(final Music music) {
            String api = Application.getContext().getString(R.string.music_online_api).replace("{0}", music.getSongmid());
            try {
                int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) { //请求焦点成功
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(api);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        public void onPrepared(final MediaPlayer mp) {
                            mp.start();
                            current_music = music;
                            playState = PlayState.PLAYING;
                            NotifyMusicChanged();
                            NotifyProgressChanged();
                        }
                    });
                }
                return;
            } catch (Exception localException) {
            }
        }

        public Boolean PlayNext() {
            Music music = playListManager.NextMusic(current_music);
            if (music == null) {
                PlayCompleted();
                return false;
            }
            StartPlaying(music);
            return true;
        }

        public boolean PlayPrevious() {
            Music music = playListManager.PreviousMusic(current_music);
            if (music == null) {
                PlayCompleted();
                return false;
            }
            StartPlaying(music);
            return true;
        }

        public void AddMusicChangedListener(ReceiveMusic paramReceiveMusic) {
            receiveMusics.add(paramReceiveMusic);
            if (current_music != null)
                paramReceiveMusic.GetMusic(current_music, mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition());
        }

        public Music GetCurrentMusic() {
            return current_music;
        }

        public PlayMode GetPlayMode() {
            return playListManager.getPlayMode();
        }

        public PlayState GetCurrentPlayState() {
            return playState;
        }

        public void SetPosition(int position) {
            mediaPlayer.seekTo(position);
        }

        public void ChangedPlayMode(PlayMode playMode) {
            playListManager.ChangedPlayMode(playMode);
        }

        public boolean Pause() {
            if (mediaPlayer.isPlaying()) {
                int result = audioManager.abandonAudioFocus(this);
                if (result == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.pause();
                    playState = PlayState.PAUSE;
                    handler.removeCallbacks(runnable);
                    Iterator localIterator = receiveMusics.iterator();
                    while (localIterator.hasNext())
                        ((ReceiveMusic) localIterator.next()).Paused(0);
                    return true;
                }
            }
            return false;
        }

        public boolean Start() {
            if (!mediaPlayer.isPlaying()) {
                int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.start();
                    playState = PlayState.PLAYING;
                    handler.postDelayed(runnable, 1000L);
                    Iterator localIterator = receiveMusics.iterator();
                    while (localIterator.hasNext())
                        ((ReceiveMusic) localIterator.next()).Paused(1);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN: //重新获得焦点
                    Start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    Pause();
                    break; //暂时失去焦点
                case AudioManager.AUDIOFOCUS_LOSS:
                    Pause();
                    break;//时期焦点，暂停播放
                default:
                    Pause();
                    break;
            }
        }
    }
}
