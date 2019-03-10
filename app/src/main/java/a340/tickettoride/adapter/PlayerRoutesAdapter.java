package a340.tickettoride.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class PlayerRoutesAdapter extends RecyclerView.Adapter<PlayerRoutesAdapter.PlayerRouteView> {
    private DestinationCards cards;
    private Set<DestinationCard> completedCards;
    private Context context;

    public PlayerRoutesAdapter(Context context) {
        setContext(context);
        completedCards = new HashSet<>();
    }

    public void setCards(DestinationCards cards) {
        this.cards = cards;
    }

    public void setCompletedCards(Set<DestinationCard> completedCards) {
        this.completedCards = completedCards;
    }

    public void setContext(Context context) {
        this.context = context;
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
        DestinationCard card = cards.get(index);
        playerRouteView.textView.setText(card.toString());
        if (completedCards.contains(card)) {
            playerRouteView.textView.setTextColor(Color.GREEN);
        } else {
            playerRouteView.textView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class PlayerRouteView extends RecyclerView.ViewHolder {
        private TextView textView;

        PlayerRouteView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.player_route_text_view);
        }
    }
}