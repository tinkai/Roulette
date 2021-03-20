package com.example.roulette;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// DB（SQLite）を構築するHelperクラス
public class RouletteOpenHelper extends SQLiteOpenHelper implements RouletteDBSettings {

    // DBのバージョン（SQLite）のバージョン
    private static final int VERSION = 1;

    public RouletteOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // DBを作成
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ROULETTE_TABLE_NAME + " (" +
                ROULETTE_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROULETTE_TABLE_NAME_COLUMN + " TEXT)" );
        db.execSQL("CREATE TABLE " + ROULETTE_ITEM_TABLE_NAME + "(" +
                ROULETTE_ITEM_TABLE_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROULETTE_ITEM_TABLE_ROULETTE_ID_COLUMN + " INTEGER, " +
                ROULETTE_ITEM_TABLE_NAME_COLUMN + " TEXT," +
                ROULETTE_ITEM_TABLE_RATIO_COLUMN + " REAL, " +
                "FOREIGN KEY(" + ROULETTE_ITEM_TABLE_ROULETTE_ID_COLUMN + ") REFERENCES " + ROULETTE_TABLE_NAME + "(" + ROULETTE_TABLE_ID_COLUMN + ")" + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ROULETTE_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ROULETTE_TABLE_NAME);
        onCreate(db);
    }

    public SQLiteDatabase open() {
        return super.getWritableDatabase();
    }

    public void close() {
        super.close();
    }
}