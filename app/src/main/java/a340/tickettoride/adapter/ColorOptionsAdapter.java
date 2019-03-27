package a340.tickettoride.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a340.tickettoride.R;
import a340.tickettoride.utility.RouteColorOption;
import cs340.TicketToRide.model.game.card.TrainCard;

public class ColorOptionsAdapter extends RecyclerView.Adapter<ColorOptionsAdapter.OptionView> {
    private List<RouteColorOption> options;
    private OptionView selectedOptionView;
    private Context context;
    private Map<View, OptionView> viewOptions;

    public ColorOptionsAdapter(List<RouteColorOption> options, Context context) {
        this.options = options;
        this.context = context;
        viewOptions = new HashMap<>();
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
        optionView.onBind(options.get(index));
        viewOptions.put(optionView.row, optionView);
        optionView.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    private void handleClick(View row) {
        OptionView optionView = viewOptions.get(row);

        if (optionView == null) {
            return;
        }

        if (selectedOptionView != null) {
            selectedOptionView.onUnselected();
        }

        selectedOptionView = optionView;

        optionView.onSelect();
    }

    public RouteColorOption getSelectedOption() {
        return selectedOptionView.option;
    }

    static class OptionView extends RecyclerView.ViewHolder {
        TextView color;
        TextView locomotive;
        View row;
        RouteColorOption option;

        OptionView(View view) {
            super(view);
            row = view.findViewById(R.id.color_option_row);
            color = view.findViewById(R.id.color_option_color);
            locomotive = view.findViewById(R.id.color_option_locomotive);
        }

        void onBind(RouteColorOption option) {
            this.option = option;

            onUnselected();
        }

        void onSelect() {
            this.color.setTextColor(Color.BLUE);
            this.locomotive.setTextColor(Color.BLUE);
        }

        void onUnselected () {
            TrainCard.Color color = option.getColor();

            String first = "";

            if (option.getNumOfColor() > 0) {
                first = String.format("%d %s", option.getNumOfColor(), TrainCard.getColorName(color));
            }

            this.color.setText(first);
            this.color.setTextColor(TrainCard.getColorValue(color));
            String second = "";

            if (option.getNumLocomotives() > 0) {
                second = String.format("%d %s", option.getNumLocomotives(), TrainCard.getColorName(TrainCard.Color.locomotive));
            }

            this.locomotive.setText(second);
            this.locomotive.setTextColor(Color.WHITE);
        }
    }
}