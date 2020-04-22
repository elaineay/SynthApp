package com.example.synthapp;

import android.content.Context;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;


public class RecordButton extends AppCompatButton {
    boolean recording = true;

    /*Listens*/
    public OnClickListener clicker = new OnClickListener() {
        @Override
        public void onClick(View view) {
            SynthPlayer.onRecord(recording);

            if (recording) {
                // https://developer.android.com/reference/android/widget/TextView#setText(int)
                setText(R.string.record_off);
            } else {
                setText(R.string.record_on);
            }

            recording = !recording;
        }
    };

    public RecordButton(Context context) {
        super(context); //pass in context to Button superclass constructor
        setText(R.string.record_on);
        setOnClickListener(clicker); //set listener to this recording
    }
}
