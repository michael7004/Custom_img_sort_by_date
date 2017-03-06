package com.example.indianic.mylibrary.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.adapter.RecordedImageListAdapter;

/**
 * Created by indianic on 13/05/16.
 */
public class GalleryImageFragment extends Fragment implements View.OnClickListener {

    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrayPath;
    private RecordedImageListAdapter recordedImageListAdapter;
    private View v;
    private Button btnSelect;
    private Button btnclear;
    private Button btnAll;
    private GridView ivList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery_image, container, false);
        intView();
        fetchImages();
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void intView() {
        btnSelect = (Button) v.findViewById(R.id.fragment_imgage_gallery_btn_select);
        btnclear = (Button) v.findViewById(R.id.fragment_image_gallery_btn_clear);
        btnAll = (Button) v.findViewById(R.id.fragment_image_gallery_btn_all);
        btnSelect.setOnClickListener(this);
        btnclear.setOnClickListener(this);
        btnAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_imgage_gallery_btn_select:
                final int len = thumbnailsselection.length;
                int cnt = 0;
                String selectImages = "";
                for (int i = 0; i < len; i++) {
                    if (thumbnailsselection[i]) {
                        cnt++;
                        selectImages = selectImages + arrayPath[i] + "|";
                    }
                }
                if (cnt == 0) {
                    Toast.makeText(getActivity(), "Please select at least one image", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "You've selected Total " + cnt + " image(s).", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.fragment_image_gallery_btn_clear:

                for (int i = 0; i < count; i++) {
                    thumbnailsselection[i] = false;
                }
                recordedImageListAdapter.notifyDataSetChanged();
                break;
            case R.id.fragment_image_gallery_btn_all:
                for (int i = 0; i < count; i++) {
                    thumbnailsselection[i] = true;
                }
                recordedImageListAdapter.notifyDataSetChanged();
                break;
        }

    }

    /**
     * This method give us all images from the content provider
     */
    private void fetchImages() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.Media.DESCRIPTION, MediaStore.Images.Media.HEIGHT, MediaStore.Images.Media.WIDTH};
        String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        Cursor imagecursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        double size = imagecursor.getColumnIndex(MediaStore.Images.Media.SIZE);
        count = imagecursor.getCount();
        thumbnails = new Bitmap[count];
        arrayPath = new String[count];
        thumbnailsselection = new boolean[count];
        for (int i = 0; i < count; i++) {
            imagecursor.moveToPosition(i);
            final int id = imagecursor.getInt(image_column_index);


            final int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            thumbnails[i] = MediaStore.Images.Thumbnails.getThumbnail(getActivity().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            arrayPath[i] = imagecursor.getString(dataColumnIndex);
        }
        ivList = (GridView) v.findViewById(R.id.fragment_image_gallery_gv_images);
        recordedImageListAdapter = new RecordedImageListAdapter(getActivity(), thumbnailsselection, thumbnails, count);
        ivList.setAdapter(recordedImageListAdapter);
        imagecursor.close();
    }
}



