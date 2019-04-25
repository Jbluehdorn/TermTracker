package com.jbluehdorn.termtracker.models;

import java.time.LocalDate;

public class Term implements Model {
    private int id;
    private String title;
    private LocalDate start, end;

    public Term() {}

    public Term(String title, LocalDate start, LocalDate end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Term(int id, String title, LocalDate start, LocalDate end) {
        this(title, start, end);

        this.id = id;
    }

    public int getId() {
        return this.id;
    }
    public void setID(int id) { this.id = id; }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getStartDate() {
        return this.start;
    }
    public void setStartDate(LocalDate date) { this.start = date; }

    public LocalDate getEndDate() {
        return this.end;
    }
    public void setEndDate(LocalDate date) { this.end = date; }
}
