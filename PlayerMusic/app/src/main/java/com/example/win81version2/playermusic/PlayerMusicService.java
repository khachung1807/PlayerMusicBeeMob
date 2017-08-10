package com.example.win81version2.playermusic;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.io.File;
import java.util.ArrayList;

public class PlayerMusicService extends Service {

    private NotificationManager notificationManager;

    ArrayList<File> songList;
    MediaPlayer mMediaPlayer;

    private final IBinder mBinder = new LocalBinder();

    public PlayerMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(R.string.app_name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle= intent.getExtras();
        songList= (ArrayList) bundle.getParcelableArrayList("songList");
        int position= bundle.getInt("position", 0);
        Uri uri= Uri.parse(songList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        PlayerMusicService getService() {
            return PlayerMusicService.this;
        }
    }


    public void startMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sugar);
        mMediaPlayer.start();
    }

    public void stopMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    public static ArrayList<File> findAllSong(File root) {
        ArrayList<File> all = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                all.addAll(findAllSong(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    all.add(singleFile);
                }
            }
        }
        return all;
    }

}
