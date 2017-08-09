package com.example.win81version2.playermusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.win81version2.playermusic.model.Song;

import java.util.ArrayList;

public class PlayerMusicService extends Service {

    ArrayList<Song> songList;
    private MediaPlayer mediaPlayer;

    private final IBinder iBinder= new localBinder();

    public PlayerMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //getSongList();

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

    public class localBinder extends Binder{
        PlayerMusicService getService(){
            return  PlayerMusicService.this;
        }
    }

}
