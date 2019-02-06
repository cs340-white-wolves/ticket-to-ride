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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        presenter = new CreateGamePresenter(this);

        // numPlayers spinner wire-up and listener
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numPlayers = position + 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // createGame button wire-up and listener
//        Button mCreateButton = findViewByID(R.id.buttonid);
//        mCreateButton.setEnabled(true);
//        mCreateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.createGame(numPlayers);
//            }
//        });
    }

    public void gameCreated() {
        // switch to Pending activity
        Intent intent = new Intent(CreateGameActivity.this, PendingActivity.class);
        startActivity(intent);
    }

}
