package com.shuaya.rodchongstudio.shuayamusic.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.adapters.RankingsAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.RankingModels.RankingBean;
import com.shuaya.rodchongstudio.shuayamusic.models.RankingModels.RankingMusic;
import com.shuaya.rodchongstudio.shuayamusic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.HttpUtil;
import utils.musicplayer.MusicPlayer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RankingFragment extends Fragment {

    public RankingFragment() {
    }

    private List<RankingMusic> Pop_lists;
    private List<RankingMusic> Hot_lists;
    private List<RankingMusic> New_lists;
    private List<RankingMusic> Mainland_lists;
    private List<RankingMusic> Tw_lists;
    private List<RankingMusic> Usa_lists;
    private List<RankingMusic> Kr_lists;
    private List<RankingMusic> Jp_lists;
    private List<RankingMusic> Musician_lists;
    private List<RankingMusic> Billboard_lists;

    public static final String ARGS_ID = "args_id";
    private int mId;

    public static RankingFragment newInstance(int id) {
        Bundle args = new Bundle();

        args.putInt(ARGS_ID, id);
        RankingFragment fragment = new RankingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt(ARGS_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        String id = getString(mId);
        String api = getString(R.string.rankings_api);
        api = api.replace("{0}", id);
        final ListView listView = (ListView) view.findViewById(R.id.rankings_listview);
        listView.setOnItemClickListener(new OnRankingListViewItemClick());
        final RankingsAdapter rankingAdapter = new RankingsAdapter(getActivity(), R.layout.ranking_item, new ArrayList<RankingMusic>());
        listView.setAdapter(rankingAdapter);
        HttpUtil.DefaultGetRequest(api, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                if (result != null && (!result.isEmpty())) {
                    Gson gson = new Gson();
                    RankingBean rankingBean = gson.fromJson(result, RankingBean.class);
                    List<RankingMusic> rankingMusics = rankingBean.getSonglist();
                    if (rankingMusics == null)
                        return;
                    InitListView(mId, rankingMusics, rankingAdapter);
                }
            }
        });
        return view;
    }

    private void InitListView(int mId, List<RankingMusic> rankingMusics, final RankingsAdapter rankingAdapter) {
        switch (mId) {
            case R.string.pop_id:
                Pop_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Pop_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.new_id:
                New_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : New_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.hot_id:
                Hot_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Hot_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.mainland_id:
                Mainland_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Mainland_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.tw_id:
                Tw_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Tw_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.usa_id:
                Usa_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Usa_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.kr_id:
                Kr_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Kr_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.jp_id:
                Jp_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Jp_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.musician_id:
                Musician_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Musician_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
            case R.string.billboard_id:
                Billboard_lists = rankingMusics;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (RankingMusic item : Billboard_lists) {
                            rankingAdapter.add(item);
                        }
                    }
                });
                break;
        }
    }

    public class OnRankingListViewItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            RankingMusic music = (RankingMusic) parent.getAdapter().getItem(position);
            MusicPlayer.Instance.StartPlaying(music.getData());
        }
    }

}