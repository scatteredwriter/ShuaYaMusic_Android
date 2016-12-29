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

import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.RankingModels.RankingMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.SearchModels.SearchMusic;
import com.shuaya.rodchongstudio.shuayamusic.widget.PopupMenu;

import java.util.List;

import utils.Application;
import utils.musicplayer.MusicPlayer;

/**
 * Created by RodChong on 2016/12/19.
 */

public class SearchAdapter extends ArrayAdapter<SearchMusic> {

    private int resourceId;
    private PopupMenu popupMenu;

    public SearchAdapter(Context context, int textViewResourceId, List<SearchMusic> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchMusic music = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView songname = (TextView) view.findViewById(R.id.search_songname);
        TextView singername = (TextView) view.findViewById(R.id.search_singername);
        TextView albumname = (TextView) view.findViewById(R.id.search_ablumname);
        ImageView menu_but = (ImageView) view.findViewById(R.id.search_menu_but);
        songname.setText(music.getSongname());
        singername.setText(music.getSinger().get(0).getName());
        albumname.setText(music.getAlbumname());
        menu_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu((AppCompatActivity) getContext(), new SearchAdapter.OnMenuItemClick(music), music);
                popupMenu.showAtLocation(((AppCompatActivity) getContext()).findViewById(R.id.activity_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        return view;
    }

    public class OnMenuItemClick implements AdapterView.OnItemClickListener {

        private SearchMusic music;

        public OnMenuItemClick(SearchMusic music) {
            this.music = music;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView pop_menu_item = (TextView) view.findViewById(R.id.pop_menu_text);
            String text = pop_menu_item.getText().toString();
            if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[0])) {
                MusicPlayer.Instance.StartPlaying(music);
            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[1])) {

            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[2])) {

            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[3])) {

            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[4])) {

            }
            popupMenu.dismiss();
        }
    }

}
