package a340.tickettoride.activity;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.model.game.Player;

class PlayerTurnTrackerAdapter extends RecyclerView.Adapter<PlayerTurnTrackerAdapter.PlayerTurnView> {

    private List<Player> players;
    public PlayerTurnTrackerAdapter(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerTurnView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_player_tracker, viewGroup, false);

        return new PlayerTurnView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerTurnView playerTurnView, int index) {
        Player player = players.get(index);
        playerTurnView.text.setText(player.getUser().getUsername().toString());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void updateList(List<Game> newList){
    }


    //This class is the layout/view for each individual list item
    public static class PlayerTurnView extends RecyclerView.ViewHolder {

        private TextView text;

        public PlayerTurnView(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.playerName);
        }

    }

}
