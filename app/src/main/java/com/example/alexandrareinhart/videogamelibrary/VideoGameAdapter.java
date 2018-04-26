package com.example.alexandrareinhart.videogamelibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindGame(videoGamesList.get(position));
        holder.itemView.setOnClickListener(holder.onClick(videoGamesList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onLongClick(videoGamesList.get(position)));
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
        protected ConstraintLayout rowLayout;
        @BindView(R.id.game_title)
        protected TextView gameTitle;
        @BindView(R.id.item_genre)
        protected TextView gameGenre;
        @BindView(R.id.item_date)
        protected TextView gameDate;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindGame(VideoGame videoGame) {

            gameTitle.setText(videoGame.getGameTitle());
            gameGenre.setText(adapterCallback.getContext().getString(R.string.game_genre, videoGame.getGameGenre()));


            if (videoGame.isCheckedOut()) {

                //Due date visible
                gameDate.setVisibility(View.VISIBLE);
                //Update checkout date
                videoGame.setDate(new Date());
                //Update background color - LIGHT GREY
                rowLayout.setBackgroundResource(R.color.lightGrey);
                //Calculate due date (2 wks out)
                int numberOfDays = 14;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(videoGame.getDate());
                calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY", Locale.US);
                gameDate.setText(adapterCallback.getContext().getString(R.string.game_due_date, formatter.format(date)));
            } else {

                //Due date invisible
                gameDate.setVisibility(View.INVISIBLE);
                //Update background color - PALE GREEN
                rowLayout.setBackgroundResource(R.color.paleGreen);
            }
        }

        public View.OnLongClickListener onLongClick(final VideoGame videoGame) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(videoGame);
                    return true;
                }
            };
        }


        public View.OnClickListener onClick(final VideoGame videoGame) {

            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(videoGame);
                }
            };
        }

        public void updateVisibility(VideoGame videoGame) {

            rowLayout.setVisibility(View.VISIBLE);
            gameTitle.setVisibility(View.VISIBLE);
            gameGenre.setVisibility(View.VISIBLE);
            adapterCallback.updateVisibility(videoGame);
        }
    }

    public interface AdapterCallback {

        Context getContext();
        void rowClicked(VideoGame videoGame);
        void rowLongClicked(VideoGame videoGame);
        void updateVisibility(VideoGame videoGame);
    }
}
