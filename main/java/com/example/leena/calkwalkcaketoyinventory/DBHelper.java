package com.example.leena.calkwalkcaketoyinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "CakeToyListDB.db";
    public static final String TABLE_NAME = "CakeToyTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TOYID";
    public static final String COL_3 = "NAME";
    public static final String COL_4 = "QUANTITY";


    public static final String DB1_NAME = "OrderListDB.db";
    public static final String TABLE1_NAME = "OrderTable";
    public static final String COL1_1 = "ID";
    public static final String COL1_2 = "DELIVERYDATE";
    public static final String COL1_3 = "TOYNAME";
    public static final String COL1_4 = "TOYID";
    public static final String COL1_5 = "DELIVERYTIME";
    public static final String COL1_6 = "LOCATION";
    public static final String COL1_7 = "CAKEWEIGHT";
    public static final String COL1_8 = "CAKEFLAVOR";
    public static final String COL1_9 = "MSG";
    public static final String COL1_10 = "SPLREQUEST";
    public static final String COL1_11 = "COMMENT";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TOYID INTEGER,NAME TEXT,QUANTITY INTEGER)");
        db.execSQL("create table " + TABLE1_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DELIVERYDATE TEXT,TOYNAME TEXT,TOYID INTEGER,DELIVERYTIME TEXT,LOCATION TEXT,CAKEWEIGHT INTEGER,CAKEFLAVOR TEXT,MSG TEXT,SPLREQUEST TEXT,COMMENT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDataToyTable(int toyID, String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, toyID);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, quantity);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean insertDataOrderTable(String deliveryDate, String toyName, int toyID, String deliveryTime, int cakeWeight, String cakeFlavor, String msg, String splRequest, String comment, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_2, deliveryDate);
        contentValues.put(COL1_3, toyName);
        contentValues.put(COL1_4, toyID);
        contentValues.put(COL1_5, deliveryTime);
        contentValues.put(COL1_6, location);
        contentValues.put(COL1_7, cakeWeight);
        contentValues.put(COL1_8, cakeFlavor);
        contentValues.put(COL1_9, msg);
        contentValues.put(COL1_10, splRequest);
        contentValues.put(COL1_11, comment);

        long result = db.insert(TABLE1_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getListContentsCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void deleteToy(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public ArrayList<ToyData> getListContentToy() {
        ArrayList<ToyData> toyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            toyList.add(new ToyData(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
        }
        return toyList;
    }
    public ArrayList<OrderData> getListContentOrder() {
        ArrayList<OrderData> toyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE1_NAME, null);

        while (cursor.moveToNext()) {
            toyList.add(new OrderData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(0)));
        }
        return toyList;
    }

    public boolean updateToyData(String id, int toyID, String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, toyID);
        values.put(COL_3, name);
        values.put(COL_4, quantity);
        db.update(TABLE_NAME, values, "ID = ? ", new String[] {id});
        return true;
    }


}