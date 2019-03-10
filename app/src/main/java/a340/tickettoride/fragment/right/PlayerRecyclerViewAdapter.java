package a340.tickettoride.fragment.right;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;


public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder> {

    private final Players players;

    public PlayerRecyclerViewAdapter() {
        players = new Players();
    }

    public void setPlayers(Players players) {
        this.players.clear();
        this.players.addAll(players);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Player player = players.get(position);

        holder.player = player;

        if (player != null) {
            holder.mPlayerName.setText(player.getUser().getUsername().toString());
            holder.mPoints.setText(String.valueOf(player.getScore()));
            holder.mNumTrains.setText(String.valueOf(player.getNumTrains()));
            holder.mNumCards.setText(String.valueOf(player.getTrainCards().size()));
            holder.mNumRoutes.setText(String.valueOf(player.getDestinationCards().size()));
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlayerName;
        public final TextView mPoints;
        public final TextView mNumTrains;
        public final TextView mNumCards;
        public final TextView mNumRoutes;
        public Player player;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlayerName = view.findViewById(R.id.player_info_player_name);
            mPoints = view.findViewById(R.id.player_info_player_points);
            mNumTrains = view.findViewById(R.id.player_info_player_num_trains);
            mNumCards = view.findViewById(R.id.player_info_player_num_cards);
            mNumRoutes = view.findViewById(R.id.player_info_player_num_routes);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNumTrains.getText() + "'";
        }
    }
}
