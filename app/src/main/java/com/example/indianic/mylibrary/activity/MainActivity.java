package com.example.indianic.mylibrary.activity;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.fragment.GalleryNativeFragment;
import com.example.indianic.mylibrary.fragment.OptionFragment;

public class MainActivity extends AppCompatActivity {

    String TAG = MainActivity.class.getSimpleName();
    private GalleryNativeFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        dataFragment = (GalleryNativeFragment) fm.findFragmentByTag("data");

        if (dataFragment == null) {
            getFragmentManager().beginTransaction().add(R.id.activity_main_frContainer, new OptionFragment(), TAG).commit();
        }
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        Log.d("Entry", "" + fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            super.onBackPressed();
            Log.i("MainActivity", "nothing on backstack, calling super");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        dataFragment.setData(collectMyLoadedData());
    }
}
