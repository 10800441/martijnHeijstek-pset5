package com.example.marty_000.martijnheijstek_pset5;

/* Many Lists (To Do List app 2.0)
 * Martijn Heijstek, 10800441
 * 02-12-2016
 *
 * This class makes a singleton that is used to access different databases.
 * This class also contains operations on the List of database names used in MainListActivity
 */

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


// Singleton class to handle
public class ToDoManager {
    static SharedPreferences prefs;
    private static ToDoManager ourInstance = new ToDoManager();

    public static ToDoManager getInstance(SharedPreferences preferences) {

        prefs = preferences;
        return ourInstance;
    }

    private ToDoManager() {
    }

    // retrieval of the listnames
    public ArrayList<String> getListNames(){
        Set<String> listTitleSet = prefs.getStringSet("ListTitleSet", null);
        ArrayList<String> listNames = new ArrayList<>(listTitleSet);
        return listNames;
    }

    // saves the updated list to the memory
    public void updatePrefs(ArrayList<String> newSubList) {
        SharedPreferences.Editor edit = prefs.edit();
        Set<String> listTitleSet = new HashSet<>(newSubList);
        edit.putStringSet("ListTitleSet", listTitleSet);
        edit.apply();
    }

    // removes a value from the list and memory
    public ArrayList<String> removeListName(int index, ArrayList<String> subLists) {
        subLists.remove(index);
        updatePrefs(subLists);
        return subLists;
    }


}