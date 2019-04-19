package com.jbluehdorn.termtracker.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

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

    public DatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
