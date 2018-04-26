package com.example.alexandrareinhart.videogamelibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGameFragment extends Fragment {

    private ActivityCallback activityCallback;
    private VideoGameDatabase videoGameDatabase;


    @BindView(R.id.enter_title_editText)
    protected EditText gameTitle;
    @BindView(R.id.enter_genre_editText)
    protected EditText gameGenre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static AddGameFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddGameFragment fragment = new AddGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {

        super.onStart();
        videoGameDatabase = ((VideoGameApplication) getActivity().getApplicationContext()).getDatabase();
    }

    @OnClick(R.id.add_game_fab)
    protected void addButtonClicked(){

        if(gameTitle.getText().toString().isEmpty() || gameGenre.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "ALL FIELDS ARE REQUIRED", Toast.LENGTH_LONG).show();

        } else {

            VideoGame videoGame = new VideoGame(gameTitle.getText().toString(), gameGenre.getText().toString(), new Date());
            addGameToDatabase(videoGame);

        }
    }

    private void addGameToDatabase(final VideoGame videoGame) {

        videoGameDatabase.videoGameDao().addVideoGame(videoGame);
        activityCallback.addClicked();
        activityCallback.up

        Toast.makeText(getActivity(), "GAME ADDED", Toast.LENGTH_LONG).show();
    }

    public void attachParent(ActivityCallback activityCallback){

        this.activityCallback = activityCallback;
    }

    public interface ActivityCallback{

        void addClicked();
    }
}
