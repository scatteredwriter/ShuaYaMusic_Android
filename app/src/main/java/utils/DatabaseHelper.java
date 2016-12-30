package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by RodChong on 2016/12/30.
 */

public class DatabaseHelper {

    public static boolean AddMusicItem(Music music) {
        try {
            if (GetMusicItem(music.getSongid()) != null)
                return false;
            BaseMusic baseMusic = CastMusicClass.MusicToBaseMusic(music);
            return baseMusic.save();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
            return false;
        }
    }

    public static List<BaseMusic> GetAllMusicItems() {
        try {
            List<BaseMusic> list = DataSupport.findAll(BaseMusic.class);
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static BaseMusic GetMusicItem(String songid) {
        try {
            List<BaseMusic> list = DataSupport.where("songid = ?", songid).find(BaseMusic.class);
            if (list != null && (!list.isEmpty()))
                return list.get(0);
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean DeleteMusicItem(String songid) {
        try {
            DataSupport.deleteAll(BaseMusic.class, "songid = ?", songid);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
