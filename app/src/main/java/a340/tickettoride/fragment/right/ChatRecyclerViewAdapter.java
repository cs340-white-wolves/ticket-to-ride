package a340.tickettoride.fragment.right;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a340.tickettoride.R;
import a340.tickettoride.fragment.right.ChatListFragment.OnListFragmentInteractionListener;
import cs340.TicketToRide.model.game.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ChatMessage} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private final List<ChatMessage> mMessages;

    public ChatRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        mMessages = new ArrayList<>();
        mListener = listener;
    }

    public void addMessage(ChatMessage message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    public void setMessages(List<ChatMessage> messages) {
        mMessages.clear();
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chat_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mChatMessage = mMessages.get(position);
        holder.mPlayerName.setText(mMessages.get(position).getUsername());
        holder.mMessage.setText(mMessages.get(position).getMessage());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mChatMessage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlayerName;
        public final TextView mMessage;
        public ChatMessage mChatMessage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPlayerName = (TextView) view.findViewById(R.id.player_name);
            mMessage = (TextView) view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessage.getText() + "'";
        }
    }
}
