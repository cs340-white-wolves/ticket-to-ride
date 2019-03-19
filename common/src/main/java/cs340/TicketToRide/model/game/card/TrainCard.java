package cs340.TicketToRide.model.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs340.TicketToRide.utility.ID;

public class TrainCard {

    private static final int NUM_NORMAL_CARDS = 12;
    private static final int NUM_LOCOMOTIVE_CARDS = 14;
    private ID id;

    public enum Color {
        passengerWhite, tankerBlue, reeferYellow, freightOrange, cabooseGreen,
        boxPurple, hopperBlack, coalRed, locomotive
    }

    private Color color;

    public TrainCard(Color color) {
        this.color = color;
        id = ID.generateID();
    }

    public Color getColor() {
        return this.color;
    }

    public static TrainCards createDeck() {
        TrainCards cards = new TrainCards();

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

        cards.shuffle();

        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainCard card = (TrainCard) o;
        return color == card.color && id.equals(card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
