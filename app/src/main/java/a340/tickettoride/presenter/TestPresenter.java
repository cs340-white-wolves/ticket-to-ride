package a340.tickettoride.presenter;


import java.util.ArrayList;
import java.util.List;

import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.interfaces.ITest;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

public class TestPresenter implements ITest {

    private List<TestCommand> commands = new ArrayList<>();
    private TestCallback view;
    private static String className = "a340.tickettoride.model.ClientModel";
    private int currentIndex = 0;


    public TestPresenter(TestCallback listener) {
        this.view = listener;

        updatePlayerPoints();
        addTrainCards();
        removeTrainCards();

        addDestinationCards();
        removeDestinationCards();

        updateTrainCardsOpponents();
        updateTrainCarsOpponents();
        updateDestCardsOpponents();
        updateFaceupTrainCards();
        updateTrainCardCount();
        updateDestinationCardCount();
        addClamiedRoute();
        addchatMessage();
        advancePlayerTurn();

    }

    private void updatePlayerPoints() {
        commands.add(new TestCommand("Updating player's score to 100", className, "updateActivePlayersPoints", new Class<?>[] {int.class}, new Object[] {100}));
    }

    private void addTrainCards() {
        TrainCards cards = new TrainCards();
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        commands.add(new TestCommand("Adding train cards to player", className, "updatePlayersTrainCards", new Class<?>[] {cards.getClass()}, new Object[] {cards}));

    }

    private void removeTrainCards() {
        commands.add(new TestCommand("Removing all train Cards from player", className, "removePlayersTrainCards", new Class<?>[] {}, new Object[] {}));
    }

    private void addDestinationCards() {
        //Create list of Destination cards
        List<DestinationCard> list = new ArrayList<>();
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));

        DestinationCards cardDeck = new DestinationCards();
        cardDeck.addAll(list);

        commands.add(new TestCommand("Adding destination cards to player", className, "updatePlayersDestCards", new Class<?>[] {cardDeck.getClass()}, new Object[] {cardDeck}));
    }


    private void removeDestinationCards() {

        commands.add(new TestCommand("Removing player's destination cards", className, "removePlayersDestCards", new Class<?>[] {}, new Object[] {}));
    }


    private void updateTrainCardsOpponents() {
        TrainCards cards = new TrainCards();
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.reeferYellow));
        cards.add(new TrainCard(TrainCard.Color.locomotive));
        cards.add(new TrainCard(TrainCard.Color.passengerWhite));
        cards.add(new TrainCard(TrainCard.Color.boxPurple));
        commands.add(new TestCommand("Changing opponents cards to 6", className, "updateOpponentsTrainCards", new Class<?>[] {cards.getClass()}, new Object[] {cards}));
    }

    private void updateTrainCarsOpponents() {
        commands.add(new TestCommand("Changing opponent's train cars to 10", className, "updateOpponentsTrainCars", new Class<?>[] {int.class}, new Object[] {10}));
    }

    private void updateDestCardsOpponents() {
        List<DestinationCard> list = new ArrayList<>();
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));

        DestinationCards cardDeck = new DestinationCards();
        cardDeck.addAll(list);

        commands.add(new TestCommand("Adding 2 destinations cards to opponents", className, "updateOpponentsDestCards", new Class<?>[] {cardDeck.getClass()}, new Object[] {cardDeck}));
    }

    private void updateFaceupTrainCards() {
        TrainCards cards = new TrainCards();
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.reeferYellow));

        commands.add(new TestCommand("Changing face up cards to 3 (Black, Red, Yellow)", className, "updateFaceUpTrainCards", new Class<?>[] {cards.getClass()}, new Object[] {cards}));
    }

    private void updateTrainCardCount() {
        TrainCards cards = new TrainCards();
        cards.add(new TrainCard(TrainCard.Color.hopperBlack));
        cards.add(new TrainCard(TrainCard.Color.coalRed));
        cards.add(new TrainCard(TrainCard.Color.reeferYellow));
        cards.add(new TrainCard(TrainCard.Color.reeferYellow));

        commands.add(new TestCommand("Changing train card count to 4", className, "updateTrainCardDeck", new Class<?>[] {cards.getClass()}, new Object[] {cards}));
    }

    private void updateDestinationCardCount() {
        List<DestinationCard> list = new ArrayList<>();
        list.add(new DestinationCard(new City("Los Angeles", "LA", 34.0522, -118.2437), new City("Seattle", "SEA", 47.6062, -122.3321), 3));

        DestinationCards cardDeck = new DestinationCards();
        cardDeck.addAll(list);

        commands.add(new TestCommand("Changing destination card count to 1", className, "updateGameDestCardDeck", new Class<?>[] {cardDeck.getClass()}, new Object[] {cardDeck}));
    }

    private void addClamiedRoute() {
        commands.add(new TestCommand("Adding claimed route for each player", className, "updateRoute", new Class<?>[] {}, new Object[] {}));
    }

    private void addchatMessage() {
        commands.add(new TestCommand("Adding message for each player", className, "addChatMessages", new Class<?>[] {}, new Object[] {}));
    }

    private void advancePlayerTurn() {
        commands.add(new TestCommand("Advance Turn marker", className, "setTurn", new Class<?>[] {}, new Object[] {}));
    }

    @Override
    public void executeCurrentTest() {
        try {
            if (currentIndex < commands.size()) {
                commands.get(currentIndex).execute(ClientModel.getInstance());
                view.showTestName(commands.get(currentIndex).toString());
                currentIndex++;
            } else {
                view.endTest();
            }

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }


    public interface TestCallback{
        void showTestName(String name);
        void endTest();

    }

 }
