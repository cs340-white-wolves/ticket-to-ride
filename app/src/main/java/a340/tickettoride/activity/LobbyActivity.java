package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import a340.tickettoride.R;
import a340.tickettoride.presenter.ILobbyPresenter;
import a340.tickettoride.presenter.LobbyPresenter;

public class LobbyActivity extends AppCompatActivity implements LobbyPresenter.View {
    private Button mCreateButton = null;
    private Button mJoinButton = null;

    private ILobbyPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        presenter = new LobbyPresenter(this);

        setupCreateGameButton();
        setupJoinGameButton();
    }

    private void setupJoinGameButton() {
        mJoinButton = findViewById(R.id.joinGameButton);
        mJoinButton.setEnabled(true);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPressJoinGame();
            }
        });
    }

    private void setupCreateGameButton() {
        mCreateButton = findViewById(R.id.createGameButton);
        mCreateButton.setEnabled(true);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onPressCreateGame();
            }
        });
    }

    public void onPressCreateGame() {
        Intent intent = new Intent(LobbyActivity.this, CreateGameActivity.class);
        startActivity(intent);
    }

    public void onPressJoinGame() {
        Intent intent = new Intent(LobbyActivity.this, JoinGameActivity.class);
        startActivity(intent);
    }
}
