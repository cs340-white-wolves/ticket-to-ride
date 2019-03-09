package a340.tickettoride.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs340.TicketToRide.model.game.card.TrainCard;

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
        model.getActiveGame().setFaceUpTrainCards(newCards);
    }


}