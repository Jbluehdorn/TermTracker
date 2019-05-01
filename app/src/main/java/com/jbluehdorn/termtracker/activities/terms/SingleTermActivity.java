package com.jbluehdorn.termtracker.activities.terms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;
import com.jbluehdorn.termtracker.widgets.DateText;

public class SingleTermActivity extends AppCompatActivity {
    public enum Type {
        EDIT,
        NEW
    }

    private int termId;
    private Term term;
    private EditText txtTitle;
    private DateText txtStart, txtEnd;
    private Switch chkActive;
    private Button btnSave, btnCancel, btnDelete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_form);

        txtTitle    = findViewById(R.id.txt_title);
        txtStart    = findViewById(R.id.date_start);
        txtEnd      = findViewById(R.id.date_end);
        chkActive   = findViewById(R.id.chk_active);

        btnSave     = findViewById(R.id.btn_save);

        btnCancel   = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getIntent().getSerializableExtra("TYPE") == Type.EDIT) {
            termId = getIntent().getIntExtra("TERM_ID", 0);
            term = DatabaseHelper.getInstance(this).getTerm(termId);

            btnDelete = findViewById(R.id.btn_delete);
            btnDelete.setVisibility(View.VISIBLE);

            populateForm();
        }


    }

    private void populateForm() {
        txtTitle.setText(term.getTitle());
        txtStart.setText(term.getStartDateString());
        txtEnd.setText(term.getEndDateString());
        chkActive.setChecked(term.getActive());
    }
}
