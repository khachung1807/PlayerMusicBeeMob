package com.example.win81version2.zingbeemob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayMusicActivity extends AppCompatActivity {
    ImageButton imageButtonPlay, imageButtonNext, imageButtonPrevious, imageButtonRun;
    TextView textNameSong, textAuthorSong, textSingerSong, textStartTimeSong, textStopTimeSong;
    SeekBar seekBarTimeSong;
    private double startTimeSong= 0;
    private double stopTimeSong= 0;
    private Handler handler= new Handler();
    public static int oneTimeOnly=0;
    private int endTime= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        addControls();
        addEvents();
    }
    private void addEvents() {
        imageButtonRun.setVisibility(View.INVISIBLE);
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PlayMusicActivity.this, PlaySongBeeMobService.class);
                startService(intent);
                imageButtonRun.setVisibility(View.VISIBLE);
                imageButtonPlay.setVisibility(View.INVISIBLE);
            }
        });
        imageButtonRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PlayMusicActivity.this, PlaySongBeeMobService.class);
                stopService(intent);
                imageButtonPlay.setVisibility(View.VISIBLE);
                imageButtonRun.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void addControls() {
        imageButtonPrevious= (ImageButton) findViewById(R.id.image_button_play_music_previous);
        imageButtonPlay= (ImageButton) findViewById(R.id.image_button_play_music_play);
        imageButtonNext= (ImageButton) findViewById(R.id.image_button_play_music_next);
        imageButtonRun= (ImageButton) findViewById(R.id.image_button_play_music_run);
        textNameSong= (TextView) findViewById(R.id.text_play_music_name_song);
        textAuthorSong= (TextView) findViewById(R.id.text_play_music_author_song);
        textSingerSong= (TextView) findViewById(R.id.text_play_music_single_song);
        textStartTimeSong= (TextView) findViewById(R.id.text_play_music_start);
        textStopTimeSong= (TextView) findViewById(R.id.text_play_music_stop);
        seekBarTimeSong= (SeekBar) findViewById(R.id.seekbar_play_music_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_folder){
            Intent intent= new Intent(PlayMusicActivity.this, ShowAllMusicActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
