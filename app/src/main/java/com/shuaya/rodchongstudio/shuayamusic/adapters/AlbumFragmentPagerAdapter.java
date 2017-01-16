package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.ui.AlbumSongFragment;
import com.shuaya.rodchongstudio.shuayamusic.ui.SingerAlbumFragment;
import com.shuaya.rodchongstudio.shuayamusic.ui.SingerSongFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RodChong on 2017/1/16.
 */

public class AlbumFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String albummid;
    private String[] tab_lists;
    private List<Fragment> fragment_lists = new ArrayList<>();

    public AlbumFragmentPagerAdapter(FragmentManager fragmentManager, Context context, String albummid) {
        super(fragmentManager);
        this.context = context;
        this.albummid = albummid;
        InitTabLists();
        InitFragmentLists();
    }

    private void InitTabLists() {
        tab_lists = context.getResources().getStringArray(R.array.album_tabs);
    }

    private void InitFragmentLists() {
        fragment_lists.add(AlbumSongFragment.newInstance(albummid));
        fragment_lists.add(AlbumSongFragment.newInstance(albummid));
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_lists.get(position);
    }

    @Override
    public int getCount() {
        return tab_lists.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_lists[position];
    }

}
