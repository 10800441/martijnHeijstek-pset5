package com.example.marty_000.martijnheijstek_pset5;

import java.util.ArrayList;

/**
 * Created by marty_000 on 28-11-2016.
 */

public class ToDoList {
    int ID;
    private String title;
    private ArrayList<ToDoItem> items = new ArrayList<>();

    public ToDoList(String title, ArrayList<ToDoItem> items) {
        this.title = title;
        this.items = items;
    }

    public ToDoList(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
