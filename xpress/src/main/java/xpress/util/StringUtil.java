package xpress.util;

import java.util.List;


public class StringUtil {

    public static String join (List<String> lst, String joiner){
        String ret = "";
        for (String s: lst) {
            ret += s + joiner;
        }
        return replaceLast(ret, joiner);
    }

    public static String replaceLast(String ret, String joiner) {
        return ret.substring(0, ret.lastIndexOf(joiner)) +
                ret.substring(ret.lastIndexOf(joiner) + joiner.length());
    }

    public static String getGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
