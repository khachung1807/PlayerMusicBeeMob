package com.example.win81version2.playermusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelloPlayerMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_player_music);
        Thread timeSleep = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (Exception e) {
                    e.getStackTrace();
                } finally {
                    Intent intent = new Intent(HelloPlayerMusicActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timeSleep.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
