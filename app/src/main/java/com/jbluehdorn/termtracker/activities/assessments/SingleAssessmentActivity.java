package com.jbluehdorn.termtracker.activities.assessments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Assessment;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;
import com.jbluehdorn.termtracker.widgets.DateText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SingleAssessmentActivity extends AppCompatActivity {
    public enum Type {
        EDIT,
        NEW
    }

    private Button btnCancel, btnSave;
    private TextView txtHeader, txtNotes;
    private DateText dateDue;
    private AppCompatSpinner spinType;
    private Assessment assessment;
    private Type type;
    private int courseId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_form);

        type = (Type) getIntent().getSerializableExtra("TYPE");
        courseId = getIntent().getIntExtra("COURSE_ID", 0);

        txtHeader       = findViewById(R.id.txt_header);
        txtHeader.setText("New Assessment");

        txtNotes        = findViewById(R.id.txt_notes);
        dateDue         = findViewById(R.id.date_due);
        spinType        = findViewById(R.id.spin_type);

        btnCancel       = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave          = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });

        if(type == Type.EDIT) {
            //Load the course
            int assessmentId = getIntent().getIntExtra("ASSESSMENT_ID", 0);
            assessment = DatabaseHelper.getInstance(this).getAssessment(assessmentId);

            //update the header
            txtHeader.setText("Update Assessment");

            //Populate the form fields
            populateForm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type == Type.EDIT)
            getMenuInflater().inflate(R.menu.single_assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_toggle_alerts:
                enableAlerts();
                return true;
            case R.id.item_delete:
                delete();
                finish();
                return true;
            case R.id.item_share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateForm() {
        //TODO: Build
    }

    private void save() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        if(assessment == null)
            assessment = new Assessment();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        assessment.setCourseID(courseId);
        assessment.setDueDate(LocalDate.parse(dateDue.getText().toString(), formatter));
        assessment.setType(spinType.getSelectedItem().toString().toLowerCase().equals("performance") ? Assessment.Type.PERFORMANCE : Assessment.Type.OBJECTIVE);
        assessment.setNotes(txtNotes.getText().toString());

        db.addOrUpdateAssessment(assessment);
    }

    private void enableAlerts() {
        //TODO: Build
    }

    private void delete() {
        //TODO: Build
    }

    private void share() {
        //TODO: Build
    }
}
