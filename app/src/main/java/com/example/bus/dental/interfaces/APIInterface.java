package com.example.bus.dental.interfaces;

import com.example.bus.dental.models.Lesson;
import com.example.bus.dental.models.Subject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {
    @GET("/subject")
    Call<List<Subject>> getSubjects();

    @GET("/subject/{id}")
    Call<Subject> getSubjectById(@Path("id") int id);
}
