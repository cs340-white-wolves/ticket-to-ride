package cs340.TicketToRide.utility;

public class Graph {
    public double getSlope(double x1, double x2, double y1, double y2) {
        if (x2 - x1 == 0) {

        }

        return (y2 - y1) / (x2 - x1);
    }

    public double getPerpendicularSlope(double slope) {
        if (slope == 0) {

        }
        return (-1 / slope);
    }

    public double getYIntercept(double y1, double x1, double slope) {
        return y1 - slope * x1;
    }

    public double getDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double getMidPointY(double y1, double y2) {
        return (y1 + y2) / 2;
    }

    public double getMitPointX(double x1, double x2) {
        return (x1 + x2) / 2;
    }
}
