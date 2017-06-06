package com.medicare.app.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by satveer on 26-05-2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
   /* public static final String TABLE_MEDICINE = "medicine";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    private static final String DATABASE_NAME = "medicnes.db";
    private static final int DATABASE_VERSION = 1;*/

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(String name, String description, byte[] image){
SQLiteDatabase database = getWritableDatabase();
        String sql="INSERT INTO MEDICINE VALUES(NULL,?,?,?)";
        SQLiteStatement statement= database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,description);
        statement.bindBlob(3,image);
        statement.executeInsert();
    }
    public Cursor getData(String sql)
    {
        SQLiteDatabase database =getReadableDatabase();
        return database.rawQuery(sql,null);

    }
  /*  // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_MEDICINE + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_DESC
            + " text not null);";
*/

    @Override
    public void onCreate(SQLiteDatabase database) {
        //database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        onCreate(db);*/
    }
}
