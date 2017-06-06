/*
package com.medicare.app.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.medicare.app.R;

*/
/**
 * Created by satveer on 03-06-2017.
 *//*


// Custom marker view used for pulsing the background view of marker.
public class PulseMarkerViewAdapter extends MapboxMap.MarkerViewAdapter<PulseMarkerView> {

    private LayoutInflater inflater;

    public PulseMarkerViewAdapter(@NonNull Context context) {
        super(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public View getView(@NonNull PulseMarkerView marker, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.view_pulse_marker, parent, false);
            viewHolder.foregroundImageView = (ImageView) convertView.findViewById(R.id.imageViewPic);
            viewHolder.backgroundImageView = (ImageView) convertView.findViewById(R.id.imageViewPic);
            convertView.setTag(viewHolder);
        }
        return convertView;
    }

    private static class ViewHolder {
        ImageView foregroundImageView;
        ImageView backgroundImageView;
    }
}
*/
