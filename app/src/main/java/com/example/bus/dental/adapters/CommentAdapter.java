package com.example.bus.dental.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus.dental.R;
import com.example.bus.dental.models.Comment;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private List<Comment> mData;

    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.item_feedback,parent,false);
        return  new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getUname());
        holder.tv_comment.setText(mData.get(position).getComment());
        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_date;

        public CommentViewHolder(View itemView){
            super(itemView);
            tv_name=itemView.findViewById(R.id.item_uname);
            tv_date=itemView.findViewById(R.id.item_date);
            tv_comment=itemView.findViewById(R.id.item_comment);
        }
    }

    private String timestampToString(long time){
        Calendar calendar=Calendar.getInstance(Locale.FRENCH);
        calendar.setTimeInMillis(time);
        String date= DateFormat.format("MM/dd/yyyy HH:mm",calendar).toString();
        return date;
    }
}
