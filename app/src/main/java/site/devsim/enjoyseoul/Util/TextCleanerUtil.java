package site.devsim.enjoyseoul.Util;

public class TextCleanerUtil {
    public static String cleanText(String s){
        String result = s;
        result = result.replaceAll("&quot;","\"");
        result = result.replaceAll("&#39;","\'");
        return result;
    }
}
