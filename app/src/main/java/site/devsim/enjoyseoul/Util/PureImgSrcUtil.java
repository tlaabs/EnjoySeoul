package site.devsim.enjoyseoul.Util;

public class PureImgSrcUtil {
    public static String in(String src){
        String url = "";
        String[] mainImg = src.split("\\/");
        for (int i = 0; i < mainImg.length; i++) {
            if ((i == 0) || (i == 1) || (i == 2)) {
                url = url + mainImg[i].toLowerCase() + "/";
            } else if (i == mainImg.length - 1) {
                url = url + mainImg[i];
            } else {
                url = url + mainImg[i] + "/";
            }
        }
        return url;
    }
}
