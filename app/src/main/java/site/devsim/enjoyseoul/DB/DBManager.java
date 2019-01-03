package site.devsim.enjoyseoul.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.EventLog;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import site.devsim.enjoyseoul.DB.POJO.EventItem;
import site.devsim.enjoyseoul.R;
import site.devsim.enjoyseoul.Util.TextCleanerUtil;

public class DBManager {
    private static final String TAG = "DBManager";
    Context context;
    private String EVENT_TABLE = null;

    SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        EVENT_TABLE = context.getString(R.string.event_table);
        db = context.openOrCreateDatabase(context.getString(R.string.db_name), context.MODE_PRIVATE, null);
    }

    public ArrayList<String> getGenreNames() {
        ArrayList<String> genreList = new ArrayList<String>();

        String sql = "SELECT GENRE FROM " + EVENT_TABLE + " GROUP BY GENRE";
        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();

        if (cursor != null && count != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < count; i++) {
                String genre = cursor.getString(0).trim();
                genreList.add(genre);
                cursor.moveToNext();
            }
        }

        return genreList;

    }

    public ArrayList<EventItem> getAllEvents() {
        ArrayList<EventItem> eventList;
        String sql = "SELECT * FROM " + EVENT_TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        eventList = unpackCursor(cursor);
        return eventList;
    }

    public ArrayList<EventItem> runSql(String sql){
        ArrayList<EventItem> eventList;
        Cursor cursor = db.rawQuery(sql, null);
        eventList = unpackCursor(cursor);
        return eventList;
    }

    public ArrayList<EventItem> getLikes(){
        String sql = "SELECT ID FROM " + context.getString(R.string.like_table);

        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        ArrayList<String> eventList = new ArrayList<String>();
        sql = "SELECT * FROM " + context.getString(R.string.event_table) + " WHERE ";
        if (cursor != null && count != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < count; i++) {
                eventList.add(cursor.getString(0));
                sql += "ID=" + cursor.getString(0);
                if (i < count - 1)
                    sql += " OR ";
                cursor.moveToNext();
            }
        }

        return runSql(sql);
    }

    private ArrayList<EventItem> unpackCursor(Cursor cursor) {
        ArrayList<EventItem> eventList = new ArrayList<EventItem>();

        int count = cursor.getCount();

        if (cursor != null && count != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < count; i++) {
                EventItem item = new EventItem();
                item.setId(cursor.getString(0));
                item.setGenre(cursor.getString(1));
                item.setTitle(TextCleanerUtil.cleanText(cursor.getString(2)));
                item.setStartDate(cursor.getString(3));
                item.setEndDate(cursor.getString(4));
                item.setTime(cursor.getString(5));
                item.setPlace(cursor.getString(6));
                item.setOrgLink(cursor.getString(7));
                item.setMainImg(cursor.getString(8));
                item.setTarget(cursor.getString(9));
                item.setFee(cursor.getString(10));
                item.setInquiry(cursor.getString(11));
                item.setEtcDesc(cursor.getString(12));
                item.setIsFree(cursor.getString(13));

                eventList.add(item);
                cursor.moveToNext();
            }
        }
        return eventList;
    }
    public boolean isLikeExist(String id){
        String sql = "SELECT * FROM " + context.getString(R.string.like_table) + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }
    public void addToggleLike(String id){
        String sql;
        if (!isLikeExist(id)) {
            sql = "INSERT INTO " + context.getString(R.string.like_table) + " values(" + id + ")";
        } else {
            sql = "DELETE FROM " + context.getString(R.string.like_table) + " WHERE ID = " + id;
        }
        db.execSQL(sql);
    }
    public void close() {
        db.close();
    }
}
