package cs340.TicketToRide.model.game.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cs340.TicketToRide.utility.ID;

import static cs340.TicketToRide.model.game.card.TrainCard.Color.boxPurple;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.cabooseGreen;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.coalRed;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.freightOrange;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.hopperBlack;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.passengerWhite;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.reeferYellow;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.tankerBlue;

public class TrainCard {

    private static final int NUM_NORMAL_CARDS = 12;
    private static final int NUM_LOCOMOTIVE_CARDS = 14;
    private ID id;

    private static final int ORANGE = 0x7FFF9800;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0x7F000000;
    private static final int BLUE = 0x7F0000FF;
    private static final int YELLOW = 0x7FFFFF00;
    private static final int GREEN = 0x7F00FF00;
    private static final int PURPLE = 0x7FFF00FF;
    private static final int RED = 0x7FFF0000;

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

    public static Map<Color, Integer> getColorMap() {
        Map<TrainCard.Color, Integer> trainColorValues = new HashMap<>();

        trainColorValues.put(hopperBlack, BLACK);
        trainColorValues.put(passengerWhite, WHITE);
        trainColorValues.put(tankerBlue, BLUE);
        trainColorValues.put(reeferYellow, YELLOW);
        trainColorValues.put(freightOrange, ORANGE);
        trainColorValues.put(cabooseGreen, GREEN);
        trainColorValues.put(boxPurple, PURPLE);
        trainColorValues.put(coalRed, RED);

        return trainColorValues;
    }

    public static int getColorValue(Color color) {
        Map<Color, Integer> colorMap = getColorMap();
        Integer col = colorMap.get(color);
        return col == null ? RED : col;
    }

    @Override
    public String toString() {
        return getColorName(color);
    }

    public static String getColorName(Color color) {
        switch (color) {
            case locomotive: return "Locomotive";
            case hopperBlack: return "Black";
            case coalRed: return "Red";
            case boxPurple: return "Purple";
            case tankerBlue: return "Blue";
            case cabooseGreen: return "Green";
            case reeferYellow: return "Yellow";
            case freightOrange: return "Orange";
            case passengerWhite: return "White";
            default: return "Gray";
        }
    }
}
