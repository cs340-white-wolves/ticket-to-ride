package a340.tickettoride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import a340.tickettoride.R;
import a340.tickettoride.presenter.CreateGamePresenter;
import a340.tickettoride.presenter.ICreateGamePresenter;

public class CreateGameActivity extends AppCompatActivity implements CreateGamePresenter.View {
    ICreateGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        presenter = new CreateGamePresenter(this);
    }


}
