package com.jbluehdorn.termtracker.activities.courses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;
import com.jbluehdorn.termtracker.widgets.DateText;
import com.jbluehdorn.termtracker.widgets.PhoneText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SingleCourseActivity extends AppCompatActivity {
    public enum Type {
        EDIT,
        NEW
    }

    private TextView txtHeader;
    private EditText txtTitle, txtMentorName, txtMentorEmail;
    private DateText txtStart, txtEnd;
    private Spinner spinStatus;
    private PhoneText txtMentorPhone;
    private Button btnCancel, btnSave;
    private Type type;
    private Course course;
    private int termId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_form);

        type = (Type) getIntent().getSerializableExtra("TYPE");
        termId = getIntent().getIntExtra("TERM_ID", 0);

        txtHeader       = findViewById(R.id.txt_header);
        txtHeader.setText("New Course");

        txtTitle        = findViewById(R.id.txt_title);
        txtMentorName   = findViewById(R.id.txt_mentor_name);
        txtMentorEmail  = findViewById(R.id.txt_mentor_email);
        txtStart        = findViewById(R.id.date_start);
        txtEnd          = findViewById(R.id.date_end);
        spinStatus      = findViewById(R.id.spin_status);
        txtMentorPhone  = findViewById(R.id.txt_mentor_phone);

        btnCancel       = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave         = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                finish();
            }
        });

        if(type == Type.EDIT) {
            //Load the course
            int courseId = getIntent().getIntExtra("COURSE_ID", 0);
            course = DatabaseHelper.getInstance(this).getCourse(courseId);

            //update the header
            txtHeader.setText("Update Course");

            //Populate the form fields
            populateForm();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(type == Type.EDIT)
            getMenuInflater().inflate(R.menu.single_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_assessments:
                //TODO: Flesh this out
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

        if(type == Type.NEW)
            course = new Course();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        course.setTitle(txtTitle.getText().toString());
        course.setStartDate(LocalDate.parse(txtStart.getText().toString(), formatter));
        course.setEndDate(LocalDate.parse(txtEnd.getText().toString(), formatter));
        course.setStatus(spinStatus.getSelectedItem().toString());
        course.setMentorName(txtMentorName.getText().toString());
        course.setMentorEmail(txtMentorEmail.getText().toString());
        course.setMentorPhone(txtMentorPhone.getText().toString());
        course.setTermId(termId);

        db.addOrUpdateCourse(course);
    }

    private void delete() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        db.deleteCourse(this.course);
    }

    private void populateForm() {
        txtTitle.setText(course.getTitle());
        txtStart.setText(course.getStartDateString());
        txtEnd.setText(course.getEndDateString());
        txtMentorName.setText(course.getMentorName());
        txtMentorEmail.setText(course.getMentorEmail());
        txtMentorPhone.setText(course.getMentorPhone());

        spinStatus.setSelection(((ArrayAdapter) spinStatus.getAdapter()).getPosition(course.getStatus()));
    }
}
