package com.example.synthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class SynthPlayer extends AppCompatActivity {

    private static final String LOG_TAG = "SynthPlayer";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;

    private RecordButton recordButton = null;
    private MediaRecorder recorder = null;

    private SynthButton synthButton = null;
    private MediaPlayer player = null;

    // request permission to record audio
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //callback for result from permission request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        // if permissions denied, exit
        if (!permissionToRecordAccepted) {
            finish();
        }
    }

    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    // https://developer.android.com/reference/android/media/MediaRecorder#setAudioSource(int)
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // set audio source to mic
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); // set 3GPP for multimedia files on phones
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // audio compression format optimized for speech decoding

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    public void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();

        try {
            player.setDataSource(fileName);
            player.prepare(); //prepares player for synchronous playback
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    /* Release resources associated with MediaPlayer object, failing to release resources:
    *  - lead to continuous battery consumption
    *  - playback failure for other apps
    *  - performance degradation when multiple instances at once
    * */
    private void stopPlaying() {
        player.release();
        player = null;
    }
}
