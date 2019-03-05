package a340.tickettoride.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.activity.GameDiff;
import a340.tickettoride.presenter.IJoinGamePresenter;
import cs340.TicketToRide.model.game.Game;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameView> {

    private List<Game> mGames;
    private IJoinGamePresenter presenter;

    public GameAdapter(List<Game> games, IJoinGamePresenter presenter) {
        mGames = games;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public GameView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_game, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.joinGame(mGames.get(index).getGameID());
            }
        });
        return new GameView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameView gameView, int index) {
        Game game = mGames.get(index);
        String formattedName = game.getCreator().toString() + "'s Game";
        gameView.gameName.setText(formattedName);
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

        private TextView gameName;
        private TextView numberOfPlayers;

        public GameView(@NonNull View itemView) {
            super(itemView);
            gameName =  itemView.findViewById(R.id.gameName);
            numberOfPlayers =  itemView.findViewById(R.id.playerCount);

        }

    }

}
