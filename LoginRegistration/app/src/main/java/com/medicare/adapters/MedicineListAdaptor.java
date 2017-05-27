package com.medicare.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.medicare.activity.R;
import com.medicare.models.Medicine;

import java.util.ArrayList;

/**
 * Created by satveer on 26-05-2017.
 */

public class MedicineListAdaptor extends BaseAdapter {
    public MedicineListAdaptor(Context context, int layout, ArrayList<Medicine> medicineList) {
        this.context = context;
        this.layout = layout;
        this.medicineList = medicineList;
    }

    private Context context;
    private int layout;
    private ArrayList<Medicine> medicineList;
    @Override
    public int getCount() {
        return medicineList.size();
    }
    private class ViewHolder{
        ImageView imageViewMedi;
        TextView txtDesc, txtName;
    }
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        View row = view;
        ViewHolder holder = new ViewHolder();
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtDesc = (TextView) row.findViewById(R.id.txtDesc);
            holder.imageViewMedi = (ImageView) row.findViewById(R.id.imageViewMedi);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }
        Medicine medicine= medicineList.get(position);
        holder.txtName.setText(medicine.getMedicineName());
        holder.txtDesc.setText(medicine.getDescription());
        byte[] medicineImage = medicine.getImageByteArray();
        Bitmap bitmap= BitmapFactory.decodeByteArray(medicineImage,0,medicineImage.length);
        holder.imageViewMedi.setImageBitmap(bitmap);
        return row;
    }

    @Override
    public Object getItem(int position) {
        return medicineList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
