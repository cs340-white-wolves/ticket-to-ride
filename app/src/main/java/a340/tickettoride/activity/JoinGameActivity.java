package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IJoinGamePresenter;
import a340.tickettoride.presenter.JoinGamePresenter;
import cs340.TicketToRide.model.Games;

public class JoinGameActivity extends AppCompatActivity implements JoinGamePresenter.View {
    private IJoinGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        presenter = new JoinGamePresenter(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView gameListRecycler = findViewById(R.id.gameListRecycler);
        gameListRecycler.setLayoutManager(new LinearLayoutManager(this));
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
    public void onGameListUpdate(Games games) {
        // TODO: Update recycler view with new game list
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }


}
