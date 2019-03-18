package a340.tickettoride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import a340.tickettoride.R;
import a340.tickettoride.presenter.GameOverPresenter;
import a340.tickettoride.presenter.interfaces.IGameOverPresenter;

public class GameOverActivity extends AppCompatActivity implements GameOverPresenter.View {
    private IGameOverPresenter presenter;
    private Button finishGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        presenter = new GameOverPresenter(this);
    }
}
