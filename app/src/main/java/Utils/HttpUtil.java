package utils;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by RodChong on 2016/12/20.
 */

public class HttpUtil {

    private static OkHttpClient client = new OkHttpClient();

    public static void DefaultGetRequest(final String url, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).build();
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    Log.e("request", e.getMessage());
                }
            }
        }).start();
    }

    public static void HasHeaderGetRequest(final String url, final String[] headers, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().header(headers[0], headers[1]).url(url).build();
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    Log.e("request", e.getMessage());
                }
            }
        }).start();
    }
}
