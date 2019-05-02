package com.jbluehdorn.termtracker.models;

import java.time.LocalDate;

public class Assessment implements Model {
    public enum Type {
        PERFORMANCE,
        OBJECTIVE;

        @Override
        public String toString() {
            switch(this) {
                case PERFORMANCE:
                    return "Performance";
                case OBJECTIVE:
                    return "Objective";
            }

            return null;
        }
    }

    private Type type;
    private LocalDate dueDate;
    private String notes;

    public Assessment() {}

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
