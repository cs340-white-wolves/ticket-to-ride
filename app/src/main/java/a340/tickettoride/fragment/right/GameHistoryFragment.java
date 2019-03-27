package a340.tickettoride.fragment.right;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.GameHistoryPresenter;
import a340.tickettoride.presenter.interfaces.IGameHistoryPresenter;
import cs340.TicketToRide.model.game.Message;

public class GameHistoryFragment extends Fragment implements GameHistoryPresenter.View {

    private IGameHistoryPresenter presenter;

    private GameHistoryRecyclerViewAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView recyclerView;

    public GameHistoryFragment() {
        presenter = new GameHistoryPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.startObserving();
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.stopObserving();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View inflate = inflater.inflate(R.layout.fragment_game_history, container, false);
        recyclerView = inflate.findViewById(R.id.game_history_list);
        Context context = getActivity();
        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setStackFromEnd(true);
        adapter = new GameHistoryRecyclerViewAdapter();
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(adapter);
        setMessages(presenter.getHistoryMessages());

        return inflate;
    }



    private void setMessages(List<Message> historyMessages) {
        adapter.setMessages(historyMessages);
        int itemCount = historyMessages.size();
        recyclerView.smoothScrollToPosition(itemCount == 0 ? itemCount : itemCount - 1);
    }

    @Override
    public void setMessagesFromPoller(final List<Message> historyMessages) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMessages(historyMessages);
            }
        });
    }
}
