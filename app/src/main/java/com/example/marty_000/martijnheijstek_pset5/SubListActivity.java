package com.example.marty_000.martijnheijstek_pset5;

/* Many Lists (To Do List app 2.0)
 * Martijn Heijstek, 10800441
 * 02-12-2016
 *
 * This class handles all interactions between a user and one To Do List.
 */

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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class SubListActivity extends AppCompatActivity {
    private DBHelper helper;
    private EditText editText;
    private SharedPreferences prefs;
    private ArrayAdapter<ToDoItem> adapter;
    private ArrayList<ToDoItem> toDoList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        String subListName;
        Intent intent = getIntent();
        subListName = intent.getExtras().getString("subListName");

        // Retrieve the helper that belongs to a specific To Do List
        helper = new DBHelper(this, subListName);

        context = getApplicationContext();
        editText = (EditText) findViewById(R.id.editText);
        ListView listView = (ListView) findViewById(R.id.toDoListView);
        toDoList = new ArrayList<>();

        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(subListName);

        TextView subTitle = (TextView) findViewById(R.id.subTitle);
        subTitle.setText(R.string.sub_subtitle);

        editText.setHint(R.string.sub_hint);

        // Load three toDoItems on first initialization
        prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (!prefs.contains("toDoList")) {

            helper.create(new ToDoItem("In this list you can add your items"));
            helper.create(new ToDoItem("Tap to check"));
            helper.create(new ToDoItem("And longpress to edit or remove"));

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("toDoList", "toDoList");
            editor.apply();
        }

        toDoList.addAll(helper.read());

        // Standard adapter
            adapter = new SubListAdapter(this, toDoList);
            listView.setAdapter(adapter);

            // Long press to delete or edit an item
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    ToDoItem editableItem = adapter.getItem(pos);

                    if (editableItem != null) {
                        // Send the user an alert dialog
                        popUp(editableItem.id_number, editableItem.text, pos, editableItem.checked);
                        updateAdapter();

                    } else {
                        Toast.makeText(context, "item is not editable", Toast.LENGTH_SHORT).show();
                        updateAdapter();
                    }

                    updateAdapter();
                    return true;
                }
            });

            // Tap to check/uncheck an item
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                    ToDoItem editableItem = adapter.getItem(pos);
                    editableItem.changeCheckedState();
                    helper.update(editableItem);
                    updateAdapter();
                }
            });
    }

    // This function will update the listview
        private void updateAdapter() {
            adapter.clear();
            toDoList.addAll(helper.read());
            adapter.notifyDataSetChanged();
        }

    // Add a new item to the listView
    public void add(View view) {
        String todo_string = editText.getText().toString();
        if(todo_string.length() !=0) {
            ToDoItem item = new ToDoItem(todo_string);
            helper.create(item);
            editText.getText().clear();
            updateAdapter();

        } else {
            Toast.makeText(context, "Please enter text!", Toast.LENGTH_SHORT).show();
        }
    }

    // Pop up screen that gives the user the ability to change an item
    public void popUp(final int id,final String popUpText,final int pos, final boolean  checked){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Update ToDo");
        alert.setMessage("Change the text of your to do item\nOr delete it");

        // Open key board
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setText(popUpText);
        alert.setView(input);

        // Set the cursor to the right of the text i the editText
        input.setSelection(input.getText().length());

        // Change the ToDoItem
        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(input.getText().length() != 0){
                    helper.update(new ToDoItem(id, input.getText().toString(), checked));
                    updateAdapter();
                } else {
                    Toast.makeText(context, "Please enter text!", Toast.LENGTH_SHORT).show();
                    popUp(id, popUpText, pos, checked);
                }
            }
        });

        // remove the item
        alert.setNeutralButton("Delete item", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                helper.delete(adapter.getItem(pos));
                updateAdapter();
            }
        });

        // Cancel popUp
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplication(), MainListActivity.class);
        startActivity(intent);
        finish();
    }
}