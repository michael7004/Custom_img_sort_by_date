package com.example.indianic.mylibrary.fragment;


import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.adapter.RecordedListAdapter;
import com.example.indianic.mylibrary.model.RecordListModel;

import java.util.ArrayList;

/**
 * Created by indianic on 09/05/16.
 */
public class GalleryAudioFragment extends Fragment {

    private View v;
    private int count;
    private Bitmap[] thumbnails;
    private String[] arrPath;
    private ListView lvRecordedList;
    private RecordedListAdapter recordedListAdapter;
    private String[] proj = {MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME};
    private ArrayList<String> audioList = new ArrayList<>();
    private ArrayList<RecordListModel> myList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery_audio, container, false);

        return v;
    }


    /**
     * When view created then it loads up all audio file
     * Setted path using model class's object
     * Added model class's object into arraylist
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myList = new ArrayList<RecordListModel>();
        Log.d("audio", "" + audioList);
        intView();
        fetchAudioFiel();

    }

    /**
     * Intialization process
     */
    private void intView() {
        lvRecordedList = (ListView) v.findViewById(R.id.fragment_gallery_aud_lvRecordedList);
        recordedListAdapter = new RecordedListAdapter(getActivity(), myList);
        lvRecordedList.setAdapter(recordedListAdapter);
    }

    /**
     * Fetching all king of audio files
     */
    private void fetchAudioFiel() {
        final String[] columns = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID};
        final String orderBy = MediaStore.Audio.Media._ID;
        Cursor imagecursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Audio.Media._ID);
        count = imagecursor.getCount();
        thumbnails = new Bitmap[count];
        arrPath = new String[count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int id = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex);
            Log.d("Path", "" + arrPath[i]);
            RecordListModel model = new RecordListModel();
            model.setId("" + id);
            model.setPath(imagecursor.getString(dataColumnIndex));
            myList.add(model);
        }
    }
}
