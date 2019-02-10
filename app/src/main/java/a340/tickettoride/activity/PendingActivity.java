package a340.tickettoride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
        setupPlayerList();
    }

    private void findViews() {
        mPlayerList = findViewById(R.id.playerList);
        mGameName = findViewById(R.id.gameNameSubtitle);
    }

    private void setupGameName() {
        String name = activeGame.getCreator() + "'s game";
        mGameName.setText(name);
    }

    private void setupPlayerList() {
        activeGame = presenter.getActiveGame();
        mPlayerList.setText(""); // TODO: set the initial players
    }

    @Override
    public void onUpdatePlayers(Set<Player> players) {
        // TODO: update list of players
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
