package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bus.dental.R;
import com.example.bus.dental.adapters.LessonsAdapter;
import com.example.bus.dental.models.Lesson;

import java.util.ArrayList;

public class LessonsListActivity extends AppCompatActivity {

    private int subjectId;
    private String subjectTitle;
    private RecyclerView lessonsRecyclerView;
    private TextView subjectTitleTV;
    private LessonsAdapter lessonsAdapter;
    private ArrayList<Lesson> lessonslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        subjectId = getIntent().getIntExtra("subject_id", 0);
        subjectTitle = getIntent().getStringExtra("subject_title");

        subjectTitleTV = findViewById(R.id.subjectTitle);
        subjectTitleTV.setText(subjectTitle);

        lessonslist.add(new Lesson(1, subjectId, "Lesson 1"));
        lessonslist.add(new Lesson(1, subjectId, "Lesson 2"));
        lessonslist.add(new Lesson(1, subjectId, "Lesson 3"));
        lessonslist.add(new Lesson(1, subjectId, "Lesson 4"));
        lessonslist.add(new Lesson(1, subjectId, "Lesson 5"));
        lessonslist.add(new Lesson(1, subjectId, "Lesson 6"));

        lessonsRecyclerView = findViewById(R.id.lessonsList);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        lessonsAdapter = new LessonsAdapter(getApplicationContext(), lessonslist, position -> {
            Intent intent = new Intent(LessonsListActivity.this, LessonActivity.class);
            intent.putExtra("lesson_id", lessonslist.get(position).getId());
            intent.putExtra("lesson_title", lessonslist.get(position).getLessonTitle());
            startActivity(intent);
        });
        lessonsRecyclerView.setAdapter(lessonsAdapter);
    }
}
