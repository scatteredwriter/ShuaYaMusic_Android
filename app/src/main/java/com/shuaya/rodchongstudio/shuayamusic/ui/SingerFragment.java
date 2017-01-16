package com.shuaya.rodchongstudio.shuayamusic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.shuaya.rodchongstudio.shuayamusic.adapters.SingerFragmentPagerAdapter;

import net.qiujuer.genius.blur.StackBlur;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;

public class SingerFragment extends Fragment {
    private static final String SINGER_MID = "singermid";
    private static final String SINGER_NAME = "singername";

    private String singermid;
    private String singername;
    private float appbarlayout_offest = 0f;
    private Bitmap singer_img_bitmap;
    private SingerFragmentPagerAdapter pagerAdapter;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ImageView singer_img;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public SingerFragment() {
        // Required empty public constructor
    }

    public static SingerFragment newInstance(String singermid, String singername) {
        SingerFragment fragment = new SingerFragment();
        Bundle args = new Bundle();
        args.putString(SINGER_MID, singermid);
        args.putString(SINGER_NAME, singername);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.singermid = getArguments().getString(SINGER_MID);
            this.singername = getArguments().getString(SINGER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singer, container, false);

        appBarLayout = (AppBarLayout) view.findViewById(R.id.singer_appbarlayout);
        toolbar = (Toolbar) view.findViewById(R.id.singer_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        singer_img = (ImageView) view.findViewById(R.id.singer_singer_img);
        tabLayout = (TabLayout) view.findViewById(R.id.singer_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.singer_viewpager);
        setFragment();
        setChildFragment();

        return view;
    }

    private void setFragment() {
        toolbar.setTitle(singername);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSingerImage();
    }

    private void setSingerImage() {
        String api = getContext().getString(R.string.singer_image_api);
        api = api.replace("{0}", "300");
        api = api.replace("{1}", singermid);
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                singer_img_bitmap = BitmapFactory.decodeStream(inputStream);
                ((AppCompatActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        singer_img.setImageBitmap(singer_img_bitmap);
                    }
                });
            }
        });
    }

    private void setChildFragment() {
        pagerAdapter = new SingerFragmentPagerAdapter(getChildFragmentManager(), getContext(), singermid);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
