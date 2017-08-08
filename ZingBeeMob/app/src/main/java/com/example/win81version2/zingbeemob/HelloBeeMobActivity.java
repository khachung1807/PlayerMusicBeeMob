package com.example.win81version2.zingbeemob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HelloBeeMobActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_bee_mob);
        Thread timeSleep=new Thread(){
            public void run()
            {
                try {
                    sleep(2000);
                } catch (Exception e) {

                }
                finally
                {
                    Intent intent=new Intent(HelloBeeMobActivity.this, PlayMusicActivity.class);
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
