package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.adapters.AlbumSongAdapter;
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerSongAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.AlbumBean;
import com.shuaya.rodchongstudio.shuayamusic.models.albummodels.AlbumMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.singersongmodels.SingerSongBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumSongFragment extends Fragment {
    private static final String ALBUM_MID = "album_mid";

    private String albummid;
    private List<AlbumMusic> musicList = new ArrayList<>();

    private RecyclerView recyclerView;
    private AlbumSongAdapter adapter;


    public AlbumSongFragment() {
        // Required empty public constructor
    }

    public static AlbumSongFragment newInstance(String albummid) {
        AlbumSongFragment fragment = new AlbumSongFragment();
        Bundle args = new Bundle();
        args.putString(ALBUM_MID, albummid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albummid = getArguments().getString(ALBUM_MID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_song, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.album_song_recyclerview);
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        String api = getString(R.string.album_api);
        api = api.replace("{0}", albummid);
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    if (result != null && (!result.isEmpty())) {
                        Gson gson = new Gson();
                        final AlbumBean albumBean = gson.fromJson(result, AlbumBean.class);
                        ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                musicList.addAll(albumBean.getData().getList());
                                adapter = new AlbumSongAdapter(musicList, R.layout.album_song_item);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                } catch (Exception e) {

                }
            }
        });
    }

}
