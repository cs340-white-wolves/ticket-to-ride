package a340.tickettoride.fragment.left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import a340.tickettoride.R;
import a340.tickettoride.presenter.BankPresenter;
import a340.tickettoride.presenter.InvalidMoveException;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.model.game.card.TrainCards;

import static cs340.TicketToRide.model.game.Game.MAX_FACE_UP;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.locomotive;

public class BankFragment extends Fragment implements BankPresenter.View, View.OnClickListener {

    private IBankPresenter presenter;
    private ImageView[] faceUpCardSlots = new ImageView[5];
    private TextView drawPile;
    private TextView trainCardCount;
    private TextView destinationCardCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_bank, container, false);
        presenter = new BankPresenter(this);
        presenter.startObserving();
        bindViews(newView);
        setClickListeners();
        updateFaceUpCards(presenter.getCurrentFaceUpCards());
        return newView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stopObserving();
    }

    private void bindViews(android.view.View view) {
        drawPile = view.findViewById(R.id.drawPile);
        trainCardCount = view.findViewById(R.id.trainCardCount);
        destinationCardCount = view.findViewById(R.id.destCardCount);

        trainCardCount.setText(String.valueOf(presenter.getNumTrainCards()));
        destinationCardCount.setText(String.valueOf(presenter.getNumDestCards()));

        faceUpCardSlots[0] = view.findViewById(R.id.card1);
        faceUpCardSlots[1] = view.findViewById(R.id.card2);
        faceUpCardSlots[2] = view.findViewById(R.id.card3);
        faceUpCardSlots[3] = view.findViewById(R.id.card4);
        faceUpCardSlots[4] = view.findViewById(R.id.card5);
//
//        for (int i = 0; i < 5; i++) {
//            faceUpCardSlots[i].setTag(R.id.INDEX_KEY, i);
//        }
    }


    private int getCardResource(TrainCard.Color color) {
        int resourceId;
        switch (color) {
            case passengerWhite: resourceId = R.drawable.white_car;
                break;
            case tankerBlue: resourceId = R.drawable.blue_car;
                break;
            case reeferYellow: resourceId = R.drawable.yellow_car;
                break;
            case freightOrange: resourceId = R.drawable.orange_car;
                break;
            case cabooseGreen: resourceId = R.drawable.green_car;
                break;
            case boxPurple: resourceId = R.drawable.pink_car;
                break;
            case hopperBlack: resourceId = R.drawable.black_car;
                break;
            case coalRed: resourceId = R.drawable.red_car;
                break;
            default: resourceId = R.drawable.locomotive;
                break;
        }
        return resourceId;
    }

    private void setClickListeners() {

        drawPile.setOnClickListener(this);

        for (int i=0; i < 5; i++) {
            faceUpCardSlots[i].setOnClickListener(this);
        }
    }

    @Override
    public void updateFaceUpCards(final TrainCards cards) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i < MAX_FACE_UP; i++) {
                    if (i >= cards.size()) {
                        faceUpCardSlots[i].setVisibility(View.INVISIBLE);
                        faceUpCardSlots[i].setEnabled(false);
                    } else {
                        TrainCard card = cards.get(i);
                        faceUpCardSlots[i].setVisibility(View.VISIBLE);
                        faceUpCardSlots[i].setEnabled(true);
                        faceUpCardSlots[i].setTag(R.id.CARD_KEY, card);
                        faceUpCardSlots[i].setImageDrawable(getResources()
                                .getDrawable(getCardResource(card.getColor()),null));
                    }

                }
            }
        });

    }

    @Override
    public void updateDestinationCardCount(final int count) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                destinationCardCount.setText(Integer.toString(count));;
            }
        });

    }

    @Override
    public void updateDrawableTrainCardCount(final int count) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                trainCardCount.setText(Integer.toString(count));
            }
        });
    }

    @Override
    public void onClick(View view) {

        try {
            switch (view.getId()) {
                case R.id.drawPile:
                    presenter.drawFromDeck();
                    break;
                default:
//                    int index = (int) view.getTag(R.id.INDEX_KEY);
                    TrainCard card = (TrainCard) view.getTag(R.id.CARD_KEY);

                    if (card.getColor() == locomotive) {
                        presenter.drawLocomotiveFaceUp(card);
                    } else {
                        presenter.drawStandardFaceUp(card);
                    }
                    break;
            }

        } catch(InvalidMoveException e) {
            Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
