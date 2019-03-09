package cs340.TicketToRide.model.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck<Card> extends ArrayList<Card> {
    public Deck() {}

    public Deck(List<Card> cards) {
        this.addAll(cards);
    }

    public void shuffle() {
        for (int i = 0; i < this.size(); i++) {
            int rand = Math.abs(new Random().nextInt()) % this.size();
            Card temp = this.get(i);
            this.set(i, this.get(rand));
            this.set(rand, temp);
        }
    }

    public Card drawFromTop() {
        if (this.isEmpty()) {
            return null;
        }

        Card card = this.get(0);

        this.remove(0);

        return card;
    }
}
