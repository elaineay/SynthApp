package com.example.synthapp;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class SynthButton extends Button {
    boolean playing = true;

    OnClickListener clicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            SynthPlayer.onPlay(playing);
            if(playing) {
                setText(R.string.play_off);
            } else {
                setText(R.string.play_on);
            }

            playing = !playing;
        }
    };

    public SynthButton(Context context) {
        super(context); // superclass Button constructor
        setText(R.string.play_on);
        setOnClickListener(clicker);
    }
}
