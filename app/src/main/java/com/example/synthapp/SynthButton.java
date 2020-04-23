package com.example.synthapp;

import android.content.Context;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;

public class SynthButton extends AppCompatButton {
    boolean playing = true;
    SynthPlayer synthPlayer = null;

    OnClickListener clicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            synthPlayer.onPlay(playing);
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

    }

    public void setListener(SynthPlayer synthPlayer) {
        setText(R.string.play_on);
        this.synthPlayer = synthPlayer;
        setOnClickListener(clicker);
    }
}
