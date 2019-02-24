package cs340.TicketToRide.model.game.board;

import cs340.TicketToRide.model.game.card.TrainCard;

public class Route {
    private City city1;
    private City city2;
    private TrainCard.Color color; // if color is empty, it's gray
    private int length;

    // todo: should a route have a player occupying it? or a player should have a set of routes they occupy?


    public Route(City city1, City city2, TrainCard.Color color, int length) {
        this.city1 = city1;
        this.city2 = city2;
        this.color = color;
        this.length = length;
    }

    public int getPointValue() {
        switch (length) {
            case 1: return 1;
            case 2: return 2;
            case 3: return 4;
            case 4: return 7;
            case 5: return 10;
            case 6: return 15;
            default: return 0;
        }
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

    public TrainCard.Color getColor() {
        return color;
    }

    public void setColor(TrainCard.Color color) {
        this.color = color;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
