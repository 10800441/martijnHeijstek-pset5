package com.example.marty_000.martijnheijstek_pset5;

import android.content.SharedPreferences;

import java.util.ArrayList;
/**
 * Created by marty_000 on 28-11-2016.
 */
public class ToDoManager {

    SharedPreferences prefs;
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
    public ArrayList<ToDoList> getPreviousLists(int counter) {
        counter = prefs.getInt("counter", 0);
        for(int i =0; i < counter; i ++){
            if (prefs.getString(String.valueOf(i), null) != null ){
                ToDoList a = new ToDoList(prefs.getString(String.valueOf(i), null), new ArrayList<ToDoItem>());
                subLists.add(a);
            }

        }return subLists;
    }


        } // Delete from datatbase