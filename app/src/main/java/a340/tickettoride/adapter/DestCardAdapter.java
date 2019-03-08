package a340.tickettoride.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class DestCardAdapter extends RecyclerView.Adapter<DestCardAdapter.DestCardView> {
    private List<DestinationCard> cards;
    private List<DestinationCard> selectedCards;
    private Map<View, DestinationCard> viewCards;
    private Context context;

    public DestCardAdapter(List<DestinationCard> cards, Context context) {
        this.cards = cards;
        viewCards = new HashMap<>();
        selectedCards = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public DestCardView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_dest_card, viewGroup, false);

        return new DestCardView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DestCardView destCardView, int index) {
        DestinationCard card = cards.get(index);
        viewCards.put(destCardView.text, card);
        destCardView.text.setText(card.toString());
        destCardView.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = (TextView)v;
                handleClick(t);
            }
        });
    }

    public List<DestinationCard> getSelectedDestCards() {
        return selectedCards;
    }

    private void handleClick(TextView view) {
        DestinationCard destinationCard = viewCards.get(view);
        if (selectedCards.contains(destinationCard)) {
            selectedCards.remove(destinationCard);
            view.setTextColor(context.getColor(R.color.White));
        } else {
            selectedCards.add(destinationCard);
            view.setTextColor(context.getColor(R.color.Blue));
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class DestCardView extends RecyclerView.ViewHolder {
        TextView text;
        public DestCardView(View view) {
            super(view);
            text = view.findViewById(R.id.dest_card_text_view);
        }
    }
}
