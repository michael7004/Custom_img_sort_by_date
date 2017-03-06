package com.example.indianic.mylibrary.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.common.Constant;


/**
 * Created by indianic on 05/05/16.
 */
public class MenuDialogFragment extends DialogFragment implements RadioButton.OnCheckedChangeListener {


    private String TAG = MenuDialogFragment.class.getSimpleName();
    private int img, vid, aud;
    private int imgg, vidd, audd;

    public MenuDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        img = bundle.getInt(getResources().getString(R.string.BTNIMG), Constant.DEFAULT);
        imgg = bundle.getInt(getResources().getString(R.string.BTNGAL), Constant.DEFAULT);
        vid = bundle.getInt(getResources().getString(R.string.BTNVID), Constant.DEFAULT);
        vidd = bundle.getInt(getResources().getString(R.string.BTNVIDD), Constant.DEFAULT);
        aud = bundle.getInt(getResources().getString(R.string.BTNAUD), Constant.DEFAULT);
        audd = bundle.getInt(getResources().getString(R.string.BTNAUDD), Constant.DEFAULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_dialog, container, false);
        getDialog().setTitle("Title");
        ((RadioButton) view.findViewById(R.id.fragment_menu_dialog_rbCapture)).setOnCheckedChangeListener(this);
        ((RadioButton) view.findViewById(R.id.fragment_menu_dialog_rbGallery)).setOnCheckedChangeListener(this);
        ((RadioButton) view.findViewById(R.id.fragment_menu_dialog_rbNativeGallery)).setOnCheckedChangeListener(this);
        return view;
    }


    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        switch (v.getId()) {
            case R.id.fragment_menu_dialog_rbCapture:
                if (img == Constant.BTNIMG) {
                    openCaptureFragment();
                } else if (vid == Constant.BTNVID) {
                    openCaptureVidFragment();
                } else if (aud == Constant.BTNAUD) {
                    openCaptureAudFragment();
                }
                break;

            case R.id.fragment_menu_dialog_rbGallery:
                if (imgg == Constant.BTNGAL) {
                    openGalleryImageFragment();
                } else if (vidd == Constant.BTNVIDD) {
                    openGalleryVidFragment();
                } else if (audd == Constant.BTNAUDD) {
                    openGalleryAud();
                }
                break;

            case R.id.fragment_menu_dialog_rbNativeGallery:
                openNativaGallery();
                break;

        }
        this.getDialog().dismiss();
    }

    private void openNativaGallery() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new GalleryNativeFragment(), TAG).addToBackStack(TAG).hide(new GalleryNativeFragment()).commit();
    }

    private void openGalleryImageFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new GalleryImageFragment(), TAG).addToBackStack(TAG).hide(new GalleryImageFragment()).commit();
    }

    private void openCaptureFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new CaptureImageFragement(), TAG).addToBackStack(TAG).hide(new CaptureImageFragement()).commit();
    }

    private void openGalleryAud() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new GalleryAudioFragment(), TAG).addToBackStack(TAG).hide(new MenuDialogFragment()).commit();
    }

    private void openCaptureAudFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new CaptureAudioFragment(), TAG).addToBackStack(TAG).hide(new CaptureAudioFragment()).commit();
    }

    private void openGalleryVidFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new GalleryVideoFragment(), TAG).addToBackStack(TAG).hide(new GalleryVideoFragment()).commit();
    }

    private void openCaptureVidFragment() {
        getFragmentManager().beginTransaction().replace(R.id.activity_main_frContainer, new CaptureVideoFragment(), TAG).addToBackStack(TAG).hide(new CaptureVideoFragment()).commit();
    }
}