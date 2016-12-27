package com.shuaya.rodchongstudio.shuayamusic.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.shuaya.rodchongstudio.shuayamusic.models.RankingModels.RankingMusic;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RodChong on 2016/12/26.
 */

public class PopupMenu extends PopupWindow {

    public PopupMenu(Activity context, AdapterView.OnItemClickListener ItemOnClickListener, RankingMusic music) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.pop_menu, null);
        TextView menu_title = (TextView) view.findViewById(R.id.pop_menu_title);
        ListView menu_listView = (ListView) view.findViewById(R.id.pop_menu_listview);
        menu_title.setText("歌曲：" + music.getData().getSongname());
        String[] listview_menus = context.getResources().getStringArray(R.array.listview_menus);
        List<String> menus = new ArrayList<String>();
        for (String item : listview_menus
                ) {
            menus.add(item);
        }
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (String menu : menus) {
            if (menu.equals("查看歌手")) {
                menu = menu + "：" + music.getData().getSinger().get(0).getName();
            } else if (menu.equals("查看专辑")) {
                menu = menu + "：" + music.getData().getAlbumname();
            }
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("text", menu);
            list.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(context, list, R.layout.pop_menu_item, new String[]{"text"}, new int[]{R.id.pop_menu_text});
        menu_listView.setAdapter(adapter);
        menu_listView.setOnItemClickListener(ItemOnClickListener);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_menu_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        setBackgroundDrawable(new ColorDrawable(0xb0000000));
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.pop_menu_animation);
        setContentView(view);
        update();
    }

}
