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
        resultItem.rank.setText(String.valueOf(index +1));
        resultItem.player.setText(player.getUser().getUsername().toString());
        resultItem.totalPoints.setText(String.valueOf(player.getScore()));
        resultItem.routePoints.setText(String.valueOf(player.getDestCardPoints()));
        resultItem.trainPoints.setText(String.valueOf(player.getTrainPoints()));
        resultItem.awardPoints.setText(player.hasAward()? "10" : "");

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ResultItem extends RecyclerView.ViewHolder {
        private TextView rank;
        private TextView player;
        private TextView totalPoints;
        private TextView routePoints;
        private TextView trainPoints;
        private TextView awardPoints;


        public ResultItem(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            player = itemView.findViewById(R.id.playerUsername);
            totalPoints = itemView.findViewById(R.id.totalPoints);
            routePoints = itemView.findViewById(R.id.routePoints);
            trainPoints = itemView.findViewById(R.id.trainPoints);
            awardPoints = itemView.findViewById(R.id.awardPoints);

        }
    }
}
