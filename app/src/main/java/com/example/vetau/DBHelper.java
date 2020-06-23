package com.example.vetau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hoaipv.db";
    private static final String TABLE_NAME = "tblTicket";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_GADEN = "GADEN";
    private static final String COLUMN_GADI = "GADI";
    private static final String COLUMN_DONGIA = "DONGIA";
    private static final String COLUMN_KHUHOI = "KHUHOI";
    private static final int VERSION = 2;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s DOUBLE NOT NULL, %s INTEGER NOT NULL)"
                , TABLE_NAME, COLUMN_ID, COLUMN_GADEN, COLUMN_GADI, COLUMN_DONGIA, COLUMN_KHUHOI);
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createDefaultData() {
        if(getCountData() == 0) {
            insertData("Vinh", "Nam Định", (double) 351500, 1);
            insertData("Nam Định", "Thanh Hóa", (double) 237500, 1);
            insertData("Thanh Hóa", "Hà Nội", (double) 170000, 0);
            insertData("Hà Nội", "Thanh Hóa", (double) 170000, 0);
            insertData("Hà Nội", "Nam Định", (double) 131100, 1);
        }
    }

    public ArrayList<VeTau> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = String.format("SELECT * FROM %s ORDER BY %s DESC", TABLE_NAME, COLUMN_DONGIA);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<VeTau> list = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                list.add(new VeTau(cursor.getInt(0), cursor.getString(2),
                        cursor.getString(1), cursor.getDouble(3), cursor.getInt(4)));
                cursor.moveToNext();
            }
        }
        return list;
    }

    public int getCountData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        db.close();
        return count;
    }

    public long insertData(String gaDen, String gaDi, Double donGia, int khuHoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GADEN, gaDen);
        values.put(COLUMN_GADI, gaDi);
        values.put(COLUMN_DONGIA, donGia);
        values.put(COLUMN_KHUHOI, khuHoi);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public long updateData(int id, String gaDen, String gaDi, Double donGia, int khuHoi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_GADEN, gaDen);
        values.put(COLUMN_GADI, gaDi);
        values.put(COLUMN_DONGIA, donGia);
        values.put(COLUMN_KHUHOI, khuHoi);
        String where = COLUMN_ID + " = " + id;
        long result = db.update(TABLE_NAME, values, where, null);
        db.close();
        return result;
    }

    public long deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_ID + " = " + id;
        long result = db.delete(TABLE_NAME, where, null);
        db.close();
        return result;
    }


}
