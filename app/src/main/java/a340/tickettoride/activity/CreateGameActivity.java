package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import a340.tickettoride.R;
import a340.tickettoride.presenter.CreateGamePresenter;
import a340.tickettoride.presenter.ICreateGamePresenter;

public class CreateGameActivity extends AppCompatActivity implements CreateGamePresenter.View {
    private ICreateGamePresenter presenter;
    private int numPlayers = 2;
    private Button mCreateButton;
    private Spinner mSpinner;

    // Since 2 players is at offset 0, this is added to the spinner offset to calculate the
    // proper player number
    private final int SPINNER_OFFSET = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        presenter = new CreateGamePresenter(this);

        setupPlayerCountSpinner();
        setupCreateButton();
    }

    private void setupCreateButton() {
        mCreateButton = findViewById(R.id.createButton);
        mCreateButton.setEnabled(true);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createGame(numPlayers);
            }
        });
    }

    private void setupPlayerCountSpinner() {
        mSpinner = findViewById(R.id.spinner);
        mSpinner.setSelection(0);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numPlayers = position + SPINNER_OFFSET;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onGameCreated() {
        // switch to Pending activity
        Intent intent = new Intent(CreateGameActivity.this, PendingActivity.class);
        startActivity(intent);
    }

}
