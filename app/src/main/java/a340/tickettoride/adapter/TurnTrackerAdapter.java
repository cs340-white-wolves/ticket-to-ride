package a340.tickettoride.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.activity.MapActivity;
import cs340.TicketToRide.model.game.Player;

public class TurnTrackerAdapter extends RecyclerView.Adapter<TurnTrackerAdapter.PlayerTurnView> {

    private List<Player> players;
    private Context context;
    private int activePlayerIndex;
    public TurnTrackerAdapter(List<Player> players, Context context) {
        this.players = players;
        this.context = context;
        this.activePlayerIndex = 0;
    }

    @NonNull
    @Override
    public PlayerTurnView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_player_tracker, viewGroup, false);

        return new PlayerTurnView(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerTurnView playerTurnView, int index) {
        Player player = players.get(index);

        int color = ((MapActivity)context).getPlayerColorValue(player.getColor());
        playerTurnView.usernameView.setText(player.getUser().getUsername().toString());
        playerTurnView.usernameView.setBackgroundColor(color);
        if (activePlayerIndex == index) {
            playerTurnView.setActive();
        } else {
            playerTurnView.setInactive();
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setActivePlayerIndex(int index) {
        if (index == getItemCount()) {
            index = 0;
        }
        this.activePlayerIndex = index;
    }

    public void setNextActivePlayer() {
        activePlayerIndex++;
        if (activePlayerIndex == getItemCount()) {
            activePlayerIndex = 0;
        }
    }

    //This class is the layout/view for each individual list item
    public static class PlayerTurnView extends RecyclerView.ViewHolder {

        private ConstraintLayout background;
        private TextView usernameView;
        private int color;
        private Context context;

        public PlayerTurnView(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            background = itemView.findViewById(R.id.player_name_background);
            usernameView = itemView.findViewById(R.id.playerName);
        }

        public void setActive() {
            background.setBackgroundColor(context.getColor(R.color.ActivePlayer));
        }

        public void setInactive() {
            background.setBackgroundColor(context.getColor(R.color.InactivePlayer));
        }
    }

}
