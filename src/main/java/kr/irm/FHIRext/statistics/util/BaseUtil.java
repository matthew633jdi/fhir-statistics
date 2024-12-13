package kr.irm.FHIRext.statistics.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtil {

    public static String fromSnaketoCamel(String str) {
        Pattern p = Pattern.compile("_(.)");
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

}
