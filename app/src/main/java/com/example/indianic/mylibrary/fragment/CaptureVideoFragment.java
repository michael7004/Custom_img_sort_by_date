package com.example.indianic.mylibrary.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.example.indianic.mylibrary.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by indianic on 09/05/16.
 */
public class CaptureVideoFragment extends Fragment {

    private View v;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Directory name to store captured images and videos
    private String IMAGE_DIRECTORY_NAME;
    private String DATE_FORMAT;
    private String VID_NAME;
    private String VID_EXTENSION;
    // File url to store images and videos
    private Uri fileUri;
    private VideoView vvRecord;
    Context context;


    private String[] proj = {MediaStore.Video.VideoColumns.DISPLAY_NAME, MediaStore.Video.VideoColumns.WIDTH};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_capture_video, container, false);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        recordVideo();
    }

    private void initView() {
        IMAGE_DIRECTORY_NAME = getResources().getString(R.string.hello_cameras);
        DATE_FORMAT = getResources().getString(R.string.date_formate);
        VID_NAME = getResources().getString(R.string.vid_name);
        VID_EXTENSION = getResources().getString(R.string.vid_extension);
        vvRecord = (VideoView) v.findViewById(R.id.fragment_capure_vid_vbRecord);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
//            previewVideo();

            getInfo();
            /*if (data != null) {
                previewVideo();
            } else {
                Toast.makeText(getActivity(), "You Must Record", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getInfo() {

        String[] projection = {MediaStore.MediaColumns.DISPLAY_NAME};
        Cursor metaCursor = getContext().getContentResolver().query(fileUri, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    String fileName = metaCursor.getString(0);
                }
            } finally {
                metaCursor.close();
            }
        }


    }

//    public String getFileName(Uri uri) {
//        String result = null;
//        if (uri.getScheme().equals("file")) {
//            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        return result;
//    }





       /* return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );*/


    /**
     * Creating file uri to store image/video
     *
     * @param type
     * @return
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Here we store the file url as it will be null after returning from camera app
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable(getResources().getString(R.string.fragment_cap_vid_file_url), fileUri);
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }


    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * returning image / video
     *
     * @param type
     * @return
     */
    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + VID_NAME + timeStamp + VID_EXTENSION);
            Log.d("path", "" + mediaFile);
        } else {
            return null;
        }
        return mediaFile;
    }

    /**
     * Previewing recorded video
     */
   /* private void previewVideo() {
        try {


//            Cursor cursor = MediaStore.Video.query(getActivity().getContentResolver(), fileUri, proj);

            Cursor audioCursor = getActivity().getContentResolver().query(MediaStore.Video.Media.getContentUri(String.valueOf(fileUri)), proj, null, null, null);

            if (audioCursor.moveToFirst()) {
                int audioIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                int WidthIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);

                Log.d("Width", "" + audioCursor.getString(audioIndex));
                Log.d("Height", "" + audioCursor.getString(WidthIndex));
            }

        } catch (Exception e) {

        }
    }*/


}








