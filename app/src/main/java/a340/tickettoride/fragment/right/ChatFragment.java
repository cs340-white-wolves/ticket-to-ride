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
import a340.tickettoride.presenter.interfaces.IChatPresenter;
import cs340.TicketToRide.model.game.ChatMessage;

public class ChatFragment extends Fragment implements ChatPresenter.View {

    private IChatPresenter presenter;

    private Button mChatMessageSendButton;
    private EditText mChatInput;

    private ChatRecyclerViewAdapter mChatRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;

    public ChatFragment() {
        presenter = new ChatPresenter(this);
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
        android.view.View inflate = inflater.inflate(R.layout.fragment_chat, container, false);

        mChatMessageSendButton = inflate.findViewById(R.id.chat_message_send);
        mChatMessageSendButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onSendPress();
            }
        });

        mChatInput = inflate.findViewById(R.id.chat_message_input);

        mRecyclerView = inflate.findViewById(R.id.chat_list);

        // Set the adapter
        Context context = mRecyclerView.getContext();

        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setStackFromEnd(true);

        mChatRecyclerAdapter = new ChatRecyclerViewAdapter();

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mChatRecyclerAdapter);

        setMessages(presenter.getChatMessages());

        return inflate;
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

    @Override
    public String getMessageInput() {
        return mChatInput.getText().toString();
    }

    @Override
    public void clearMessageInput() {
        mChatInput.setText("");
    }
}
