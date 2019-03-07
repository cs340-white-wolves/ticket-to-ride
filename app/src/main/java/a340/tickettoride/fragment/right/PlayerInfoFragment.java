package a340.tickettoride.fragment.right;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.PlayerInfoPresenter;
import cs340.TicketToRide.model.game.Player;


public class PlayerInfoFragment extends Fragment implements PlayerInfoPresenter.View {

    public PlayerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_players, container, false);
    }

    @Override
    public void updateAllPlayers(List<Player> players) {

    }
}
