package com.example.indianic.mylibrary.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.model.RecordListModel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by indianic on 09/05/16.
 */
public class RecordedListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RecordListModel> arrayList;
    private RecordListModel userModel;


    public RecordedListAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * Here We get the particular postion with particular path
     * by taking path we can start and stop it
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        userModel = arrayList.get(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fragment_gallery_aud, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.row_fragment_gallery_tvTitle);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvTitle.setText(userModel.getId());
        holder.tvTitle.setText(userModel.getPath());
        holder.tvTitle = (TextView) convertView.findViewById(R.id.row_fragment_gallery_tvTitle);
        holder.tvTitle.setText(userModel.getId());
        holder.tvTitle.setText(userModel.getPath());
        holder.btnPaly = (Button) convertView.findViewById(R.id.row_fragment_gallery_tvPlay);
        holder.btnPaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer m = new MediaPlayer();
                try {
                    m.setDataSource(userModel.getPath());
                    m.prepare();
                    m.start();
                    Toast.makeText(context, "Playing audio", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("OnUpate", "" + arrayList.get(position).getPath().toString());
            }
        });
        return convertView;
    }

    public class Holder {
        TextView tvTitle;
        Button btnPaly;
    }
}


