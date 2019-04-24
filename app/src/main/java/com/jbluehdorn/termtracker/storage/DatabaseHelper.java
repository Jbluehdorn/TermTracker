package com.jbluehdorn.termtracker.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jbluehdorn.termtracker.storage.tables.Table;
import com.jbluehdorn.termtracker.storage.TableFactory.TableType;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;

    // DB Info
    private static final String DB_NAME = "termsDatabase";
    private static final int DB_VERSION = 1;

    // Tables
    private static final ArrayList<Table> tables = new ArrayList<>();

    public static synchronized DatabaseHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION );

        //register tables
        tables.add(TableFactory.getTable(TableType.TERMS));
        tables.add(TableFactory.getTable(TableType.COURSES));
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
