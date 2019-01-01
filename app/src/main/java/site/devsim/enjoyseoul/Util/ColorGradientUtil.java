package site.devsim.enjoyseoul.Util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import site.devsim.enjoyseoul.DB.DBManager;
import site.devsim.enjoyseoul.R;

public class ColorGradientUtil {

    public static void applyGenreColorGradient(Context context,String genre, View view){
        GradientDrawable gradient;
        String[] colors = context.getResources().getStringArray(R.array.genre_colors);

        DBManager db = new DBManager(context);
        ArrayList<String> genreList = db.getGenreNames();
        genreList.add(0,"전체");
        db.close();

        HashMap<String, Integer> colorMap = new HashMap<String,Integer>();
        int idx = 0;
        for(String genreKey : genreList){
            colorMap.put(genreKey,idx++);
        }

        int value = colorMap.get(genre);
        gradient = (GradientDrawable) view.getBackground();
        gradient.setColor(Color.parseColor(colors[value]));
    }

    public static void applyFeeColorGradient(Context context,String isFree, View view){
        GradientDrawable gradient;
        int colorId = 0;
        if(isFree.equals("0")){
            colorId = R.color.feeNotFree;
        }else if(isFree.equals("1")){
            colorId = R.color.feeFree;
        }
        if(colorId != 0) {
            int color = ContextCompat.getColor(context, colorId);
            gradient = (GradientDrawable) view.getBackground();
            gradient.setColor(color);
        }
    }
}
