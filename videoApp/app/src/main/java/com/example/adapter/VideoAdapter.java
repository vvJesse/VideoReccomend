package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entity.VideoEntity;
import com.example.videoapp.R;
import com.example.view.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoEntity> data;


    public void setData(List<VideoEntity> data) {
        this.data = data;
    }

    public VideoAdapter(Context context){
        this.context = context;
    }

    public VideoAdapter(Context context, List<VideoEntity> dt){
        this.context = context;
        this.data = dt;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_video_layout, parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        VideoEntity videoEntity = data.get(position);
        vh.tvTitle.setText(videoEntity.getVtitle());
        vh.tvAuthor.setText(videoEntity.getAuthor());
        vh.tvLike.setText(String.valueOf(videoEntity.getLikeNum()));
        vh.tvComment.setText(String.valueOf(videoEntity.getCommentNum()));
        vh.tvCollect.setText(String.valueOf(videoEntity.getCollectNum()));

        Picasso.with(context)
                .load(videoEntity.getHeadurl()).transform(new CircleTransform())
                .into(vh.imgHeader);
        Picasso.with(context).load(videoEntity.getCoverurl()).into(vh.imgCover);
    }

    @Override
    public int getItemCount() {
        if(data!=null&&data.size()>0)
            return data.size();
        else
            return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvLike;
        private TextView tvComment;
        private TextView tvCollect;
        private ImageView imgHeader;
        private ImageView imgCover;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvLike = itemView.findViewById(R.id.dz);
            tvComment = itemView.findViewById(R.id.comment);
            tvCollect = itemView.findViewById(R.id.collect);
            imgHeader = itemView.findViewById(R.id.img_header);
            imgCover = itemView.findViewById(R.id.img_cover);
        }
    }
}
