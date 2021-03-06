package cs340.TicketToRide.model.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cs340.TicketToRide.model.game.board.Board;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Username;

public class Game {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 5;
    public static final int MAX_FACE_UP = 5;
    public static final int MAX_FACEUP_LOCOMOTIVES = 2;
    public static final int MAX_ITERATIONS = 5;
    public static final int MIN_NUM_PLAYERS_FOR_DOUBLE_ROUTES = 4;

    private int targetNumPlayers;
    private Players players;
    private ID gameID;
    private Username creator;
    private Board board;
    private TrainCards discardedTrainCards;
    private TrainCards faceUpTrainCards;
    private TrainCards trainCardDeck;
    private DestinationCards destinationCardDeck;
    private int currentPlayerTurnIdx = 0;
    private ID lastRoundLastPlayerId = null;
    private int playersLeftToDiscard;

    public Game(int targetNumPlayers, Username creator) {
        setTargetNumPlayers(targetNumPlayers);
        players = new Players();
        playersLeftToDiscard = targetNumPlayers;
        gameID = ID.generateID();
        board = new Board();
        discardedTrainCards = new TrainCards();
        faceUpTrainCards = new TrainCards();
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

    public Players getPlayers() {
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
            if (card.isCompleted()) {
                completedCards.add(card);
            }
//            if (playerCompletedDestCard(playerId, card)) {
//                completedCards.add(card);
//            }
        }
        return completedCards;
    }

    public boolean playerCompletedDestCard(ID playerId, DestinationCard card) {
        City start = card.getCity1();
        City end = card.getCity2();
        List<City> visitedCities = new ArrayList<>();
        return pathOwnedByPlayer(playerId, start, end, visitedCities);
    }

    private boolean pathOwnedByPlayer(ID playerId, City start, City target, List<City> visitedCities) {
        Set<Route> connectedRoutes = getRoutesFromCity(start);
        visitedCities.add(start);

        for (Route connectedRoute : connectedRoutes) {
            if (connectedRoute.occupiedBy(playerId)) {
                City intermediate = connectedRoute.getOtherCity(start);
                if (target.equals(intermediate)) {
                    return true;
                }

                if (!visitedCities.contains(intermediate) &&
                        pathOwnedByPlayer(playerId, intermediate, target, visitedCities)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isPlayerTurn(ID playerId) {
        Player player = getPlayerById(playerId);
        return currentPlayerTurnIdx == players.indexOf(player);
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

    public boolean canUseDoubleRoutes () {
        return (getNumCurrentPlayers() >= MIN_NUM_PLAYERS_FOR_DOUBLE_ROUTES);
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
        Players players = getPlayers();

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
            player.setRoutePoints(0);
            player.setNumTrains(45);
        }

        // Set the 5 face up train cards
        setupFaceUpCards();

    }

    public void setupFaceUpCards() {
        int iteration = 0;

        do {
            if (hasTooManyFaceupLocomotives()) {
                discardedTrainCards.addAll(faceUpTrainCards);
                faceUpTrainCards.clear();
            }

            if (trainCardDeck.size() < MAX_FACE_UP) {
                addDiscardedToDrawDeck();
            }

            for (int i = 0; i < MAX_FACE_UP; i++) {
                TrainCard trainCard = trainCardDeck.drawFromTop();

                if (trainCard != null) {
                    faceUpTrainCards.add(trainCard);
                }
            }

            iteration++;

        } while (hasTooManyFaceupLocomotives() && iteration <= MAX_ITERATIONS);

    }

    public void addDiscardedToDrawDeck() {
        discardedTrainCards.shuffle();
        trainCardDeck.addAll(discardedTrainCards);
        discardedTrainCards.clear();
    }

    public Board getBoard() {
        return board;
    }

    public void addCardsToDestCardDeck(DestinationCards cards) {
        destinationCardDeck.addAll(cards);
    }

    public DestinationCards getDestCardDeck() {
        return destinationCardDeck;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public void setGameID(ID gameID) {
        this.gameID = gameID;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void addDiscardedTrainCards(TrainCards cards) {
        this.discardedTrainCards.addAll(cards);
    }

    public TrainCards getDiscardedTrainCards() {
        return discardedTrainCards;
    }

    public void setDiscardedTrainCards(TrainCards discardedTrainCards) {
        this.discardedTrainCards = discardedTrainCards;
    }

    public TrainCards getFaceUpTrainCards() {
        return faceUpTrainCards;
    }

    public void setFaceUpTrainCards(TrainCards faceUpTrainCards) {
        this.faceUpTrainCards = faceUpTrainCards;
    }

    public void setTrainCardDeck(TrainCards newTrainCardDeck) {
        this.trainCardDeck = newTrainCardDeck;
    }

    public TrainCards getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setDestinationCardDeck(DestinationCards destinationCardDeck) {
        this.destinationCardDeck = destinationCardDeck;
    }

    public int getCurrentPlayerTurnIdx() {
        return currentPlayerTurnIdx;
    }

    public void setCurrentPlayerTurnIdx(int currentPlayerTurnIdx) {
        this.currentPlayerTurnIdx = currentPlayerTurnIdx;
    }

    public void setNextPlayerTurn() {
        this.currentPlayerTurnIdx++;
        if (currentPlayerTurnIdx == players.size()) {
            currentPlayerTurnIdx = 0;
        }
    }

    public boolean hasTooManyFaceupLocomotives() {
        int numFaceUpLocomotives = 0;
        for (TrainCard card : faceUpTrainCards) {
            if (card.getColor() == TrainCard.Color.locomotive) {
                numFaceUpLocomotives++;
            }
        }
        return numFaceUpLocomotives > MAX_FACEUP_LOCOMOTIVES;
    }

    public Route getPartnerRoute(Route route) {
        for (Route possibleRoute : board.getRoutes()) {
            if (possibleRoute.isDoubleRoute() && route.isPartnerRoute(possibleRoute)) {
                return possibleRoute;
            }
        }
        return null;
    }

    public ID getLastRoundLastPlayerId() {
        return lastRoundLastPlayerId;
    }

    public void setLastRoundLastPlayerId(ID lastRoundLastPlayerId) {
        this.lastRoundLastPlayerId = lastRoundLastPlayerId;
    }
  
    public void decrementPlayersLeftToDiscard() {
        this.playersLeftToDiscard--;
    }

    public boolean allPlayersReady() {
        return playersLeftToDiscard == 0;
    }

    public void updateRoute(Route route) {
        Set<Route> routes = board.getRoutes();
        routes.remove(route);
        routes.add(route);
    }
}