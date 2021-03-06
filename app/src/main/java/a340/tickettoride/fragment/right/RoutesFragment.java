package a340.tickettoride.fragment.right;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.adapter.PlayerRoutesAdapter;
import a340.tickettoride.presenter.RoutesPresenter;
import a340.tickettoride.presenter.interfaces.IRoutesPresenter;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.DestinationCards;

public class RoutesFragment extends Fragment implements RoutesPresenter.View {

    private View view;
    private IRoutesPresenter presenter;
    private RecyclerView routesRecycler;
    private PlayerRoutesAdapter adapter;

    public RoutesFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopObserving();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter = new RoutesPresenter(this);
        presenter.startObserving();

        view = inflater.inflate(R.layout.fragment_routes, container, false);
        adapter = new PlayerRoutesAdapter(getActivity());
        adapter.setCards(presenter.getPlayerDestCards());

        routesRecycler = view.findViewById(R.id.player_routes_recycler);
        routesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        routesRecycler.setAdapter(adapter);


        return view;
    }

    @Override
    public void updatePlayerDestCardDisplay(final Set<DestinationCard> completedCards,
                                            final DestinationCards cards) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setCards(cards);
                adapter.setCompletedCards(completedCards);
            }
        });

    }




}
