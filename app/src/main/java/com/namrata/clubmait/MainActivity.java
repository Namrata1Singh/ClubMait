package com.namrata.clubmait;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.h).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,register_President.class);
            startActivity(intent);
        });
    }
}