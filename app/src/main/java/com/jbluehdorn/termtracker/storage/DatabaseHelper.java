package com.jbluehdorn.termtracker.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.jbluehdorn.termtracker.storage.tables.Table;
import com.jbluehdorn.termtracker.storage.tables.TermsTable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DB Info
    private static final String DB_NAME = "termsDatabase";
    private static final int DB_VERSION = 1;

    // Table Names
    private static final String TABLE_TERMS = "terms";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_NOTES = "notes";
    private static final String TABLE_ASSESSMENTS = "assessments";
    private static final String TABLE_ALERTS = "alerts";

    // Tables
    private static final ArrayList<Table> tables = new ArrayList<>();
    private static final Table TermTable = new TermsTable();

    public DatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION );

        //register tables
        tables.add(TermTable);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(Table tbl : tables) {
            db.execSQL(tbl.getCreateString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1) {
            for(Table tbl : tables) {
                db.execSQL(tbl.getDropString());
            }
            onCreate(db);
        }
    }
}
