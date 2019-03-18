package a340.tickettoride.presenter;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.IGameOverPresenter;

public class GameOverPresenter implements IGameOverPresenter {
    private View view;

    public GameOverPresenter(View view) {this.view = view;}

    public interface View {

    }
}
