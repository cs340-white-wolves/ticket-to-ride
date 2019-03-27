package cs340.TicketToRide.model.game.card;

import java.util.HashMap;
import java.util.Map;

/**
 * This class helps us use reflection with generic types.
 */
public class TrainCards extends Deck<TrainCard> {
    public TrainCards() {
        super();
    }

    public boolean hasColorCount(TrainCard.Color color, int neededCount, boolean useLocomotives) {
        if (color == TrainCard.Color.locomotive) {
            throw new RuntimeException("This function is not designed to be used for locomotives");
        }

        Map<TrainCard.Color, Integer> colorCounts = getColorCounts();

        int hasCount = 0;

        if (useLocomotives) {
            hasCount += colorCounts.get(TrainCard.Color.locomotive);
        }

        if (color == null) {
            for (Map.Entry<TrainCard.Color, Integer> entry : colorCounts.entrySet()) {
                if ((hasCount + entry.getValue()) >= neededCount) {
                    return true;
                }
            }
        } else {
            hasCount += colorCounts.get(color);
        }


        return (hasCount >= neededCount);
    }

    public TrainCards removeColorCount(TrainCard.Color color, int neededCount, boolean useLocomotives) {
        TrainCards removed = new TrainCards();

        // todo: if color == null, player chooses what cards to discard

        // First use non-locomotive cards
        for (TrainCard card : this) {
            if (neededCount == 0) {
                break;
            }

            if (card.getColor() == color) {
                removed.add(card);
                neededCount--;
            }
        }

        // If required and allowed, use locomotive cards
        if (neededCount > 0 && useLocomotives) {
            for (TrainCard card : this) {
                if (neededCount == 0) {
                    break;
                }

                if (card.getColor() == TrainCard.Color.locomotive) {
                    removed.add(card);
                    neededCount--;
                }
            }
        }

        if (neededCount > 0) {
            throw new RuntimeException("Could not remove cards to satisfy requirements.");
        }

        // Actually remove them
        this.removeAll(removed);

        return removed;
    }

    public Map<TrainCard.Color, Integer> getColorCounts () {
        final Map<TrainCard.Color, Integer> colorCounts = new HashMap<>();

        // Initialize to zero
        for (TrainCard.Color c : TrainCard.Color.values()) {
            colorCounts.put(c, 0);
        }

        // Sum
        for (TrainCard card : this) {
            TrainCard.Color c = card.getColor();
            Integer cnt = colorCounts.get(c);
            colorCounts.put(c, cnt == null ? 0 : cnt + 1);
        }

        return colorCounts;
    }
}
