package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.ui.PlayInfoFragment;
import com.shuaya.rodchongstudio.shuayamusic.ui.PlayLyricFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RodChong on 2017/1/2.
 */

public class PlayActivityPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private MusicService.MusicServiceBinder binder;
    private List<Fragment> fragments;
    private PlayInfoFragment playInfoFragment;
    private PlayLyricFragment playLyricFragment;

    public PlayActivityPagerAdapter(FragmentManager fm, Context context, MusicService.MusicServiceBinder binder) {
        super(fm);

        this.context = context;
        fragments = new ArrayList<>();
        playInfoFragment = new PlayInfoFragment();
        playLyricFragment = new PlayLyricFragment();
        fragments.add(playInfoFragment);
        fragments.add(playLyricFragment);
        this.binder = binder;
        playInfoFragment.setContext(context, binder);
        playLyricFragment.setContext(context, binder);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
