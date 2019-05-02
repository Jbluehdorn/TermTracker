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
    private static final String COL_ACTIVE = "ACTIVE";

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
        this.columns.add(new Column(COL_ACTIVE, DataType.BOOLEAN));
    }

    @Override
    public Model get(int id, SQLiteDatabase db) {
        Term term = null;
        String SELECT_STRING = "SELECT * FROM " + this.name + " WHERE ID=" + id;
        Cursor cursor = db.rawQuery(SELECT_STRING, null);

        try {
            if(cursor.moveToFirst()) {
                term = this.createTermFromCursor(cursor);
            }
        } catch(Exception ex) {
            Log.e(TAG, "ERROR while trying to read term from db");
        } finally {
            cursor.close();
        }
        return term;
    }

    @Override
    public List<Model> get(SQLiteDatabase db) {
        List<Model> terms = new ArrayList<>();
        String SELECT_STRING = "SELECT * FROM " + this.name;

        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    Term term = this.createTermFromCursor(cursor);

                    terms.add(term);

                } while(cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.d(TAG, "ERROR while trying to read all terms from db");
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return terms;
    }

    @Override
    public void add(Model model, SQLiteDatabase db) {
        Term term = (Term) model;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_NAME, term.getTitle());
            values.put(COL_START, term.getStartDate().toString());
            values.put(COL_END, term.getEndDate().toString());
            values.put(COL_ACTIVE, term.getActive());

            if(term.getActive())
                setAllTemsInactive(db);

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
    public void delete(Model model, SQLiteDatabase db) {
        Term term = (Term) this.get(((Term) model).getId(), db);

        db.beginTransaction();
        try {
            db.delete(this.name, "ID = ?", new String[]  { String.valueOf(term.getId()) });
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int addOrUpdate(Model model, SQLiteDatabase db) {
        Term term = (Term) model;

        if(this.get(term.getId(), db) == null) {
            this.add(term, db);
            return 1;
        }

        ContentValues values = new ContentValues();
        values.put(COL_NAME, term.getTitle());
        values.put(COL_START, term.getStartDate().toString());
        values.put(COL_END, term.getEndDate().toString());
        values.put(COL_ACTIVE, term.getActive());

        //Ensures that only this term will be active
        if(term.getActive())
            setAllTemsInactive(db);

        return db.update(this.name, values, "ID = ?", new String[] { String.valueOf(term.getId()) });
    }

    private Term createTermFromCursor(Cursor cursor) {
        Term term = new Term();

        term.setID(cursor.getInt(cursor.getColumnIndex(COL_ID)));
        term.setTitle(cursor.getString(cursor.getColumnIndex(COL_NAME)));
        term.setStartDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_START))));
        term.setEndDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_END))));
        term.setActive(cursor.getInt(cursor.getColumnIndex(COL_ACTIVE)) > 0);

        return term;
    }

    private int setAllTemsInactive(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COL_ACTIVE, false);

        return db.update(this.name, values, null,null);
    }
}