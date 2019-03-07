package a340.tickettoride.fragment.left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.BankPresenter;
import a340.tickettoride.presenter.interfaces.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class BankFragment extends Fragment implements BankPresenter.View {

    private IBankPresenter presenter;
    private ImageView[] faceUpCardSlots = new ImageView[5];
    private TextView drawPile;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View newView = inflater.inflate(R.layout.fragment_bank, container, false);
        presenter = new BankPresenter(this);
        bindViews(newView);
        setClickListeners();
        updateFaceUpCards(presenter.getCurrentFaceUpCards());
        return newView;
    }

    private void bindViews(android.view.View view) {
        drawPile = view.findViewById(R.id.drawPile);
        faceUpCardSlots[0] = view.findViewById(R.id.card1);
        faceUpCardSlots[1] = view.findViewById(R.id.card2);
        faceUpCardSlots[2] = view.findViewById(R.id.card3);
        faceUpCardSlots[3] = view.findViewById(R.id.card4);
        faceUpCardSlots[4] = view.findViewById(R.id.card5);

        for (int i = 0; i < 5; i++) {
            faceUpCardSlots[i].setTag(i);
        }
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

        drawPile.setOnClickListener(new android.view.View.OnClickListener(){

            @Override
            public void onClick(android.view.View view) {
                Toast.makeText(getContext(), "You drew a new card",Toast.LENGTH_SHORT).show();

            }
        });

        for (int i=0; i < 5; i++) {
            faceUpCardSlots[i].setOnClickListener(new android.view.View.OnClickListener(){

                @Override
                public void onClick(android.view.View view) {
                    TrainCard newCard = presenter.drawTrainCard();
                    ImageView card = (ImageView) view;
                    card.setImageDrawable(getResources().getDrawable(getCardResource(newCard.getColor()),null));
                }
            });
        }
    }


    @Override
    public void updateFaceUpCards(List<TrainCard> cards) {
        for (int i=0; i < 5; i++) {
            faceUpCardSlots[i]
                    .setImageDrawable(getResources()
                            .getDrawable(getCardResource(cards.get(i).getColor()),null));
        }
    }

    @Override
    public void updateDestinationCardCount(int count) {

    }
}
