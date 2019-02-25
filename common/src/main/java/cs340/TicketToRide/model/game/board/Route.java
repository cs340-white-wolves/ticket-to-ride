package cs340.TicketToRide.model.game.board;

import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.Graph;
import cs340.TicketToRide.utility.ID;

public class Route {
    public static final double SHIFT_OFFSET = 0.3;
    public static final int PRIMARY = 1;
    private City city1;
    private City city2;
    private TrainCard.Color color; // if color is empty, it's gray
    private int length;
    private boolean isDoubleRoute;
    private int priority;
    private ID occupierId;
    private Graph graph = new Graph();

    // todo: should a route have a player occupying it? or a player should have a set of routes they occupy?


    public Route(City city1, City city2, TrainCard.Color color, int length) {
        setCity1(city1);
        setCity2(city2);
        setColor(color);
        setLength(length);
        setDoubleRoute(false);
        setPriority(0);
    }

    public Route(City city1, City city2, TrainCard.Color color, int length, int priority) {
        setCity1(city1);
        setCity2(city2);
        setColor(color);
        setLength(length);
        setDoubleRoute(true);
        setPriority(priority);
    }



    public double getCity1OffsetLat() {
        double x1 = city1.getLat();
        double y1 = city1.getLng();
        double x2 = city2.getLat();
        double y2 = city2.getLng();

        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);

        double slope = graph.getSlope(x1, x2, y1, y2);
        System.out.println("slope: " + slope);
        double perpendicularSlope = graph.getPerpendicularSlope(slope);
        System.out.println("perp slope: " + perpendicularSlope);
        double perpendicularYIntercept = graph.getYIntercept(y1, x1, perpendicularSlope);
        System.out.println("perp y int: " + perpendicularYIntercept);

        double distance = graph.getDistance(x1, 0, y1, perpendicularYIntercept);
        System.out.println("distance: " + distance);
        double shift = (SHIFT_OFFSET / distance) * (0 - x1);
        System.out.println("shift: " + shift);

        System.out.println(getPriority());
        return (getPriority() == PRIMARY ? x1 + shift : x1 - shift);
    }

    public double getCity2OffsetLat() {
        double x2 = city1.getLat();
        double y2 = city1.getLng();
        double x1 = city2.getLat();
        double y1 = city2.getLng();

        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);

        double slope = graph.getSlope(x1, x2, y1, y2);
        System.out.println("slope: " + slope);
        double perpendicularSlope = graph.getPerpendicularSlope(slope);
        System.out.println("perp slope: " + perpendicularSlope);
        double perpendicularYIntercept = graph.getYIntercept(y1, x1, perpendicularSlope);
        System.out.println("perp y int: " + perpendicularYIntercept);

        double distance = graph.getDistance(x1, 0, y1, perpendicularYIntercept);
        System.out.println("distance: " + distance);
        double shift = (SHIFT_OFFSET / distance) * (0 - x1);
        System.out.println("shift: " + shift);

        System.out.println(getPriority());
        return (getPriority() == PRIMARY ? x1 + shift : x1 - shift);
    }

    public double getCity1OffsetLng() {
        double x1 = city1.getLat();
        double y1 = city1.getLng();
        double x2 = city2.getLat();
        double y2 = city2.getLng();

        double slope = graph.getSlope(x1, x2, y1, y2);
        double perpendicularSlope = graph.getPerpendicularSlope(slope);
        double perpendicularYIntercept = graph.getYIntercept(y1, x1, perpendicularSlope);

        double distance = graph.getDistance(x1, 0, y1, perpendicularYIntercept);
        double shift = (SHIFT_OFFSET / distance) * (perpendicularYIntercept - y1);

        return (getPriority() == PRIMARY ? y1 + shift : y1 - shift);
    }

    public double getCity2OffsetLng() {
        double x2 = city1.getLat();
        double y2 = city1.getLng();
        double x1 = city2.getLat();
        double y1 = city2.getLng();

        double slope = graph.getSlope(x1, x2, y1, y2);
        double perpendicularSlope = graph.getPerpendicularSlope(slope);
        double perpendicularYIntercept = graph.getYIntercept(y1, x1, perpendicularSlope);

        double distance = graph.getDistance(x1, 0, y1, perpendicularYIntercept);
        double shift = (SHIFT_OFFSET / distance) * (perpendicularYIntercept - y1);

        return (getPriority() == PRIMARY ? y1 + shift : y1 - shift);
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

    public void setDoubleRoutePriority(int priority) {
        setDoubleRoute(true);
        setPriority(priority);
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

    public boolean isDoubleRoute() {
        return isDoubleRoute;
    }

    public void setDoubleRoute(boolean isDoubleRoute) {
        this.isDoubleRoute = isDoubleRoute;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getCity1Lat() {
        return city1.getLat();
    }

    public double getCity1Lng() {
        return city1.getLng();
    }

    public double getCity2Lat() {
        return city2.getLat();
    }

    public double getCity2Lng() {
        return city2.getLng();
    }
}
