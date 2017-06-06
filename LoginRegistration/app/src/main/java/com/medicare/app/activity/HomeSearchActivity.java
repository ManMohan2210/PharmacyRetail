
package com.medicare.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.medicare.app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import butterknife.Bind;

public class HomeSearchActivity extends BaseActivty implements OnItemClickListener, AdapterView.OnItemSelectedListener {
    Map<String, Integer> mapIndex;
    ListView mLvMedicines;
    @Bind(R.id.atv_search)
    AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;
    public static final String PREFERENCES = "Prefs" ;
    public static final String MEDICINE_NAME = "nameKey";
    SharedPreferences sharedpreferences;
    private String selectedValue;
    ArrayList<String> MEDICINE_LIST = new ArrayList<String>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setContentView(R.layout.activity_homesearch);

        LinkedList<String> mLinked = new LinkedList<String>();
        for (int i = 0; i < item.length; i++) {
            mLinked.add(item[i]);
        }
        Collections.sort(mLinked);
      //  MedicinesListAdapter medicinesListAdapter = new MedicinesListAdapter(this, mLinked);
       // mLvMedicines = (ListView) findViewById(R.id.lv_medicines);
       // mLvMedicines.setFastScrollEnabled(true);
      //  mLvMedicines.setAdapter(medicinesListAdapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView = (AutoCompleteTextView) findViewById(R.id.atv_search);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);

       /* mLvMedicines.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });*/



        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 sharedpreferences = getSharedPreferences(MEDICINE_NAME, 0);
                MEDICINE_LIST = new ArrayList<String>(sharedpreferences.getStringSet("name", new HashSet<String>()));
                //Set values=new HashSet();
                //values=ss.getStringSet(MEDICINE_NAME,null);//.getStringSet(&quot;movies&quot;, null);
                if(MEDICINE_LIST!=null) {
                   // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,MEDICINE_LIST);
                    textView = (AutoCompleteTextView) findViewById(R.id.atv_search);
                    textView.setThreshold(1);
                    textView.setAdapter(adapter);
                }
               // SharedPreferences.Editor editor = sharedpreferences.edit();
               // editor.putString(MEDICINE_NAME, selectedValue);
               // editor.commit();

//                SharedPreferences ss = getSharedPreferences("db", 0);
//                Set<String> hs = ss.getStringSet(selectedValue, new LinkedHashSet<String>());
////                hs.add(String.valueOf(hs.size()+1));
//                Editor edit = ss.edit();
//                edit.clear();
//                edit.putStringSet(MEDICINE_NAME, hs);
//                edit.commit();
//                ss.getStringSet(MEDICINE_NAME,hs);
//                String restoredText = prefs.getString("text", null);
//                if (restoredText != null) {
//                    String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
//                    int idName = prefs.getInt("idName", 0); //0 is the default value.
//                }
//                Set values=new HashSet();
//                values=ss.getStringSet(MEDICINE_NAME,null);//.getStringSet(&quot;movies&quot;, null);
//                if(values!=null) {

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                            (this, android.R.layout.simple_dropdown_item_1line, hs);
//                    textView = (AutoCompleteTextView) findViewById(R.id.atv_search);
//                    textView.setThreshold(1);
//                    textView.setAdapter(adapter);
//                }
                //textView.setOnItemSelectedListener(this);
                //textView.setOnItemClickListener(this);
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
         selectedValue = (String) arg0.getItemAtPosition(arg2);
         sharedpreferences = getSharedPreferences(selectedValue, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet("name", new HashSet<String>(MEDICINE_LIST));
        editor.commit();

        switch (selectedValue) {
            case "ParacetamolActivity":
                intent = new Intent(HomeSearchActivity.this, ParacetamolActivity.class);
                startActivity(intent);
                break;
            case "CrocinActivity":
                intent = new Intent(HomeSearchActivity.this, CrocinActivity.class);
                startActivity(intent);
                break;
        }
    }

}
