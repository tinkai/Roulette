package com.example.roulette;

import android.util.Log;

// ルーレットの項目情報を保存するクラス
public class RouletteItem {

    // 項目id
    private int id;

    // ルーレットid
    private int rouletteId;

    // ルーレットの項目名
    private String name;

    // ルーレットの割合
    private float ratio;

    public RouletteItem() {}

    public RouletteItem(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouletteId() {
        return this.rouletteId;
    }

    public void setRouletteId(int rouletteId) {
        this.rouletteId = rouletteId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRatio() {
        return this.ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    // ルーレットの割合に対応したルーレットの描写角度
    public float getAngle() {
        return 360 * (this.ratio / 100);
    }

    public void showInfo() {
        Log.d("Roulette Item Info", "");
        Log.d(" id: ", String.valueOf(this.id));
        Log.d(" rouletteId: ", String.valueOf(this.rouletteId));
        Log.d(" name: ", this.name);
        Log.d(" ratio: ", String.valueOf(this.ratio));
    }
}
