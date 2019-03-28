package cs340.TicketToRide.utility;

import java.util.Objects;

import cs340.TicketToRide.model.game.card.TrainCard;

public class RouteColorOption {
    private TrainCard.Color color;
    private int numOfColor;
    private int numLocomotives;

    public RouteColorOption(TrainCard.Color color, int numOfColor, int numLocomotives) {
        this.color = color;
        this.numOfColor = numOfColor;
        this.numLocomotives = numLocomotives;
    }

    public TrainCard.Color getColor() {
        return color;
    }

    public void setColor(TrainCard.Color color) {
        this.color = color;
    }

    public int getNumOfColor() {
        return numOfColor;
    }

    public void setNumOfColor(int numOfColor) {
        this.numOfColor = numOfColor;
    }

    public int getNumLocomotives() {
        return numLocomotives;
    }

    public void setNumLocomotives(int numLocomotives) {
        this.numLocomotives = numLocomotives;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteColorOption that = (RouteColorOption) o;
        return numOfColor == that.numOfColor &&
                numLocomotives == that.numLocomotives &&
                color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, numOfColor, numLocomotives);
    }
}
