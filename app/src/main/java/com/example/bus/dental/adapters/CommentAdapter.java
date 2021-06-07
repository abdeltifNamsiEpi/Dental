package com.example.bus.dental.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bus.dental.activities.CommentClickInterface;
import com.example.bus.dental.R;
import com.example.bus.dental.models.Comment;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private CommentClickInterface commentClickInterface;

    private List<Comment> mData;

    public CommentAdapter(Context mContext, List<Comment> mData,CommentClickInterface commentClickInterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.commentClickInterface=commentClickInterface;

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
        holder.tv_lastname.setText(mData.get(position).getUlastname());
        holder.tv_comment.setText(mData.get(position).getComment());
        holder.tv_date.setText(timestampToString((Long)mData.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name,tv_comment,tv_lastname,tv_date;

        public CommentViewHolder(View itemView){
            super(itemView);
            tv_name=itemView.findViewById(R.id.item_uname);
            tv_date=itemView.findViewById(R.id.item_date);
            tv_comment=itemView.findViewById(R.id.item_comment);
            tv_lastname=itemView.findViewById(R.id.item_ulastname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentClickInterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }

    private String timestampToString(long time){
        Calendar calendar=Calendar.getInstance(Locale.FRENCH);
        calendar.setTimeInMillis(time);
        String date= DateFormat.format("MM/dd/yyyy HH:mm",calendar).toString();
        return date;
    }
}
