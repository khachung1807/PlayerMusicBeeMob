package com.example.win81version2.playermusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.win81version2.playermusic.model.Song;

import java.util.ArrayList;

public class PlayerMusicActivity extends AppCompatActivity {

    ArrayList<Song> songList;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_music);
    }
}
