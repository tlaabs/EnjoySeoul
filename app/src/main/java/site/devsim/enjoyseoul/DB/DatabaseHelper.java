package site.devsim.enjoyseoul.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import site.devsim.enjoyseoul.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "enjoy.db";
    private String TABLE_EVENT;
    private String TABLE_LIKE;

    private static final int DB_VERSION = 1;

    DatabaseHelper(Context context){
        super(context,context.getString(R.string.db_name),null,DB_VERSION);
        TABLE_EVENT  = context.getString(R.string.event_table);
        TABLE_LIKE = context.getString(R.string.like_table);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EVENT + "("
                + "ID INTEGER PRIMARY KEY, " //문화행사코드
                + "GENRE TEXT, " //장르명
                + "TITLE TEXT, " //제목
                + "START_DATE DATE, " //시작일자
                + "END_DATE DATE, " //종료일자
                + "TIME TEXT, " //시간
                + "PLACE TEXT, " //장소
                + "ORG_LINK TEXT, " //원문링크주소
                + "MAIN_IMG TEXT, " //대표이미지
                + "TARGET TEXT, " //이용대상
                + "FEE TEXT, " //이용요금
                + "INQUIRY TEXT, " //문의
                + "ETC_DESC TEXT, " //기타내용
                + "IS_FREE INTEGER);");//무료구분

        //찜
        db.execSQL("CREATE TABLE " + TABLE_LIKE + "("
                + "ID INTEGER PRIMARY KEY );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKE);
        onCreate(db);
    }
}
