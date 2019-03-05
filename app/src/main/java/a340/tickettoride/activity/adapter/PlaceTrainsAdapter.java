package a340.tickettoride.activity.adapter;

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
import cs340.TicketToRide.model.game.board.Route;

public class PlaceTrainsAdapter extends RecyclerView.Adapter<PlaceTrainsAdapter.RouteView> {
    private List<Route> routes;
    private Route selectedRoute;
    private Context context;
    private Map<View, Route> viewRoutes;

    public PlaceTrainsAdapter(List<Route> routes, Context context) {
        this.routes = routes;
        this.context = context;
        viewRoutes = new HashMap<>();
    }

    @NonNull
    @Override
    public RouteView onCreateViewHolder(@NonNull ViewGroup viewGroup, final int index) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_route, viewGroup, false);

        return new RouteView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteView routeView, int index) {
        Route route = routes.get(index);
        viewRoutes.put(routeView.text, route);
        routeView.text.setText(route.toString());
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
        Route route = viewRoutes.get(textView);
        if (route != null && route.equals(selectedRoute)) {
            return;
        }

        if (selectedRoute != null) {
            for (View view : viewRoutes.keySet()) {
                if (viewRoutes.get(view).equals(selectedRoute)) {
                    ((TextView)view).setTextColor(context.getColor(R.color.White));
                    break;
                }
            }
        }
        selectedRoute = route;
        textView.setTextColor(context.getColor(R.color.Blue));
    }

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    static class RouteView extends RecyclerView.ViewHolder {
        TextView text;

        RouteView(View view) {
            super(view);
            text = view.findViewById(R.id.route_text_view);
        }
    }
}
