package com.example.indianic.mylibrary.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.adapter.RecoredVideoListAdapter;
import com.example.indianic.mylibrary.model.RecordedVideoListModel;

import java.util.ArrayList;

/**
 * Created by indianic on 09/05/16.
 */
public class GalleryVideoFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View v;
    private ListView lvRecordedList;
    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private RecoredVideoListAdapter recoredVideoListAdapter;
    private ArrayList<RecordedVideoListModel> myList;
    //    private String[] projj={MediaStore.Video.VideoColumns};
    private String[] proj = {MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DISPLAY_NAME, MediaStore.Video.VideoColumns.HEIGHT,
            MediaStore.Video.VideoColumns.WIDTH, MediaStore.Video.VideoColumns.SIZE, MediaStore.Video.VideoColumns.DESCRIPTION, MediaStore.Video.VideoColumns.DURATION
            , MediaStore.Video.VideoColumns.RESOLUTION};


   /* private String[] proj = {MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.HEIGHT, MediaStore.Video.Media.WIDTH, MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.RESOLUTION, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.CONTENT_TYPE,
            MediaStore.Video.Media.ALBUM, MediaStore.Video.Media.DESCRIPTION, MediaStore.Video.Media.LATITUDE, MediaStore.Video.Media.LONGITUDE, MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATE_TAKEN, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media.DATE_MODIFIED};
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery_video, container, false);
        myList = new ArrayList<RecordedVideoListModel>();
        getfile();
        intView();
        return v;
    }

    /**
     * This method gives us all videos files whith their path
     * Stores them in array of string
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String[] columns = {MediaStore.Video.Media.DATA, MediaStore.Video.VideoColumns._ID};
        final String orderBy = MediaStore.Video.Media._ID;
        Cursor imagecursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Video.Media._ID);
        this.count = imagecursor.getCount();
        this.thumbnails = new Bitmap[this.count];
        this.arrPath = new String[this.count];
        this.thumbnailsselection = new boolean[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Video.Media.DATA);
            thumbnails[i] = MediaStore.Video.Thumbnails.getThumbnail(getActivity().getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
            arrPath[i] = imagecursor.getString(dataColumnIndex);
            Log.d("Path", "" + arrPath[i]);
        }
    }

    private void intView() {
        lvRecordedList = (ListView) v.findViewById(R.id.fragment_gallery_vid_lvID);
        recoredVideoListAdapter = new RecoredVideoListAdapter(getActivity(), myList);
        lvRecordedList.setAdapter(recoredVideoListAdapter);
        lvRecordedList.setOnItemClickListener(this);
    }

    /**
     * When clicks on particuler item its return path
     * By displaying intent chooser action will be completed
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent tostart = new Intent(Intent.ACTION_VIEW);
        tostart.setDataAndType(Uri.parse(arrPath[position]), "video/*");
        startActivity(tostart);
    }


    /**
     * This method scan sd card ang gives back all video filr or all type of videos like mp4,avi..etc
     * and added to listview to list out all names
     *
     * @return
     */
    public ArrayList<RecordedVideoListModel> getfile() {
//        Cursor audioCursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
        Cursor audioCursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);

        Log.d("Response", "" + audioCursor.toString());
        if (audioCursor != null) {
            if (audioCursor.moveToFirst()) {
                do {
                    int audioIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                    int WidthIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
                    int HeightIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
                    int SizeIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                    int DiscriptionIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION);
                    int DurationIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION);

                    int ResulationIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.RESOLUTION);


               /*     int ResulationIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION);
                    int DurationIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                    int TitleIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
                    int BucketDispkayNameIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
                    int ContentTypeIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.CONTENT_TYPE);
                    int AlbumIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM);
                    int DescriptionIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DESCRIPTION);
                    int LatutudeIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.LATITUDE);
                    int LoagitudeIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.LONGITUDE);
                    int MIMETYPEIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE);
                    int DateTakenIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN);
                    int DateAddedIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
                    int DateModifiedIndex = audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
*/

//                    Log.d("Height", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
//                    Log.d("Height", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.HEIGHT));
//                    Log.d("Size", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
//                    Log.d("Duration", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
//                    Log.d("Width", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
//                    Log.d("Tital", "" + audioCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));


                    RecordedVideoListModel model = new RecordedVideoListModel();
                    model.setId("");//No need to pass id
                    model.setPathe(audioCursor.getString(audioIndex));


                    Log.d("Width_test", "" + audioCursor.getString(WidthIndex));
                    Log.d("Height_test", "" + audioCursor.getString(HeightIndex));
                    Log.d("Size_test", "" + audioCursor.getString(SizeIndex));
                    Log.d("Description_test", "" + audioCursor.getString(DiscriptionIndex));
                    Log.d("Duration_test", "" + audioCursor.getString(DurationIndex));
                    Log.d("Duration_test", "" + audioCursor.getString(ResulationIndex));



                  /*  Log.d("ResulationIndex", "" + audioCursor.getString(ResulationIndex));
                    Log.d("DurationIndex", "" + audioCursor.getString(DurationIndex));
                    Log.d("TitleIndex", "" + audioCursor.getString(TitleIndex));
                    Log.d("BucketDispkayNameIndex", "" + audioCursor.getString(BucketDispkayNameIndex));
                    Log.d("ContentTypeIndex", "" + audioCursor.getString(ContentTypeIndex));
                    Log.d("AlbumIndex", "" + audioCursor.getString(AlbumIndex));
                    Log.d("DescriptionIndex", "" + audioCursor.getString(DescriptionIndex));
                    Log.d("LatutudeIndex", "" + audioCursor.getString(LatutudeIndex));
                    Log.d("LoagitudeIndex", "" + audioCursor.getString(LoagitudeIndex));
                    Log.d("MIMETYPEIndex", "" + audioCursor.getString(MIMETYPEIndex));
                    Log.d("DateTakenIndex", "" + audioCursor.getString(DateTakenIndex));
                    Log.d("DateAddedIndex", "" + audioCursor.getString(DateAddedIndex));
                    Log.d("DateModifiedIndex", "" + audioCursor.getString(DateModifiedIndex));*/


//                    Log.d("Width",""+audioCursor.getString(WidthIndex));
                    myList.add(model);
                } while (audioCursor.moveToNext());
            }
        }
        audioCursor.close();
        return myList;
    }
}
