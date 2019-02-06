package a340.tickettoride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import a340.tickettoride.R;
import a340.tickettoride.presenter.CreateGamePresenter;

public class CreateGameActivity extends AppCompatActivity implements CreateGamePresenter.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }


}
