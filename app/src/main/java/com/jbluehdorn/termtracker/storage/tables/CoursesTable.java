package com.jbluehdorn.termtracker.storage.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.models.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CoursesTable extends Table {
    private static CoursesTable sInstance;

    //Columns
    private static final String COL_TERM_ID = "TERM_ID";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_START = "START_DATE";
    private static final String COL_END = "END_DATE";
    private static final String COL_STATUS = "STATUS";
    private static final String COL_NOTES = "NOTES";
    private static final String COL_MENTOR_NAME = "MENTOR_NAME";
    private static final String COL_MENTOR_PHONE = "MENTOR_PHONE";
    private static final String COL_MENTOR_EMAIL = "MENTOR_EMAIL";

    public static synchronized CoursesTable getInstance() {
        if(sInstance == null) {
            sInstance = new CoursesTable();
        }
        return sInstance;
    }

    private CoursesTable() {
        super();

        this.name = "COURSES";
        this.columns.add(new Column(COL_TERM_ID, DataType.INT));
        this.columns.add(new Column(COL_TITLE, DataType.STRING));
        this.columns.add(new Column(COL_START, DataType.DATE));
        this.columns.add(new Column(COL_END, DataType.DATE));
        this.columns.add(new Column(COL_STATUS, DataType.STRING));
        this.columns.add(new Column(COL_NOTES, DataType.STRING));
        this.columns.add(new Column(COL_MENTOR_NAME, DataType.STRING));
        this.columns.add(new Column(COL_MENTOR_PHONE, DataType.STRING));
        this.columns.add(new Column(COL_MENTOR_EMAIL, DataType.STRING));
    }

    @Override
    public Model get(int id, SQLiteDatabase db) {
        Course course = null;
        String SELECT_STRING = "SELECT * FROM " + this.name + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                course = this.createCourseFromCursor(cursor);
            }
        } catch(Exception ex) {
            Log.d(TAG, ex.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return course;
    }

    @Override
    public List<Model> get(SQLiteDatabase db) {
        List<Model> courses = new ArrayList<>();
        String SELECT_STRING = "SELECT * FROM " + this.name;

        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    Course course = this.createCourseFromCursor(cursor);

                    courses.add(course);
                } while(cursor.moveToNext());
            }
        } catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return courses;
    }

    @Override
    public void add(Model model, SQLiteDatabase db) {
        Course course = (Course) model;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_TERM_ID, course.getTermId());
            values.put(COL_TITLE, course.getTitle());
            values.put(COL_START, course.getStartDate().toString());
            values.put(COL_END, course.getEndDate().toString());
            values.put(COL_STATUS, course.getStatus());
            values.put(COL_NOTES, course.getNotes());
            values.put(COL_MENTOR_NAME, course.getMentorName());
            values.put(COL_MENTOR_EMAIL, course.getMentorEmail());
            values.put(COL_MENTOR_PHONE, course.getMentorPhone());

            db.insertOrThrow(this.name, null, values);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(course.getTitle(), ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void delete(Model model, SQLiteDatabase db) {
        Course course = (Course) this.get(((Course) model).getId(), db);
        db.beginTransaction();
        try {
            db.delete(this.name, "ID = ?", new String[] {String.valueOf(course.getId())});
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int addOrUpdate(Model model, SQLiteDatabase db) {
           Course course = (Course) model;

           if(this.get(course.getId(), db) == null) {
               this.add(course, db);
               return 1;
           }

           ContentValues values = new ContentValues();
           values.put(COL_TITLE, course.getTitle());
           values.put(COL_TERM_ID, course.getTermId());
           values.put(COL_START, course.getStartDate().toString());
           values.put(COL_END, course.getEndDate().toString());
           values.put(COL_STATUS, course.getStatus());
           values.put(COL_NOTES, course.getNotes());
           values.put(COL_MENTOR_NAME, course.getMentorName());
           values.put(COL_MENTOR_EMAIL, course.getMentorEmail());
           values.put(COL_MENTOR_PHONE, course.getMentorPhone());

           return db.update(this.name, values, "ID = ?", new String[] {String.valueOf(course.getId())});
    }

    private Course createCourseFromCursor(Cursor cursor) {
        Course course = new Course();

        course.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
        course.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));
        course.setTermId(cursor.getInt(cursor.getColumnIndex(COL_TERM_ID)));
        course.setStartDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_START))));
        course.setEndDate(LocalDate.parse(cursor.getString(cursor.getColumnIndex(COL_END))));
        course.setStatus(cursor.getString(cursor.getColumnIndex(COL_STATUS)));
        course.setNotes(cursor.getString(cursor.getColumnIndex(COL_NOTES)));
        course.setMentorName(cursor.getString(cursor.getColumnIndex(COL_MENTOR_NAME)));
        course.setMentorEmail(cursor.getString(cursor.getColumnIndex(COL_MENTOR_EMAIL)));
        course.setMentorPhone(cursor.getString(cursor.getColumnIndex(COL_MENTOR_PHONE)));

        return course;
    }
}
