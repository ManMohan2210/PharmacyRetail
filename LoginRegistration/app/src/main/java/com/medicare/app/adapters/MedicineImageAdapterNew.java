package com.medicare.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicare.app.models.PrescriptionDataModel;
import com.medicare.launch.app.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by satveer on 06-03-2017.
 */

public class MedicineImageAdapterNew extends ArrayAdapter<PrescriptionDataModel>{

        Context context;

        int layoutResourceId;

                // BcardImage data[] = null;

        ArrayList<PrescriptionDataModel> data=new ArrayList<PrescriptionDataModel>();

        public MedicineImageAdapterNew(Context context, int layoutResourceId, ArrayList<PrescriptionDataModel> data) {

            super(context, layoutResourceId, data);

            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;

        }



        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;

            ImageHolder holder = null;
            if(row == null)

            {

                LayoutInflater inflater = ((Activity)context).getLayoutInflater();

                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ImageHolder();

                //holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

                holder.iv_selected_image = (ImageView)row.findViewById(R.id.iv_selected_image);

                row.setTag(holder);

            }

            else

            {

                holder = (ImageHolder)row.getTag();

            }



            PrescriptionDataModel picture = data.get(position);
            //holder.txtTitle.setText(picture._name);

            //convert byte to bitmap take from prescriptionData class


//._image;


            byte[] outImage=picture.getPhotoBitmap();

            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);

            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            holder.iv_selected_image.setImageBitmap(theImage);

            return row;

        }



        static class ImageHolder

        {

            ImageView iv_selected_image;

            TextView txtTitle;
            
        }

    }
