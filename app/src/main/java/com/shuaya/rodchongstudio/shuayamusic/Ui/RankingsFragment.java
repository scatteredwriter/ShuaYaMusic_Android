package com.shuaya.rodchongstudio.shuayamusic.Ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shuaya.rodchongstudio.shuayamusic.Adapters.RankingsFragmentPagerAdapter;
import com.shuaya.rodchongstudio.shuayamusic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {


    public RankingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rankings, container, false);
        setTab(view);

        return view;
    }

    private void setTab(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.rankings_viewpager);
        RankingsFragmentPagerAdapter adapter = new RankingsFragmentPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.rankings_tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

}
