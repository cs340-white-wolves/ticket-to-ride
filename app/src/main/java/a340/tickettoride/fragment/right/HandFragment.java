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
import a340.tickettoride.presenter.interfaces.IHandPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

import static cs340.TicketToRide.model.game.card.TrainCard.Color.*;

public class HandFragment extends Fragment implements HandPresenter.View {

    private View view;
    private IHandPresenter presenter;

    public HandFragment() {
        presenter = new HandPresenter(this);
        // Required empty public constructor
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

    public void updatePlayerHandDisplay(TrainCards trainCards) {
        final Map<TrainCard.Color, Integer> colorCounts = new HashMap<>();
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
            colorCounts.put(color, count == null ? 0 : count + 1);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)view.findViewById(R.id.num_red)).setText(String.valueOf(colorCounts.get(coalRed)));
                ((TextView)view.findViewById(R.id.num_white)).setText(String.valueOf(colorCounts.get(passengerWhite)));
                ((TextView)view.findViewById(R.id.num_orange)).setText(String.valueOf(colorCounts.get(freightOrange)));
                ((TextView)view.findViewById(R.id.num_yellow)).setText(String.valueOf(colorCounts.get(reeferYellow)));
                ((TextView)view.findViewById(R.id.num_green)).setText(String.valueOf(colorCounts.get(cabooseGreen)));
                ((TextView)view.findViewById(R.id.num_purple)).setText(String.valueOf(colorCounts.get(boxPurple)));
                ((TextView)view.findViewById(R.id.num_blue)).setText(String.valueOf(colorCounts.get(tankerBlue)));
                ((TextView)view.findViewById(R.id.num_black)).setText(String.valueOf(colorCounts.get(hopperBlack)));
                ((TextView)view.findViewById(R.id.num_locomotive)).setText(String.valueOf(colorCounts.get(locomotive)));
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hand, container, false);
        return view;
    }

}
