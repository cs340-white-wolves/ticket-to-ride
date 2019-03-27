package a340.tickettoride.fragment.right;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a340.tickettoride.R;
import a340.tickettoride.presenter.PlayerInfoPresenter;
import a340.tickettoride.presenter.interfaces.IPlayerInfoPresenter;
import cs340.TicketToRide.model.game.Players;


public class PlayerInfoFragment extends Fragment implements PlayerInfoPresenter.View {

    private PlayerRecyclerViewAdapter mPlayerRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private IPlayerInfoPresenter presenter;
    private RecyclerView mRecyclerView;


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopObserving();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_players, container, false);

        mRecyclerView = view.findViewById(R.id.player_info_recycler_view);
        presenter = new PlayerInfoPresenter(this);
        presenter.startObserving();


        // Set the adapter
        if (mRecyclerView != null) {
            Context context = view.getContext();

            mLinearLayoutManager = new LinearLayoutManager(context);

            mPlayerRecyclerAdapter = new PlayerRecyclerViewAdapter();
            mPlayerRecyclerAdapter.setPlayers(presenter.getPlayers());

            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mPlayerRecyclerAdapter);
        }

        return view;
    }

    @Override
    public void updateAllPlayers(final Players players) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPlayerRecyclerAdapter.setPlayers(players);
            }
        });
    }
}
