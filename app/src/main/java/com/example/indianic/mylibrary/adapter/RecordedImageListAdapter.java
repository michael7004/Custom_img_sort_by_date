package com.example.indianic.mylibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.indianic.mylibrary.R;

/**
 * Created by indianic on 13/05/16.
 */
public class RecordedImageListAdapter extends BaseAdapter {

    private int count;
    private boolean[] thumbNailSelection;
    private Bitmap[] thumbNails;
    private Context context;
    private LayoutInflater mInflater;

    public RecordedImageListAdapter(Context context, boolean[] thumbNailSelection, Bitmap[] thumbNails, final int count) {
        this.context = context;
        this.thumbNailSelection = thumbNailSelection;
        this.thumbNails = thumbNails;
        this.count = count;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(final int position) {

        return position;
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.row_fragment_gallery_image, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.fragment_gallery_image_iv_thumbImage);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.fragment_gallery_image_cb_itemCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkbox.setId(position);
        holder.imageview.setId(position);
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                final int id = cb.getId();
                if (thumbNailSelection[id]) {
                    cb.setChecked(false);
                    thumbNailSelection[id] = false;
                } else {
                    cb.setChecked(true);
                    thumbNailSelection[id] = true;
                }
            }
        });

        holder.imageview.setImageBitmap(thumbNails[position]);
        holder.checkbox.setChecked(thumbNailSelection[position]);
        holder.id = position;
        return convertView;
    }

    class ViewHolder {
        private ImageView imageview;
        private CheckBox checkbox;
        private int id;
    }
}

