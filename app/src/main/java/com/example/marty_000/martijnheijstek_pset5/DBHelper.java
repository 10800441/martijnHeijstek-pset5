package com.example.marty_000.martijnheijstek_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME;
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "todo_table";
    private String todoString = "todo_string";
    private String checked = "checked";

    // Constructor
    public DBHelper(Context context, String DB_NAME) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    // execute on databse creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query
        String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT , "
                + todoString + " TEXT , " + checked + " BOOLEAN )";
        db.execSQL(CREATE_TABLE);
    }

    // executed when database is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    // CRUD methods
    // Create new item in database
    public void create(ToDoItem item) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(todoString, item.text);
values.put(checked, item.checked);
        db.insert(DB_TABLE, null, values);
        db.close();
    }

    // Read database
    public ArrayList<ToDoItem> read() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ToDoItem> todo_list = new ArrayList<>();

        String query = "SELECT _id , " + todoString + " , " + checked + " FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String todo_string = cursor.getString(cursor.getColumnIndex(todoString));

            Boolean checkedItem = cursor.getInt(cursor.getColumnIndex(checked)) > 0;
            ToDoItem item = new ToDoItem(id, todo_string, checkedItem);
            System.out.println("" + checkedItem);
            todo_list.add(item);
        }

        cursor.close();
        db.close();
        return todo_list;
    }

    // update item in database
    public void update(ToDoItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(checked, item.checked);
        values.put(todoString, item.text);
        db.update(DB_TABLE, values, "_id = ? ", new String[] {String.valueOf(item.id_number)});
        db.close();
    }

    // delete item from database
    public void delete(ToDoItem item) {
        int id = item.id_number;
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DB_TABLE, " _id = ? ", new String[] {String.valueOf(id)});
        db.close();
    }
}