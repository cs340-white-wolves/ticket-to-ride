package cs340.TicketToRide.exception;

public class GameFullException extends RuntimeException {
    public GameFullException(String msg) {
        super(msg);
    }
}
