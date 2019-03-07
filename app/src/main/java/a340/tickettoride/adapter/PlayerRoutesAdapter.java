package a340.tickettoride.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Set;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class PlayerRoutesAdapter extends RecyclerView.Adapter<PlayerRoutesAdapter.PlayerRouteView> {
    private DestinationCard[] cards;
    private Set<DestinationCard> completedCards;
    private Context context;

    public PlayerRoutesAdapter(Set<DestinationCard> completedCards, DestinationCard[] cards, Context context) {
        this.cards = cards;
        this.context = context;
        this.completedCards = completedCards;
    }

    @NonNull
    @Override
    public PlayerRouteView onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_player_route, viewGroup, false);
        return new PlayerRouteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerRouteView playerRouteView, int index) {
        DestinationCard card = cards[index];
        playerRouteView.textView.setText(card.toString());
        if (completedCards.contains(card)) {
            playerRouteView.textView.setTextColor(Color.GREEN);
        } else {
            playerRouteView.textView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return cards.length;
    }

    static class PlayerRouteView extends RecyclerView.ViewHolder {
        private TextView textView;

        PlayerRouteView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.player_route_text_view);
        }
    }
}