package com.example.bingoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class WinActivity extends AppCompatActivity {
    TextView congrats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_win);
        congrats = findViewById(R.id.congrats);
        congrats.setText("CONGRATULATIONS!\n" + getIntent().getStringExtra("token"));
    }


    public void newGame(View view) {
        Intent newGame = new Intent(WinActivity.this,MainActivity.class);
        startActivity(newGame);
    }
}