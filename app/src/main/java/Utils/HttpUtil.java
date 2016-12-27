package utils;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by RodChong on 2016/12/20.
 */

public class HttpUtil {

    static OkHttpClient client = new OkHttpClient();

    public static void DefaultGetRequest(final String url, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    Log.e("a", e.getMessage());
                }
            }
        }).start();
    }
}
