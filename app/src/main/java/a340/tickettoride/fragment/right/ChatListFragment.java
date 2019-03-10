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
import a340.tickettoride.presenter.ChatListPresenter;
import a340.tickettoride.presenter.interfaces.IChatListPresenter;
import cs340.TicketToRide.model.game.ChatMessage;

public class ChatListFragment extends Fragment implements ChatListPresenter.View {

    private ChatRecyclerViewAdapter mChatRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private IChatListPresenter presenter;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatListFragment() {
        presenter = new ChatListPresenter(this);
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
        android.view.View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setStackFromEnd(true);

            mChatRecyclerAdapter = new ChatRecyclerViewAdapter();

            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mChatRecyclerAdapter);
            setMessages(presenter.getChatMessages());
        }

        return view;
    }

    @Override
    public void setChatMsgsFromPoller(final List<ChatMessage> messages) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setMessages(messages);
            }
        });

    }

    private void setMessages(List<ChatMessage> messages) {
        mChatRecyclerAdapter.setMessages(messages);
        int itemCount = mChatRecyclerAdapter.getItemCount();
        mRecyclerView.smoothScrollToPosition(itemCount == 0 ? itemCount : itemCount - 1);
    }

}
