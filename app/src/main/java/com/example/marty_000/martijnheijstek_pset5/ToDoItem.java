package com.example.marty_000.martijnheijstek_pset5;

public class ToDoItem {

        public int id_number;
        public int listNumber;
        public String text;
        public boolean checked;
        public boolean priority;
        private ToDoItem(int id_number, int listNumber, String text, boolean checked, boolean priority){
            this.id_number = id_number;
            this.text = text;
            this.listNumber = listNumber;
            this.checked = checked;
            this.priority = priority;
        }

    public ToDoItem(int id_number, String text, boolean checked){
        this.id_number = id_number;
        this.text = text;
        this.checked = checked;
    }
    public ToDoItem(String text){
        this.text = text;
    }
    public boolean checkChecked(){
        return this.checked;
    }

    public ToDoItem(int listNumber, String text){
            this.listNumber = listNumber;
            this.text = text;
    }

        public ToDoItem changeCheckedState(){
            checked = !checked;
            return this;
        }

        @Override
        public String toString() {
            return text;
        }
}
