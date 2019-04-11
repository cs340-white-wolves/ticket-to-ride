package a340.tickettoride.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a340.tickettoride.R;
import a340.tickettoride.activity.CreateGameActivity;
import a340.tickettoride.activity.LobbyActivity;
import a340.tickettoride.adapter.ResultsAdapter;
import a340.tickettoride.model.ClientModel;
import a340.tickettoride.presenter.ResultsPresenter;
import a340.tickettoride.presenter.interfaces.IResultsPresenter;
import cs340.TicketToRide.model.game.Players;


public class ResultsFragment extends Fragment {

    private IResultsPresenter presenter;
    private Button endGameBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_end_game_results, container, false);
        presenter = new ResultsPresenter();

        endGameBtn = newView.findViewById(R.id.endGameButton);
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.terminateGame();
                Intent intent = new Intent(getActivity(), LobbyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish(); // call this to finish the current activity
            }
        });

        ResultsAdapter listAdapter = new ResultsAdapter(ClientModel.getInstance().getPlayers());
        RecyclerView recycler = newView.findViewById(R.id.resultsRecyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(listAdapter);

        return newView;
    }





}
