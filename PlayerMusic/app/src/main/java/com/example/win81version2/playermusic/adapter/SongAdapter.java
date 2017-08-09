package com.example.win81version2.playermusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.win81version2.playermusic.R;
import com.example.win81version2.playermusic.model.Song;

import java.util.ArrayList;

/**
 * Created by Win 8.1 Version 2 on 8/9/2017.
 */

public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> songs;
    private LayoutInflater songInflater;

    public SongAdapter(Context context, ArrayList<Song> songs) {
        this.songs = songs;
        songInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //map to song item
        LinearLayout songItem= (LinearLayout) songInflater.inflate(R.layout.item_song, viewGroup, false);
        //get title and artist
        TextView textViewTitle= songItem.findViewById(R.id.text_item_title);
        TextView textViewArtist= songItem.findViewById(R.id.text_item_artist);
        //get song by position(i)
        Song cussorSong= songs.get(i);

        textViewTitle.setText(cussorSong.getTitle());
        textViewArtist.setText(cussorSong.getArtist());

        songItem.setTag(i);

        return songItem;
    }
}
