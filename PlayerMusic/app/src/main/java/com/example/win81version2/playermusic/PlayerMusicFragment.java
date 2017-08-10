package com.example.win81version2.playermusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class PlayerMusicFragment extends Fragment {

    private ImageButton imageButtonPlay, imageButtonStop;
    private PlayerMusicService playerMusicService;
    private boolean mBound;

    @Override
    public void onStart() {
        super.onStart();
        Intent intent= new Intent(getActivity(), PlayerMusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // We've bound to LocalService, cast the IBinder and get
            // LocalService instance
            mBound= true;
            PlayerMusicService.LocalBinder binder = (PlayerMusicService.LocalBinder) iBinder;
            playerMusicService= binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playerMusicService= null;
            mBound= false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if(mBound){
            getActivity().unbindService(mConnection);
            mBound= false;
        }
    }

    public PlayerMusicFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_player_music, container, false);
        touchListener(view);
        imageButtonPlay= view.findViewById(R.id.image_button_play_music_play);
        imageButtonStop= view.findViewById(R.id.image_button_play_music_run);

        imageButtonPlay.setVisibility(View.VISIBLE);
        imageButtonStop.setVisibility(View.INVISIBLE);
        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerMusicService.startMediaPlayer();
                imageButtonPlay.setVisibility(View.INVISIBLE);
                imageButtonStop.setVisibility(View.VISIBLE);
            }
        });
        imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerMusicService.stopMediaPlayer();
                imageButtonPlay.setVisibility(View.VISIBLE);
                imageButtonStop.setVisibility(View.INVISIBLE);
            }
        });
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
