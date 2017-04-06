
package mypackage.loginregistration;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import adapters.MedicinesListAdapter;
import butterknife.Bind;

    public class MedicineSearchActivity extends BaseActivty  {
        TextView textName, textDescription;
        SqliteHandler myDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.medicine_search_activity);

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
