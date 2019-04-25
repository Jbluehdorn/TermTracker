package com.jbluehdorn.termtracker.storage.tables;

import android.database.sqlite.SQLiteDatabase;

import com.jbluehdorn.termtracker.models.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    protected String name;
    protected ArrayList<Column> columns = new ArrayList<>();
    protected static final String COL_ID = "ID";

    // What the type of a column will be
    protected enum DataType {
        STRING  ("STRING"),
        DATE    ("DATE"),
        INT     ("INTEGER"),
        BOOLEAN ("BOOLEAN");

        private String type;

        DataType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }

    // Class to hold type and name of each column
    protected class Column {
        private String name;
        private DataType type;

        Column(String name, DataType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {return this.name;}
        public DataType getType() {return this.type;}
    }

    protected Table() {}

    public String getCreateString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE " + this.name + "(");
        sb.append(COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        for(Column col : columns) {
            sb.append(" " + col.getName() + " " + col.getType().toString() + ",");
        }
        sb.deleteCharAt(sb.length() - 1); //remove the final comma
        sb.append(")");

        return sb.toString();
    }

    public String getDropString() {
        return "DROP TABLE IF EXISTS " + this.name;
    }

    public abstract Model Get(int id, SQLiteDatabase db);
    public abstract List<Model> Get(SQLiteDatabase db);
    public abstract void Add(Model model, SQLiteDatabase db);
    public abstract void Delete(Model model, SQLiteDatabase db);
    public abstract void AddOrUpdate(Model model, SQLiteDatabase db);
}
