package com.example.roulette;

import android.util.Log;

import java.util.ArrayList;

// ルーレット情報を保存するDTO
public class Roulette {

    // ルーレットid
    private int id;

    // ルーレット名
    private String name;

    // ルーレットの項目リスト
    private ArrayList<RouletteItem> rouletteItemList;

    // デフォルトのサイコロルーレット
    public static Roulette getDefaultRoulette() {
        Roulette roulette = new Roulette();
        //roulette.setId(0);
        roulette.setName("saikoro");
        roulette.addRouletteItemList(new RouletteItem("1"));
        roulette.addRouletteItemList(new RouletteItem("2"));
        roulette.addRouletteItemList(new RouletteItem("3"));
        roulette.addRouletteItemList(new RouletteItem("4"));
        roulette.addRouletteItemList(new RouletteItem("5"));
        roulette.addRouletteItemList(new RouletteItem("6"));
        return roulette;
    }

    public Roulette() {
        this.rouletteItemList = new ArrayList<RouletteItem>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RouletteItem> getRouletteItemList() {
        return this.rouletteItemList;
    }

    public void addRouletteItemList(RouletteItem item) {
        this.rouletteItemList.add(item);
    }

    public void showInfo() {
        Log.d("Roulette Info", "");
        Log.d(" id: ", String.valueOf(this.id));
        Log.d(" name: ", this.name);

        for (RouletteItem item : this.rouletteItemList) {
            item.showInfo();
        }
    }
}
