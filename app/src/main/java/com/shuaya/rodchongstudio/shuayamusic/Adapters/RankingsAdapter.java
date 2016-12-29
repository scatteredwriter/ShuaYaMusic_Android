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

import com.shuaya.rodchongstudio.shuayamusic.models.RankingModels.RankingMusic;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.widget.PopupMenu;

import java.util.List;

import utils.Application;
import utils.musicplayer.MusicPlayer;

/**
 * Created by RodChong on 2016/12/20.
 */

public class RankingsAdapter extends ArrayAdapter<RankingMusic> {

    private int resourceId;
    private PopupMenu popupMenu;

    public RankingsAdapter(Context context, int textViewResourceId, List<RankingMusic> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RankingMusic music = getItem(position);
        final View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView number = (TextView) view.findViewById(R.id.ranking_number);
        TextView songname = (TextView) view.findViewById(R.id.ranking_songname);
        TextView singername = (TextView) view.findViewById(R.id.ranking_singername);
        TextView albumname = (TextView) view.findViewById(R.id.ranking_ablumname);
        TextView isonly = (TextView) view.findViewById(R.id.ranking_isonly);
        TextView pay = (TextView) view.findViewById(R.id.ranking_pay);
        ImageView menu_but = (ImageView) view.findViewById(R.id.ranking_menu_but);
        number.setText(String.valueOf(position + 1));
        switch (String.valueOf(position + 1)) {
            case "1":
            case "2":
            case "3":
                number.setTextColor(view.getResources().getColor(R.color.top3_color));
                break;
            default:
                number.setTextColor(view.getResources().getColor(R.color.second_color));
                break;
        }
        if (music.getData().getIsonly() == 1)
            isonly.setVisibility(View.VISIBLE);
        if (music.getData().getPay().getPaydownload() == 1)
            pay.setVisibility(View.VISIBLE);
        songname.setText(music.getData().getSongorig());
        singername.setText(music.getData().getSinger().get(0).getName());
        albumname.setText(music.getData().getAlbumname());
        menu_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu((AppCompatActivity) getContext(), new OnMenuItemClick(music), music);
                popupMenu.showAtLocation(((AppCompatActivity) getContext()).findViewById(R.id.activity_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        return view;
    }

    public class OnMenuItemClick implements AdapterView.OnItemClickListener {

        private RankingMusic music;

        public OnMenuItemClick(RankingMusic music) {
            this.music = music;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView pop_menu_item = (TextView) view.findViewById(R.id.pop_menu_text);
            String text = pop_menu_item.getText().toString();
            if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[0])) {
                music.getData().setSongname(music.getData().getSongorig());
                MusicPlayer.Instance.StartPlaying(music.getData());
            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[1])) {

            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[2])) {

            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[3])) {

            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[4])) {

            }
            popupMenu.dismiss();
        }
    }

}
