package com.jbluehdorn.termtracker.activities.terms;

import android.content.Intent;
import android.graphics.Color;
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
    }

    @Override
    public void onStart() {
        super.onStart();

        this.populateTable();

        Button btnNewTerm = findViewById(R.id.btn_new_term);
        btnNewTerm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleBtnNewTerm(v);
            }
        });
    }

    private void populateTable() {
        listTerms.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 10);

        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        terms = db.getTerms();

        for(int i = 0, len = terms.size(); i < len; i++) {
            final Term term = terms.get(i);
            Button btn = new Button(this);
            btn.setText(term.getTitle());
            btn.setBackgroundResource(R.color.colorPrimaryDark);
            btn.setTextColor(Color.WHITE);
            btn.setLayoutParams(layoutParams);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnEditTerm(view, term.getId());
                }
            });

            listTerms.addView(btn, 0);
        }
    }

    private void handleBtnEditTerm(View view, int id) {
        Intent intent = new Intent(this, SingleTermActivity.class);
        intent.putExtra("TYPE", SingleTermActivity.Type.EDIT);
        intent.putExtra("TERM_ID", id);
        startActivity(intent);
    }

    private void handleBtnNewTerm(View view) {
        Intent intent = new Intent(this, SingleTermActivity.class);
        intent.putExtra("TYPE", SingleTermActivity.Type.NEW);
        startActivity(intent);
    }
}
