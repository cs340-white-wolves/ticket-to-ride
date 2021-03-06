package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import a340.tickettoride.R;
import a340.tickettoride.presenter.CreateGamePresenter;
import a340.tickettoride.presenter.interfaces.ICreateGamePresenter;

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

        String[] playerOptions = {"Two", "Three", "Four", "Five"};

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this,R.layout.spinner_item, playerOptions
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onGameCreated() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CreateGameActivity.this, PendingActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onInvalid(String errorMessage) {
        showMessage(errorMessage); // toast indicating what went wrong
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }

}
