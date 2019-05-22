package com.jbluehdorn.termtracker.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jbluehdorn.termtracker.models.Assessment;
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
        TermsTable tbl = TermsTable.getInstance();
        tbl.add(term, getWritableDatabase());
    }

    public List<Term> getTerms() {
        TermsTable tbl = TermsTable.getInstance();
        return (List<Term>)(List<?>) tbl.get(getReadableDatabase());
    }

    public Term getTerm(int id) {
        TermsTable tbl = TermsTable.getInstance();
        return (Term) tbl.get(id, getReadableDatabase());
    }

    public Boolean addOrUpdateTerm(Term term) {
        TermsTable tbl = TermsTable.getInstance();
        return tbl.addOrUpdate(term, getReadableDatabase()) == 1;
    }

    public void deleteTerm(Term term) {
        TermsTable tbl = TermsTable.getInstance();
        tbl.delete(term, getWritableDatabase());
    }

    /*
        COURSE METHODS
     */

    public void addCourse(Course course) {
        CoursesTable tbl = CoursesTable.getInstance();
        tbl.add(course, getWritableDatabase());
    }

    public List<Course> getCourses() {
        CoursesTable tbl = CoursesTable.getInstance();
        return (List<Course>)(List<?>) tbl.get(getReadableDatabase());
    }

    public Course getCourse(int id) {
        CoursesTable tbl = CoursesTable.getInstance();
        return (Course) tbl.get(id, getReadableDatabase());
    }

    public Boolean addOrUpdateCourse(Course course) {
        CoursesTable tbl = CoursesTable.getInstance();
        return tbl.addOrUpdate(course,getWritableDatabase()) == 1;
    }

    public void deleteCourse(Course course) {
        CoursesTable tbl = CoursesTable.getInstance();
        tbl.delete(course, getWritableDatabase());
    }

    /*
        ASSSESSMENT METHODS
     */
    public void addAssessment(Assessment assessment) {
        AssessmentsTable tbl = AssessmentsTable.getInstance();
        tbl.add(assessment, getWritableDatabase());
    }

    public List<Assessment> getAssessments() {
        AssessmentsTable tbl = AssessmentsTable.getInstance();
        return (List<Assessment>)(List<?>) tbl.get(getReadableDatabase());
    }

    public Assessment getAssessment(int id) {
        AssessmentsTable tbl = AssessmentsTable.getInstance();
        return (Assessment) tbl.get(id, getReadableDatabase());
    }

    public Boolean addOrUpdateAssessment(Assessment assessment) {
        AssessmentsTable tbl = AssessmentsTable.getInstance();
        return tbl.addOrUpdate(assessment, getWritableDatabase()) == 1;
    }

    public void deleteAssessment(Assessment assessment) {
        AssessmentsTable tbl = AssessmentsTable.getInstance();
        tbl.delete(assessment, getWritableDatabase());
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
