package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.adapters.ALbumsAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.AlbumModels.Album;
import com.shuaya.rodchongstudio.shuayamusic.models.AlbumModels.AlbumBean;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {


    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.albums_gridview);
        final ALbumsAdapter aLbumsAdapter = new ALbumsAdapter(getContext(), R.layout.album_item, new ArrayList<Album>());
        gridView.setAdapter(aLbumsAdapter);
        String api = getContext().getString(R.string.new_albums_api);
        api = api.replace("{0}", "1");
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String result = response.body().string();
                result = result.replace("GetAlbumListJsonCallback(", "");
                result = result.substring(0, result.length() - 1);
                try {
                    AlbumBean albumBean = gson.fromJson(result, AlbumBean.class);
                    if (albumBean != null) {
                        final List<Album> lists = albumBean.getData().getAlbumlist();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (Album item : lists) {
                                    aLbumsAdapter.add(item);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        });
        return view;
    }
}