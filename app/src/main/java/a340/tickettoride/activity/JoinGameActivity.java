package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IJoinGamePresenter;
import a340.tickettoride.presenter.JoinGamePresenter;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.model.Player;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class JoinGameActivity extends AppCompatActivity implements JoinGamePresenter.View {

    private IJoinGamePresenter presenter;
    private GameAdapter listAdapter;
    private TextView message;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        presenter = new JoinGamePresenter(this);
        message = findViewById(R.id.noGames);
        title = findViewById(R.id.joinGameTitle);
        title.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Games games = new Games();
                games.addGame(new Game(5, new Username("username")));
                onGameListUpdate(games);
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Games games = new Games();
        checkGameListLength(games);
        listAdapter = new GameAdapter(games.getGameList(), presenter);
        RecyclerView gameListRecycler = findViewById(R.id.gameListRecycler);
        gameListRecycler.setLayoutManager(new LinearLayoutManager(this));
        gameListRecycler.setAdapter(listAdapter);
    }

    @Override
    public void onGameJoined() {
        // start Pending activity
        Intent intent = new Intent(JoinGameActivity.this, PendingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGameJoinFail(String msg) {
        showMessage(msg);
    }

    @Override
    public void onGameListUpdate(final Games games) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkGameListLength(games);
                listAdapter.updateList(games.getGameList());
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void checkGameListLength(Games games) {
        int numGames = games.getGameList().size();

        if (numGames == 0) {
            message.setVisibility(View.VISIBLE);
        }
        else {
            message.setVisibility(View.GONE);
        }

    }



}


