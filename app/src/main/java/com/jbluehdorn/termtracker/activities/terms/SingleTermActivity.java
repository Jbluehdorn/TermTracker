package com.jbluehdorn.termtracker.activities.terms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;
import com.jbluehdorn.termtracker.widgets.DateText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SingleTermActivity extends AppCompatActivity {
    public enum Type {
        EDIT,
        NEW
    }

    private Type type;
    private int termId;
    private Term term;
    private EditText txtTitle;
    private DateText txtStart, txtEnd;
    private Switch chkActive;
    private Button btnSave, btnCancel;
    private TextView txtHeader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_form);

        txtHeader   = findViewById(R.id.txt_header);
        txtHeader.setText(R.string.new_term);

        txtTitle    = findViewById(R.id.txt_title);
        txtStart    = findViewById(R.id.date_start);
        txtEnd      = findViewById(R.id.date_end);
        chkActive   = findViewById(R.id.chk_active);

        btnSave     = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });

        btnCancel   = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        type = (Type) getIntent().getSerializableExtra("TYPE");

        if(type == Type.EDIT) {
            //Set the term
            termId = getIntent().getIntExtra("TERM_ID", 0);
            term = DatabaseHelper.getInstance(this).getTerm(termId);

            //Update the header
            txtHeader.setText(R.string.update_term);

            //Populate the form fields
            populateForm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type == Type.EDIT)
            getMenuInflater().inflate(R.menu.single_term_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_courses:
                return true;
            case R.id.item_delete:
                delete();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        if(this.type == Type.NEW)
            this.term = new Term();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        this.term.setTitle(txtTitle.getText().toString());
        this.term.setStartDate(LocalDate.parse(txtStart.getText().toString(), formatter));
        this.term.setEndDate(LocalDate.parse(txtEnd.getText().toString(), formatter));
        this.term.setActive(chkActive.isChecked());

        db.addOrUpdateTerm(this.term);
    }

    private void delete() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        db.deleteTerm(this.term);
    }

    private void populateForm() {
        txtTitle.setText(term.getTitle());
        txtStart.setText(term.getStartDateString());
        txtEnd.setText(term.getEndDateString());
        chkActive.setChecked(term.getActive());
    }
}
