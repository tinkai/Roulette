package com.example.roulette;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Debug;
import android.view.View;

// ルーレットを回すメインアクティビティ
public class RoulettePlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette_play);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RouletteDAO dao = new RouletteDAO(this);
        dao.connect();
        dao.deleteAllTable();

        dao.insert(Roulette.getDefaultRoulette());

        for (Roulette d : dao.selectAll()) {
            d.showInfo();
        }

        dao.close();





    }
}