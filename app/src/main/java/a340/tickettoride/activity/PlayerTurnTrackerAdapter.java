package a340.tickettoride.activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Player;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

class PlayerTurnTrackerAdapter extends RecyclerView.Adapter<PlayerTurnTrackerAdapter.PlayerTurnView> {

    private List<Player> players;
    private List<PlayerTurnView> playerTurnViews;
    private Context context;
    Map<Player.Color, Integer> playerColors;
    private int activePlayerIndex;
    public PlayerTurnTrackerAdapter(List<Player> players, Context context) {
        this.players = players;
        this.context = context;
        playerTurnViews = new ArrayList<>();
        initPlayerColors();
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
        int color = getPlayerColor(player);
        playerTurnView.usernameView.setText(player.getUser().getUsername().toString());
        playerTurnView.usernameView.setBackgroundColor(color);
        if (activePlayerIndex == index) {
            playerTurnView.setActive();
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    private int getPlayerColor(Player player) {
        Player.Color color = player.getColor();
        if (playerColors.containsKey(color)) {
            return playerColors.get(color);
        }
        return GRAY;
    }

    private void initPlayerColors() {
        playerColors = new HashMap<>();
        playerColors.put(Player.Color.black, context.getColor(R.color.Black));
        playerColors.put(Player.Color.blue, context.getColor(R.color.Blue));
        playerColors.put(Player.Color.red, context.getColor(R.color.Red));
        playerColors.put(Player.Color.green, context.getColor(R.color.Green));
        playerColors.put(Player.Color.yellow, context.getColor(R.color.Gold));
    }

    public void setActivePlayerIndex(int index) {
        this.activePlayerIndex = index;
    }

    public int getActivePlayerIndex() {
        return activePlayerIndex;
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
