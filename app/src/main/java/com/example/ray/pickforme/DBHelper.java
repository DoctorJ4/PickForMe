package com.example.ray.pickforme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Created by Jesse on 12/27/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private final String TABLE_LIST_INFO = "List_Info";
    private final String TABLE_LIST_CONTENTS = "List_Contents";
    private final String COLUMN_LIST_ID = "ID";
    private final String COLUMN_NAME = "Name";
    private final String COLUMN_SIZE = "Size";

    private static final String DATABASE_NAME = "Lists";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIST_INFO_TABLE = "CREATE TABLE " + TABLE_LIST_INFO + " (" +
                COLUMN_LIST_ID + "  INTEGER PRIMARY KEY NOT NULL, " +
                COLUMN_NAME + " TEXT (1) NOT NULL, " +
                COLUMN_SIZE + " number (1) DEFAULT (2))";
        String CREATE_LIST_CONTENTS_TABLE = "CREATE TABLE " + TABLE_LIST_CONTENTS + " (" +
                COLUMN_LIST_ID + " INTEGER REFERENCES " + TABLE_LIST_INFO + " (" + COLUMN_LIST_ID + "), " +
                COLUMN_NAME +" TEXT (1) NOT NULL)";
        db.execSQL(CREATE_LIST_INFO_TABLE);
        db.execSQL(CREATE_LIST_CONTENTS_TABLE);
        defaultList(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_INFO);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion < oldVersion)
            db.setVersion(oldVersion);
        //super.onDowngrade(db,newVersion,oldVersion);
    }

    public void addList(String name, List<String> contents, SQLiteDatabase db){
        if(contents.size() < 2)
        { return; }
        int lastRowId;

        String insertStatement = "INSERT INTO " + TABLE_LIST_INFO + " (" + COLUMN_NAME + ", " + COLUMN_SIZE + ")\n" +
                "VALUES ('" + name + "', " + contents.size() + ");";
        db.execSQL(insertStatement);


        Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
        c.moveToFirst();
        lastRowId = c.getInt(0);
        insertStatement = "INSERT INTO " + TABLE_LIST_CONTENTS + " (" + COLUMN_LIST_ID + ", " + COLUMN_NAME + ")\n" +
                "SELECT " + lastRowId + ", '" + contents.get(0) + "'\n";
        for(int i = 1; i < contents.size(); i++)
        {
            insertStatement = insertStatement + "union SELECT " + lastRowId + ", '" + contents.get(i) + "'\n";
        }
        insertStatement = insertStatement + ";";
        db.execSQL(insertStatement);
        c.close();
    }

    public void addList(String name, List<String> contents){
        if(contents.size() < 2)
        { return; }

        long lastRowId;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SIZE, contents.size());
        lastRowId = db.insert(TABLE_LIST_INFO, null, values);

        for(int i = 0; i < contents.size(); i++)
        {
            values = new ContentValues();
            values.put(COLUMN_LIST_ID, lastRowId);
            values.put(COLUMN_NAME, contents.get(i));
            db.insert(TABLE_LIST_CONTENTS, null, values);
        }
        db.close();
    }


    public List <PickList> getPickListsNameIdSize ()
    {
        List<PickList> list = new ArrayList<>();
        PickList tempList;
        SQLiteDatabase db = this.getReadableDatabase();
        String getListQuery = "SELECT * FROM " + TABLE_LIST_INFO;
        Cursor i = db.rawQuery(getListQuery, null);
        if(i.moveToFirst())
        {
            do {
                tempList = new PickList();
                tempList.ID = i.getInt(0);
                tempList.Name = i.getString(1);
                tempList.Size = i.getInt(2);
                list.add(tempList);
            }while(i.moveToNext());
        }
        i.close();
        return list;
    }

    public PickList getPickList(int id, String name, int size)
    {
        PickList list = new PickList();
        SQLiteDatabase db = this.getReadableDatabase();
        String getListQuery = "SELECT * FROM " + TABLE_LIST_CONTENTS + " WHERE " + COLUMN_LIST_ID + "=" + id;
        Cursor i = db.rawQuery(getListQuery, null);
        int k = 0;
        if(i.moveToFirst())
        {
            list.ID = id;
            list.Name = name;
            list.Size = size;
            do {
                list.Contents.add(i.getString(1));
                k++;
            }while(i.moveToNext());
        }
        i.close();
        return list;
    }

    public void deleteList(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteFromContents = "DELETE FROM " + TABLE_LIST_CONTENTS + " WHERE " + COLUMN_LIST_ID + "=" + id;
        String deleteFromInfo = "DELETE FROM " + TABLE_LIST_INFO + " WHERE " + COLUMN_LIST_ID + "=" + id;
        db.execSQL(deleteFromContents);
        db.execSQL(deleteFromInfo);
        db.close();
    }

    public void overwriteList(int id, String name, List<String> contents)
    {
        addList(name, contents);
        deleteList(id);
    }

    private void defaultList(SQLiteDatabase db){
        List<String> defaultList = new ArrayList<>();
        defaultList.add("One");
        defaultList.add("Two");
        defaultList.add("Three");
        defaultList.add("Four");
        defaultList.add("Five");
        addList("Default", defaultList, db);
    }

}
