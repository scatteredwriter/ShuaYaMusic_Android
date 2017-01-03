package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.LyricBean;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.ReceiveMusic;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.widget.LyricView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayLyricFragment extends Fragment implements ReceiveMusic {

    private Context context;
    private MusicService.MusicServiceBinder binder;
    private LyricView lyricView;

    public PlayLyricFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_lyric, container, false);
        lyricView = (LyricView) view.findViewById(R.id.play_lyric_lyricview);
        return view;
    }

    public void setContext(Context context, MusicService.MusicServiceBinder binder) {
        this.context = context;
        this.binder = binder;
        binder.AddMusicChangedListener(this);
    }

    private void setLyricView(String songmid) {
        String api = ((AppCompatActivity) context).getString(R.string.lyric_api);
        api = api.replace("{0}", songmid);
        String[] string = new String[]{"Referer", "https://y.qq.com/portal/player.html"};
        HttpUtil.HasHeaderGetRequest(api, string, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String json = response.body().string();
                json = json.replace("MusicJsonCallback_lrc(", "");
                json = json.substring(0, json.length() - 1);
                byte[] bytes = Base64.decode(gson.fromJson(json, LyricBean.class).getLyric(), Base64.DEFAULT);
                final String result = new String(bytes, "utf-8");
                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lyricView.setLyric(result);
                    }
                });
            }
        });
    }

    @Override
    public void GetMusic(Music music, int duration, int current_position) {
        setLyricView(music.getSongmid());
    }

    @Override
    public void ProgressChanged(int progress) {
        lyricView.ProgressChanged(progress);
    }

    @Override
    public void PlayingCompleted() {

    }

    @Override
    public void Paused(int status) {

    }
}
