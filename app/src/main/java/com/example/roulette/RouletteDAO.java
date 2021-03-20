package com.example.roulette;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

// ルーレットDBを操作するDAO
public class RouletteDAO implements RouletteDBSettings {

    // 使用中アクティビティ？
    private Context context = null;

    // 使用中DB
    private SQLiteDatabase db = null;

    public RouletteDAO(Context context) {
        this.context = context;
    }

    // OpenHelperで接続
    public void connect() {
        RouletteOpenHelper helper = new RouletteOpenHelper(context);
        db = helper.open();
    }

    // DB接続解除
    public void close() {
        db.close();
        db = null;
    }

    public void dropAllTable() {
        db.execSQL("DROP TABLE IF EXISTS " + ROULETTE_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ROULETTE_TABLE_NAME);
        Log.d("DROP TABLE!!!!", "");
    }

    public void deleteAllTable() {
        db.execSQL("DELETE FROM " + ROULETTE_ITEM_TABLE_NAME);
        db.execSQL("DELETE FROM " + ROULETTE_TABLE_NAME);
        Log.d("DELETE DATA!!!!", "");
    }

    // DBから全てのルーレットを取得するメソッド
    public ArrayList<Roulette> selectAll() {
        ArrayList<Roulette> rouletteList = new ArrayList<Roulette>();
        Cursor cursor = null;
        try {
            Log.d("", "SELECT " + ROULETTE_TABLE_ID_COLUMN + ", " + ROULETTE_TABLE_NAME_COLUMN + " " + "FROM " + ROULETTE_TABLE_NAME);
            cursor = db.rawQuery("SELECT " + ROULETTE_TABLE_ID_COLUMN + ", " + ROULETTE_TABLE_NAME_COLUMN + " " +
                            "FROM " + ROULETTE_TABLE_NAME,
                    null);

            // ルーレットごとにRouletteを設定、リストに追加
            while(cursor.moveToNext()) {
                Roulette roulette = new Roulette();
                int rouletteId = cursor.getInt(cursor.getColumnIndex(ROULETTE_TABLE_ID_COLUMN));
                String rouletteName = cursor.getString(cursor.getColumnIndex(ROULETTE_TABLE_NAME_COLUMN));
                roulette.setId(rouletteId);
                roulette.setName(rouletteName);
                for (RouletteItem item : selectRouletteItemListById(rouletteId)) {
                    roulette.addRouletteItemList(item);
                }
                rouletteList.add(roulette);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return rouletteList;
    }

    // ルーレットのidに該当するRouletteインスタンスを返すメソッド
    public Roulette selectById(int id) {
        Roulette roulette = new Roulette();
        roulette.setId(id);
        Cursor cursor = null;
        try {
            // ルーレットのidとnameを取得
            String[] selectArgs = {String.valueOf(id)};
            cursor = db.rawQuery("SELECT " + ROULETTE_TABLE_NAME_COLUMN + " " +
                            "FROM " + ROULETTE_TABLE_NAME + " " +
                            "WHERE " + ROULETTE_TABLE_ID_COLUMN + " = ?",
                    selectArgs);
            if (cursor.moveToNext()) {
                String rouletteName = cursor.getString(cursor.getColumnIndex(ROULETTE_TABLE_NAME_COLUMN));
                roulette.setName(rouletteName);
            }

            // ルーレットの項目を設定
            for (RouletteItem item : selectRouletteItemListById(id)) {
                roulette.addRouletteItemList(item);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return roulette;
    }

    // ルーレットのidに対応する項目リストを取得するメソッド
    private ArrayList<RouletteItem> selectRouletteItemListById(int id) {
        ArrayList<RouletteItem> rouletteItemList = new ArrayList<RouletteItem>();
        Cursor cursor = null;
        try {
            String[] selectArgs = {String.valueOf(id)};
            cursor = db.rawQuery("SELECT " + ROULETTE_ITEM_TABLE_NAME_COLUMN + ", " + ROULETTE_ITEM_TABLE_RATIO_COLUMN + " " +
                            "FROM " + ROULETTE_ITEM_TABLE_NAME + " " +
                            "WHERE " + ROULETTE_ITEM_TABLE_ROULETTE_ID_COLUMN + "= ?",
                    selectArgs);
            while (cursor.moveToNext()) {
                RouletteItem item = new RouletteItem();
                String rouletteItemName = cursor.getString(cursor.getColumnIndex(ROULETTE_ITEM_TABLE_NAME_COLUMN));
                float rouletteItemRatio = cursor.getFloat(cursor.getColumnIndex(ROULETTE_ITEM_TABLE_RATIO_COLUMN));
                item.setName(rouletteItemName);
                item.setRatio(rouletteItemRatio);
                rouletteItemList.add(item);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return rouletteItemList;
    }

    // ルーレットを新規でDBに格納するメソッド
    public long insert(Roulette roulette) {
        roulette.showInfo();
        // ルーレットをinsert
        ContentValues rouletteValues = new ContentValues();
        rouletteValues.put(ROULETTE_TABLE_NAME_COLUMN, roulette.getName());
        long roulette_id = db.insert(ROULETTE_TABLE_NAME, null, rouletteValues);

        // ルーレット項目をinsert
        for (RouletteItem item : roulette.getRouletteItemList()) {
            ContentValues rouletteItemValues = new ContentValues();
            rouletteItemValues.put(ROULETTE_ITEM_TABLE_ROULETTE_ID_COLUMN, roulette_id);
            rouletteItemValues.put(ROULETTE_ITEM_TABLE_NAME_COLUMN, item.getName());
            rouletteItemValues.put(ROULETTE_ITEM_TABLE_RATIO_COLUMN, item.getRatio());
            db.insert(ROULETTE_ITEM_TABLE_NAME, null, rouletteItemValues);
        }
        Log.d("DB insert id", String.valueOf(roulette_id));
        return roulette_id;
    }
}
