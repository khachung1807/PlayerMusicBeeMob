package com.example.win81version2.playermusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewSong, listViewAlbum;
    String[] items;
    ArrayList<File> songList;
    FrameLayout frameLayoutFragment;
    ArrayAdapter<String> arrayAdapter;
    ImageButton imageButtonPlay;
    ImageButton imageButtonStop;
    private PlayerMusicService playerMusicService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private ServiceConnection mConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // We've bound to LocalService, cast the IBinder and get
            // LocalService instance
            PlayerMusicService.LocalBinder binder= (PlayerMusicService.LocalBinder) iBinder;
            playerMusicService= binder.getService();
            mBound= true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playerMusicService= null;
            mBound= false;
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Intent intent= new Intent(MainActivity.this, PlayerMusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mConnection);
            mBound= false;
        }
    }

    private void addEvents() {
        listViewSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(MainActivity.this, PlayerMusicService.class);
                intent.putExtra("position", i).putExtra("songList", songList);
                startService(intent);
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


        songList = new ArrayList<File>();

        imageButtonPlay = (ImageButton) findViewById(R.id.image_button_play_music_play);
        imageButtonStop = (ImageButton) findViewById(R.id.image_button_play_music_run);
        songList = playerMusicService.findAllSong(Environment.getExternalStorageDirectory());
        listViewSong = (ListView) findViewById(R.id.list_view_song);
        items = new String[songList.size()];
        for (int i = 0; i < songList.size(); i++) {
            //Toast.makeText(ShowAllMusicActivity.this, mySong.get(i).toString(), Toast.LENGTH_LONG).show();
            items[i] = songList.get(i).toString().replace(".mp3", "").replace(".wav", "");
            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.item_song, R.id.text_item_title, items);
            listViewSong.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            //load all music on device
        }
    }

    private void callFragment(PlayerMusicFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main_fragment, fragment);
        fragmentTransaction.commit();
    }

}
