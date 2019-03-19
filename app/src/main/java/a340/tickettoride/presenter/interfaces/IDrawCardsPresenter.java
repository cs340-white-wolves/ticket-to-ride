package a340.tickettoride.presenter.interfaces;

public interface IDrawCardsPresenter {
    void drawTrainCards();
    // todo: account for if there are not train cards left in deck and no discarded; then they can't select that option
}
