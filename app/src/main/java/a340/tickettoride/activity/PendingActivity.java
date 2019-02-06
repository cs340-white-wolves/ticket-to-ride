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
    private Set<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        presenter = new PendingPresenter(this);

        TextView gameName = findViewById(R.id.textView3);
        gameName.setText(presenter.getGameName());

        TextView playerList = findViewById(R.id.playerList);
        players = presenter.getPlayers();
        playerList.setText(""); // sets the initial players
    }

    public void updatePlayers(Set<Player> players) {
        // update list of players
    }

    public void gameStarting() { // this will be implemented in the next phase
        // start the game
        showMessage("Game is starting");
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }
}
