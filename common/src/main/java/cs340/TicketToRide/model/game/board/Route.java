package cs340.TicketToRide.model.game.board;

import cs340.TicketToRide.model.game.card.StandardCard;

public class Route {
    private City city1;
    private City city2;
    private StandardCard.Color color; // todo: if color is empty, it's gray
    private int length;
    // todo: should a route have a player occupying it? or a player should have a set of routes they occupy?

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
}
