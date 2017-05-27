package com.medicare.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.medicare.adapters.MedicinesListAdapter;
import com.medicare.sqlite.SqliteHandler;

import java.util.ArrayList;

public class ListViewActivity extends Activity {

	SqliteHandler SQLITEHANDLER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    MedicinesListAdapter ListAdapter ;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_medicines);

        LISTVIEW = (ListView) findViewById(R.id.lv_medicines);

        SQLITEHANDLER = new SqliteHandler(this);

    }

    @Override
    protected void onResume() {

    	ShowSQLiteDBdata() ;

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

    	/*SQLITEDATABASE = SQLITEHANDLER.getWritableDatabase();

        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM medicine", null);

        ID_ArrayList.clear();
        NAME_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
            	ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SqliteHandler.MEDICINE_ID)));

            	NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(SqliteHandler.MEDICINE_NAME)));

            } while (cursor.moveToNext());
        }

       // ListAdapter = new MedicinesListAdapter(ListViewActivity.this, ID_ArrayList,NAME_ArrayList);

        LISTVIEW.setAdapter(ListAdapter);

        cursor.close();*/
    }
}