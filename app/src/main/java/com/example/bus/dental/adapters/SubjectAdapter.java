package com.example.bus.dental.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus.dental.R;
import com.example.bus.dental.interfaces.ItemClickInterface;
import com.example.bus.dental.models.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private Context mContext;
    private ItemClickInterface itemClickInterface;

    private List<Subject> mData;

    public SubjectAdapter(Context mContext, List<Subject> mData, ItemClickInterface itemClickInterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickInterface = itemClickInterface;

    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.subject_item,parent,false);
        return  new SubjectViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.subjectTextview.setText(mData.get(position).getSubjectName());
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

    public class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextview;
        ImageView subjectIcon;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectIcon=itemView.findViewById(R.id.icon_subject);
            subjectTextview=itemView.findViewById(R.id.textview_subject);
        }
    }
}
