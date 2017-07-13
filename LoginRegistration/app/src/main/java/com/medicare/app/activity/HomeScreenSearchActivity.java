
package com.medicare.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.medicare.app.adapters.MedicinesListAdapter;
import com.medicare.launch.app.R;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;

public class HomeScreenSearchActivity extends BaseActivty implements OnItemClickListener  {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private Set<String> history;
    Map<String, Integer> mapIndex;
    //ListView mLvMedicines;
    @Bind(R.id.atv_search)
    AutoCompleteTextView textView;
    private ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
            Intent intent = getIntent();
        setContentView(R.layout.activity_homescreen_search);


            //textView.setOnItemSelectedListener(this);
//            textView.setOnItemClickListener(this);
//
           /* mLvMedicines.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    Toast.makeText(getApplicationContext(),
                            ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                }
            });*/


        setAutoCompleteSource();
        // Set the "Enter" event on the search input
        //final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.textInput);
        textView.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    addSearchInput(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }
    private void setAutoCompleteSource() {
        /*//AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.textInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, history.toArray(new String[history.size()]));
        textView.setAdapter(adapter);*/
        LinkedList<String> mLinked = new LinkedList<String>();
        for (int i = 0; i < item.length; i++) {
            mLinked.add(item[i]);
        }
        Collections.sort(mLinked);
        MedicinesListAdapter medicinesListAdapter = new MedicinesListAdapter(this, mLinked);
        // mLvMedicines = (ListView) findViewById(R.id.lv_medicines);
        //mLvMedicines.setFastScrollEnabled(true);
        //  mLvMedicines.setAdapter(medicinesListAdapter);
        settings = getSharedPreferences(PREFS_NAME, 0);
        history = settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
        textView = (AutoCompleteTextView) findViewById(R.id.atv_search);
        textView.setThreshold(1);
        textView.setAdapter(adapter);
    }
    private void addSearchInput(String input)
    {
        if (!history.contains(input))
        {
            history.add(input);
            setAutoCompleteSource();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        savePrefs();
    }

    private void savePrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, history);

        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
