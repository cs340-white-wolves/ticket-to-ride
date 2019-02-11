package a340.tickettoride.activity;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import cs340.TicketToRide.model.Game;

class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameView> {

    private List<Game> mGames;

    public GameAdapter(List<Game> games) {
        mGames = games;
    }

    @NonNull
    @Override
    public GameView onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_game, viewGroup, false);
        return new GameView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameView gameView, int index) {
        Game game = mGames.get(index);

        gameView.gameName.setText(game.getCreator().toString());
        gameView.numberOfPlayers.setText(game.getPlayerString());
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public void updateList(List<Game> newList){
        DiffUtil.DiffResult differences = DiffUtil.calculateDiff(new GameDiff(mGames, newList));
        mGames.clear();
        mGames.addAll(newList);
        differences.dispatchUpdatesTo(this);
    }

    //This class is the layout/view for each individual list item
    public static class GameView extends RecyclerView.ViewHolder {

        TextView gameName;
        TextView numberOfPlayers;

        public GameView(@NonNull View itemView) {
            super(itemView);
            gameName =  itemView.findViewById(R.id.gameName);
            numberOfPlayers =  itemView.findViewById(R.id.playerCount);

        }
    }

}
