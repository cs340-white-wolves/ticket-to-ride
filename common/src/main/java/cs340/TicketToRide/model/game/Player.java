package cs340.TicketToRide.model.game;

import java.util.Objects;

import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;

public class Player implements Comparable<Player>{

    public enum Color {
        red, green, blue, yellow, black
    }
    private User user;

    private int totalPoints = 0;
    private int routePoints = 0;
    private int awardPoints = 0;
    private int numTrains;
    private int score;
    private TrainCards trainCards = new TrainCards();
    private DestinationCards destinationCards = new DestinationCards();
    private Color color;
    private ID id;

    public Player(User user) {
        setUser(user);
        id = ID.generateID();
    }

    public void addTrainCard(TrainCard card) {
        trainCards.add(card);
    }

    public void addDestCard(DestinationCard card) {
        destinationCards.add(card);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user == null || !user.isValid()) {
            throw new IllegalArgumentException();
        }
        this.user = user;
    }

    public int getDestCardCompletePoints() {
        int destPoints = 0;

        for (DestinationCard card: this.destinationCards) {
            if (card.isCompleted()) {
                destPoints += card.getPoints();
            }
        }

        return destPoints;
    }

    public int getDestCardIncompletePoints() {
        int destPoints = 0;

        for (DestinationCard card: this.destinationCards) {
            if (! card.isCompleted()) {
                destPoints += card.getPoints();
            }
        }

        return destPoints;
    }

    public int getNumOfCompletedDests() {
        int numDests = 0;

        for (DestinationCard card: this.destinationCards) {
            if (card.isCompleted()) {
                numDests++;
            }
        }

        return numDests;
    }

    public boolean hasEnoughTrainPieces (int length ) {
        return (this.getNumTrains() >= length);
    }

    public int getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(int routePoints) {
        this.routePoints = routePoints;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumTrains() {
        return numTrains;
    }

    public void setNumTrains(int numTrains) {
        this.numTrains = numTrains;
    }

    public TrainCards getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(TrainCards trainCards) {
        this.trainCards = trainCards;
    }

    public DestinationCards getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(DestinationCards destinationCards) {
        this.destinationCards = destinationCards;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isValid() {
        return user != null && user.isValid();
    }

    public ID getId() {
        return id;
    }

    public void setAwardPoints(int awardPoints) {
        this.awardPoints = awardPoints;
    }

    public int getAwardPoints() {
        return this.awardPoints;
    }

    public void updateTotalPoints () {
        setTotalPoints(getRoutePoints() + getDestCardCompletePoints() - getDestCardIncompletePoints() + getAwardPoints());
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    private void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(user, player.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public int compareTo(Player that) {

        // By Score
        if (this.getTotalPoints() < that.getTotalPoints()) {
            return 1;
        }

        if (this.getTotalPoints() > that.getTotalPoints()) {
            return -1;
        }

        // By number of completed destination tickets
        if (this.getNumOfCompletedDests() < that.getNumOfCompletedDests()) {
            return 1;
        }

        if (this.getNumOfCompletedDests() > that.getNumOfCompletedDests()) {
            return -1;
        }

        // The next tie breaker would be the longest continuous path, but
        // we don't have that, so we will say it's a tie!

        return 0;
    }

}
