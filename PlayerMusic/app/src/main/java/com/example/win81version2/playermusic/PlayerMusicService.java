package com.example.win81version2.playermusic;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class PlayerMusicService extends Service {

    public static final String ACTION_PLAY = "com.beemob.musicplayer.ACTION_PLAY";
    public static final String ACTION_NEXT = "com.beemob.musicplayer.ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "com.beemob.musicplayer.ACTION_PREVIOUS";
    public static final String CUSTOM_CONTENT_VIEW= "com.beemob.musicplayer.CUSTOM_CONTENT_VIEW";

    public static final String EXTRA_POSITION = "EXTRA_POSITION";

    private NotificationManager notificationManager;

    ArrayList<File> songList;
    MediaPlayer mMediaPlayer;
    int position;
    Uri uri;
    public int notificationId;
    private final IBinder mBinder = new LocalBinder();

    public PlayerMusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        //notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        //notificationManager.cancel(R.string.app_name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if(TextUtils.isEmpty(action)) return START_STICKY;

        position = intent.getIntExtra(EXTRA_POSITION, 0);
        if(action.equals(ACTION_PLAY)){
            startMediaPlayer();
        }else if(action.equals(ACTION_NEXT)){
            nextMediaPlayer();
        } else if(action.equals(ACTION_PREVIOUS)){
            preMediaPlayer();
        }

        testPlayerMusic();
        Bundle bundle = intent.getExtras();
        songList = (ArrayList) bundle.getParcelableArrayList("songList");
        position = bundle.getInt("position", 0);
        uri = Uri.parse(songList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        PlayerMusicService getService() {
            return PlayerMusicService.this;
        }
    }

    public class PhoneIntervalReceiver extends WakefulBroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent service = new Intent(context, PlayerMusicService.class);
            startWakefulService(context, service);
        }
    }

    public static void startPhoneService(Context context) {
        context.stopService(new Intent(context, PlayerMusicService.class));
        Intent intent = new Intent(context, PhoneIntervalReceiver.class);
        //intent.setAction(ALARM);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarm.set(AlarmManager.RTC_WAKEUP, 1000, pIntent);
        } else {
            alarm.setExact(AlarmManager.RTC_WAKEUP, 1000, pIntent);
        }

    }


    public static void startServiceWithAction(Context context, String action, int position) {
        Intent intent = new Intent(context, PlayerMusicService.class);
        if (action != null) {
            intent.setAction(action);
        }
        intent.putExtra(EXTRA_POSITION, position);
        context.startService(intent);
    }

    public void startMediaPlayer() {
        uri = Uri.parse(songList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
        createNotification();
        //update broadcast
    }

    public void stopMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    public boolean isPlaying() {
        return (mMediaPlayer != null && mMediaPlayer.isPlaying());
    }

    public void pauseMediaPlayer() {
        mMediaPlayer.pause();
    }


    public void nextMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        position = (position + 1) % songList.size();
        uri = Uri.parse(songList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
    }

    public void preMediaPlayer() {
        mMediaPlayer.stop();
        mMediaPlayer.release();
        position = (position - 1 < 0) ? songList.size() - 1 : position - 1;
        uri = Uri.parse(songList.get(position).toString());
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mMediaPlayer.start();
    }

    public void testPlayerMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    public int setTotalDuration(){
        return mMediaPlayer.getDuration();
    }
    public int setCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }
    public void seekToPlayerMusic(SeekBar seekBar){
        mMediaPlayer.seekTo(seekBar.getProgress());
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

    public void createNotification(){
        Intent intent= new Intent(PlayerMusicService.this, PlayerMusicActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(PlayerMusicService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews= new RemoteViews(getPackageName(), R.layout.fragment_player_music);
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.image_song)
                .addAction(R.drawable.image_previos, "", pendingIntent);
        setListener(remoteViews);
        mBuilder.setContent(remoteViews);
        mBuilder.setContentIntent(pendingIntent);
        notificationId= 001;
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    private void setListener(RemoteViews remoteViews) {
        Intent intent= new Intent(this, PlayerMusicFragment.class);
        intent.putExtra("", "");
    }
}
