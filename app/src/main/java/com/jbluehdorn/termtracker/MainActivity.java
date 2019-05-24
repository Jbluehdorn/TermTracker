package com.jbluehdorn.termtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jbluehdorn.termtracker.activities.terms.AllTermsActivity;
import com.jbluehdorn.termtracker.activities.terms.SingleTermActivity;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;

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
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<Term> allTerms = db.getTerms();

        for(Term t : allTerms) {
            if(t.getActive()) {
                Intent intent = new Intent(this, SingleTermActivity.class);
                intent.putExtra("TYPE", SingleTermActivity.Type.EDIT);
                intent.putExtra("TERM_ID", t.getId());
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(getApplicationContext(), R.string.no_curr_term_err, Toast.LENGTH_SHORT).show();
    }

    private void handleBtnAllTerms(View v) {
        Intent intent = new Intent(this, AllTermsActivity.class);
        startActivity(intent);
    }
}
