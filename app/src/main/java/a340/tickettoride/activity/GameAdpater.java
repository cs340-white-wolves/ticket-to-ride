package a340.tickettoride.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a340.tickettoride.R;
import cs340.TicketToRide.model.Game;

class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameView> {

    private Game[] mGames = null;

    public GameAdapter(Game[] games) {
        mGames=games;
    }

    @NonNull
    @Override
    public GameView onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_game, viewGroup, false);

        return new GameView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameView gameView, int index) {

    }

    @Override
    public int getItemCount() {
        return mGames.length;
    }



    //This class is the layout/view for each individual list item
    public static class GameView extends RecyclerView.ViewHolder {

        public GameView(@NonNull View itemView) {
            super(itemView);
            TextView gameName =  itemView.findViewById(R.id.gameName);
            TextView numberOfPlayers = itemView.findViewById(R.id.playerCount);

        }
    }

}
