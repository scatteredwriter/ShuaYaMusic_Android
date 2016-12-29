package com.shuaya.rodchongstudio.shuayamusic.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.io.IOException;
import java.io.InputStream;

import utils.HttpUtil;
import utils.musicplayer.MusicPlayer;
import utils.musicplayer.ReceiveMusic;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements ReceiveMusic {

    private static DrawerLayout drawerLayout;
    private MusicWallFragment musicWallFragment;
    private SearchFragment searchFragment;
    private Fragment current_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicPlayer.Instance.AddMusicChangedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        InitMenu();
        ChangedFragment(MusicWallFragment.class);
    }

    public static void openMenu() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private void InitMenu() {
        NavigationView navigationView = (NavigationView) drawerLayout.findViewById(R.id.main_navigationview);
        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.main_menu_musicwall);
        item.setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_musicwall:
                        ChangedFragment(MusicWallFragment.class);
                        break;
                    case R.id.main_menu_search:
                        ChangedFragment(SearchFragment.class);
                        break;
                    case R.id.main_menu_setting:
                        return false;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        navigationView.findFocus();
    }

    private void ChangedFragment(Class<?> fragment_class) {
        if (current_fragment == null) { //初始化
            musicWallFragment = new MusicWallFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_framelayout, musicWallFragment);
            current_fragment = musicWallFragment;
            transaction.commit();
        } else if (current_fragment.getClass() != MusicWallFragment.class) { //不从音乐墙到别的Fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(current_fragment);
            if (fragment_class == SearchFragment.class) {
                searchFragment = new SearchFragment();
                transaction.add(R.id.main_framelayout, searchFragment);
                current_fragment = searchFragment;
            } else if (fragment_class == MusicWallFragment.class) {
                transaction.show(musicWallFragment);
                current_fragment = musicWallFragment;
            }
            transaction.commit();
        } else if (current_fragment.getClass() == MusicWallFragment.class) { //从音乐墙到别的Fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment_class == SearchFragment.class) {
                searchFragment = new SearchFragment();
                transaction.hide(current_fragment);
                transaction.add(R.id.main_framelayout, searchFragment);
                current_fragment = searchFragment;
                transaction.commit();

            }
        }
    }

    public void GetMusic(Music music, int duration) {
        final ImageView music_img = (ImageView) findViewById(R.id.current_music_img);
        TextView music_songname = (TextView) findViewById(R.id.current_music_songname);
        TextView muusic_singername = (TextView) findViewById(R.id.current_music_singername);
        ProgressBar music_progress = (ProgressBar) findViewById(R.id.current_music_progress);
        final ImageView pause_but = (ImageView) findViewById(R.id.current_music_pause_but);
        String api = getString(R.string.album_image_api);
        api = api.replace("{0}", "90");
        api = api.replace("{1}", String.valueOf(music.getAlbummid().charAt(music.getAlbummid().length() - 2)));
        api = api.replace("{2}", String.valueOf(music.getAlbummid().charAt(music.getAlbummid().length() - 1)));
        api = api.replace("{3}", music.getAlbummid());
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        music_img.setImageBitmap(bitmap);
                    }
                });
            }
        });
        music_songname.setText(music.getSongname());
        muusic_singername.setText((music.getSinger().get(0)).getName());
        music_progress.setMax(duration);
        pause_but.setImageResource(R.mipmap.ic_pause_circle_filled_black_48dp);
        pause_but.setOnClickListener(new OnPuaseClick(pause_but));
    }

    public void ProgressChanged(int position) {
        ((ProgressBar) findViewById(R.id.current_music_progress)).setProgress(position);
    }

    public void PlayingCompleted() {
        ImageView pause_but = (ImageView) findViewById(R.id.current_music_pause_but);
        pause_but.setImageResource(R.mipmap.ic_play_circle_filled_black_48dp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        super.onBackPressed();
    }

    public class OnPuaseClick implements View.OnClickListener {

        private ImageView imageView;

        public OnPuaseClick(ImageView imageView) {
            this.imageView = imageView;
        }

        public void onClick(View paramView) {
            switch (MusicPlayer.Instance.GetCurrentPlayState()) {
                case PLAYING:
                    if (MusicPlayer.Instance.Pause())
                        this.imageView.setImageResource(R.mipmap.ic_play_circle_filled_black_48dp);
                    break;
                case PAUSE:
                    if (MusicPlayer.Instance.Start())
                        this.imageView.setImageResource(R.mipmap.ic_pause_circle_filled_black_48dp);
                    break;
                case NOSTATR:
                case COMPLETED:
            }
        }
    }

}
