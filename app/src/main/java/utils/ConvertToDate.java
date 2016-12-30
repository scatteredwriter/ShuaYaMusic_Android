package utils;

/**
 * Created by RodChong on 2016/12/30.
 */

public class ConvertToDate {

    public static String Convert(int duration) {
        duration /= 1000;
        int min = duration / 60;
        int second = duration % 60;
        String re_min = null, re_second = null;
        if (min < 10) {
            re_min = "0" + String.valueOf(min);
        } else {
            re_min = String.valueOf(min);
        }
        if (second < 10) {
            re_second = "0" + String.valueOf(second);
        } else {
            re_second = String.valueOf(second);
        }
        return re_min + ":" + re_second;
    }

}
