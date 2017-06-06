
package com.medicare.app.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.medicare.app.R;
import com.medicare.app.sqlite.SqliteHandler;


public class MedicineSearchActivity extends BaseActivty {
        TextView textName, textDescription;
        SqliteHandler myDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_medicine_search);

            textName = (TextView) findViewById(R.id.textName);
            textDescription= (TextView) findViewById(R.id.textDesc);

            int id = getIntent().getExtras().getInt("id");
            myDatabase = new SqliteHandler(MedicineSearchActivity.this);
            showMedicineDescription(id);

        }

        private void showMedicineDescription(int id) {

           String description="";
            String name = "";

            Cursor cursor = myDatabase.getMedicineDescription(id + 1);
            if (cursor.getCount() < 1) {
                description += "No Data Available";
                name += "No Data Available";

            } else {
                cursor.moveToFirst();
                do {

                    name = cursor.getString(cursor.getColumnIndex(myDatabase.MEDICINE_NAME));
                    description = cursor.getString(cursor.getColumnIndex(myDatabase.DESCRIPTION));
                }
                while (cursor.moveToNext());
            }
            // set Name
            textName.setText(name);
            //set Age
           textDescription.setText(description);
        }
}
