package a340.tickettoride.activity;

import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.graphics.Color.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IMapPresenter;
import a340.tickettoride.presenter.MapPresenter;
import cs340.TicketToRide.model.game.board.Board;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCard.Color;
import cs340.TicketToRide.utility.Graph;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final float GAP = 5f;
    public static final int LINE_WIDTH = 10;
    private static Map<Color, Integer> colorValues = new HashMap<>();
    private static final int ORANGE = 0xFF8C0000;
    private static final double CENTER_LAT = 39.8283;
    private static final double CENTER_LNG = -98.5795;
    private static final float ZOOM = 3.5f;

    private GoogleMap map;
    private IMapPresenter presenter;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        presenter = new MapPresenter();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        final LatLng center = new LatLng(CENTER_LAT, CENTER_LNG);
        final LatLngBounds bounds = new LatLngBounds(center, center);
        map.setLatLngBoundsForCameraTarget(bounds);
        map.setMinZoomPreference(ZOOM);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_options));
        map.moveCamera(CameraUpdateFactory.newLatLng(center));
        initColorValues();
        drawRoutes();
    }

    private void drawRoutes() {
        Board board = new Board();
        Set<City> cities = board.getCities();
        Set<Route> routes = board.getRoutes();
        for (City city : cities) {
            addCityToMap(city);
        }

        for (Route route : routes) {
            drawRoute(route);
        }
    }

    private void addCityToMap(City city) {
        LatLng latLng = new LatLng(city.getLat(), city.getLng());
        String name = city.getName();
        CircleOptions options = new CircleOptions()
                .center(latLng)
                .strokeColor(BLACK)
                .fillColor(RED)
                .clickable(true);

        Circle circle = map.addCircle(options);

        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
    }

    private void drawRouteSegments(Route route, float segmentSize) {
        LatLng first = new LatLng(route.getCity1Lat(), route.getCity1Lng());
        LatLng second = new LatLng(route.getCity2Lat(), route.getCity2Lng());

        if (route.isDoubleRoute()) {
            first = new LatLng(route.getCity1OffsetLat(), route.getCity1OffsetLng());
            second = new LatLng(route.getCity2OffsetLat(), route.getCity2OffsetLng());
        }

        final PolylineOptions polylineOptions = getPolylineOptions(route, first, second, segmentSize);
        final Polyline polyline = map.addPolyline(polylineOptions);
    }

    private PolylineOptions getPolylineOptions(final Route route, final LatLng first,
                                               final LatLng second, final float segmentSize) {
        Color color = route.getColor();
        int colorValue = getColorValue(color);
        List<PatternItem> patterns = Arrays.asList(new Dash(segmentSize - GAP), new Gap(GAP));

        return new PolylineOptions()
                .add(first, second)
                .color(colorValue)
                .width(LINE_WIDTH)
                .pattern(patterns);
    }

    private void drawRoute(final Route route) {
        final LatLng latLng1 = new LatLng(route.getCity1Lat(), route.getCity1Lng());
        final LatLng latLng2 = new LatLng(route.getCity2Lat(), route.getCity2Lng());
        final View view = mapFragment.getView();

        if (view.getViewTreeObserver().isAlive()) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
//                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    final Point point1 = map.getProjection().toScreenLocation(latLng1);
                    final Point point2 = map.getProjection().toScreenLocation(latLng2);
                    final float segmentSize = calculateSegmentSize(point1, point2, route);
                    drawRouteSegments(route, segmentSize);
                }
            });
        }

    }

    private float calculateSegmentSize(Point point1, Point point2, Route route) {
        int length = route.getLength();
        Graph graph = new Graph();
        double distance = graph.getDistance(point1.x, point2.x, point1.y, point2.y);
        return (float) (distance / length);
    }

    private Integer getColorValue(Color color) {
        if (color == null) {
            return GRAY;
        }

        return colorValues.get(color);
    }

    private void initColorValues() {
        colorValues.put(Color.hopperBlack, BLACK);
        colorValues.put(Color.passengerWhite, WHITE);
        colorValues.put(Color.tankerBlue, BLUE);
        colorValues.put(Color.reeferYellow, YELLOW);
        colorValues.put(Color.freightOrange, ORANGE);
        colorValues.put(Color.cabooseGreen, GREEN);
        colorValues.put(Color.boxPurple, MAGENTA);
        colorValues.put(Color.coalRed, RED);
    }


}
