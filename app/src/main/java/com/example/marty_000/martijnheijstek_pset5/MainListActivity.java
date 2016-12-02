package com.example.marty_000.martijnheijstek_pset5;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainListActivity extends AppCompatActivity {

    private DBHelper helper;
    private ListView mainListView;
    private EditText mainEditText;
    private SharedPreferences prefs;
    private ArrayAdapter<String> adapter;
    private Context context;
    ArrayList<String> subLists= new ArrayList<>();
    ToDoManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        context = getApplicationContext();
        mainEditText = (EditText) findViewById(R.id.editText);
        mainListView = (ListView) findViewById(R.id.toDoListView);

        prefs = getApplicationContext().getSharedPreferences("listNames", MODE_PRIVATE);
        manager =  ToDoManager.getInstance(prefs);

        // Load example List
        if(!prefs.contains("ListTitleSet")) {
            SharedPreferences.Editor editor= prefs.edit();
            Set<String> ListTitleSet = new HashSet<>();
            editor.putStringSet("ListTitleSet", ListTitleSet);
            editor.apply();
        }

        subLists = manager.getListNames();

        // Standard adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, subLists);
        mainListView.setAdapter(adapter);

        // Long press to delete an item
        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                popUp(pos, adapter.getItem(pos));
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        // Tap to edit an item
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                String editableItem = adapter.getItem(pos);
                Intent intent = new Intent(getApplication(), SubListActivity.class);
                intent.putExtra("subListName", editableItem);
                startActivity(intent);
                finish();
            }
        });

    }



    // Add a new item to the listView
    public void add(View view) {
        String listName = mainEditText.getText().toString();
        if (listName.length() != 0) {
            if (!subLists.contains(listName)) {
                subLists.add(listName);
                manager.updatePrefs(subLists);
                mainEditText.getText().clear();
                adapter.notifyDataSetChanged(); 
            } else {
                Toast.makeText(context, "Your List name must be unique!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "Please enter text!", Toast.LENGTH_SHORT).show();
        }
    }
    // Pop up screen that gives the user the ability to change an item
    public void popUp(final int pos, final String dBname) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        String title = adapter.getItem(pos);
        alert.setTitle("Warning");
        alert.setMessage("Are you sure you want to delete the entire list '" + title + "'?");

        // Change the ToDoItem
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                adapter.remove(adapter.getItem(pos));
                deleteDatabase(dBname);
                subLists = manager.removeListName(pos, manager.getListNames());
                adapter.notifyDataSetChanged();

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




    @Override
    protected void onRestart() {
        super.onRestart();

        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(R.string.app_name);

        TextView subTitle = (TextView) findViewById(R.id.subTitle);
        subTitle.setText(R.string.sub_subtitle);

        mainEditText.setHint(R.string.main_hint);
    }
}