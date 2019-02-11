package a340.tickettoride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IPendingPresenter;
import a340.tickettoride.presenter.PendingPresenter;
import cs340.TicketToRide.model.Game;
import cs340.TicketToRide.model.Player;

public class PendingActivity extends AppCompatActivity implements PendingPresenter.View {
    private IPendingPresenter presenter;
    private TextView mGameName;
    private TextView mPlayerList;
    private Game activeGame;

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
        activeGame = presenter.getActiveGame();
        String name = activeGame.getCreator() + "'s game";
        mGameName.setText(name);
    }

    @Override
    public void onUpdatePlayers(Set<Player> players) {
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
    public void onGameStarting() { // this will be implemented in the next phase
        // start the game
        showMessage("Game is starting");
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
