package a340.tickettoride.fragment.right;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Message;

import java.util.ArrayList;
import java.util.List;


public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    private final List<Message> mMessages;

    public ChatRecyclerViewAdapter() {
        mMessages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        mMessages.add(message);
        notifyDataSetChanged();
    }

    public void addMessages(List<Message> messages) {
        mMessages.addAll(messages);
        notifyDataSetChanged();
    }

    public void setMessages(List<Message> messages) {
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
        holder.mPlayerName.setText(mMessages.get(position).getUsername().toString());
        holder.mMessage.setText(mMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlayerName;
        public final TextView mMessage;
        public Message mChatMessage;

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
