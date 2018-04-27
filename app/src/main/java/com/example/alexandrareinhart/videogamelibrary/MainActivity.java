package com.example.alexandrareinhart.videogamelibrary;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements VideoGameAdapter.AdapterCallback, AddGameFragment.ActivityCallback {

    @BindView(R.id.game_recycler_view)
    protected RecyclerView recyclerView;

    private VideoGameDatabase videoGameDatabase;
    private VideoGameAdapter videoGameAdapter;
    private AddGameFragment addGameFragment;
    private LinearLayoutManager linearLayoutManager;

    /**
     * ASK CRYSTAL: This List is called videoGameList, but there is another list in the program called videoGamesList that we have been utilizing up to this point. Are they 2 different lists or the same? NEED TO FIX.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        videoGameDatabase = ((VideoGameApplication) getApplicationContext()).getDatabase(); //explicitly tell Android to go into VGA to get app context, since that is where we (developer) established context, but not where Android would normally get app context from.
        /**
         * ASK CRYSTAL: Where does Android "normally" get app context from if not specified by developer?
         */

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        videoGameAdapter = new VideoGameAdapter(videoGameDatabase.videoGameDao().getVideoGames(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoGameAdapter);

        videoGameAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.add_game_button)
    protected void addGameButtonClicked(){

        addGameFragment = AddGameFragment.newInstance();
        addGameFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addGameFragment).commit();
    }


    @Override
    public void addClicked() {

        getSupportFragmentManager().beginTransaction().remove(addGameFragment).commit();
        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
    }

    @Override
    public Context getContext(){

        return getApplicationContext(); //returns the context of the app to adapter
    }

    @Override
    public void rowClicked(VideoGame videoGame) {

        if(videoGame.isCheckedOut()){
            checkGameBackIn(videoGame);
        } else {
            checkGameOut(videoGame);
        }
    }

    @Override
    public void rowLongClicked(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Game?")
                .setMessage("Are you sure you want to permanently delete this game from your library?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoGameDatabase.videoGameDao().deleteVideoGame(videoGame); //delete game from database
                        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames()); //adapter updates view
                        Toast.makeText(MainActivity.this, "GAME DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void checkGameOut(final VideoGame videoGame) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check-Out Game?")
                .setMessage("Are you sure you want to check this game out?")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoGame.setCheckedOut(true);
                        videoGameDatabase.videoGameDao().updateVideoGame(videoGame); //update database
                        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames()); //adapter updates view
                        Toast.makeText(MainActivity.this, "GAME CHECKED OUT", Toast.LENGTH_LONG).show(); //Toast to confirm checkout
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void checkGameBackIn(final VideoGame videoGame) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.check_in_game)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        videoGame.setCheckedOut(false);
                        //update database with videogame info
                        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
                        //inform adapter; adapter updates view accordingly
                        videoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());

                        Toast.makeText(MainActivity.this, R.string.game_checked_in, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
