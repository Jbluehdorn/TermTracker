package com.jbluehdorn.termtracker.storage;

import com.jbluehdorn.termtracker.storage.tables.CoursesTable;
import com.jbluehdorn.termtracker.storage.tables.Table;
import com.jbluehdorn.termtracker.storage.tables.TermsTable;

public class TableFactory {
    private static final Table TERMS_INSTANCE = new TermsTable();
    private static final Table COURSES_INSTANCE = new CoursesTable();

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
        }

        return null;
    }
}
