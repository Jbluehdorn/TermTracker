package com.jbluehdorn.termtracker.storage.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jbluehdorn.termtracker.models.Model;
import com.jbluehdorn.termtracker.models.Term;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TermsTable extends Table {
    private static TermsTable sInstance;

    //Columns
    private static final String COL_NAME = "NAME";
    private static final String COL_START = "START_DATE";
    private static final String COL_END = "END_DATE";

    public static synchronized TermsTable getInstance() {
        if(sInstance == null) {
            sInstance = new TermsTable();
        }
        return sInstance;
    }

    private TermsTable() {
        super();

        this.name = "TERMS";
        this.columns.add(new Column(COL_NAME, DataType.STRING));
        this.columns.add(new Column(COL_START, DataType.DATE));
        this.columns.add(new Column(COL_END, DataType.DATE));
    }

    @Override
    public Model Get(int id, SQLiteDatabase db) {

        return null;
    }

    @Override
    public List<Model> Get(SQLiteDatabase db) {
        List<Model> terms = new ArrayList<>();
        String SELECT_STRING = "SELECT * FROM " + this.name;

        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    Term term = new Term();

                    term.setID(cursor.getInt(cursor.getColumnIndex("ID")));
                    term.setTitle(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                    term.setStartDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_START))));
                    term.setEndDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_END))));

                    terms.add(term);

                } while(cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.d(TAG, "ERROR while trying to read all posts from db");
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return terms;
    }

    @Override
    public void Add(Model model, SQLiteDatabase db) {
        Term term = (Term) model;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, term.getTitle());
            values.put(COL_START, term.getStartDate().toString());
            values.put(COL_END, term.getEndDate().toString());

            db.insertOrThrow(this.name, null, values);
            db.setTransactionSuccessful();

        } catch(Exception ex) {
            Log.d(TAG, "Error while trying to add term to database");
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void Delete(Model model, SQLiteDatabase db) {
        Term term = (Term) model;
    }

    @Override
    public void AddOrUpdate(Model model, SQLiteDatabase db) {

    }
}