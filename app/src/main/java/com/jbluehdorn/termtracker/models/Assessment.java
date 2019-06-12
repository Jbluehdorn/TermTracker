package com.jbluehdorn.termtracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private String title;
    private int id, courseID;
    private Type type;
    private LocalDate dueDate;
    private String notes;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public Assessment() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    public int getCourseID() { return this.courseID; }

    public void setCourseID(int id) { this.courseID = id; }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public String getDueDateString() { return this.formatter.format(this.getDueDate()); }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getType());
        sb.append(" Assessment on ");
        sb.append(this.getDueDateString());
        return sb.toString();
    }
}
