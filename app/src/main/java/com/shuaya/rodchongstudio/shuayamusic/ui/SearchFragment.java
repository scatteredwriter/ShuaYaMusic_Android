package com.shuaya.rodchongstudio.shuayamusic.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shuaya.rodchongstudio.shuayamusic.R;
import com.shuaya.rodchongstudio.shuayamusic.adapters.SearchAdapter;
import com.shuaya.rodchongstudio.shuayamusic.models.searchmodels.SearchBean;
import com.shuaya.rodchongstudio.shuayamusic.models.searchmodels.SearchMusic;
import com.shuaya.rodchongstudio.shuayamusic.models.searchmodels.ZhidaBean;
import com.shuaya.rodchongstudio.shuayamusic.services.MusicService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.Application;
import utils.DatabaseHelper;
import utils.HttpUtil;
import utils.UnicodeEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ListView listview;
    private SearchView searchview;
    private View listview_header;
    private String keyword;
    private int p;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        setToolBar(view);
        setMenuButton(view);
        setSearchView(view);
        listview_header = inflater.inflate(R.layout.search_zhida, container, false);
        setListView(view);
        return view;
    }

    private void setToolBar(View view) {
        ((AppCompatActivity) getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.search_toolbar));
    }

    private void setMenuButton(View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.search_menu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.openMenu();
            }
        });
    }

    private void setSearchView(View view) {
        searchview = (SearchView) view.findViewById(R.id.search_searchview);
        searchview.setOnQueryTextListener(new OnSearchViewQuery());
    }

    private void setListView(View view) {
        listview = (ListView) view.findViewById(R.id.search_listview);
        listview.setOnItemClickListener(new OnSearchListViewItemClick());
    }

    public class OnSearchViewQuery implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            searchview.clearFocus();
            Search(query);
            listview.setOnScrollListener(new OnListViewScroll());
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

        private void Search(String keyword) {
            p = 1;
            SearchFragment.this.keyword = keyword;
            String api = Application.getContext().getString(R.string.search_api);
            api = api.replace("{0}", keyword);
            api = api.replace("{1}", String.valueOf(p++));
            HttpUtil.DefaultGetRequest(api, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    return;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (json != null) {
                        json = json.replace("callback(", "");
                        json = json.substring(0, json.length() - 1);
                        json = UnicodeEncoder.Encoder(json);
                        Gson gson = new Gson();
                        try {
                            final SearchBean lists = gson.fromJson(json, SearchBean.class);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listview.setAdapter(new SearchAdapter(getContext(), R.layout.search_item, new ArrayList<SearchMusic>()));
                                    try {
                                        SearchAdapter adapter = (SearchAdapter) listview.getAdapter();
                                        adapter.addAll(lists.getData().getSong().getList());
                                    } catch (Exception e) {
                                        SearchAdapter adapter = (SearchAdapter) ((HeaderViewListAdapter) listview.getAdapter()).getWrappedAdapter();
                                        adapter.addAll(lists.getData().getSong().getList());
                                    }
                                }
                            });
                            AddHeader(lists);
                        } catch (Exception e) {
                            return;
                        }
                    }
                }
            });
        }

        private void AddHeader(SearchBean lists) {
            if (lists.getData().getZhida().getType() == 2 || lists.getData().getZhida().getType() == 3) {
                final ZhidaBean zhida = lists.getData().getZhida();
                final ImageView img = (ImageView) listview_header.findViewById(R.id.search_zhida_img);
                final TextView main_text = (TextView) listview_header.findViewById(R.id.search_zhida_main_text);
                final TextView second_text = (TextView) listview_header.findViewById(R.id.search_zhida_second_text);
                String api;
                switch (zhida.getType()) {
                    case 2: //歌手
                        api = getString(R.string.singer_image_api);
                        api = api.replace("{0}", "90");
                        api = api.replace("{1}", String.valueOf(zhida.getSingermid().charAt(zhida.getSingermid().length() - 2)));
                        api = api.replace("{2}", String.valueOf(zhida.getSingermid().charAt(zhida.getSingermid().length() - 1)));
                        api = api.replace("{3}", zhida.getSingermid());
                        HttpUtil.DefaultGetRequest(api, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                return;
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                InputStream inputStream = response.body().byteStream();
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        img.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                main_text.setText(zhida.getSingername());
                                second_text.setText("单曲:" + zhida.getSongnum() + " 专辑:" + zhida.getAlbumnum());
                            }
                        });
                        break;
                    case 3: //专辑
                        api = getString(R.string.album_image_api);
                        api = api.replace("{0}", "90");
                        api = api.replace("{1}", String.valueOf(zhida.getAlbummid().charAt(zhida.getAlbummid().length() - 2)));
                        api = api.replace("{2}", String.valueOf(zhida.getAlbummid().charAt(zhida.getAlbummid().length() - 1)));
                        api = api.replace("{3}", zhida.getAlbummid());
                        HttpUtil.DefaultGetRequest(api, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                return;
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                InputStream inputStream = response.body().byteStream();
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        img.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                main_text.setText(zhida.getAlbumname());
                                second_text.setText(zhida.getSingername());
                            }
                        });
                        break;
                }
                if (listview.getHeaderViewsCount() == 0)
                    listview.addHeaderView(listview_header);
            }
        }
    }

    public class OnListViewScroll implements AbsListView.OnScrollListener {

        private boolean isloading = false;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (isloading == false && keyword != null && totalItemCount != 0 && (firstVisibleItem == (totalItemCount / 3))) {
                isloading = true;
                Addmore();
            }
        }

        private void Addmore() {
            String api = Application.getContext().getString(R.string.search_api);
            api = api.replace("{0}", keyword);
            api = api.replace("{1}", String.valueOf(p++));
            HttpUtil.DefaultGetRequest(api, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    isloading = false;
                    return;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    if (json != null) {
                        json = json.replace("callback(", "");
                        json = json.substring(0, json.length() - 1);
                        json = UnicodeEncoder.Encoder(json);
                        Gson gson = new Gson();
                        try {
                            final SearchBean lists = gson.fromJson(json, SearchBean.class);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        SearchAdapter adapter = (SearchAdapter) listview.getAdapter();
                                        adapter.addAll(lists.getData().getSong().getList());
                                    } catch (Exception e) {
                                        SearchAdapter adapter = (SearchAdapter) ((HeaderViewListAdapter) listview.getAdapter()).getWrappedAdapter();
                                        adapter.addAll(lists.getData().getSong().getList());
                                    }
                                    isloading = false;
                                }
                            });
                        } catch (Exception e) {
                            isloading = false;
                            return;
                        }
                    }
                }
            });
        }
    }

    public class OnSearchListViewItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listview.getHeaderViewsCount() != 0 && position == 0) {
            } else {
                SearchMusic music = (SearchMusic) parent.getAdapter().getItem(position);
                DatabaseHelper.AddMusicItem(music);
                ((MusicService.MusicServiceBinder) ((BaseActivity) getActivity()).GetBinder()).StartPlaying(music);
            }
        }
    }

}
