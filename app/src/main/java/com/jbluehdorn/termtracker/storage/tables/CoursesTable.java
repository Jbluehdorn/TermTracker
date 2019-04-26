package com.jbluehdorn.termtracker.storage.tables;

import android.database.sqlite.SQLiteDatabase;

import com.jbluehdorn.termtracker.models.Model;

import java.util.List;

public class CoursesTable extends Table {
    private static CoursesTable sInstance;

    public static synchronized CoursesTable getInstance() {
        if(sInstance == null) {
            sInstance = new CoursesTable();
        }
        return sInstance;
    }

    private CoursesTable() {
        super();

        this.name = "COURSES";
        this.columns.add(new Column("TERM_ID", DataType.INT));
        this.columns.add(new Column("TITLE", DataType.STRING));
        this.columns.add(new Column("START_DATE", DataType.DATE));
        this.columns.add(new Column("END_DATE", DataType.DATE));
        this.columns.add(new Column("STATUS", DataType.STRING));
        this.columns.add(new Column("NOTES", DataType.STRING));
        this.columns.add(new Column("MENTOR_NAME", DataType.STRING));
        this.columns.add(new Column("MENTOR_PHONE", DataType.STRING));
        this.columns.add(new Column("MENTOR_EMAIL", DataType.STRING));
    }

    @Override
    public Model get(int id, SQLiteDatabase db) {
        return null;
    }

    @Override
    public List<Model> get(SQLiteDatabase db) {
        return null;
    }

    @Override
    public void add(Model model, SQLiteDatabase db) {

    }

    @Override
    public void delete(Model model, SQLiteDatabase db) {

    }

    @Override
    public int addOrUpdate(Model model, SQLiteDatabase db) {
        return 0;
    }
}
