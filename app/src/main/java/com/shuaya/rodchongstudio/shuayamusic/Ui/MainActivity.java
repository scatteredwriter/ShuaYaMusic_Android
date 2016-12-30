package com.shuaya.rodchongstudio.shuayamusic.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.adapters.RankingsAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;
import com.shuaya.rodchongstudio.shuayamusic.musicplayer.PlayState;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import utils.CastMusicClass;
import utils.DatabaseHelper;
import utils.HttpUtil;

import com.shuaya.rodchongstudio.shuayamusic.musicplayer.ReceiveMusic;
import com.shuaya.rodchongstudio.shuayamusic.widget.PopupMenu;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements ReceiveMusic {

    private static DrawerLayout drawerLayout;
    private MusicWallFragment musicWallFragment;
    private SearchFragment searchFragment;
    private Fragment current_fragment;
    public MusicService.MusicServiceBinder binder;
    private ImageView pause_but;
    private ImageView playlist_but;

    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BindMusicService();
        setCurrentLayout();
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        InitMenu();
        ChangedFragment(MusicWallFragment.class);
    }

    @Override
    public IBinder GetBinder() {
        return binder;
    }

    private void BindMusicService() {
        Intent music_zervice = new Intent(this, MusicService.class);
        bindService(music_zervice, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binder = (MusicService.MusicServiceBinder) service;
                binder.AddMusicChangedListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }

    private void setCurrentLayout() {
        RelativeLayout curremt_music = (RelativeLayout) findViewById(R.id.current_music);
        playlist_but = (ImageView) findViewById(R.id.current_music_playlist_but);
        curremt_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binder.GetCurrentMusic() != null) {
                    Intent play_activity = new Intent(MainActivity.this, PlayActivity.class);
                    startActivity(play_activity);
                }
            }
        });
        playlist_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BaseMusic> list = DatabaseHelper.GetAllMusicItems();
                if (list != null) {
                    popupMenu = new PopupMenu(MainActivity.this, new OnPlaylistItemClick(), list);
                    popupMenu.showAtLocation(MainActivity.this.findViewById(R.id.activity_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
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

    public void GetMusic(Music music, int duration, int current_position) {
        final ImageView music_img = (ImageView) findViewById(R.id.current_music_img);
        TextView music_songname = (TextView) findViewById(R.id.current_music_songname);
        TextView muusic_singername = (TextView) findViewById(R.id.current_music_singername);
        ProgressBar music_progress = (ProgressBar) findViewById(R.id.current_music_progress);
        pause_but = (ImageView) findViewById(R.id.current_music_pause_but);
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
        music_progress.setProgress(current_position);
        if (binder.GetCurrentPlayState() == PlayState.PLAYING)
            pause_but.setImageResource(R.mipmap.ic_pause_circle_filled_black_48dp);
        else
            pause_but.setImageResource(R.mipmap.ic_play_circle_filled_black_48dp);
        pause_but.setOnClickListener(new OnPuaseClick());
    }

    public void ProgressChanged(int position) {
        ((ProgressBar) findViewById(R.id.current_music_progress)).setProgress(position);
    }

    public void PlayingCompleted() {
        ImageView pause_but = (ImageView) findViewById(R.id.current_music_pause_but);
        pause_but.setImageResource(R.mipmap.ic_play_circle_filled_black_48dp);
    }

    @Override
    public void Paused(int status) {
        if (status == 0) { //暂停
            pause_but.setImageResource(R.mipmap.ic_play_circle_filled_black_48dp);
        } else if (status == 1) { //播放
            pause_but.setImageResource(R.mipmap.ic_pause_circle_filled_black_48dp);
        }
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

        public void onClick(View paramView) {
            switch (binder.GetCurrentPlayState()) {
                case PLAYING:
                    if (binder.Pause())
                        Paused(0);
                    break;
                case PAUSE:
                    if (binder.Start())
                        Paused(1);
                    break;
                case NOSTATR:
                case COMPLETED:
            }
        }
    }

    public class OnPlaylistItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BaseMusic baseMusic = (BaseMusic) parent.getAdapter().getItem(position);
            MainActivity.this.binder.StartPlaying(CastMusicClass.BaseMusicToMusic(baseMusic));
            popupMenu.dismiss();
        }

    }

}
