package a340.tickettoride.fragment.right;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.adapter.PlayerRoutesAdapter;
import a340.tickettoride.presenter.RoutesPresenter;
import a340.tickettoride.presenter.interfaces.IRoutesPresenter;
import cs340.TicketToRide.model.game.card.DestinationCard;

public class RoutesFragment extends Fragment implements RoutesPresenter.View {
    private View view;
    private IRoutesPresenter presenter;

    public RoutesFragment() {
        presenter = new RoutesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_routes, container, false);
        return view;
    }

    @Override
    public void updatePlayerDestCardDisplay(Set<DestinationCard> completedCards, List<DestinationCard> cards) {
        PlayerRoutesAdapter adapter = new PlayerRoutesAdapter(completedCards,
                cards.toArray(new DestinationCard[0]), getActivity());

        RecyclerView routesRecycler = view.findViewById(R.id.player_routes_recycler);
        routesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        routesRecycler.setAdapter(adapter);
    }


}
