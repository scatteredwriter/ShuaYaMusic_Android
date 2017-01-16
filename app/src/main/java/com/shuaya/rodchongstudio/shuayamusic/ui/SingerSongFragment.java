package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerSongAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.singersongmodels.SingerMusic;
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
 * Use the {@link SingerSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingerSongFragment extends Fragment {
    private static final String SINGER_MID = "singer_mid";

    private String singer_mid;
    private int begin_index = 0;
    private static int num = 30;

    private RecyclerView recyclerView;
    private SingerSongAdapter adapter;
    private List<SingerMusic> musicList = new ArrayList<>();

    public SingerSongFragment() {
        // Required empty public constructor
    }

    public static SingerSongFragment newInstance(String singer_mid) {
        SingerSongFragment fragment = new SingerSongFragment();
        Bundle args = new Bundle();
        args.putString(SINGER_MID, singer_mid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.singer_mid = getArguments().getString(SINGER_MID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singer_song, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.singer_song_recyclerview);
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        String api = getString(R.string.singer_song_api);
        api = api.replace("{0}", singer_mid);
        api = api.replace("{1}", String.valueOf(begin_index));
        api = api.replace("{2}", String.valueOf(num));
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
                        final SingerSongBean singerSongBean = gson.fromJson(result, SingerSongBean.class);
                        ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (SingerSongBean.DataBean.ListBean item :
                                        singerSongBean.getData().getList()) {
                                    musicList.add(item.getMusicData());
                                }
                                adapter = new SingerSongAdapter(musicList, R.layout.singer_song_item);
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
