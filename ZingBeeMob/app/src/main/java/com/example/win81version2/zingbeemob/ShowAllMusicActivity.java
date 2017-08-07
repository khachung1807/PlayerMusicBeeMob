package com.example.win81version2.zingbeemob;

import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowAllMusicActivity extends AppCompatActivity {

    ListView listViewMusic;
    ArrayList<String> listMusic;
    ArrayAdapter<String> adapterMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_music);
        addControls();
        addEvents();
        readAllMusic();
    }

    private void addEvents() {
        listViewMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ShowAllMusicActivity.this, listMusic.get(i), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void readAllMusic() {
        String[] music= new String []{
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.MIME_TYPE
        };
        CursorLoader cursorLoader= new CursorLoader(ShowAllMusicActivity.this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                music, null, null,null);
        Cursor cursor= cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String s= "";
        while (!cursor.isAfterLast()){
            for (int i=0; i<cursor.getColumnCount(); i++){
                s += cursor.getString(i)+ " - ";
            }
            s += "\n";
            cursor.moveToNext();
            listMusic.add(s);
        }
        cursor.close();
        adapterMusic.notifyDataSetChanged();
    }

    private void addControls() {
        listViewMusic= (ListView) findViewById(R.id.list_show_all_music);
        listMusic= new ArrayList<>();
        adapterMusic= new ArrayAdapter<String>(ShowAllMusicActivity.this,
                android.R.layout.simple_list_item_1, listMusic);
        listViewMusic.setAdapter(adapterMusic);
    }
}
