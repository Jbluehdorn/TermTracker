package com.jbluehdorn.termtracker.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.tables.AssessmentsTable;
import com.jbluehdorn.termtracker.storage.tables.CoursesTable;
import com.jbluehdorn.termtracker.storage.tables.Table;
import com.jbluehdorn.termtracker.storage.tables.TermsTable;

import java.util.ArrayList;
import java.util.List;

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
        tables.add(TermsTable.getInstance());
        tables.add(CoursesTable.getInstance());
        tables.add(AssessmentsTable.getInstance());
    }

    /*
        TERMS METHODS
     */
    public void addTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();
        TermsTable tbl = TermsTable.getInstance();

        tbl.add(term, db);
    }

    public List<Term> getTerms() {
        SQLiteDatabase db = getReadableDatabase();
        TermsTable tbl = TermsTable.getInstance();

        return (List<Term>)(List<?>) tbl.get(db);
    }

    public Term getTerm(int id) {
        SQLiteDatabase db = getReadableDatabase();
        TermsTable tbl = TermsTable.getInstance();

        return (Term) tbl.get(id, db);
    }

    public Boolean addOrUpdateTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();
        TermsTable tbl = TermsTable.getInstance();

        return tbl.addOrUpdate(term, db) == 1;
    }

    public void deleteTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();
        TermsTable tbl = TermsTable.getInstance();

        tbl.delete(term, db);
    }

    /*
        COURSE METHODS
     */

    public void addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        CoursesTable tbl = CoursesTable.getInstance();

        tbl.add(course, db);
    }

    public List<Course> getCourses() {
        SQLiteDatabase db = getReadableDatabase();
        CoursesTable tbl = CoursesTable.getInstance();

        return (List<Course>)(List<?>) tbl.get(db);
    }

    public Course getCourse(int id) {
        SQLiteDatabase db = getReadableDatabase();
        CoursesTable tbl = CoursesTable.getInstance();

        return (Course) tbl.get(id, db);
    }

    public Boolean addOrUpdateCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        CoursesTable tbl = CoursesTable.getInstance();

        return tbl.addOrUpdate(course, db) == 1;
    }

    public void deleteCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        CoursesTable tbl = CoursesTable.getInstance();

        tbl.delete(course, db);
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
