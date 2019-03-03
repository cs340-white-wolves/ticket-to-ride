package a340.tickettoride.fragment.left;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import a340.tickettoride.R;
import a340.tickettoride.presenter.BankPresenter;
import a340.tickettoride.presenter.IBankPresenter;
import cs340.TicketToRide.model.game.card.TrainCard;

public class BankFragment extends Fragment implements BankPresenter.BankPresenterListener {


    private IBankPresenter presenter;
    private ImageView[] faceUpCards = new ImageView[5];
    private TextView drawPile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_bank, container, false);
        presenter = new BankPresenter(this);
        bindViews(newView);
        setClickListeners();
        updateFaceUpCards(presenter.getCurrentFaceUpCards());
        return newView;
    }

    private void bindViews(View view) {
        drawPile = view.findViewById(R.id.drawPile);
        faceUpCards[0] = view.findViewById(R.id.card1);
        faceUpCards[1] = view.findViewById(R.id.card2);
        faceUpCards[2] = view.findViewById(R.id.card3);
        faceUpCards[3] = view.findViewById(R.id.card4);
        faceUpCards[4] = view.findViewById(R.id.card5);

        for (int i = 0; i < 5; i++) {
            faceUpCards[i].setTag(i);
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

        drawPile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "You drew a new card",Toast.LENGTH_SHORT).show();

            }
        });

        for (int i=0; i < 5; i++) {
            faceUpCards[i].setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
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
            faceUpCards[i].setImageDrawable(getResources().getDrawable(getCardResource(cards.get(i).getColor()),null));
        }
    }
}
