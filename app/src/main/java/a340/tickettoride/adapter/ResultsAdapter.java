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
        this.players = new Players();
        this.players.addAll(players);
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
        resultItem.rank.setText(String.format("#%s", String.valueOf(index + 1)));
        resultItem.player.setText(player.getUser().getUsername().toString());
        resultItem.routePoints.setText(String.valueOf(player.getRoutePoints()));
        resultItem.completePoints.setText(String.format("+%s", String.valueOf(player.getDestCardCompletePoints())));
        resultItem.incompletePoints.setText(String.format("-%s", String.valueOf(player.getDestCardIncompletePoints())));
        int awardPoints = player.getAwardPoints();
        resultItem.numDestinationPoints.setText(awardPoints > 0 ? String.format("+%d", awardPoints) : "");
        resultItem.totalPoints.setText(String.valueOf(player.getTotalPoints()));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ResultItem extends RecyclerView.ViewHolder {
        private TextView rank;
        private TextView player;
        private TextView routePoints;
        private TextView completePoints;
        private TextView incompletePoints;
        private TextView numDestinationPoints;
        private TextView totalPoints;


        public ResultItem(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            player = itemView.findViewById(R.id.playerUsername);
            routePoints = itemView.findViewById(R.id.routePoints);
            completePoints = itemView.findViewById(R.id.completePoints);
            incompletePoints = itemView.findViewById(R.id.incompletePoints);
            numDestinationPoints = itemView.findViewById(R.id.numDestinationsPoints);
            totalPoints = itemView.findViewById(R.id.totalPoints);

        }
    }
}
