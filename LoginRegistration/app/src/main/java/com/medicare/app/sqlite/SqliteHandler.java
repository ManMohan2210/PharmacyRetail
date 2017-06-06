package com.medicare.app.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.R.attr.description;

public class SqliteHandler extends SQLiteOpenHelper {
    private static final String TAG = SqliteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medicare";
    private static final String TABLE_NAME = "medicine";
    public static final String MEDICINE_NAME = "name";
    public static final String MEDICINE_ID = "id";
    public static final String DESCRIPTION = "description";



    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_sql = "CREATE TABLE medicine (id INTEGER PRIMARY KEY" +
                ", name TEXT PRIMARY KEY unique ON CONFLICT REPLACE, description TEXT)";

        db.execSQL(table_sql);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    void addMedicine(String name, String age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MEDICINE_NAME, name);
        contentValues.put(DESCRIPTION, description);

        long id = db.insert(TABLE_NAME, null, contentValues);
        db.close(); // Closing database connection

        Log.d(TAG, "New medicine inserted into sqlite: " + id);
    }
    public ArrayList<String> getAllName() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> medicineNames = new ArrayList<String>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();


            do {
                String name = cursor.getString(cursor.getColumnIndex(MEDICINE_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                medicineNames.add(name);
                medicineNames.add(desc);
            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return medicineNames;

    }

    public Cursor getMedicineDescription(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selections = {String.valueOf(id)};
        String columns[] = {MEDICINE_NAME, MEDICINE_ID,DESCRIPTION};
        Cursor cursor = db.query(TABLE_NAME, columns, MEDICINE_ID + "=?",
                selections, null, null, null);
        //db.close();
        return cursor;
    }
}
