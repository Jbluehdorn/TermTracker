package com.jbluehdorn.termtracker.storage.tables;

import android.database.sqlite.SQLiteDatabase;

import com.jbluehdorn.termtracker.terms.Term;

public class TermsTable extends Table {
    private static TermsTable sInstance;

    public static synchronized TermsTable getInstance() {
        if(sInstance == null) {
            sInstance = new TermsTable();
        }
        return sInstance;
    }

    private TermsTable() {
        super();

        this.name = "TERMS";
        this.columns.add(new Column("Name", DataType.STRING));
        this.columns.add(new Column("START_DATE", DataType.DATE));
        this.columns.add(new Column("END_DATE", DataType.DATE));
    }

    public void AddTerm(Term term, SQLiteDatabase db) {

    }
}