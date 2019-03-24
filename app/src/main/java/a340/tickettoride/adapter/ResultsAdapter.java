package a340.tickettoride.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.Players;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultItem> {

    private Players players;


    public ResultsAdapter(Players players) {
        this.players = players;
        Collections.sort(this.players);
    }

    @NonNull
    @Override
    public ResultsAdapter.ResultItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_result_list_item, viewGroup, false);

        return new ResultItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsAdapter.ResultItem resultItem, int index) {
        Player player = players.get(index);
        resultItem.rank.setText(Integer.toString(index +1));
        resultItem.player.setText(player.getUser().getUsername().toString());
        resultItem.points.setText(Integer.toString(player.getScore()));
        resultItem.cards.setText(Integer.toString(player.getTrainCards().size()));
        resultItem.trains.setText(Integer.toString(player.getNumTrains()));
        resultItem.routes.setText(Integer.toString(player.getDestinationCards().size()));

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ResultItem extends RecyclerView.ViewHolder {
        private TextView rank;
        private TextView player;
        private TextView points;
        private TextView cards;
        private TextView trains;
        private TextView routes;


        public ResultItem(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            player = itemView.findViewById(R.id.playerUsername);
            points = itemView.findViewById(R.id.points);
            cards = itemView.findViewById(R.id.cards);
            trains = itemView.findViewById(R.id.trains);
            routes = itemView.findViewById(R.id.routes);

        }
    }
}
