package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.adapters.AlbumFragmentPagerAdapter;
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerFragmentPagerAdapter;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {
    private static final String ALBUM_MID = "album_mid";
    private static final String ALBUM_NAME = "album_name";

    private String albummid;
    private String albumname;
    private Bitmap album_image_bitmap;
    private AlbumFragmentPagerAdapter pagerAdapter;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView album_img;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(String albummid, String albumname) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ALBUM_MID, albummid);
        args.putString(ALBUM_NAME, albumname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albummid = getArguments().getString(ALBUM_MID);
            albumname = getArguments().getString(ALBUM_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        appBarLayout = (AppBarLayout) view.findViewById(R.id.album_appbarlayout);
        toolbar = (Toolbar) view.findViewById(R.id.album_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        album_img = (ImageView) view.findViewById(R.id.album_img);
        tabLayout = (TabLayout) view.findViewById(R.id.album_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.album_viewpager);
        setFragment();
        setChildFragment();

        return view;
    }

    private void setFragment() {
        toolbar.setTitle(albumname);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setAlbumImage();
    }

    private void setAlbumImage() {
        String api = getContext().getString(R.string.album_image_api);
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
                album_image_bitmap = BitmapFactory.decodeStream(inputStream);
                ((AppCompatActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        album_img.setImageBitmap(album_image_bitmap);
                    }
                });
            }
        });
    }

    private void setChildFragment() {
        pagerAdapter = new AlbumFragmentPagerAdapter(getChildFragmentManager(), getContext(), albummid);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
