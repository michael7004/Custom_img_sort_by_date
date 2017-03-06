package com.example.indianic.mylibrary.fragment;


import android.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.indianic.mylibrary.R;

import java.io.IOException;

/**
 * Created by indianic on 09/05/16.
 */
public class CaptureAudioFragment extends Fragment implements View.OnClickListener {

    private View v;
    private Button btnRecord;
    private Button btnStop;
    private Button btnPlay;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private String AUDIO_IMG_EXTESION;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_capture_audio, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMedia();
        intitView();
    }

    /**
     * Initialized all required parameters MediaRecorder class
     */
    private void initMedia() {
        AUDIO_IMG_EXTESION = getResources().getString(R.string.aud_extension);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_IMG_EXTESION;
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    /**
     * Initialization Process
     */
    private void intitView() {
        btnRecord = (Button) v.findViewById(R.id.fragment_capture_aud_btnRecord);
        btnStop = (Button) v.findViewById(R.id.fragment_capture_aud_btnStop);
        btnPlay = (Button) v.findViewById(R.id.fragment_capture_aud_btnPlay);
        btnRecord.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
    }

    /**
     * Navigate three different option
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_capture_aud_btnRecord:
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                btnRecord.setEnabled(false);
                btnStop.setEnabled(true);
                break;
            case R.id.fragment_capture_aud_btnStop:
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);
                Toast.makeText(getActivity(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
            case R.id.fragment_capture_aud_btnPlay:
                MediaPlayer m = new MediaPlayer();
                try {
                    m.setDataSource(outputFile);
                    m.prepare();
                    m.start();
                    Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}

