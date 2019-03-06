package a340.tickettoride.presenter;

import a340.tickettoride.ServiceFacade;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.observerable.ModelChangeType;
import a340.tickettoride.observerable.ModelObserver;
import a340.tickettoride.presenter.interfaces.IJoinGamePresenter;
import cs340.TicketToRide.model.Games;
import cs340.TicketToRide.utility.ID;

public class JoinGamePresenter implements IJoinGamePresenter, ModelObserver {
    private View view;

    public JoinGamePresenter(View view) {
        ClientModel.getInstance().addObserver(this);
        this.view = view;
    }

    @Override
    public void joinGame(ID gameID) {

        if (gameID == null || !gameID.isValid()) {
            throw new IllegalArgumentException();
        }

        ServiceFacade.getInstance().joinGame(gameID);
    }

    @Override
    public void startObserving() {
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void stopObserving() {
        ClientModel.getInstance().deleteObserver(this);
    }


    @Override
    public void onModelEvent(ModelChangeType changeType, Object obj) {
        if (changeType == ModelChangeType.JoinGame) {
            view.onGameJoined();
            return;
        }

        if (changeType == ModelChangeType.AvailableGameList) {
            view.onGameListUpdate((Games) obj);
            return;
        }

        if (changeType == ModelChangeType.FailureException) {
            Exception e = (Exception) obj;
            view.onGameJoinFail(e.getMessage());
            return;
        }
    }

    public interface View {
        void onGameJoined();
        void onGameJoinFail(String msg);
        void onGameListUpdate(Games games);
    }
}
