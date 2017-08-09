package com.example.win81version2.playermusic;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.win81version2.playermusic.adapter.SongAdapter;
import com.example.win81version2.playermusic.model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ListView listViewSong, listViewAlbum;
    ArrayList<Song> songList;
    FrameLayout frameLayoutFragment;

    PlayerMusicService playerMusicService= new PlayerMusicService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

        getSongList();
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song song, Song t1) {
                return song.getTitle().compareTo(t1.getTitle());
            }
        });

        SongAdapter songAdapter= new SongAdapter(this, songList);
        listViewSong.setAdapter(songAdapter);
        songAdapter.notifyDataSetChanged();

    }

    private void addEvents() {
        listViewSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(MainActivity.this, PlayerMusicActivity.class);
                intent.putExtra("position", i).putExtra("songList", songList);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        //create tabhost;
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost_main);
        tabHost.setup();

        TabHost.TabSpec tabPlayList = tabHost.newTabSpec("tabPlayList");
        tabPlayList.setIndicator("", getResources().getDrawable(R.drawable.image_playlists));
        tabPlayList.setContent(R.id.playlist);
        tabHost.addTab(tabPlayList);

        TabHost.TabSpec tabAlbum = tabHost.newTabSpec("tabAlbum");
        tabAlbum.setIndicator("", getResources().getDrawable(R.drawable.image_album));
        tabAlbum.setContent(R.id.album);
        tabHost.addTab(tabAlbum);

        frameLayoutFragment = (FrameLayout) findViewById(R.id.frame_layout_main_fragment);
        callFragment(new PlayerMusicFragment());

        listViewSong = (ListView) findViewById(R.id.list_view_song);
        songList= new ArrayList<Song>();

        //load all music on device
    }

    public void getSongList() {
        ContentResolver musicResolver= getContentResolver();
        Uri musicUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor= musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor!= null && musicCursor.moveToFirst()){
            //get column
            int titleColumn= musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn= musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn= musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            //add song to list
            do {
                long thisId= musicCursor.getLong(idColumn);
                String thisTitle= musicCursor.getString(titleColumn);
                String thisArtist= musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            } while (musicCursor.moveToNext());
        }
        musicCursor.close();
    }

    private void callFragment(PlayerMusicFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main_fragment, fragment);
        fragmentTransaction.commit();
    }
}
