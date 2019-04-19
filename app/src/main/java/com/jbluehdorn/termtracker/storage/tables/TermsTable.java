package com.jbluehdorn.termtracker.storage.tables;

import java.util.HashMap;
import java.util.Map;

public class TermsTable extends Table {

    public TermsTable() {
        super();
        String name = "TERMS";
        Map<String, DataType> columns = new HashMap<String, DataType>() {
            {
                put("NAME", DataType.STRING);
                put("START_DATE", DataType.DATE);
                put("END_DATE", DataType.DATE);
            }
        };

        this.name = name;
        this.columns = columns;
    }
}
