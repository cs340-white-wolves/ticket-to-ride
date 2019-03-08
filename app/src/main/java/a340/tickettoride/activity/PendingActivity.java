package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.presenter.PendingPresenter;
import a340.tickettoride.presenter.interfaces.IPendingPresenter;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;

public class PendingActivity extends AppCompatActivity implements PendingPresenter.View {
    private IPendingPresenter presenter;
    private TextView mGameName;
    private TextView mPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        presenter = new PendingPresenter(this);

        findViews();
        setupGameName();
    }

    @Override
    protected void onResume() {
        presenter.startObserving();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.stopObserving();
        super.onPause();
    }

    private void findViews() {
        mPlayerList = findViewById(R.id.playerList);
        mGameName = findViewById(R.id.gameNameSubtitle);
    }

    private void setupGameName() {
        mGameName.setText("Loading game...");
    }

    @Override
    public void onUpdatePlayers(List<Player> players) {
        Log.i("JoinGame", "got player list");

        StringWriter sw = new StringWriter();

        for (Player player : players) {
            sw.append(player.getUser().getUsername().toString());
            sw.append("\n");
        }

        final String result = sw.toString();

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlayerList.setText(result);
            }
        });
    }

    @Override
    public void onUpdateGame(Game game) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Game activeGame = presenter.getActiveGame();
                String name = activeGame.getCreator() + "'s game";
                mGameName.setText(name);
            }
        });
    }

    @Override
    public void onGameStarting() { // this will be implemented in the next phase
        // start the game
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessage("Starting game!");
                Intent intent = new Intent(PendingActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
