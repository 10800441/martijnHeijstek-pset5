package com.example.marty_000.martijnheijstek_pset5;

/* Many Lists (To Do List app 2.0)
 * Martijn Heijstek, 10800441
 * 02-12-2016
 *
 * This class contains one To Do Item which is the building block of a To Do List.
 *  A To Do Item can be checked/unchecked (unchecked by default) and has text.
 */

public class ToDoItem {

    public int id_number;
    public String text;
    public boolean checked;

    public ToDoItem(int id_number, String text, boolean checked){
        this.id_number = id_number;
        this.text = text;
        this.checked = checked;
    }
    public ToDoItem(String text){
        this.text = text;
    }

    // Switch that changes the boolean checked iff not checked
    public ToDoItem changeCheckedState(){
       checked = !checked;
       return this;
    }

    @Override
    public String toString() {
            return text;
        }
}
