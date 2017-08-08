package com.example.win81version2.zingbeemob.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.win81version2.zingbeemob.R;
import com.example.win81version2.zingbeemob.model.Song;

import java.util.List;

/**
 * Created by Win 8.1 Version 2 on 8/7/2017.
 */

public class MusicAdapter extends ArrayAdapter<Song>{

    Activity context;
    int resource;
    List<Song> objects;
    public MusicAdapter(Activity context, int resource, List<Song> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resource= resource;
        this.objects= objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View row= inflater.inflate(this.resource, null);
        TextView textViewNameSong= row.findViewById(R.id.text_item_name_song);
        TextView textViewAuthorSong= row.findViewById(R.id.text_item_author_song);
        TextView textViewSingerSong= row.findViewById(R.id.text_item_singer_song);
        TextView textViewTimeSong= row.findViewById(R.id.text_item_time_song);
        final Song song= this.objects.get(position);
        textViewNameSong.setText(song.getNameSong());
        textViewAuthorSong.setText(song.getAuthorSong());
        textViewSingerSong.setText(song.getSingerSong());
        textViewTimeSong.setText(song.getTimeSong());
        return row;
    }
}
