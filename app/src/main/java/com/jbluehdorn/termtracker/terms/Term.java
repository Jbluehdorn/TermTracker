package com.jbluehdorn.termtracker.terms;

import java.time.LocalDate;

public class Term {
    private int id;
    private String title;
    private LocalDate start, end;

    public Term(int id, String title, LocalDate start, LocalDate end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getStartDate() {
        return this.start;
    }

    public LocalDate getEndDate() {
        return this.end;
    }
}
