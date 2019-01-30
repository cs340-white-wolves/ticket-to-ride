package cs340.TicketToRide;

public interface IServer {
    AuthToken login(Username username, Password password);
    AuthToken register(Username username, Password password);
    Game createGame(AuthToken token);
    Game joinGame(AuthToken token, ID gameId);
}
