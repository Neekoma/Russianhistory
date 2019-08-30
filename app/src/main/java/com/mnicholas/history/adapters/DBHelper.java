package com.mnicholas.history.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // old = 1
    public static final String DATABASE_NAME = "favoritesDb";

    public static final String TABLE_FAVORITES_NAME = "favorites";
    public static final String KEY_ID = "_id";
    public static final String KEY_FAVORITE = "favorite";

    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_FAVORITES_NAME  + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FAVORITE + " INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES_NAME);
        onCreate(db);
    }


}
