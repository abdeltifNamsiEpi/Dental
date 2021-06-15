package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bus.dental.R;
import com.example.bus.dental.adapters.LessonAdapter;
import com.example.bus.dental.adapters.LessonsAdapter;
import com.example.bus.dental.interfaces.APIInterface;
import com.example.bus.dental.models.Bloc;
import com.example.bus.dental.models.Lesson;
import com.example.bus.dental.models.Subject;
import com.example.bus.dental.utilities.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class LessonActivity extends AppCompatActivity {
    TextView lessonTitleTV;
    APIInterface apiInterface;
    private String lessonTitle;
    private int lessonId;
    private RecyclerView lessonRecyclerView;
    private LessonAdapter lessonAdapter;
    private ArrayList<Bloc> blocArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        lessonTitle = getIntent().getStringExtra("lesson_title");
        lessonId = getIntent().getIntExtra("lesson_id", 0);
        lessonTitleTV = findViewById(R.id.lesson_title_content);
        lessonTitleTV.setText(lessonTitle);


        apiInterface = APIClient.getClient().create(APIInterface.class);


        Call<Lesson> call = apiInterface.getLessonById(lessonId);
        call.enqueue(new Callback<Lesson>() {

            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                Log.e("TAG", response.body() + "");
                List<Bloc> list = response.body().getBlocs();
                blocArrayList.clear();
                blocArrayList.addAll(list);
                lessonAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                Log.e("fqqq", t.toString());
            }


        });

        lessonRecyclerView = findViewById(R.id.lesson_content_recycler_view);
        lessonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonAdapter=new LessonAdapter(blocArrayList,this);


        lessonRecyclerView.setAdapter(lessonAdapter);


    }
}