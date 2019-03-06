package a340.tickettoride.activity.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import cs340.TicketToRide.model.game.Game;
import cs340.TicketToRide.utility.ID;

public class GameDiff extends DiffUtil.Callback {

    List<Game> oldList;
    List<Game> newList;

    public GameDiff(List<Game> oldList, List<Game> newList) {
        this.oldList = oldList;
        this.newList = newList;

    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldPosition, int newPosition) {
        ID oldGameId = oldList.get(oldPosition).getGameID();
        ID newGameId = newList.get(newPosition).getGameID();

        return oldGameId.equals(newGameId);
    }

    @Override
    public boolean areContentsTheSame(int oldPosition, int newPosition) {
        Game oldGame = oldList.get(oldPosition);
        Game newGame = newList.get(newPosition);

        return (oldGame.getNumCurrentPlayers() == newGame.getNumCurrentPlayers()) &&
                (oldGame.getCreator().equals(newGame.getCreator()));
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
