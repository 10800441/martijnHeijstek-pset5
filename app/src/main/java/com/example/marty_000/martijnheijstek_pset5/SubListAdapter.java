package com.example.marty_000.martijnheijstek_pset5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/* Many Lists (To Do List app 2.0)
 * Martijn Heijstek, 10800441
 * 02-12-2016
 *
 * Adapter to give the To Do Items in the listview a checkbox
 * used in SubListActivity
 */

    public class SubListAdapter extends ArrayAdapter<ToDoItem> {

        // constructor
        public SubListAdapter(Context context, ArrayList<ToDoItem> toDoList) {
            super(context, R.layout.sub_list_item, toDoList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.sub_list_item, parent, false);

            ToDoItem toDo = getItem(position);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.listViewCB);

            TextView text = (TextView) view.findViewById(R.id.listViewTV);
            text.setText(toDo.text);
            checkBox.setChecked(toDo.checked);
           return view;
        }
    }
