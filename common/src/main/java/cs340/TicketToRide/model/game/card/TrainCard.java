package cs340.TicketToRide.model.game.card;

import java.util.ArrayList;
import java.util.List;

public class TrainCard {

    private static final int NUM_NORMAL_CARDS = 12;
    private static final int NUM_LOCOMOTIVE_CARDS = 14;

    public enum Color {
        passengerWhite, tankerBlue, reeferYellow, freightOrange, cabooseGreen,
        boxPurple, hopperBlack, coalRed, locomotive
    }

    private Color color;

    public TrainCard(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public static Deck<TrainCard> createDeck() {
        List<TrainCard> cards = new ArrayList<>();

        for (int i = 0; i < NUM_NORMAL_CARDS; i++) {
            cards.add(new TrainCard(Color.passengerWhite));
            cards.add(new TrainCard(Color.tankerBlue));
            cards.add(new TrainCard(Color.reeferYellow));
            cards.add(new TrainCard(Color.freightOrange));
            cards.add(new TrainCard(Color.cabooseGreen));
            cards.add(new TrainCard(Color.boxPurple));
            cards.add(new TrainCard(Color.hopperBlack));
            cards.add(new TrainCard(Color.coalRed));
        }

        for (int i = 0; i < NUM_LOCOMOTIVE_CARDS; i++) {
            cards.add(new TrainCard(Color.locomotive));
        }

        return new Deck<TrainCard>(cards);
    }
}
