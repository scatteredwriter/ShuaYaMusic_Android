package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.shuaya.rodchongstudio.shuayamusic.R;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicWallFragment extends Fragment {

    private FragmentManager fragmentManager;
    private Fragment rankingsFragment;
    private Fragment albumsFragment;

    public MusicWallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_wall, container, false);
        setToolBar(view);
        setSegmentedGroup(view);
        setMenuButton(view);
        InitChildFragment();
        setRadioButton(view);
        return view;
    }

    private void setToolBar(View view) {
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.music_wall_toolbar));
    }

    private void setSegmentedGroup(View view) {
        SegmentedGroup segmentedGroup = (SegmentedGroup) view.findViewById(R.id.music_wall_segmented);
        RadioButton button = (RadioButton) view.findViewById(R.id.music_wall_rankings);
        button.setChecked(true);
    }

    private void setRadioButton(View view) {
        RadioButton rankings_but = (RadioButton) view.findViewById(R.id.music_wall_rankings);
        RadioButton albums_but = (RadioButton) view.findViewById(R.id.music_wall_albums);
        if (fragmentManager != null) {
            Music_Wall_Button_OnClickListener onClickListener = new Music_Wall_Button_OnClickListener(fragmentManager, rankingsFragment, albumsFragment);
            rankings_but.setOnClickListener(onClickListener);
            albums_but.setOnClickListener(onClickListener);
        }
    }

    private void InitChildFragment() {
        rankingsFragment = new RankingsFragment();
        albumsFragment = new AlbumsFragment();
        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.music_wall_fragment, rankingsFragment);
        transaction.commit();
    }

    private void setMenuButton(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.music_wall_menu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openMenu();
            }
        });
    }

    public class Music_Wall_Button_OnClickListener implements View.OnClickListener {

        private FragmentManager fragmentManager;
        private Fragment fragment1;
        private Fragment fragment2;

        public Music_Wall_Button_OnClickListener(FragmentManager fragmentManager, Fragment fragment1, Fragment fragment2) {
            this.fragmentManager = fragmentManager;
            this.fragment1 = fragment1;
            this.fragment2 = fragment2;
        }

        @Override
        public void onClick(View v) {
            if (fragmentManager != null && fragment1 != null && fragment2 != null) {
                RadioButton button = (RadioButton) v;
                String text = button.getText().toString();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                final String tab1 = getString(R.string.albums_anme);
                final String tab2 = getString(R.string.rankings_name);
                if (text == tab1) {
                    if (fragment2.isAdded()) {
                        transaction.hide(fragment1).show(fragment2).commit();
                    } else {
                        transaction.hide(fragment1).add(R.id.music_wall_fragment, fragment2).commit();
                    }
                } else if (text == tab2) {
                    if (fragment1.isAdded()) {
                        transaction.hide(fragment2).show(fragment1).commit();
                    } else {
                        transaction.hide(fragment2).add(R.id.music_wall_fragment, fragment1).commit();
                    }
                }
            }
        }
    }
}
