package com.example.indianic.mylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.indianic.mylibrary.R;
import com.example.indianic.mylibrary.model.RecordedVideoListModel;

import java.util.ArrayList;

/**
 * Created by indianic on 11/05/16.
 */
public class RecoredVideoListAdapter extends BaseAdapter {

    Context context;
    ArrayList<RecordedVideoListModel> arrayList;
    RecordedVideoListModel userModel;

    public RecoredVideoListAdapter(Context context, ArrayList arrayList) {
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        userModel = arrayList.get(position);
        Holder holder;
        if (convertView == null) {
            //for recycling object
            holder = new Holder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fragment_gallery_vid, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.row_fragment_gallery_vid_tvTitle);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvTitle.setText(userModel.getId());
        holder.tvTitle.setText(userModel.getPathe());
        return convertView;
    }

    public class Holder {
        TextView tvTitle;
    }
}