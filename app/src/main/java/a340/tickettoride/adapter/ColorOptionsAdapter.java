package a340.tickettoride.adapter;

import android.content.Context;
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
import cs340.TicketToRide.model.game.board.Route;

public class ColorOptionsAdapter extends RecyclerView.Adapter<ColorOptionsAdapter.OptionView> {
    private List<RouteColorOption> options;
    private RouteColorOption selectedOption;
    private Context context;
    private Map<View, RouteColorOption> viewOptions;

    public ColorOptionsAdapter(List<RouteColorOption> options, Context context) {
        this.options = options;
        this.context = context;
        viewOptions = new HashMap<>();
    }

    @NonNull
    @Override
    public OptionView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_route, viewGroup, false);

        return new OptionView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionView optionView, int index) {
        RouteColorOption option = options.get(index);
        viewOptions.put(optionView.text, option);
        optionView.text.setText(option.toString());
        optionView.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = (TextView)v;
                handleClick(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    private void handleClick(TextView textView) {
        RouteColorOption option = viewOptions.get(textView);
        if (option != null && option.equals(selectedOption)) {
            return;
        }

        if (selectedOption != null) {
            for (View view : viewOptions.keySet()) {
                if (viewOptions.get(view).equals(selectedOption)) {
                    ((TextView)view).setTextColor(context.getColor(R.color.White));
                    break;
                }
            }
        }
        selectedOption = option;
        textView.setTextColor(context.getColor(R.color.Blue));
    }

    public RouteColorOption getSelectedOption() {
        return selectedOption;
    }

    static class OptionView extends RecyclerView.ViewHolder {
        TextView text;

        OptionView(View view) {
            super(view);
            text = view.findViewById(R.id.route_text_view);
        }
    }
}
