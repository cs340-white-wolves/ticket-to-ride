package cs340.TicketToRide.model.game.card;

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
        return city1.getName() + ", " + city2.getName();
    }

    @Override
    public int hashCode() {
        return points * (city1.hashCode() + city2.hashCode());
    }
}
