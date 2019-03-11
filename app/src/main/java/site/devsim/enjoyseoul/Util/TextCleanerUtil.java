package site.devsim.enjoyseoul.Util;

public class TextCleanerUtil {
    public static String cleanText(String s){
        s = s
                .replaceAll("&quot;","\"")
                .replaceAll("&#39;","\'");
        return s;
    }
}
