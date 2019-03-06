package a340.tickettoride.fragment.right;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a340.tickettoride.R;
import a340.tickettoride.presenter.HandPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

import static cs340.TicketToRide.model.game.card.TrainCard.Color.*;

public class HandFragment extends Fragment implements HandPresenter.View {

    private OnFragmentInteractionListener mListener;
    private View view;

    public HandFragment() {
        // Required empty public constructor
    }

    public void updatePlayerHandDisplay(List<TrainCard> trainCards) {
        Map<TrainCard.Color, Integer> colorCounts = new HashMap<>();
        colorCounts.put(coalRed, 0);
        colorCounts.put(passengerWhite, 0);
        colorCounts.put(reeferYellow, 0);
        colorCounts.put(tankerBlue, 0);
        colorCounts.put(cabooseGreen, 0);
        colorCounts.put(boxPurple, 0);
        colorCounts.put(freightOrange, 0);
        colorCounts.put(hopperBlack, 0);
        colorCounts.put(locomotive, 0);

        for (TrainCard card : trainCards) {
            TrainCard.Color color = card.getColor();
            Integer count = colorCounts.get(color);
            colorCounts.put(color, count + 1);
        }
        ((TextView)view.findViewById(R.id.num_red)).setText(colorCounts.get(coalRed));
        ((TextView)view.findViewById(R.id.num_white)).setText(colorCounts.get(passengerWhite));
        ((TextView)view.findViewById(R.id.num_orange)).setText(colorCounts.get(freightOrange));
        ((TextView)view.findViewById(R.id.num_yellow)).setText(colorCounts.get(reeferYellow));
        ((TextView)view.findViewById(R.id.num_green)).setText(colorCounts.get(cabooseGreen));
        ((TextView)view.findViewById(R.id.num_purple)).setText(colorCounts.get(boxPurple));
        ((TextView)view.findViewById(R.id.num_blue)).setText(colorCounts.get(tankerBlue));
        ((TextView)view.findViewById(R.id.num_black)).setText(colorCounts.get(hopperBlack));
        ((TextView)view.findViewById(R.id.num_locomotive)).setText(colorCounts.get(locomotive));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hand, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
