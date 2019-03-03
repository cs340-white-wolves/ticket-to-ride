package a340.tickettoride.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class DestCardAdapter extends RecyclerView.Adapter<DestCardAdapter.DestCardView> {
    private List<DestinationCard> cards;

    public DestCardAdapter(List<DestinationCard> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public DestCardView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_dest_card, viewGroup, false);

        return new DestCardView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestCardView destCardView, int index) {
        DestinationCard card = cards.get(index);
        destCardView.text.setText(card.toString());
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
