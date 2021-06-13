package com.example.bus.dental.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus.dental.R;
import com.example.bus.dental.interfaces.ItemClickInterface;
import com.example.bus.dental.models.Lesson;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonViewHolder> {

    private Context mContext;
    private ItemClickInterface itemClickInterface;

    private List<Lesson> mData;

    public LessonsAdapter(Context mContext, List<Lesson> mData, ItemClickInterface itemClickInterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickInterface = itemClickInterface;

    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.lesson_item,parent,false);
        return  new LessonViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        holder.lessonTitle.setText(mData.get(position).getLessonTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle;
        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTitle=itemView.findViewById(R.id.lessonTitle);
        }
    }
}
