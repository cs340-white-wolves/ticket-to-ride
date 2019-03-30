package a340.tickettoride.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a340.tickettoride.R;
import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.card.TrainCard;

public class ColorOptionsAdapter extends RecyclerView.Adapter<ColorOptionsAdapter.OptionView> {
    private List<RouteColorOption> options;
    private int lastSelectPos = -1;

    public ColorOptionsAdapter(List<RouteColorOption> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public OptionView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_color_option, viewGroup, false);

        return new OptionView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionView optionView, int index) {
        RouteColorOption option = options.get(index);
        optionView.onBind(option);
        optionView.radioButton.setChecked(lastSelectPos == index);
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public RouteColorOption getSelectedOption() {
        return lastSelectPos >= 0 ? options.get(lastSelectPos) : null;
    }

    public class OptionView extends RecyclerView.ViewHolder {
        TextView color;
        TextView locomotive;
        RouteColorOption option;
        RadioButton radioButton;

        OptionView(View view) {
            super(view);
            color = view.findViewById(R.id.color_option_color);
            locomotive = view.findViewById(R.id.color_option_locomotive);
            radioButton = view.findViewById(R.id.color_option_radio);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectPos = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }

        void onBind(RouteColorOption option) {
            this.option = option;
            TrainCard.Color color = option.getColor();
            this.color.setTextColor(TrainCard.getColorValue(color));
            this.locomotive.setTextColor(Color.WHITE);

            int numOfColor = option.getNumOfColor();
            int numLocomotives = option.getNumLocomotives();

            String colorStr = String.format("%d %s", numOfColor, TrainCard.getColorName(color));
            String locomotiveStr = String.format("%d %s", numLocomotives, TrainCard.getColorName(TrainCard.Color.locomotive));

            if (numOfColor > 0) {
                this.color.setText(colorStr);
                if (numLocomotives > 0) {
                    this.locomotive.setText(locomotiveStr);
                } else {
                    this.locomotive.setText("");
                }

            } else {
                this.color.setText(locomotiveStr);
                this.locomotive.setText("");
            }


        }

    }
}
