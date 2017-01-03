package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by RodChong on 2016/12/29.
 */

public class UnicodeEncoder {

    public static String Encoder(String source) {
        String regExp = "&#.*?;";
        Matcher m = Pattern.compile(regExp).matcher(source);
        while (m.find()) {
            String group = m.group();
            String code = group.replaceAll("(&#|;)", "");
            source = source.replaceAll(group, (char) Integer.decode(code).intValue() + "");
        }
        return source;
    }

}
