package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayState;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.ReceiveMusic;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.widget.PauseableRotateAnimator;
import com.shuaya.rodchongstudio.shuayamusic.widget.RoundImageView;

import net.qiujuer.genius.blur.StackBlur;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;

public class PlayInfoFragment extends Fragment implements ReceiveMusic {

    private Context context;
    private MusicService.MusicServiceBinder binder;
    private Music current_music;
    private PauseableRotateAnimator animator;
    private RoundImageView music_album_ing;

    public PlayInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setContext(Context context, MusicService.MusicServiceBinder binder) {
        this.context = context;
        this.binder = binder;
        this.binder.AddMusicChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_info, container, false);
        music_album_ing = (RoundImageView) view.findViewById(R.id.play_info_album_img);
        animator = new PauseableRotateAnimator(music_album_ing, 15000L);
        return view;
    }

    private void setMusic_album_ing(String albummid) {
        String api = context.getString(R.string.album_image_api);
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
                final Bitmap album_bitmap = BitmapFactory.decodeStream(inputStream);
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        music_album_ing.setImageBitmap(album_bitmap);
                        if (binder.GetCurrentPlayState() == PlayState.PLAYING)
                            animator.Start();
                    }
                });
            }
        });
    }

    @Override
    public void GetMusic(Music music, int duration, int current_position) {
        this.current_music = music;
        setMusic_album_ing(this.current_music.getAlbummid());
    }

    @Override
    public void ProgressChanged(int progress) {

    }

    @Override
    public void PlayingCompleted() {
        animator.Stop();
    }

    @Override
    public void Paused(int status) {
        if (status == 0) { //暂停
            animator.Pause();
        } else if (status == 1) { //继续
            animator.Start();
        }
    }
}
