package com.shuaya.rodchongstudio.shuayamusic.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.adapters.PlayActivityPagerAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayMode;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayState;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.ReceiveMusic;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.widget.PopupMenu;

import net.qiujuer.genius.blur.StackBlur;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import utils.CastMusicClass;
import utils.ConvertToDate;
import utils.DatabaseHelper;
import utils.HttpUtil;

public class PlayActivity extends BaseActivity implements ReceiveMusic {

    private MusicService.MusicServiceBinder binder;
    private ServiceConnection connection;
    private Music playing_music;
    private Bitmap album_bitmap;
    private PlayActivityPagerAdapter playActivityPagerAdapter;
    private PopupMenu popupMenu;

    private ImageView background_img;
    private TextView songname;
    private TextView singername;
    private ViewPager viewPager;
    private ImageView playmode_but;
    private ImageView previous_but;
    private ImageView pause_but;
    private ImageView next_but;
    private ImageView playlist_but;
    private SeekBar seekBar;
    private TextView current_position;
    private TextView total_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        BindMusicService();
        setCommand();
        setSeekBar();
    }

    private void BindMusicService() {
        Intent music_service = new Intent(this, MusicService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (MusicService.MusicServiceBinder) service;
                binder.AddMusicChangedListener(PlayActivity.this);
                setPlaymode_but();
                setViewPage();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                binder = null;
            }
        };
        bindService(music_service, connection, BIND_AUTO_CREATE);
    }

    private void setCommand() {
        playmode_but = (ImageView) findViewById(R.id.play_playmode_but);
        previous_but = (ImageView) findViewById(R.id.play_previous_but);
        pause_but = (ImageView) findViewById(R.id.play_pause_but);
        next_but = (ImageView) findViewById(R.id.play_next_but);
        playlist_but = (ImageView) findViewById(R.id.play_playlist_but);
        playmode_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMode playMode = binder.GetPlayMode();
                if (playMode.ordinal() == PlayMode.values().length - 1)
                    binder.ChangedPlayMode(PlayMode.values()[0]);
                else
                    binder.ChangedPlayMode(PlayMode.values()[(playMode.ordinal() + 1)]);
                String name = null;
                switch (binder.GetPlayMode()) {
                    case Shuffle:
                        name = "随机播放";
                        break;
                    case Order:
                        name = "顺序播放";
                        break;
                    case RepeatList:
                        name = "列表循环";
                        break;
                    case RepeatOne:
                        name = "单曲循环";
                        break;
                }
                Toast.makeText(PlayActivity.this, name, Toast.LENGTH_SHORT).show();
                setPlaymode_but();
            }
        });
        previous_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.PlayPrevious();
            }
        });
        pause_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (binder.GetCurrentPlayState()) {
                    case PLAYING:
                        if (binder.Pause())
                            Paused(0);
                        break;
                    case PAUSE:
                        if (binder.Start())
                            Paused(1);
                        break;
                    case NOSTATR:
                    case COMPLETED:
                }
            }
        });
        next_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.PlayNext();
            }
        });
        playlist_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BaseMusic> list = DatabaseHelper.GetAllMusicItems();
                if (list != null) {
                    popupMenu = new PopupMenu(PlayActivity.this, new PlaylistItemClick(), list);
                    popupMenu.showAtLocation(PlayActivity.this.findViewById(R.id.activity_play), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
    }

    private void setPlaymode_but() {
        switch (binder.GetPlayMode()) {
            case Shuffle:
                playmode_but.setImageResource(R.mipmap.ic_shuffle_white_48dp);
                break;
            case RepeatOne:
                playmode_but.setImageResource(R.mipmap.ic_repeat_one_white_48dp);
                break;
            case RepeatList:
                playmode_but.setImageResource(R.mipmap.ic_repeat_white_48dp);
                break;
            case Order:
                playmode_but.setImageResource(R.mipmap.ic_playlist_play_white_48dp);
                break;
        }
    }

    private void setSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.play_progress_seekbar);
        current_position = (TextView) findViewById(R.id.play_current_position);
        total_duration = (TextView) findViewById(R.id.play_total_duration);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarProgressChangedLinstener());
    }

    private void setViewPage() {
        viewPager = (ViewPager) findViewById(R.id.play_viewpager);
        playActivityPagerAdapter = new PlayActivityPagerAdapter(getSupportFragmentManager(), this, binder);
        viewPager.setAdapter(playActivityPagerAdapter);
    }

    private void setBackground() {
        background_img = (ImageView) findViewById(R.id.play_background_img);
        setBackgroundImage(playing_music.getAlbummid());
    }

    private void setBackgroundImage(final String albummid) {
        String api = getString(R.string.album_image_api);
        api = api.replace("{0}", "300");
        api = api.replace("{1}", String.valueOf(albummid.charAt(albummid.length() - 2)));
        api = api.replace("{2}", String.valueOf(albummid.charAt(albummid.length() - 1)));
        api = api.replace("{3}", albummid);
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                album_bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
/*                        setAlbumImage();*/
                        Bitmap newBitmap = StackBlur.blur(album_bitmap, 20, false);
                        background_img.setImageBitmap(newBitmap);
                        UseAlphaAnimation(background_img);
                    }
                });
            }
        });
    }

    private void setMusicInfo() {
        songname = (TextView) findViewById(R.id.play_songname);
        singername = (TextView) findViewById(R.id.play_singername);
        songname.setText(playing_music.getSongname());
        singername.setText(playing_music.getSinger().get(0).getName());
    }

    private void UseAlphaAnimation(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000L);
        animationSet.addAnimation(alphaAnimation);
        view.startAnimation(animationSet);
    }

    @Override
    public void onBackPressed() {
        unbindService(connection);
        super.onBackPressed();
        finish();
    }

    @Override
    public IBinder GetBinder() {
        return binder;
    }

    @Override
    public void GetMusic(Music music, int duration, int current_position_time) {
        playing_music = music;
        current_position.setText(ConvertToDate.Convert(current_position_time));
        total_duration.setText(ConvertToDate.Convert(duration));
        seekBar.setMax(duration);
        if (binder.GetCurrentPlayState() == PlayState.PLAYING)
            pause_but.setImageResource(R.mipmap.ic_pause_white_48dp);
        else
            pause_but.setImageResource(R.mipmap.ic_play_arrow_white_48dp);
        setMusicInfo();
        setBackground();
    }

    @Override
    public void ProgressChanged(int progress) {
        seekBar.setProgress(progress);
        current_position.setText(ConvertToDate.Convert(progress));
    }

    @Override
    public void PlayingCompleted() {
        pause_but.setImageResource(R.mipmap.ic_play_arrow_white_48dp);
    }

    @Override
    public void Paused(int status) {
        if (status == 0) { //暂停
            pause_but.setImageResource(R.mipmap.ic_play_arrow_white_48dp);
        } else if (status == 1) { //播放
            pause_but.setImageResource(R.mipmap.ic_pause_white_48dp);
        }
    }

    public class OnSeekBarProgressChangedLinstener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            binder.SetPosition(seekBar.getProgress());
            current_position.setText(ConvertToDate.Convert(seekBar.getProgress()));
        }

    }

    public class PlaylistItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BaseMusic baseMusic = (BaseMusic) parent.getAdapter().getItem(position);
            binder.StartPlaying(CastMusicClass.BaseMusicToMusic(baseMusic));
            popupMenu.dismiss();
        }

    }
}
