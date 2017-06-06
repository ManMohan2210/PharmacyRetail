package com.medicare.app.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.medicare.app.R;
import com.medicare.app.sqlite.SqliteHandler;

import java.util.Map;


/**
 * Created by satveer on 14-01-2017.
 */


public class SearchMedicineActivity extends BaseActivty implements SearchView.OnSuggestionListener, OnItemClickListener, AdapterView.OnItemSelectedListener {
    SqliteHandler myDatabase;
    SearchView searchView;
    Map<String, Integer> mapIndex;
    ListView mLvMedicines;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_medicines);

        myDatabase = new SqliteHandler(SearchMedicineActivity.this);


/*
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
        mLvMedicines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
*/


    }
   /* private void openAndQueryDatabase() {
        try {
            DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT FirstName, Age FROM " +
                    tableName +
                    " where Age > 10 LIMIT 4", null);
            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String firstName = c.getString(c.getColumnIndex("FirstName"));
                        int age = c.getInt(c.getColumnIndex("Age"));
                        results.add("Name: " + firstName + ",Age: " + age);
                    }while (c.moveToNext());
                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            if (newDB != null)
                newDB.execSQL("DELETE FROM " + tableName);
            newDB.close();
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        doSearchActivateSearch(menu);

        return true;
    }


    private void doSearchActivateSearch(Menu menu) {
      //  MenuItem searchItem = menu.findItem(R.id.search);
       // searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MedicineSearchActivity.class)));
        searchView.setOnSuggestionListener(this);
        searchView.setIconifiedByDefault(false);
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        int id = (int) searchView.getSuggestionsAdapter().
                getItemId(position);

        Intent intent = new Intent(this, MedicineSearchActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);

        return true;
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

    }
}