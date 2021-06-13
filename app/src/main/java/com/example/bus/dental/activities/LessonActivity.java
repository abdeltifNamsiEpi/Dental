package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bus.dental.R;

public class LessonActivity extends AppCompatActivity {
    private String lessonTitle;
    TextView lessonTitleTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        lessonTitle = getIntent().getStringExtra("lesson_title");
        lessonTitleTV = findViewById(R.id.lesson_title_content);
        lessonTitleTV.setText(lessonTitle);


    }
}