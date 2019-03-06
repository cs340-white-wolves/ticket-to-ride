package cs340.TicketToRide.model.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cs340.TicketToRide.model.game.board.Board;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.Deck;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public class Game {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;

    private int targetNumPlayers;
    private Set<Player> players;
    private ID gameID;
    private Username creator;
    private Board board;
    private List<TrainCard> discardedTrainCards;
    private List<TrainCard> faceUpTrainCards;
    private Deck<TrainCard> trainCardDeck;
    private Deck<DestinationCard> destinationCardDeck;

    public Game(int targetNumPlayers, Username creator) {
        setTargetNumPlayers(targetNumPlayers);
        players = new HashSet<>();
        gameID = ID.generateID();
        setCreator(creator);
    }

    public boolean addPlayer(Player player) {
        if (player == null || !player.isValid()) {
            throw new IllegalArgumentException();
        }

        if (players.contains(player)) {
            throw new RuntimeException("This player is already in the game");
        }

        if (hasTargetNumPlayers()) {
            throw new RuntimeException("This game already reached its max number of players");
        }

        return players.add(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public boolean isValid() {
        if (targetNumPlayers < MIN_PLAYERS || targetNumPlayers > MAX_PLAYERS) {
            return false;
        }

        if (players == null || gameID == null || !gameID.isValid()) {
            return false;
        }

        return true;
    }

    public List<DestinationCard> getPlayersCompletedDestCards(ID playerId) {
        Player player = getPlayerById(playerId);
        List<DestinationCard> completedCards = new ArrayList<>();
        for (DestinationCard card : player.getDestinationCards()) {
            if (playerCompletedDestCard(playerId, card)) {
                completedCards.add(card);
            }
        }
        return completedCards;
    }

    private boolean playerCompletedDestCard(ID playerId, DestinationCard card) {
        City start = card.getCity1();
        City end = card.getCity2();
        return pathOwnedByPlayer(playerId, start, end);
    }

    private boolean pathOwnedByPlayer(ID playerId, City start, City target) {
        Set<Route> connectedRoutes = getRoutesFromCity(start);

        for (Route connectedRoute : connectedRoutes) {
            if (connectedRoute.occupiedBy(playerId)) {
                City intermediate = connectedRoute.getOtherCity(start);
                if (target.equals(intermediate)) {
                    return true;
                }

                return pathOwnedByPlayer(playerId, intermediate, target);
            }
        }

        return false;
    }

    private Set<Route> getRoutesFromCity(City city) {
        Set<Route> connectedRoutes = new HashSet<>();
        for (Route route : board.getRoutes()) {
            if (route.contains(city)) {
                connectedRoutes.add(route);
            }
        }
        return connectedRoutes;
    }

    public boolean hasTargetNumPlayers() {
        return players.size() == targetNumPlayers;
    }

    public ID getGameID() {
        return gameID;
    }

    public int getTargetNumPlayers() {
        return targetNumPlayers;
    }

    public int getNumCurrentPlayers() {
        return players.size();
    }

    public void setTargetNumPlayers(int targetNumPlayers) {
        if (targetNumPlayers < MIN_PLAYERS || targetNumPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException();
        }

        this.targetNumPlayers = targetNumPlayers;
    }

    public Username getCreator() {
        return creator;
    }

    public void setCreator(Username creator) {
        if (creator == null || !creator.isValid()) {
            throw new IllegalArgumentException();
        }
        this.creator = creator;
    }

    public Player getPlayerById(ID playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return targetNumPlayers == game.targetNumPlayers &&
                Objects.equals(gameID, game.gameID) &&
                Objects.equals(creator, game.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetNumPlayers, gameID, creator);
    }

    @Override
    public String toString() {
        return "Game{" +
                "targetNumPlayers=" + targetNumPlayers +
                ", players=" + players +
                ", gameID=" + gameID +
                ", creator=" + creator +
                '}';
    }

    public String getPlayerString(){
        return getNumCurrentPlayers() + "/" +getTargetNumPlayers() + " Players";
    }
}
