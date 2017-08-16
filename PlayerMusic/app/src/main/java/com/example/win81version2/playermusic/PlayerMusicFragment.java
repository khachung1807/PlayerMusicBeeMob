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
import android.widget.SeekBar;


public class PlayerMusicFragment extends Fragment implements View.OnClickListener {

    ImageButton imageButtonPlay,
            imageButtonNext,
            imageButtonPre;
    SeekBar seekBarTime;
    PlayerMusicService playerMusicService;
    boolean mBound;
    Thread updateSeekBar;

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), PlayerMusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // We've bound to LocalService, cast the IBinder and get
            // LocalService instance
            mBound = true;
            PlayerMusicService.LocalBinder binder = (PlayerMusicService.LocalBinder) iBinder;
            playerMusicService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            playerMusicService = null;
            mBound = false;
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    public PlayerMusicFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_music, container, false);
        touchListener(view);
        imageButtonPlay = view.findViewById(R.id.image_button_play_music_play);
        imageButtonNext = view.findViewById(R.id.image_button_play_music_next);
        imageButtonPre = view.findViewById(R.id.image_button_play_music_previous);
        seekBarTime = view.findViewById(R.id.seekbar_play_music_time);
        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration= playerMusicService.setTotalDuration();
                int currentPosition= 0;
                seekBarTime.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = playerMusicService.setCurrentPosition();
                        seekBarTime.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        imageButtonPlay.setImageResource(R.drawable.image_play);
        addEvents();
        return view;
    }

    private void addEvents() {
        imageButtonPlay.setOnClickListener(this);
        imageButtonNext.setOnClickListener(this);
        imageButtonPre.setOnClickListener(this);
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playerMusicService.seekToPlayerMusic(seekBar);
            }
        });
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getActivity(), PlayerMusicActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.image_button_play_music_play:
                if (playerMusicService.isPlaying()) {
                    imageButtonPlay.setImageResource(R.drawable.image_play);
                    playerMusicService.pauseMediaPlayer();
                } else {
                    imageButtonPlay.setImageResource(R.drawable.image_run);
                    playerMusicService.startMediaPlayer();
                    seekBarTime.setMax(playerMusicService.setTotalDuration());
                    updateSeekBar.start();
                }
                break;
            case R.id.image_button_play_music_next:
                playerMusicService.nextMediaPlayer();
                seekBarTime.setMax(playerMusicService.setTotalDuration());
                break;
            case R.id.image_button_play_music_previous:
                playerMusicService.preMediaPlayer();
                seekBarTime.setMax(playerMusicService.setTotalDuration());
                break;
        }
    }
}
