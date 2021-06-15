package com.example.bus.dental.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bus.dental.R;
import com.example.bus.dental.models.Bloc;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Bloc> blocs;
    private Context mContext;

    public LessonAdapter(List<Bloc> blocs, Context mContext) {
        this.blocs = blocs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new TitleViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_title, parent, false));
            case 2:
                return new ParagraphViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_lesson_paragraph, parent, false));
            case 3:
                return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_lesson_image, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.titleContent.setText(blocs.get(position).getTitleContent());
                break;
            case 2:
                ParagraphViewHolder paragraphViewHolder = (ParagraphViewHolder) holder;
                paragraphViewHolder.paragraphContent.setText(blocs.get(position).getParagraphContent());
                break;
            case 3:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.imageLegend.setText(blocs.get(position).getLegend());
                Glide.with(mContext).load(blocs.get(position).getUrl()).into(imageViewHolder.imageContent);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (blocs.get(position).getTitleId() != null) {
            return 1;
        } else if (blocs.get(position).getParagraphId() != null) {
            return 2;
        } else if (blocs.get(position).getImageId() != null) {
            return 3;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return blocs.size();
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView titleContent;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleContent = itemView.findViewById(R.id.item_title_content);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageContent;
        TextView imageLegend;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageContent = itemView.findViewById(R.id.item_image_url);
            imageLegend = itemView.findViewById(R.id.item_image_legend);

        }
    }

    public class ParagraphViewHolder extends RecyclerView.ViewHolder {
        TextView paragraphContent;

        public ParagraphViewHolder(@NonNull View itemView) {
            super(itemView);
            paragraphContent = itemView.findViewById(R.id.item_paragraph_content);
        }
    }
}
