package com.jijiyan.instagramclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jijiyan.instagramclient.R;
import com.jijiyan.instagramclient.model.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yuandaxia on 2015/12/6.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvLikesCount = (TextView) convertView.findViewById(R.id.tvLikesCount);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        tvCaption.setText(photo.getCaption());
        tvLikesCount.setText(String.valueOf(photo.getLikesCount()));
        tvUsername.setText(photo.getUsername());
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);
        return convertView;
    }
}
