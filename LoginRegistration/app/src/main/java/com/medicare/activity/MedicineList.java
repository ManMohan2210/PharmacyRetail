package com.medicare.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.medicare.adapters.MedicineListAdaptor;
import com.medicare.models.Medicine;

import java.util.ArrayList;

/**
 * Created by satveer on 26-05-2017.
 */

public class MedicineList extends BaseActivty {
    GridView gridView;
    ArrayList<Medicine> list;
    MedicineListAdaptor adaptor=null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list);
        gridView= (GridView) findViewById(R.id.gridview);
        list= new ArrayList<>();
        adaptor= new MedicineListAdaptor(this, R.layout.medicine_name_list,list);
        gridView.setAdapter(adaptor);


        //get all the data from sqlte
        Cursor cursor= AddMedicine.sqLiteHelper.getData("SELECT * FROM MEDICINE");
        list.clear();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name= cursor.getString(1);
            String  description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            list.add(new Medicine(id, name, description,image));

        }
        adaptor.notifyDataSetChanged();
    }
}
