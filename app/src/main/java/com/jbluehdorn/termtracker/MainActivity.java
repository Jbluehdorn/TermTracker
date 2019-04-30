package com.jbluehdorn.termtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jbluehdorn.termtracker.terms.AllTermsActivity;
import com.jbluehdorn.termtracker.util.Masker;

import java.time.LocalDate;

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

        String date1 = "05251994";
        String date2 = "052519941";
        String date3 = "1023";

        Log.d("Test", Masker.maskDate(date1));
        Log.d("Test", Masker.maskDate(date2));
        Log.d("Test", Masker.maskDate(date3));
    }

    private void handleBtnCurrTerm(View v) {
        Toast.makeText(getApplicationContext(), R.string.no_curr_term_err, Toast.LENGTH_SHORT).show();

        LocalDate now = LocalDate.now();
        LocalDate tommorrow = now.plusDays(1);
    }

    private void handleBtnAllTerms(View v) {
        Intent intent = new Intent(this, AllTermsActivity.class);
        startActivity(intent);
    }
}
