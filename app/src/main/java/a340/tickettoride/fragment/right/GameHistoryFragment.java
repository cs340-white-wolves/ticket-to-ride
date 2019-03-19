package a340.tickettoride.fragment.right;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.ChatPresenter;
import a340.tickettoride.presenter.GameHistoryPresenter;
import a340.tickettoride.presenter.interfaces.IChatPresenter;
import a340.tickettoride.presenter.interfaces.IGameHistoryPresenter;
import cs340.TicketToRide.model.game.ChatMessage;

public class GameHistoryFragment extends Fragment implements GameHistoryPresenter.View {

    private IGameHistoryPresenter presenter;

//    private ChatRecyclerViewAdapter mChatRecyclerAdapter;
//    private LinearLayoutManager mLinearLayoutManager;
//    private RecyclerView mRecyclerView;

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

//        mChatMessageSendButton = inflate.findViewById(R.id.chat_message_send);
//        mChatMessageSendButton.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View view) {
//                presenter.onSendPress();
//            }
//        });
//
//        mChatInput = inflate.findViewById(R.id.chat_message_input);
//
//        mRecyclerView = inflate.findViewById(R.id.chat_list);
//
//        // Set the adapter
//        Context context = mRecyclerView.getContext();
//
//        mLinearLayoutManager = new LinearLayoutManager(context);
//        mLinearLayoutManager.setStackFromEnd(true);
//
//        mChatRecyclerAdapter = new ChatRecyclerViewAdapter();
//
//        mRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mRecyclerView.setAdapter(mChatRecyclerAdapter);
//
//        setMessages(presenter.getChatMessages());

        return inflate;
    }
}
