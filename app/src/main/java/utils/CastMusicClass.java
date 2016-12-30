package utils;

import com.shuaya.rodchongstudio.shuayamusic.models.BaseMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.Music;
import com.shuaya.rodchongstudio.shuayamusic.models.SingerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RodChong on 2016/12/30.
 */

public class CastMusicClass {

    public static BaseMusic MusicToBaseMusic(Music music) {
        BaseMusic baseMusic = new BaseMusic();
        baseMusic.setAlbummid(music.getAlbummid());
        baseMusic.setAlbumid(music.getAlbumid());
        baseMusic.setAlbumname(music.getAlbumname());
        baseMusic.setSongname(music.getSongname());
        baseMusic.setAlertid(music.getAlertid());
        baseMusic.setBasesinger(music.getSinger().get(0).getName());
        baseMusic.setInterval(music.getInterval());
        baseMusic.setIsonly(music.getIsonly());
        baseMusic.setSize128(music.getSize128());
        baseMusic.setSize320(music.getSize320());
        baseMusic.setSizeape(music.getSizeape());
        baseMusic.setSizeflac(music.getSizeflac());
        baseMusic.setSizeogg(music.getSizeogg());
        baseMusic.setSongid(music.getSongid());
        baseMusic.setStream(music.getStream());
        baseMusic.setSongmid(music.getSongmid());
        return baseMusic;
    }

    public static Music BaseMusicToMusic(BaseMusic music) {
        Music baseMusic = new Music();
        SingerBean singerBean = new SingerBean();
        List<SingerBean> singerBeanList = new ArrayList<>();
        baseMusic.setAlbummid(music.getAlbummid());
        baseMusic.setAlbumid(music.getAlbumid());
        baseMusic.setAlbumname(music.getAlbumname());
        baseMusic.setSongname(music.getSongname());
        baseMusic.setAlertid(music.getAlertid());
        singerBean.setName(music.getBasesinger());
        singerBeanList.add(singerBean);
        baseMusic.setSinger(singerBeanList);
        baseMusic.setInterval(music.getInterval());
        baseMusic.setIsonly(music.getIsonly());
        baseMusic.setSize128(music.getSize128());
        baseMusic.setSize320(music.getSize320());
        baseMusic.setSizeape(music.getSizeape());
        baseMusic.setSizeflac(music.getSizeflac());
        baseMusic.setSizeogg(music.getSizeogg());
        baseMusic.setSongid(music.getSongid());
        baseMusic.setStream(music.getStream());
        baseMusic.setSongmid(music.getSongmid());
        return baseMusic;
    }

}
