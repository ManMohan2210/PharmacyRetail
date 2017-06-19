
package com.medicare.app.activity;

import android.content.Intent;
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

import com.medicare.app.adapters.MedicinesListAdapter;
import com.pharma.medicare.app.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

public class HomeScreenActivity extends BaseActivty implements OnItemClickListener, AdapterView.OnItemSelectedListener {
    Map<String, Integer> mapIndex;
    ListView mLvMedicines;
    //@Bind(R.id.atv_medicines_search)
    AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setContentView(R.layout.activity_homescreen);

        LinkedList<String> mLinked = new LinkedList<String>();
        for (int i = 0; i < item.length; i++) {
            mLinked.add(item[i]);
        }
        Collections.sort(mLinked);
        MedicinesListAdapter medicinesListAdapter = new MedicinesListAdapter(this, mLinked);
        mLvMedicines = (ListView) findViewById(R.id.lv_medicines);
        mLvMedicines.setFastScrollEnabled(true);
        mLvMedicines.setAdapter(medicinesListAdapter);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView = (AutoCompleteTextView) findViewById(R.id.atv_medicines_search);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);

        mLvMedicines.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent intent;
        String selectedValue = (String) arg0.getItemAtPosition(arg2);

        switch (selectedValue) {
            case "ParacetamolActivity":
                intent = new Intent(HomeScreenActivity.this, ParacetamolActivity.class);
                startActivity(intent);
                break;
            case "CrocinActivity":
                intent = new Intent(HomeScreenActivity.this, CrocinActivity.class);
                startActivity(intent);
                break;
        }
    }

}
