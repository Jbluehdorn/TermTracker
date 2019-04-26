package com.jbluehdorn.termtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;
import com.jbluehdorn.termtracker.storage.tables.TermsTable;
import com.jbluehdorn.termtracker.terms.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnCurrTerm, btnAllTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCurrTerm = findViewById(R.id.btn_curr_term);
        btnCurrTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleBtnCurrTerm(v);
            }
        });

        btnAllTerms = findViewById(R.id.btn_all_terms);
        btnAllTerms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleBtnAllTerms(v);
            }
        });
    }

    private void handleBtnCurrTerm(View v) {
        Toast.makeText(getApplicationContext(), R.string.no_curr_term_err, Toast.LENGTH_SHORT).show();

        LocalDate now = LocalDate.now();
        LocalDate tommorrow = now.plusDays(1);

        Term term = new Term();
        term.setEndDate(tommorrow);
        term.setStartDate(now);
        term.setTitle("Bananas?");
        term.setActive(true);

        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        db.addOrUpdateTerm(term);

        List<Term> terms = db.getTerms();
        for(Term t : terms) {
            Log.d("Info", t.getTitle());
        }

        term = db.getTerm(1);
        db.deleteTerm(term);

        Log.d("Info", "Deleting...");
        terms = db.getTerms();
        for(Term t : terms) {
            Log.d("Info", t.getTitle());
        }
    }

    private void handleBtnAllTerms(View v) {
        Intent intent = new Intent(this, Controller.class);
        startActivity(intent);
    }
}
