package a340.tickettoride.fragment.right;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.ChatListPresenter;
import a340.tickettoride.presenter.interfaces.IChatListPresenter;
import cs340.TicketToRide.model.game.ChatMessage;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChatListFragment extends Fragment implements ChatListPresenter.ChatListPresenterListener {

    private OnListFragmentInteractionListener mListener;
    private ChatRecyclerViewAdapter mChatRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private IChatListPresenter presenter;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();

            mLinearLayoutManager = new LinearLayoutManager(context);
            mLinearLayoutManager.setStackFromEnd(true);

            mChatRecyclerViewAdapter = new ChatRecyclerViewAdapter(mListener);

            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(mLinearLayoutManager);
            recyclerView.setAdapter(mChatRecyclerViewAdapter);
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void updateChatMessages(List<ChatMessage> messages) {
        mChatRecyclerViewAdapter.setMessages(messages);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ChatMessage chatMessage);
    }
}
