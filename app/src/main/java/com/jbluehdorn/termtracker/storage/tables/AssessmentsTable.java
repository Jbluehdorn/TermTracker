package com.jbluehdorn.termtracker.storage.tables;

public class AssessmentsTable extends Table {
    public AssessmentsTable() {
        super();

        this.name = "ASSESSMENTS";
        this.columns.add(new Column("TYPE", DataType.STRING));
        this.columns.add(new Column("DUE_DATE", DataType.DATE));
        this.columns.add(new Column("NOTES", DataType.STRING));
    }
}
