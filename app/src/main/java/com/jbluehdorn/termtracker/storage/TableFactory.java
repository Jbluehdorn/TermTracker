package com.jbluehdorn.termtracker.storage;

import com.jbluehdorn.termtracker.storage.tables.CoursesTable;
import com.jbluehdorn.termtracker.storage.tables.NotesTable;
import com.jbluehdorn.termtracker.storage.tables.Table;
import com.jbluehdorn.termtracker.storage.tables.TermsTable;

public class TableFactory {
    private static final Table TERMS_INSTANCE = new TermsTable();
    private static final Table COURSES_INSTANCE = new CoursesTable();
    private static final Table NOTES_INSTANCE = new NotesTable();

    public enum TableType {
        TERMS,
        COURSES,
        NOTES
    }

    public static Table getTable(TableType tbl) {
        switch(tbl) {
            case TERMS:
                return TERMS_INSTANCE;
            case COURSES:
                return COURSES_INSTANCE;
            case NOTES:
                return NOTES_INSTANCE;
        }

        return null;
    }
}
