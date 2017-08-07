package com.example.win81version2.zingbeemob.model;

import java.io.Serializable;

/**
 * Created by Win 8.1 Version 2 on 8/7/2017.
 */

public class Song implements Serializable{
    private int id;
    private String nameSong;
    private String authorSong;
    private String singerSong;
    private String timeSong;

    public Song() {
    }

    public Song(int id, String nameSong, String authorSong, String singerSong, String timeSong) {
        this.id = id;
        this.nameSong = nameSong;
        this.authorSong = authorSong;
        this.singerSong = singerSong;
        this.timeSong = timeSong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getAuthorSong() {
        return authorSong;
    }

    public void setAuthorSong(String authorSong) {
        this.authorSong = authorSong;
    }

    public String getSingerSong() {
        return singerSong;
    }

    public void setSingerSong(String singerSong) {
        this.singerSong = singerSong;
    }

    public String getTimeSong() {
        return timeSong;
    }

    public void setTimeSong(String timeSong) {
        this.timeSong = timeSong;
    }

    @Override
    public String toString() {
        return this.nameSong+"\n"+this.authorSong+"\n"+this.singerSong+"\n"+this.timeSong;
    }
}
