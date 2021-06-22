package com.simi.plantorderingapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MapUrl extends SQLiteOpenHelper {
    Context context;
    public MapUrl(@Nullable Context context) {
        super(context, "map1", null, 1);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table map(name text, link text);";
        db.execSQL(query);
    }

    public void insertData(String name1, String link1) {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("name", name1);
        cv.put("link", link1);
        db.insert("map",null,cv);
        db.close();
    }

    public void delete() {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("map", null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
