package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.rankingmodels.RankingMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.singersongmodels.SingerMusic;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.ui.BaseActivity;
import com.shuaya.rodchongstudio.shuayamusic.ui.SingerFragment;
import com.shuaya.rodchongstudio.shuayamusic.widget.PopupMenu;

import java.util.List;

import utils.Application;
import utils.DatabaseHelper;

/**
 * Created by RodChong on 2017/1/15.
 */

public class SingerSongAdapter extends RecyclerView.Adapter<SingerSongAdapter.SingerSongHolder> {

    private View view;
    private List<SingerMusic> list;
    private int resourceId;

    private PopupMenu popupMenu;

    public SingerSongAdapter(List<SingerMusic> list, int resourceId) {
        this.list = list;
        this.resourceId = resourceId;
    }

    @Override
    public SingerSongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        SingerSongHolder holder = new SingerSongHolder(view);
        holder.rootView.setOnClickListener(new ItemClickListener(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(SingerSongHolder holder, int position) {
        final SingerMusic music = list.get(position);
        holder.number.setText(String.valueOf(position + 1));
        switch (String.valueOf(position + 1)) {
            case "1":
            case "2":
            case "3":
                holder.number.setTextColor(view.getResources().getColor(R.color.top3_color));
                break;
            default:
                holder.number.setTextColor(view.getResources().getColor(R.color.second_color));
                break;
        }
        if (music.getIsonly() == 1)
            holder.isonly.setVisibility(View.VISIBLE);
        if (music.getPay().getPaydownload() == 1)
            holder.pay.setVisibility(View.VISIBLE);
        holder.songname.setText(music.getSongorig());
        holder.singername.setText(music.getSinger().get(0).getName());
        holder.albumname.setText(music.getAlbumname());
        holder.menu_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu((AppCompatActivity) view.getContext(), new OnMenuItemClick(music), music);
                popupMenu.showAtLocation(((AppCompatActivity) view.getContext()).findViewById(R.id.activity_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingerSongHolder extends RecyclerView.ViewHolder {

        View rootView;
        TextView number;
        TextView songname;
        TextView singername;
        TextView albumname;
        TextView isonly;
        TextView pay;
        ImageView menu_but;

        public SingerSongHolder(View view) {
            super(view);

            rootView = view;
            number = (TextView) view.findViewById(R.id.singer_number);
            songname = (TextView) view.findViewById(R.id.singer_songname);
            singername = (TextView) view.findViewById(R.id.singer_singername);
            albumname = (TextView) view.findViewById(R.id.singer_ablumname);
            isonly = (TextView) view.findViewById(R.id.singer_isonly);
            pay = (TextView) view.findViewById(R.id.singer_pay);
            menu_but = (ImageView) view.findViewById(R.id.singer_menu_but);
        }

    }

    public class ItemClickListener implements View.OnClickListener {

        private SingerSongHolder holder;

        public ItemClickListener(SingerSongHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int position = holder.getAdapterPosition();
            SingerMusic music = list.get(position);
            DatabaseHelper.AddMusicItem(music);
            ((MusicService.MusicServiceBinder) ((BaseActivity) view.getContext()).GetBinder()).StartPlaying(music);
        }
    }

    public class OnMenuItemClick implements AdapterView.OnItemClickListener {

        private SingerMusic music;

        public OnMenuItemClick(SingerMusic music) {
            this.music = music;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView pop_menu_item = (TextView) view.findViewById(R.id.pop_menu_text);
            String text = pop_menu_item.getText().toString();
            if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[0])) { //播放
                music.setSongname(music.getSongorig());
                DatabaseHelper.AddMusicItem(music);
                ((MusicService.MusicServiceBinder) ((BaseActivity) view.getContext()).GetBinder()).StartPlaying(music);
            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[1])) { //喜欢

            } else if (text.equals(Application.getContext().getResources().getStringArray(R.array.listview_menus)[2])) { //添加到播放列表
                DatabaseHelper.AddMusicItem(music);
                Toast.makeText(view.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[3])) { //下载

            } else if (text.contains(Application.getContext().getResources().getStringArray(R.array.listview_menus)[5])) { //专辑详情

            }
            popupMenu.dismiss();
        }
    }
}
