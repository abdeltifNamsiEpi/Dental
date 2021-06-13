package com.example.bus.dental.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bus.dental.R;
import com.example.bus.dental.interfaces.APIInterface;
import com.example.bus.dental.models.Lesson;
import com.example.bus.dental.models.Subject;
import com.example.bus.dental.utilities.APIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class LessonActivity extends AppCompatActivity {
    private String lessonTitle;
    private int lessonId;
    TextView lessonTitleTV;
    APIInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        lessonTitle = getIntent().getStringExtra("lesson_title");
        lessonId=getIntent().getIntExtra("lesson_id",0);
        lessonTitleTV = findViewById(R.id.lesson_title_content);
        lessonTitleTV.setText(lessonTitle);


        apiInterface = APIClient.getClient().create(APIInterface.class);


        Call<Lesson> call = apiInterface.getLessonById(lessonId);
        call.enqueue(new Callback<Lesson>() {

            @Override
            public void onResponse(Call<Lesson> call, Response<Lesson> response) {
                Log.e("TAG",response.body()+"");

            }

            @Override
            public void onFailure(Call<Lesson> call, Throwable t) {
                Log.e("fqqq",t.toString());
            }


        });


    }
}