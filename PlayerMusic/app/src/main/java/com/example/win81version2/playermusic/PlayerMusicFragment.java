package com.example.win81version2.playermusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


public class PlayerMusicFragment extends Fragment {

    MediaPlayer mediaPlayer;

    public PlayerMusicFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_player_music, container, false);
        touchListener(view);
        return view;
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getActionMasked()== MotionEvent.ACTION_DOWN){
                    Intent intent= new Intent(getActivity(), PlayerMusicActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

}
