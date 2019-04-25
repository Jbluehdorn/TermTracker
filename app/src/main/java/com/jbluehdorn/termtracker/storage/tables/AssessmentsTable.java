package com.jbluehdorn.termtracker.storage.tables;

import android.database.sqlite.SQLiteDatabase;

import com.jbluehdorn.termtracker.models.Model;

import java.util.List;

public class AssessmentsTable extends Table {
    private static AssessmentsTable sInstance;

    public static synchronized AssessmentsTable getInstance() {
        if(sInstance == null) {
            sInstance =  new AssessmentsTable();
        }
        return sInstance;
    }

    public AssessmentsTable() {
        super();

        this.name = "ASSESSMENTS";
        this.columns.add(new Column("TYPE", DataType.STRING));
        this.columns.add(new Column("DUE_DATE", DataType.DATE));
        this.columns.add(new Column("NOTES", DataType.STRING));
    }

    @Override
    public Model Get(int id, SQLiteDatabase db) {
        return null;
    }

    @Override
    public List<Model> Get(SQLiteDatabase db) {
        return null;
    }

    @Override
    public void Add(Model model, SQLiteDatabase db) {

    }

    @Override
    public void Delete(Model model, SQLiteDatabase db) {

    }

    @Override
    public void AddOrUpdate(Model model, SQLiteDatabase db) {

    }
}
