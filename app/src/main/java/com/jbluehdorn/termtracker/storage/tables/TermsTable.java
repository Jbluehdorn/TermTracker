package com.jbluehdorn.termtracker.storage.tables;

public class TermsTable extends Table {
    public TermsTable() {
        super();

        this.name = "TERMS";
        this.columns.add(new Column("Name", DataType.STRING));
        this.columns.add(new Column("START_DATE", DataType.DATE));
        this.columns.add(new Column("END_DATE", DataType.DATE));
    }
}