package a340.tickettoride.fragment.right;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.Message;

class GameHistoryRecyclerViewAdapter extends RecyclerView.Adapter<GameHistoryRecyclerViewAdapter.ViewHolder> {

    private List<Message> historyMessages;

    public GameHistoryRecyclerViewAdapter(List<Message> historyMessages) {
        this.historyMessages = historyMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_chat_content, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mHistoryMessage = historyMessages.get(i);
        viewHolder.mPlayerName.setText(historyMessages.get(i).getUsername().toString());
        viewHolder.mMessage.setText(historyMessages.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return historyMessages.size();
    }

    public void setMessages(List<Message> historyMessages) {
        this.historyMessages = historyMessages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPlayerName;
        public final TextView mMessage;
        public Message mHistoryMessage;

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
