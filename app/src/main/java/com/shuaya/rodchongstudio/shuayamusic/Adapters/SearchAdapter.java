package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.util.List;

/**
 * Created by RodChong on 2016/12/19.
 */

public class SearchAdapter extends ArrayAdapter<Music> {

    private int resourceId;

    public SearchAdapter(Context context, int textViewResourceId, List<Music> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Music music = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView songname = (TextView) view.findViewById(R.id.search_songname);
        TextView singername = (TextView) view.findViewById(R.id.search_singername);
        TextView albumname = (TextView) view.findViewById(R.id.search_ablumname);
        songname.setText(music.getSongname());
        singername.setText(music.getSinger().get(0).getName());
        albumname.setText(music.getAlbumname());
        return view;
    }
}
