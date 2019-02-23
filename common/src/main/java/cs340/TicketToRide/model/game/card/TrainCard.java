package cs340.TicketToRide.model.game.card;

public class TrainCard {
    public enum Color {
        passengerWhite, tankerBlue, reeferYellow, freightOrange,
        cabooseGreen, boxPurple, hopperBlack, coalRed
    }

    private Color color;
    // todo: this may not need to be abstract. We could just have it have a color, and if not, it's a locomotive
}
