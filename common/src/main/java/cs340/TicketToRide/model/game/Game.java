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
    private List<Player> players;
    private ID gameID;
    private Username creator;
    private Board board;
    private List<TrainCard> discardedTrainCards;
    private List<TrainCard> faceUpTrainCards;
    private Deck<TrainCard> trainCardDeck;
    private Deck<DestinationCard> destinationCardDeck;

    public Game(int targetNumPlayers, Username creator) {
        setTargetNumPlayers(targetNumPlayers);
        players = new ArrayList<>();
        gameID = ID.generateID();
        board = new Board();
        discardedTrainCards = new ArrayList<>();
        faceUpTrainCards = new ArrayList<>();
        trainCardDeck = TrainCard.createDeck();
        destinationCardDeck = DestinationCard.createDeck(this);
        setCreator(creator);
    }

    public City getCityByName(String name) {
        for (City city : board.getCities()) {
            if (city.getName().equals(name)) {
                return city;
            }
        }
        return null;
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

    public List<Player> getPlayers() {
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

    public Set<DestinationCard> getPlayerCompletedDestCards(ID playerId) {
        Player player = getPlayerById(playerId);
        Set<DestinationCard> completedCards = new HashSet<>();
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

        // todo: check if city already visited
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

    public String getPlayerString() {
        return getNumCurrentPlayers() + "/" + getTargetNumPlayers() + " Players";
    }

    public void setup() {
        List<Player> players = getPlayers();

        // Possible colors
        Player.Color[] values = Player.Color.values();

        // Assign color, make sure player attributes are initialized
        for (int i = 0; i < players.size(); i++) {

            Player player = players.get(i);

            for (int j = 0; j < 4; j++) {
                TrainCard trainCard = trainCardDeck.drawFromTop();
                player.getTrainCards().add(trainCard);
            }

            for (int j = 0; j < 3; j++) {
                DestinationCard destinationCard = destinationCardDeck.drawFromTop();
                player.getDestinationCards().add(destinationCard);
            }

            player.setColor(values[i]);
            player.setScore(0);
            player.setNumTrains(45);
        }
    }

    public Board getBoard() {
        return board;
    }

    public void addCardsToDestCardDeck(List<DestinationCard> cards) {
        destinationCardDeck.addAll(cards);
    }

    public Deck<DestinationCard> getDestCardDeck() {
        return destinationCardDeck;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setGameID(ID gameID) {
        this.gameID = gameID;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<TrainCard> getDiscardedTrainCards() {
        return discardedTrainCards;
    }

    public void setDiscardedTrainCards(List<TrainCard> discardedTrainCards) {
        this.discardedTrainCards = discardedTrainCards;
    }

    public List<TrainCard> getFaceUpTrainCards() {
        return faceUpTrainCards;
    }

    public void setFaceUpTrainCards(List<TrainCard> faceUpTrainCards) {
        this.faceUpTrainCards = faceUpTrainCards;
    }

    public Deck<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(Deck<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public Deck<DestinationCard> getDestinationCardDeck() {
        return destinationCardDeck;
    }

    public void setDestinationCardDeck(Deck<DestinationCard> destinationCardDeck) {
        this.destinationCardDeck = destinationCardDeck;
    }
}
