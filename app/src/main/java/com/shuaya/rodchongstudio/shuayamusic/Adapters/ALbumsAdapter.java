package com.shuaya.rodchongstudio.shuayamusic.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.models.albummodels.Album;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by RodChong on 2016/12/21.
 */

public class ALbumsAdapter extends ArrayAdapter<Album> {

    private int resourceId;

    public ALbumsAdapter(Context context, int textViewResourceId, List<Album> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Album album = getItem(position);
        final View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        final ImageView album_img = (ImageView) view.findViewById(R.id.album_img);
        TextView album_name = (TextView) view.findViewById(R.id.album_albumname);
        TextView singer_name = (TextView) view.findViewById(R.id.album_singername);
        TextView public_time = (TextView) view.findViewById(R.id.album_public_time);
        String api = getContext().getString(R.string.album_image_api);
        api = api.replace("{0}", "300");
        api = api.replace("{1}", String.valueOf(album.getAlbum_mid().charAt(album.getAlbum_mid().length() - 2)));
        api = api.replace("{2}", String.valueOf(album.getAlbum_mid().charAt(album.getAlbum_mid().length() - 1)));
        api = api.replace("{3}", album.getAlbum_mid());
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ((AppCompatActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        album_img.setImageBitmap(bitmap);
                    }
                });
            }
        });
        album_name.setText(album.getAlbum_name());
        singer_name.setText(album.getSinger_name());
        public_time.setText(album.getPublic_time());
        return view;
    }
}
