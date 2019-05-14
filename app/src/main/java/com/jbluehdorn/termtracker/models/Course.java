package com.jbluehdorn.termtracker.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Course implements Model {
    private int id, term_id;
    private LocalDate start_date, end_date;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private String title, status, notes, mentor_name, mentor_phone, mentor_email;

    public Course() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return term_id;
    }

    public void setTermId(int term_id) {
        this.term_id = term_id;
    }

    public LocalDate getStartDate() {
        return start_date;
    }
    public String getStartDateString() { return this.start_date.format(formatter); }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
    public String getEndDateString() { return this.end_date.format(formatter); }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMentorName() {
        return mentor_name;
    }

    public void setMentorName(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentorPhone() {
        return mentor_phone;
    }

    public void setMentorPhone(String mentor_phone) {
        this.mentor_phone = mentor_phone;
    }

    public String getMentorEmail() {
        return mentor_email;
    }

    public void setMentorEmail(String mentor_email) {
        this.mentor_email = mentor_email;
    }
}
