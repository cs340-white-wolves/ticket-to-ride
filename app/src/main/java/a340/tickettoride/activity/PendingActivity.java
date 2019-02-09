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
    private Game pendingGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        presenter = new PendingPresenter(this);
        pendingGame = presenter.getPendingGame();

        findViews();
        setupGameName();
        setupPlayerList();
    }

    private void findViews() {
        mGameName = findViewById(R.id.gameNameSubtitle);
        mPlayerList = findViewById(R.id.playerList);
    }

    private void setupGameName() {
        String gameName = pendingGame.getCreator() + "\'s game";
        mGameName.setText(gameName);
    }

    private void setupPlayerList() {
        // get player list string
        Set<Player> players = pendingGame.getPlayers();

        mPlayerList.setText(playersToString(players)); // sets the initial players
    }

    private String playersToString(Set<Player> players) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {

        }
        return null;
    }

    public void onUpdateGame(Game updatedGame) {
        // update list of players
    }

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
