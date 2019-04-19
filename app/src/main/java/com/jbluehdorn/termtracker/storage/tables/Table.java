package com.jbluehdorn.termtracker.storage.tables;

import java.util.Map;

abstract class Table {
    protected String name;
    protected Map<String, DataType> columns;
    protected enum DataType {
        STRING  ("STRING"),
        DATE    ("DATE"),
        INT     ("INTEGER");

        private String type;

        DataType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }

    }

    public Table() {}

    public String getCreateString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE " + this.name + "(");
        sb.append("ID INTEGER PRIMARY KEY,");
        for(Map.Entry<String, DataType> pair : columns.entrySet()) {
            sb.append(" " + pair.getKey() + " " + pair.getValue().toString() + ",");
        }
        sb.deleteCharAt(sb.length() - 1); //remove the final comma
        sb.append(")");

        return sb.toString();
    }
}
