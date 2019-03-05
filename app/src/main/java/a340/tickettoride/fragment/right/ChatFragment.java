package a340.tickettoride.fragment.right;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import a340.tickettoride.R;
import a340.tickettoride.presenter.ChatPresenter;
import a340.tickettoride.presenter.interfaces.IChatPresenter;

public class ChatFragment extends Fragment implements ChatPresenter.ChatPresenterListener {
    private Button mChatMessageSendButton;
    private EditText mChatInput;

    private IChatPresenter presenter;

    public ChatFragment() {
        presenter = new ChatPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_chat, container, false);

        mChatMessageSendButton = inflate.findViewById(R.id.chat_message_send);
        mChatMessageSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSendPress();
            }
        });

        mChatInput = inflate.findViewById(R.id.chat_message_input);

        return inflate;
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
