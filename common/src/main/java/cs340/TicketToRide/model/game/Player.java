package cs340.TicketToRide.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.board.Route;
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

    private int score;
    private int numTrains;
    private TrainCards trainCards = new TrainCards();
    private DestinationCards destinationCards = new DestinationCards();
    private Color color;
    private ID id;
    private boolean award = false;

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

    public int getTrainPoints() {

        int trainPoints = score - getDestCardPoints();
        if (award) {
            trainPoints = trainPoints - 10;
        }
        return trainPoints;
    }

    public int getDestCardPoints() {
        int destPoints = 0;

        for (DestinationCard card: this.destinationCards) {
            if (card.isCompleted()) {
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

    public void setAward(boolean award) {
        this.award = award;
    }

    public boolean hasAward() {
        return this.award;
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
    public int compareTo(Player player) {

        if (player.getScore() == this.score) {

            if (player.getNumOfCompletedDests() > this.getNumOfCompletedDests()) {
                return -1;

            } else if (player.hasAward() && !this.hasAward()) {
                return -1;
            } else {
                return 0;
            }
        }
        else if (player.getScore() > this.score) { return -1; }
        else { return 1; }


    }

}
