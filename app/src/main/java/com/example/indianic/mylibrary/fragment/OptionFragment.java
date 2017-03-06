package com.example.indianic.mylibrary.fragment;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.common.Constant;

/**
 * Created by indianic on 05/05/16.
 */
public class OptionFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button btnImage;
    private Button btnVideo;
    private Button btnAudio;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container, false);
        getActivity().setTitle(R.string.simple_dialog);
        intView();
        return view;
    }

    private void intView() {
        btnImage = (Button) view.findViewById(R.id.fragment_option_btnImage);
        btnVideo = (Button) view.findViewById(R.id.fragment_option_btnVideo);
        btnAudio = (Button) view.findViewById(R.id.fragment_option_btnAudio);
        btnImage.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnAudio.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_option_btnImage:
                FragmentManager fm = getFragmentManager();
                MenuDialogFragment dialogFragment = new MenuDialogFragment();
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                Bundle bundle = new Bundle();
                bundle.putInt(getResources().getString(R.string.BTNIMG), Constant.BTNIMG);
                bundle.putInt(getResources().getString(R.string.BTNGAL), Constant.BTNGAL);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(fm, "fsfs");
                break;
            case R.id.fragment_option_btnVideo:
                FragmentManager fm2 = getFragmentManager();
                MenuDialogFragment dialogFragment2 = new MenuDialogFragment();
                dialogFragment2.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                Bundle bundle2 = new Bundle();
                bundle2.putInt(getResources().getString(R.string.BTNVID), Constant.BTNVID);
                bundle2.putInt(getResources().getString(R.string.BTNVIDD), Constant.BTNVIDD);
                dialogFragment2.setArguments(bundle2);
                dialogFragment2.show(fm2, String.valueOf(R.string.simple_dialog));
                break;
            case R.id.fragment_option_btnAudio:
                FragmentManager fm3 = getFragmentManager();
                MenuDialogFragment dialogFragment3 = new MenuDialogFragment();
                dialogFragment3.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                Bundle bundle3 = new Bundle();
                bundle3.putInt(getResources().getString(R.string.BTNAUD), Constant.BTNAUD);
                bundle3.putInt(getResources().getString(R.string.BTNAUDD), Constant.BTNAUDD);
                dialogFragment3.setArguments(bundle3);
                dialogFragment3.show(fm3, String.valueOf(R.string.simple_dialog));
                break;

        }
    }


}
