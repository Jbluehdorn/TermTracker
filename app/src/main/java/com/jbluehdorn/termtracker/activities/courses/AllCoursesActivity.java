package com.jbluehdorn.termtracker.activities.courses;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jbluehdorn.termtracker.R;
import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.models.Term;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AllCoursesActivity extends AppCompatActivity {
    private TextView txtHeader;
    private LinearLayout listCourses;
    private Button btnNew;

    private List<Course> courses = new ArrayList<>();
    private Term term;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);

        txtHeader   = findViewById(R.id.txt_header);
        listCourses = findViewById(R.id.list_courses);
        btnNew      = findViewById(R.id.btn_new_course);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnNewCourse(view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(loadTerm()) {
            txtHeader.setText(term.getTitle());
            loadCourses();
        } else {
            Toast.makeText(getApplicationContext(), R.string.term_not_found_err, Toast.LENGTH_LONG).show();
        }
    }

    private Boolean loadTerm() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        this.term = db.getTerm(getIntent().getIntExtra("TERM_ID", 0));

        return this.term != null;
    }

    //Load the courses into local memory
    private void loadCourses() {
        this.courses = new ArrayList<>();
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        //Get all courses
        List<Course> allCourses = db.getCourses();

        //Add courses relating to the term into the courses
        for(Course course : allCourses) {
            if(course.getTermId() == term.getId()) {
                this.courses.add(course);
            }
        }

        this.populateList();
    }

    //Populate the linear layout with all courses
    private void populateList() {
        listCourses.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 10);

        for(final Course course : this.courses) {
            Button btn = new Button(this);
            btn.setText(course.getTitle() + "\n Due: " + course.getEndDateString());
            btn.setBackgroundResource(R.color.colorPrimaryDark);
            btn.setTextColor(Color.WHITE);
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnEditCourse(view, course.getId());
                }
            });

            listCourses.addView(btn);
        }
    }

    public void handleBtnEditCourse(View v, int id) {
        Intent intent = new Intent(this, SingleCourseActivity.class);
        intent.putExtra("TYPE", SingleCourseActivity.Type.EDIT);
        intent.putExtra("TERM_ID", term.getId());
        intent.putExtra("COURSE_ID", id);
        startActivity(intent);
    }

    public void handleBtnNewCourse(View v) {
        Intent intent = new Intent(this, SingleCourseActivity.class);
        intent.putExtra("TYPE", SingleCourseActivity.Type.NEW);
        intent.putExtra("TERM_ID", term.getId());
        startActivity(intent);
    }
}
