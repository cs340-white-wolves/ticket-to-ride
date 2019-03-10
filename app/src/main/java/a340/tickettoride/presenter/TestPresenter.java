package a340.tickettoride.presenter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.ITest;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.Username;

public class TestPresenter implements ITest {

    private List<TestCommand> commands = new ArrayList<>();
    private TestCallback view;
    private static String className = "a340.tickettoride.model.ClientModel";


    public TestPresenter(TestCallback listener) {
        this.view = listener;
        ClientModel.getInstance().setActiveGame(new Game(2, new Username("Curt")));

    }

    private void createDestinationUpdate() {
        //Create list of Destination cards
        List<DestinationCard> list = new ArrayList<>();
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));

        Deck<DestinationCard> cardDeck = new Deck<>(list);

        commands.add(new TestCommand("Updating Destination Card Deck", className, "updateGameDestCardDeck", new Class<?>[] {cardDeck.getClass()}, new Object[] {cardDeck}));
    }


    private void addTrainCards() {

    }

    private void removeTrainCards() {

    }

    private void AddDestinationCards() {

    }

    private void removeDestinationCards() {

    }



    private void updatePlayerPoints() {

    }



    private void updateTrainCardsOpponents() {

    }

    private void updateTrainCarsOpponents() {

    }

    private void updateDestCardsOpponents() {

    }

    private void updateFaceupTrainCards() {

    }

    private void updateTrainCardCount() {

    }

    private void addClamiedRoute() {

    }

    private void addchatMessage() {

    }

    private void advancePlayerTurn() {

    }



    @Override
    public void executeCurrentTest() {
        try {
            commands.get(0).execute(ClientModel.getInstance());
            view.showTestName(commands.toString());

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }


    public interface TestCallback{
        void showTestName(String name);
        void endTest();

    }

 }

//    List<TrainCard> list = new ArrayList<>();
//    Deck<TrainCard> cardDeck = new Deck<>(list);
//        list.add(new TrainCard(TrainCard.Color.coalRed));
//                list.add(new TrainCard(TrainCard.Color.coalRed));
//                list.add(new TrainCard(TrainCard.Color.coalRed));
//                list.add(new TrainCard(TrainCard.Color.coalRed));
//                list.add(new TrainCard(TrainCard.Color.coalRed));
