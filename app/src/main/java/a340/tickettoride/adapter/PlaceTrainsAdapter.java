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
import java.util.Objects;

import a340.tickettoride.R;
import cs340.TicketToRide.model.game.board.Route;

public class PlaceTrainsAdapter extends RecyclerView.Adapter<PlaceTrainsAdapter.RouteView> {
    private List<Route> routes;
    private int selectedRouteIdx;
    private Context context;
    private Map<TextView, RouteView> routeViews;

    public PlaceTrainsAdapter(List<Route> routes, Context context) {
        this.routes = routes;
        this.context = context;
        routeViews = new HashMap<>();
        this.selectedRouteIdx = -1;
    }

    @NonNull
    @Override
    public RouteView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_route, viewGroup, false);

        return new RouteView(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteView routeView, int index) {
        Route route = routes.get(index);
        routeView.route = route;
        routeViews.put(routeView.text, routeView);
        routeView.text.setText(route.toString());
        if (index == selectedRouteIdx) {
            routeView.onSelect();
        } else {
            routeView.onUnselect();
        }
        routeView.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = (TextView)v;
                handleClick(t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    private void handleClick(TextView textView) {
        RouteView routeView = routeViews.get(textView);
        if (routeView == null) {
            return;
        }

        Route route = routeView.route;
        int index = routes.indexOf(route);
        int prevSelectedIdx = selectedRouteIdx;
        selectedRouteIdx = index;

        if (prevSelectedIdx != index) {
            notifyItemChanged(prevSelectedIdx);
        }

        routeView.onSelect();
    }

    public Route getSelectedRoute() {
        return selectedRouteIdx >= 0 ? routes.get(selectedRouteIdx) : null;
    }

    static class RouteView extends RecyclerView.ViewHolder {
        TextView text;
        Route route;
        private Context context;

        RouteView(View view, Context context) {
            super(view);
            this.context = context;
            text = view.findViewById(R.id.route_text_view);
        }

        void onSelect() {
            text.setTextColor(context.getColor(R.color.Blue));
        }

        void onUnselect() {
            text.setTextColor(context.getColor(R.color.White));
        }

    }
}
