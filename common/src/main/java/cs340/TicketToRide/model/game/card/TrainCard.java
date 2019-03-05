package cs340.TicketToRide.model.game.card;

public class TrainCard {
    public enum Color {
        passengerWhite, tankerBlue, reeferYellow, freightOrange,
        cabooseGreen, boxPurple, hopperBlack, coalRed, locomotive
    }

    private Color color;

    public TrainCard(Color color) {
        this.color = color;
    }

    public Color getColor() {

        return this.color;
    }

}
