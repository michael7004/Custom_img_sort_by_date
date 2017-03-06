package com.example.indianic.mylibrary.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.indianic.mylibrary.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by indianic on 05/05/16.
 */
public class CaptureImageFragement extends Fragment {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private View v;
    private Button button;
    private ImageView imageView;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static final int MEDIA_TYPE_IMAGE = 1;
    // Directory name to store captured images and videos
    private String IMAGE_DIRECTORY_NAME;
    private String DATE_FORMAT;
    private String IMAGE_NAME;
    private String IMAGE_EXTENSION;
    private Uri fileUri; // file url to store image/video
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_capture_image, container, false);
        return v;
    }

    /**
     * As soon view inflated load camera to be captured image for below <23
     * if 23 pops up permission window to grant permission from the user side
     * if allow then open camera and let to do capture image
     * else denie main fragment app shuld not be crashed
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intView();

        if (Build.VERSION.SDK_INT >= 23) {
            insertDummyContactWrapper();


        } else {
            captureImage();
        }

    }


    /**
     * Here we store the file url as it will be null after returning from camera app
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable(getResources().getString(R.string.file_url), fileUri);
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

            previewCapturedImage();
        } else {
            FragmentManager fm = getFragmentManager();
            Log.d("Entry", "" + fm.getBackStackEntryCount());
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                fm.popBackStack();
            }
        }

    }

    /**
     * initialization
     */
    private void intView() {
        IMAGE_DIRECTORY_NAME = getResources().getString(R.string.hello_images);
        DATE_FORMAT = getResources().getString(R.string.date_formate);
        IMAGE_NAME = getResources().getString(R.string.img_name);
        IMAGE_EXTENSION = getResources().getString(R.string.img_extension);
        imageView = (ImageView) v.findViewById(R.id.fragment_capture_ivProfile);
    }


    /**
     * Helper method
     */

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
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + IMAGE_NAME + timeStamp + IMAGE_EXTENSION);
        } else {
            return null;
        }
        return mediaFile;
    }

    /**
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }


    /**
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            imageView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            Log.d("IMG_CAP_PATH", fileUri.getPath());
            imageView.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void insertDummyContactWrapper() {

        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();

        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("External Storage");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentCompat.requestPermissions(CaptureImageFragement.this, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            FragmentCompat.requestPermissions(CaptureImageFragement.this, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        captureImage();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!FragmentCompat.shouldShowRequestPermissionRationale(CaptureImageFragement.this, permission))
                return false;
        }
        return true;
    }

    /**
     * This method specially developed when below scenario happens
     * When user checks never show again option fron the dialog msg
     * Once cheched permissopn request dialog will not pops up
     * so we deliberately show the dialog to indicate user that he/she need to allow permission
     * NOTE:-This is not handled by system by default
     * This is from the UI point of view so user can understand easily.
     *
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    captureImage();
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
