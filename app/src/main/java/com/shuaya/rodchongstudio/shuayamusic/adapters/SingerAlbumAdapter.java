package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.singeralbummodels.SingerAlbum;
import com.shuaya.rodchongstudio.shuayamusic.models.singersongmodels.SingerMusic;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;
import com.shuaya.rodchongstudio.shuayamusic.ui.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.DatabaseHelper;
import utils.HttpUtil;

/**
 * Created by RodChong on 2017/1/16.
 */

public class SingerAlbumAdapter extends RecyclerView.Adapter<SingerAlbumAdapter.SingerAlbumHolder> {


    private View view;
    private List<SingerAlbum> list;
    private int resourceId;

    public SingerAlbumAdapter(List<SingerAlbum> list, int resourceId) {
        this.list = list;
        this.resourceId = resourceId;
    }

    @Override
    public SingerAlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        SingerAlbumHolder holder = new SingerAlbumHolder(view);
        holder.rootView.setOnClickListener(new ItemClickListener(holder));
        return holder;
    }

    @Override
    public void onBindViewHolder(final SingerAlbumHolder holder, int position) {
        SingerAlbum album = list.get(position);
        String api = view.getContext().getString(R.string.album_image_api);
        api = api.replace("{0}", "90");
        api = api.replace("{1}", String.valueOf(album.getAlbumMID().charAt(album.getAlbumMID().length() - 2)));
        api = api.replace("{2}", String.valueOf(album.getAlbumMID().charAt(album.getAlbumMID().length() - 1)));
        api = api.replace("{3}", album.getAlbumMID());
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ((AppCompatActivity) view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.album_img.setImageBitmap(bitmap);
                    }
                });
            }
        });
        holder.albumname.setText(album.getAlbumName());
        holder.singername.setText(album.getSingerName());
        holder.public_time.setText(album.getPubTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingerAlbumHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView album_img;
        TextView albumname;
        TextView singername;
        TextView public_time;

        public SingerAlbumHolder(View view) {
            super(view);

            rootView = view;
            album_img = (ImageView) view.findViewById(R.id.album_album_img);
            albumname = (TextView) view.findViewById(R.id.album_albumname);
            singername = (TextView) view.findViewById(R.id.album_singername);
            public_time = (TextView) view.findViewById(R.id.album_public_time);
        }

    }

    public class ItemClickListener implements View.OnClickListener {

        private SingerAlbumHolder holder;

        public ItemClickListener(SingerAlbumHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
/*            int position = holder.getAdapterPosition();
            SingerMusic music = list.get(position);
            DatabaseHelper.AddMusicItem(music);
            ((MusicService.MusicServiceBinder) ((BaseActivity) view.getContext()).GetBinder()).StartPlaying(music);*/
        }
    }

}
