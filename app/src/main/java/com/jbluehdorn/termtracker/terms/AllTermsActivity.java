package com.jbluehdorn.termtracker.terms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;

import java.util.List;

public class AllTermsActivity extends AppCompatActivity {
    private LinearLayout listTerms;
    private List<Term> terms;

    public AllTermsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_terms);

        listTerms = findViewById(R.id.terms_list);
        this.populateTable();

        Button btnNewTerm = findViewById(R.id.btn_new_term);
        btnNewTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleBtnNewTerm(v);
            }
        });
    }

    private void populateTable() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 10);

        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        terms = db.getTerms();

        for(Term term : terms) {
            Button btn = new Button(this);
            btn.setText(term.getTitle());
            btn.setBackgroundResource(R.color.colorPrimaryDark);
            btn.setTextColor(getResources().getColor(R.color.colorWhite));
            btn.setLayoutParams(layoutParams);

            listTerms.addView(btn, 0);
        }
    }

    private void handleBtnNewTerm(View view) {
        Intent intent = new Intent(this, NewTermActivity.class);
        startActivity(intent);
    }
}
