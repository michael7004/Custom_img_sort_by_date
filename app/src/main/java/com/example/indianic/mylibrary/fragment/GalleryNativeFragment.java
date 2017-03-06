package com.example.indianic.mylibrary.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.indianic.mylibrary.R;

import java.io.IOException;

/**
 * Created by indianic on 19/05/16.
 */
public class GalleryNativeFragment extends Fragment {

    private View view;
    private ImageView ivCapturedImage;
    private Context context;
    private Bitmap bitmap;
    private static final int SELECT_PICTURE = 1;
    GalleryNativeFragment data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        openNatiGallery();
    }


    public void setData(GalleryNativeFragment data) {
        this.data = data;
    }


    private void openNatiGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery_native, container, false);
        ivCapturedImage = (ImageView) view.findViewById(R.id.fragment_gallery_iv_captured_img);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    ivCapturedImage.setVisibility(View.VISIBLE);
                    ivCapturedImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "must select", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
            }

        }
    }

}


