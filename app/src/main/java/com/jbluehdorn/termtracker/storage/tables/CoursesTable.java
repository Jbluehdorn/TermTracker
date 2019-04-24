package com.jbluehdorn.termtracker.storage.tables;

public class CoursesTable extends Table {
    public CoursesTable() {
        super();

        this.name = "COURSES";
        this.columns.add(new Column("TERM_ID", DataType.INT));
        this.columns.add(new Column("TITLE", DataType.STRING));
        this.columns.add(new Column("START_DATE", DataType.DATE));
        this.columns.add(new Column("END_DATE", DataType.DATE));
        this.columns.add(new Column("STATUS", DataType.STRING));
        this.columns.add(new Column("NOTES", DataType.STRING));
        this.columns.add(new Column("MENTOR_NAME", DataType.STRING));
        this.columns.add(new Column("MENTOR_PHONE", DataType.STRING));
        this.columns.add(new Column("MENTOR_EMAIL", DataType.STRING));
    }
}
