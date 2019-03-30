package cs340.TicketToRide.utility;

import java.util.Locale;
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

    @Override
    public String toString() {
        String colorStr = String.format(Locale.US, "%d %s card%s", numOfColor, TrainCard.getColorName(color), numOfColor != 1 ? "s" : "");
        String locoStr = String.format(Locale.US, "%d %s card%s", numLocomotives, TrainCard.getColorName(TrainCard.Color.locomotive), numLocomotives != 1 ? "s" : "");

        if (numOfColor > 0 && numLocomotives > 0) {
            return colorStr + " and " + locoStr;
        }

        if (numOfColor > 0) {
            return colorStr;
        }

        if (numLocomotives > 0) {
            return locoStr;
        }

        return "";
    }
}
