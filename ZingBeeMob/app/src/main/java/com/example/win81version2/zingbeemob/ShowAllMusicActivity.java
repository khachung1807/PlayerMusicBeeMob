package com.example.win81version2.zingbeemob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class ShowAllMusicActivity extends AppCompatActivity {

    ListView listViewMusic;
    String[] items;
    ArrayList<File> mySong;
    ArrayAdapter<String> arrayAdapter;
//    ArrayList<Song> listMusic;
//    MusicAdapter adapterMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_music);
        addControls();
        addEvents();
    }

    private void addEvents() {
        listViewMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(ShowAllMusicActivity.this, PlayMusicActivity.class);
                intent.putExtra("position", i).putExtra("songList", mySong);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        listViewMusic= (ListView) findViewById(R.id.list_show_all_music);
        mySong= findAllSong(Environment.getExternalStorageDirectory());
        items= new String[mySong.size()];
        for (int i=0; i<mySong.size(); i++){
            //Toast.makeText(ShowAllMusicActivity.this, mySong.get(i).toString(), Toast.LENGTH_LONG).show();
            items[i]= mySong.get(i).toString().replace(".mp3", "").replace(".wav", "");
        }
        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(),
                R.layout.item_music, R.id.text_item_name_song, items);
        listViewMusic.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    private ArrayList<File> findAllSong(File root) {
        ArrayList<File> all= new ArrayList<File>();
        File[] files= root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                all.addAll(findAllSong(singleFile));
            } else {
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    all.add(singleFile);
                }
            }
        }
        return all;
    }
}
