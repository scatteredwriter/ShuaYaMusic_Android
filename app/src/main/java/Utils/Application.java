package utils;

/**
 * Created by RodChong on 2016/12/27.
 */

import android.content.Context;

import org.litepal.LitePalApplication;

public class Application extends LitePalApplication {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public void onCreate() {
        context = getApplicationContext();
    }
}
