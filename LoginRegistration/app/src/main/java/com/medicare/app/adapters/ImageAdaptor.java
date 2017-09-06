package com.medicare.app.adapters;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.medicare.app.interfaces.ItemClickListener;
import com.medicare.app.models.UploadImage;
import com.medicare.launch.app.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by satveer on 11-08-2017.
 */

public class ImageAdaptor extends RecyclerView.Adapter<ImageAdaptor.ViewHolder>  {

    private Context context;
    private ArrayList<UploadImage> uploads;
    public  ArrayList<UploadImage> checkedImages=new ArrayList<>();


    public ImageAdaptor(Context context, ArrayList<UploadImage> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_images, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UploadImage upload = uploads.get(position);
        String date = getDate(upload.getTimeStamp());
        holder.posTxt.setText(uploads.get(position).getPosition());
        holder.textViewName.setText( date);

        Glide.with(context).load(upload.getUrl()).into(holder.imageView);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk= (CheckBox) v;

                //CKE IF ITS CHECKED OR NOT
                if(chk.isChecked())
                {
                    checkedImages.add(uploads.get(pos));
                }else  if(!chk.isChecked())
                {
                    checkedImages.remove(uploads.get(pos));
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getDate(long timeStamp) {
        DateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy' 'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }
    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewName,posTxt;
        public ImageView imageView;
        ItemClickListener itemClickListener;
        CheckBox chk;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            posTxt= (TextView) itemView.findViewById(R.id.posTxt);
            chk= (CheckBox) itemView.findViewById(R.id.chk);

            chk.setOnClickListener(this);
        }
       public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }

        @Override
        public void onClick(View v) {
         this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}