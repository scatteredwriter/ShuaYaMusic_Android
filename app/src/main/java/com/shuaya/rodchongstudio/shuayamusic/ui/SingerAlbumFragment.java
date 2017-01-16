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
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerAlbumAdapter;
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerSongAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.singeralbummodels.SingerAlbum;
import com.shuaya.rodchongstudio.shuayamusic.models.singeralbummodels.SingerAlbumBean;
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
 * Use the {@link SingerAlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingerAlbumFragment extends Fragment {
    private static final String SINGER_MID = "singer_mid";

    private String singer_mid;
    private int begin_index = 0;
    private static int num = 30;

    private RecyclerView recyclerView;
    private List<SingerAlbum> albumList = new ArrayList<>();
    private SingerAlbumAdapter adapter;


    public SingerAlbumFragment() {
        // Required empty public constructor
    }

    public static SingerAlbumFragment newInstance(String singer_mid) {
        SingerAlbumFragment fragment = new SingerAlbumFragment();
        Bundle args = new Bundle();
        args.putString(SINGER_MID, singer_mid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            singer_mid = getArguments().getString(SINGER_MID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singer_album, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.singer_album_recyclerview);
        setRecyclerView();

        return view;
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        String api = getString(R.string.singer_album_api);
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
                        final SingerAlbumBean singerAlbumBean = gson.fromJson(result, SingerAlbumBean.class);
                        ((AppCompatActivity) getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (SingerAlbum item :
                                        singerAlbumBean.getData().getList()) {
                                    albumList.add(item);
                                }
                                adapter = new SingerAlbumAdapter(albumList, R.layout.singer_album_item);
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
