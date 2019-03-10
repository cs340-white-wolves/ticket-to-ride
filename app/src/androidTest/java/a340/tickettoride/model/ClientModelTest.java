package a340.tickettoride.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.Username;

import static org.junit.Assert.*;

public class ClientModelTest {

    @Test
    public void getActiveGame() {
        List<TrainCard> newCards = new ArrayList<>();
        newCards.add(new TrainCard(TrainCard.Color.reeferYellow));
        newCards.add(new TrainCard(TrainCard.Color.reeferYellow));
        newCards.add(new TrainCard(TrainCard.Color.reeferYellow));
        newCards.add(new TrainCard(TrainCard.Color.reeferYellow));
        newCards.add(new TrainCard(TrainCard.Color.reeferYellow));

        ClientModel model = ClientModel.getInstance();
        model.setActiveGame(new Game(2, new Username("Curt")));
        model.getActiveGame().setFaceUpTrainCards(newCards);
    }

}