package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;

import java.util.List;

import utils.Application;
import utils.DatabaseHelper;

/**
 * Created by RodChong on 2016/12/30.
 */

public class PlaylistAdapter extends ArrayAdapter<BaseMusic> {

    private int resourceId;

    public PlaylistAdapter(Context context, int textViewResourceId, List<BaseMusic> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseMusic music = getItem(position);
        final View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView songname = (TextView) view.findViewById(R.id.pop_playlist_songname);
        TextView singername = (TextView) view.findViewById(R.id.pop_playlist_singername);
        ImageView delete_but = (ImageView) view.findViewById(R.id.pop_playlist_delete_but);
        songname.setText(music.getSongname());
        singername.setText(music.getBasesinger());
        delete_but.setOnClickListener(new OnDeleteClick(position, music));
        return view;
    }

    public class OnDeleteClick implements View.OnClickListener {

        private int position;
        private BaseMusic music;

        public OnDeleteClick(int position, BaseMusic music) {
            this.position = position;
            this.music = music;
        }

        @Override
        public void onClick(View v) {
            if (DatabaseHelper.DeleteMusicItem(music.getSongid()))
                PlaylistAdapter.this.remove(PlaylistAdapter.this.getItem(position));
        }
    }

}
