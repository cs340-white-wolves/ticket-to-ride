package cs340.TicketToRide.model.game.card;

import java.util.ArrayList;
import java.util.List;

import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.board.City;

public class DestinationCard {
    private City city1;
    private City city2;
    private int points;

    public DestinationCard(City city1, City city2, int points) {
        this.city1 = city1;
        this.city2 = city2;
        this.points = points;
    }



    private static final String[][] destCardCombos = {
            {"Denver", "El Paso", "4"},
            {"Kansas City", "Houston", "5"},
            {"New York", "Atlanta", "6"},
            {"Chicago", "New Orleans", "7"},
            {"Calgary", "Salt Lake City", "7"},
            {"Helena", "Los Angeles", "8"},
            {"Duluth", "Houston", "8"},
            {"Sault Ste. Marie", "Nashville", "8"},
            {"Montreal", "Atlanta", "9"},
            {"Sault Ste. Marie", "Oklahoma City", "9"},
            {"Seattle", "Los Angeles", "9"},
            {"Chicago", "Santa Fe", "9"},
            {"Duluth", "El Paso", "10"},
            {"Toronto", "Miami", "10"},
            {"Portland", "Phoenix", "11"},
            {"Dallas", "New York", "11"},
            {"Denver", "Pittsburgh", "11"},
            {"Winnipeg", "Little Rock", "11"},
            {"Winnipeg", "Houston", "12"},
            {"Boston", "Miami", "12"},
            {"Vancouver", "Santa Fe", "13"},
            {"Calgary", "Phoenix", "13"},
            {"Montreal", "New Orleans", "13"},
            {"Los Angeles", "Chicago", "16"},
            {"San Francisco", "Atlanta", "17"},
            {"Portland", "Nashville", "17"},
            {"Vancouver", "Montreal", "20"},
            {"Los Angeles", "Miami", "20"},
            {"Los Angeles", "New York", "21"},
            {"Seattle", "New York", "22"},
    };

    public static Deck<DestinationCard> createDeck(Game game) {
        List<DestinationCard> cards = new ArrayList<>();
        for (String[] combo : destCardCombos) {
            City city1 = game.getCityByName(combo[0]);
            City city2 = game.getCityByName(combo[1]);
            int points = Integer.parseInt(combo[2]);
            if (city1 == null || city2 == null) {
                throw new RuntimeException("This City doesn't exist");
            }
            cards.add(new DestinationCard(city1, city2, points));
        }
        return new Deck<>(cards);
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String toString() {
        return city1.getName() + " (" + city1.getCode() + "), " +
                city2.getName() + " (" + city2.getCode() + ")";
    }

    @Override
    public int hashCode() {
        return points * (city1.hashCode() + city2.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {return false;}
        if (o == this) {return true;}
        if(o.getClass() != getClass()) {return false;}

        DestinationCard other = (DestinationCard) o;

        return (this.getCity1().getName().equals(other.getCity1().getName()) &&
                this.getCity2().getName().equals(other.getCity2().getName()));
    }


}
