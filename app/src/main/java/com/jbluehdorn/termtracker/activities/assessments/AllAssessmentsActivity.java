package com.jbluehdorn.termtracker.activities.assessments;

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
import com.jbluehdorn.termtracker.models.Assessment;
import com.jbluehdorn.termtracker.models.Course;
import com.jbluehdorn.termtracker.storage.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AllAssessmentsActivity extends AppCompatActivity {
    private TextView txtHeader;
    private Button btnNew;
    private LinearLayout listAssessments;

    private Course course;
    private List<Assessment> assessments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessments);

        txtHeader       = findViewById(R.id.txt_header);
        listAssessments = findViewById(R.id.list_assessments);
        btnNew          = findViewById(R.id.btn_new_assessment);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnNewAssessment(view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(loadCourse()) {
            txtHeader.setText(this.course.getTitle());
            loadAssessments();
        } else {
            Toast.makeText(this, "Course was not found", Toast.LENGTH_LONG).show();
        }
    }

    public boolean loadCourse() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        this.course = db.getCourse(getIntent().getIntExtra("COURSE_ID", 0));

        return this.course != null;
    }

    private void loadAssessments() {
        this.assessments = new ArrayList<>();
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        //Load all assessments
        List<Assessment> allAssessments = db.getAssessments();

        for(Assessment assessment : allAssessments) {
            if(assessment.getCourseID() == this.course.getId()) {
                this.assessments.add(assessment);
            }
        }

        this.populateList();
    }

    private void populateList() {
        this.listAssessments.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(0, 0, 0, 10);

        for(final Assessment assessment : this.assessments) {
            Button btn = new Button(this);
            btn.setText(assessment.toString());
            btn.setBackgroundResource(R.color.colorPrimaryDark);
            btn.setTextColor(Color.WHITE);
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleBtnEditCourse(view, assessment.getId());
                }
            });

            listAssessments.addView(btn);
        }
    }

    private void handleBtnNewAssessment(View v) {
        Intent intent = new Intent(this, SingleAssessmentActivity.class);
        intent.putExtra("TYPE", SingleAssessmentActivity.Type.NEW);
        intent.putExtra("COURSE_ID", this.course.getId());
        startActivity(intent);
    }

    private void handleBtnEditCourse(View v, int id) {
        Intent intent = new Intent(this, SingleAssessmentActivity.class);
        intent.putExtra("TYPE", SingleAssessmentActivity.Type.EDIT);
        intent.putExtra("COURSE_ID", this.course.getId());
        intent.putExtra("ASSESSMENT_ID", id);
        startActivity(intent);
    }
}
