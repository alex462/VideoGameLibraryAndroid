package com.example.alexandrareinhart.videogamelibrary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoGameAdapter extends RecyclerView.Adapter<VideoGameAdapter.ViewHolder> {

    private List<VideoGame> videoGamesList;
    private AdapterCallback adapterCallback;

    public VideoGameAdapter(List<VideoGame> videoGamesList, AdapterCallback adapterCallback){

        this.adapterCallback = adapterCallback;
        this.videoGamesList = videoGamesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_game, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {

        holder.bind(videoGamesList.get(position));

        //TODO - set up onClicks for deleting games and checking out games
        holder.itemView.setOnClickListener(holder.onClick(videoGamesList).get(position));
        holder.itemView.setOnLongClickListener();
    }

    @Override
    public int getItemCount() {

        return videoGamesList.size();
    }

    public void updateList(List<VideoGame> list){

        videoGamesList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_row_layout)
        protected RelativeLayout rowLayout;
        @BindView(R.id.item_title)
        protected TextView gameTitle;
        @BindView(R.id.item_genre)
        protected TextView gameGenre;
        @BindView(R.id.item_date)
        protected TextView gameDate;

        public ViewHolder (View itemView){

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindGame(VideoGame videoGame){

            gameTitle.setText(videoGame.getGameTitle());
            gameGenre.setText();
        }
    }

    public interface AdapterCallback {


    }
}
