package com.jbluehdorn.termtracker.terms;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;

import java.util.List;

public class AllTermsActivity extends AppCompatActivity {
    private TableLayout tblTerms;
    private List<Term> terms;

    public AllTermsActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_terms);

        tblTerms = findViewById(R.id.tbl_terms);
        this.populateTable();
        this.stripeRows();
    }

    private void populateTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        terms = db.getTerms();

        for(Term term : terms) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(layoutParams);

            //First column
            TextView txtId = new TextView(this);
            txtId.setText(Integer.toString(term.getId()));
            row.addView(txtId, 0);

            //Second column
            TextView txtTitle = new TextView(this);
            txtTitle.setText(term.getTitle());
            row.addView(txtTitle,1 );

            //Third column
            TextView txtStart = new TextView(this);
            txtStart.setText(term.getStartDate().toString());
            row.addView(txtStart, 2);

            //Fourth column
            TextView txtEnd = new TextView(this);
            txtEnd.setText(term.getEndDate().toString());
            row.addView(txtEnd, 3);

            //Fifth column
            TextView txtActive = new TextView(this);
            txtActive.setText(term.getActive() ? "True" : "False");
            row.addView(txtActive, 4);

            tblTerms.addView(row);
        }
    }

    private void stripeRows() {
        for(int i = 0, j = tblTerms.getChildCount(); i < j; i++) {
            View view = tblTerms.getChildAt(i);
            if(view instanceof TableRow) {
                if(i % 2 == 0) {
                    TableRow row = (TableRow) view;
                    row.setBackgroundResource(R.color.colorLightGrey);
                }
            }
        }
    }
}
