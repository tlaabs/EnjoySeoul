package site.devsim.enjoyseoul.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import site.devsim.enjoyseoul.R;

public class DBManager {
    private static final String TAG = "DBManager";
    Context context;
    private String EVENT_TABLE = null;

    SQLiteDatabase db;

    public DBManager(Context context){
        this.context = context;
        EVENT_TABLE = context.getString(R.string.event_table);
        db = context.openOrCreateDatabase(context.getString(R.string.db_name),context.MODE_PRIVATE, null);
    }

    public ArrayList<String> getGenreNames(){
        ArrayList<String> genreList = new ArrayList<String>();

        String sql = "SELECT GENRE FROM " + EVENT_TABLE + " GROUP BY GENRE";
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();

        if(cursor != null && count != 0){
            cursor.moveToFirst();
            for(int i = 0 ; i < count; i++){
                String genre = cursor.getString(0).trim();
                genreList.add(genre);
                cursor.moveToNext();
            }
        }

        return genreList;

    }

    public void close(){
        db.close();
    }
}
