package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import a340.tickettoride.R;
import a340.tickettoride.presenter.IJoinGamePresenter;
import a340.tickettoride.presenter.JoinGamePresenter;


public class JoinGameActivity extends AppCompatActivity implements JoinGamePresenter.View {
    private IJoinGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        presenter = new JoinGamePresenter(this);
    }

    public void gameJoined() {
        // start Pending activity
        Intent intent = new Intent(JoinGameActivity.this, PendingActivity.class);
        startActivity(intent);
    }
}
