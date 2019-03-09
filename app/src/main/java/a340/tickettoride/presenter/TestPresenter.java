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


    public TestPresenter(TestCallback listener) {
        this.view = listener;
        ClientModel.getInstance().setActiveGame(new Game(2, new Username("Curt")));
        List<DestinationCard> list = new ArrayList<>();
        Deck<DestinationCard> cardDeck = new Deck<>(list);
        cardDeck.addCard(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));
        cardDeck.addCard(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));
        Class<?>[] types = new Class<?>[] {cardDeck.getClass()};
        Object[] parameters = new Object[] {cardDeck};

        commands.add(new TestCommand("Updating Destination Card Deck","a340.tickettoride.model.ClientModel", "updateGameDestCardDeck", types, parameters));

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
