package a340.tickettoride.observerable;

public enum ModelChangeType {
    AvailableGameList,
    JoinGame,
    UpdatePlayers, //Used to update the Routes & All Player info frags
    UpdatePlayerHand, //Used to update the Player hand page (train cards)
    TrainCardsDrawn,
    RouteClaimed,
    ChatMessageReceived,
    AdvanceTurn,
    FaceUpTrainCardsUpdate,
    DrawableTrainCardCount,
    DiscardedTrainCardCount,
    DrawableDestinationCardCount,
    FailureException,
    GameStarted,
    StartMap,
    DestCardsAdded,
    AuthenticateSuccess,
    GameHistoryReceived,
    DrawTrainCards,
    SelectedSingleCard, EndGame
}
