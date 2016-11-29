package com.example.marty_000.martijnheijstek_pset5;

import java.util.ArrayList;

/**
 * Created by marty_000 on 28-11-2016.
 */
public class ToDoManager {
    private static ToDoManager ourInstance = new ToDoManager();

    private static ArrayList<ToDoList> subLists = new ArrayList<>();

    public static ToDoManager getInstance() {
        return ourInstance;
    }


    private ToDoManager() {
        }

    public ArrayList<ToDoList> getLists(){
        return subLists;
    }
/*    public void setManagerString(String newString){
            managerString = newString;
        }
    public String getManagerString(){
            return managerString;
        }





  private static DatabaseHelper sInstance;

  private static final String DATABASE_NAME = "database_name";
  private static final String DATABASE_TABLE = "table_name";
  private static final int DATABASE_VERSION = 1;

  public static synchronized DatabaseHelper getInstance(Context context) {

    // Use the application context, which will ensure that you
    // don't accidentally leak an Activity's context.
    // See this article for more information: http://bit.ly/6LRzfx
    if (sInstance == null) {
      sInstance = new DatabaseHelper(context.getApplicationContext());
    }
    return sInstance;
  }

private DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
}




  */      //Delete methods etc die de database aanroepen

}