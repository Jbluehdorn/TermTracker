package com.jbluehdorn.termtracker.storage.tables;

public class NotesTable extends Table {
    public NotesTable() {
        super();

        this.columns.add(new Column("COURSE_ID", DataType.INT));
        this.columns.add(new Column("TEXT", DataType.STRING));
    }
}
