package com.jbluehdorn.termtracker.storage.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jbluehdorn.termtracker.models.Assessment;
import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.models.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AssessmentsTable extends Table {
    private static AssessmentsTable sInstance;

    //Columns
    private static final String COL_COURSE_ID = "COURSE_ID";
    private static final String COL_TYPE = "TYPE";
    private static final String COL_DUE_DATE = "DUE_DATE";
    private static final String COL_NOTES = "NOTES";
    private static final String COL_TITLE = "TITLE";

    public static synchronized AssessmentsTable getInstance() {
        if(sInstance == null) {
            sInstance =  new AssessmentsTable();
        }
        return sInstance;
    }

    public AssessmentsTable() {
        super();

        this.name = "ASSESSMENTS";
        this.columns.add(new Column(COL_COURSE_ID, DataType.INT));
        this.columns.add(new Column(COL_TYPE, DataType.STRING));
        this.columns.add(new Column(COL_DUE_DATE, DataType.DATE));
        this.columns.add(new Column(COL_NOTES, DataType.STRING));
        this.columns.add(new Column(COL_TITLE, DataType.STRING));
    }

    @Override
    public Model get(int id, SQLiteDatabase db) {
        Assessment assessment = null;
        String SELECT_STRING = "SELECT * FROM " + this.name + " WHERE ID = " + id;
        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                assessment = this.createAsssessmentFromCursor(cursor);
            }
        } catch(Exception ex) {
            Log.d(TAG, ex.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return assessment;
    }

    @Override
    public List<Model> get(SQLiteDatabase db) {
        List<Model> assessments = new ArrayList<>();
        String SELECT_STRING = "SELECT * FROM " + this.name;

        Cursor cursor = db.rawQuery(SELECT_STRING, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    Assessment assessment = this.createAsssessmentFromCursor(cursor);

                    assessments.add(assessment);
                } while(cursor.moveToNext());
            }
        } catch(Exception ex) {
            Log.d(TAG, ex.getMessage());
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return assessments;
    }

    @Override
    public void add(Model model, SQLiteDatabase db) {
        Assessment assessment = (Assessment) model;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COL_COURSE_ID, assessment.getCourseID());
            values.put(COL_DUE_DATE, assessment.getDueDate().toString());
            values.put(COL_NOTES, assessment.getNotes());
            values.put(COL_TYPE, assessment.getType().toString());
            values.put(COL_TITLE, assessment.getTitle());

            db.insertOrThrow(this.name, null, values);
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.e("ASSSESSMENT ERROR", ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void delete(Model model, SQLiteDatabase db) {
        Assessment assessment = (Assessment) this.get(((Assessment) model).getId(), db);
        db.beginTransaction();
        try {
            db.delete(this.name, "ID = ?", new String[] { String.valueOf(assessment.getId())});
            db.setTransactionSuccessful();
        } catch(Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public int addOrUpdate(Model model, SQLiteDatabase db) {
        Assessment assessment = (Assessment) model;

        if(this.get(assessment.getId(), db) == null) {
            this.add(assessment, db);
            return 1;
        }

        ContentValues values = new ContentValues();
        values.put(COL_COURSE_ID, assessment.getCourseID());
        values.put(COL_DUE_DATE, assessment.getDueDate().toString());
        values.put(COL_NOTES, assessment.getNotes());
        values.put(COL_TYPE, assessment.getType().toString());
        values.put(COL_TITLE, assessment.getTitle());

        return db.update(this.name, values, "ID = ?", new String[] { String.valueOf(assessment.getId())});

    }

    private Assessment createAsssessmentFromCursor(Cursor cursor) {
        Assessment assessment = new Assessment();

        assessment.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
        assessment.setCourseID(cursor.getInt(cursor.getColumnIndex(COL_COURSE_ID)));
        assessment.setDueDate(LocalDate.parse(cursor.getString((cursor.getColumnIndex(COL_DUE_DATE)))));
        assessment.setNotes(cursor.getString(cursor.getColumnIndex(COL_NOTES)));
        assessment.setTitle(cursor.getString(cursor.getColumnIndex(COL_TITLE)));

        String type = cursor.getString(cursor.getColumnIndex(COL_TYPE));
        Assessment.Type assessment_type = null;

        switch(type.toLowerCase()) {
            case "performance":
                assessment_type = Assessment.Type.PERFORMANCE;
                break;
            case "objective":
                assessment_type = Assessment.Type.OBJECTIVE;
                break;
            default:
                assessment_type = Assessment.Type.PERFORMANCE;
        }

        assessment.setType(assessment_type);

        return assessment;
    }

}
