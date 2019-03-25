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
        final Map<TrainCard.Color, Integer> colorCounts = trainCards.getColorCounts();

        final Map<Integer, TrainCard.Color> slotMap = new HashMap<>();
        slotMap.put(R.id.num_red, coalRed);
        slotMap.put(R.id.num_white, passengerWhite);
        slotMap.put(R.id.num_orange, freightOrange);
        slotMap.put(R.id.num_yellow, reeferYellow);
        slotMap.put(R.id.num_green, cabooseGreen);
        slotMap.put(R.id.num_purple, boxPurple);
        slotMap.put(R.id.num_blue, tankerBlue);
        slotMap.put(R.id.num_black, hopperBlack);
        slotMap.put(R.id.num_locomotive, locomotive);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Integer, TrainCard.Color> entry : slotMap.entrySet()) {
                    ((TextView)view.findViewById(entry.getKey())).setText(String.valueOf(colorCounts.get(entry.getValue())));
                }
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
