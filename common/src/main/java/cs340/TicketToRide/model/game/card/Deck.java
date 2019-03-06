package cs340.TicketToRide.model.game.card;

import java.util.List;
import java.util.Random;

public class Deck<Card> {
    private List<Card> cards;
    public Deck(List<Card> cards) {
        this.cards = cards;
        shuffle();
    }

    public void shuffle() {
        for (int i = 0; i < cards.size(); i++) {
            int rand = Math.abs(new Random().nextInt()) % cards.size();
            Card temp = cards.get(i);
            cards.set(i, cards.get(rand));
            cards.set(rand, temp);
        }
    }

    public Card drawFromTop() {
        if (cards.isEmpty()) {
            return null;
        }

        Card card = cards.get(0);

        cards.remove(0);

        return card;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
