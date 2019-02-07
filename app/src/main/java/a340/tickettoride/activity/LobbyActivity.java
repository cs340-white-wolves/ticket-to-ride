package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import a340.tickettoride.R;

public class LobbyActivity extends AppCompatActivity {
    Button mCreateButton = null;
    Button mJoinButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        setupCreateGameButton();
        setupJoinGameButton();
    }

    private void setupJoinGameButton() {
        mJoinButton = findViewById(R.id.joinGameButton);
        mJoinButton.setEnabled(true);
        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJoinGameActivity();
            }
        });
    }

    private void setupCreateGameButton() {
        mCreateButton = findViewById(R.id.createGameButton);
        mCreateButton.setEnabled(true);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateGameActivity();
            }
        });
    }

    public void startCreateGameActivity() {
        Intent intent = new Intent(LobbyActivity.this, CreateGameActivity.class);
        startActivity(intent);
    }

    public void startJoinGameActivity() {
        Intent intent = new Intent(LobbyActivity.this, JoinGameActivity.class);
        startActivity(intent);
    }
}
