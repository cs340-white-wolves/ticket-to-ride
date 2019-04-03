package a340.tickettoride.presenter;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.IResultsPresenter;

public class ResultsPresenter implements IResultsPresenter {


    @Override
    public void terminateGame() {
        ClientModel model = ClientModel.getInstance();
        model.stopPoller();
        model.clearGame();
        model.startGameListPoller();
    }


}
