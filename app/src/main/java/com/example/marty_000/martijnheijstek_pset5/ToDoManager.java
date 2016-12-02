package com.example.marty_000.martijnheijstek_pset5;

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
    public ArrayList<String> removeListName(int index, ArrayList<String> subLists) {
        subLists.remove(index);
        updatePrefs(subLists);
        return subLists;
    }


}