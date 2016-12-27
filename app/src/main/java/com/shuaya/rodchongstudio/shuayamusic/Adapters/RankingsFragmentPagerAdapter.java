package com.shuaya.rodchongstudio.shuayamusic.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shuaya.rodchongstudio.shuayamusic.Ui.RankingFragment;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.util.ArrayList;

/**
 * Created by RodChong on 2016/12/20.
 */

public class RankingsFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] tab_lists;

    public RankingsFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
        InitTab_Lists();
    }

    private void InitTab_Lists() {
        tab_lists = context.getResources().getStringArray(R.array.rankings_list);
    }

    @Override
    public Fragment getItem(int position) {
        int id = 0;
        switch (position) {
            case 0:
                id = R.string.pop_id;
                break;
            case 1:
                id = R.string.new_id;
                break;
            case 2:
                id = R.string.hot_id;
                break;
            case 3:
                id = R.string.mainland_id;
                break;
            case 4:
                id = R.string.tw_id;
                break;
            case 5:
                id = R.string.usa_id;
                break;
            case 6:
                id = R.string.kr_id;
                break;
            case 7:
                id = R.string.jp_id;
                break;
            case 8:
                id = R.string.musician_id;
                break;
            case 9:
                id = R.string.billboard_id;
                break;
        }
        return RankingFragment.newInstance(id);
    }

    @Override
    public int getCount() {
        if (tab_lists != null)
            return tab_lists.length;
        else
            return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tab_lists[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
