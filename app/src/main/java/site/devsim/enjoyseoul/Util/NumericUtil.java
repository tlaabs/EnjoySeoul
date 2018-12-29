package site.devsim.enjoyseoul.Util;

public class NumericUtil {
    public static boolean isNumeric(String str){
        try{
            int d = Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
}
