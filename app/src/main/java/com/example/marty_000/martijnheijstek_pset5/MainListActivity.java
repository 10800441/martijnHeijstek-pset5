package com.example.marty_000.martijnheijstek_pset5;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView mainListView;
    private EditText mainEditText;
    private SharedPreferences prefs;
    private ArrayAdapter<ToDoList> adapter;
    private Context context;
    ArrayList<ToDoList> subLists= new ArrayList<>();
    ToDoManager manager;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        context = getApplicationContext();
        mainEditText = (EditText) findViewById(R.id.editText);
        mainListView = (ListView) findViewById(R.id.toDoListView);
        manager =  ToDoManager.getInstance();

        // Load example List
        prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        if(!prefs.contains("counter")) {
            SharedPreferences.Editor initialEditor = prefs.edit();
            initialEditor.putInt("counter", 0);
            initialEditor.apply();
        }
        // Retrieve previous lists



//        for (int i = 0; i < numberOfTitles; i++) {
  //          String tableName = pref.getString(String.valueOf(i), "");
    //    subLists = manager.getPreviousLists(counter);





        updateAdapter();
    }

    // This function will update the listview
    private void updateAdapter() {
        subLists = manager.getLists();

        // Standard adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subLists);
        mainListView.setAdapter(adapter);

        // Long press to delete an item
        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                popUp(pos);
                updateAdapter();
                return true;
            }
        });

        // Tap to edit an item
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                ToDoList editableItem = adapter.getItem(pos);
                Intent intent = new Intent(getApplication(), SubListActivity.class);
                intent.putExtra("subListName", editableItem.toString());
                startActivity(intent);
                finish();
            }
        });
    }

    // Add a new item to the listView
    public void add(View view) {
        String todoListTitle= mainEditText.getText().toString();
        if (todoListTitle.length() != 0) {
            counter ++;
            ToDoList item = new ToDoList(counter, todoListTitle, new ArrayList<ToDoItem>());
            subLists.add(item);
            SharedPreferences.Editor prefEditor = prefs.edit();

            prefEditor.putString(String.valueOf(counter), todoListTitle);
            prefEditor.putInt("counter", counter);
            prefEditor.apply();

            mainEditText.getText().clear();
            updateAdapter();

        } else {
            Toast.makeText(context, "Please enter text!", Toast.LENGTH_SHORT).show();
        }
    }

    // Pop up screen that gives the user the ability to change an item
    public void popUp(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        String title = adapter.getItem(pos).toString();
        alert.setTitle("Warning");
        alert.setMessage("Are you sure you want to delete the entire list '" + title + "'?");

        // Change the ToDoItem
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                adapter.remove(adapter.getItem(pos));
               //TODO delete the whole database with name "toDolListTitle"

                updateAdapter();
            }
        });

        // Cancel popUp
        alert.setNegativeButton("No/Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}