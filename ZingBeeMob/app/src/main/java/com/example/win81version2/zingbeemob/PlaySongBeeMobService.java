package com.example.win81version2.zingbeemob;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlaySongBeeMobService extends Service {
    private MediaPlayer mediaPlayer;

    public PlaySongBeeMobService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer= MediaPlayer.create(getApplicationContext(), R.raw.sugar);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return START_STICKY;
    }
}
