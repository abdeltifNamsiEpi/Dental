package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bus.dental.R;
import com.example.bus.dental.adapters.LessonsAdapter;
import com.example.bus.dental.interfaces.APIInterface;
import com.example.bus.dental.models.Lesson;
import com.example.bus.dental.models.Subject;
import com.example.bus.dental.utilities.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonsListActivity extends AppCompatActivity {

    private int subjectId;
    private String subjectTitle;
    private RecyclerView lessonsRecyclerView;
    private TextView subjectTitleTV;
    private LessonsAdapter lessonsAdapter;
    private ArrayList<Lesson> lessonslist = new ArrayList<>();
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_list);
        subjectId = getIntent().getIntExtra("subject_id", 0);
        subjectTitle = getIntent().getStringExtra("subject_title");

        subjectTitleTV = findViewById(R.id.subjectTitle);
        subjectTitleTV.setText(subjectTitle);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Subject> call = apiInterface.getSubjectById(subjectId);
        call.enqueue(new Callback<Subject>() {


            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                Log.e("TAG", response.body() + "");
                List<Lesson> list=response.body().getLessons();
                lessonslist.clear();
                lessonslist.addAll(list);
                lessonsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                Log.e("TAG", t.toString());

            }
        });

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
