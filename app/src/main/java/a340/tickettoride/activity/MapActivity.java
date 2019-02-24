package a340.tickettoride.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static android.graphics.Color.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IMapPresenter;
import a340.tickettoride.presenter.MapPresenter;
import cs340.TicketToRide.model.game.board.Board;
import cs340.TicketToRide.model.game.board.City;
import cs340.TicketToRide.model.game.board.Route;
import cs340.TicketToRide.model.game.card.TrainCard.Color;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private static Map<Color, Integer> colorValues = new HashMap<>();

    public static final double CENTER_LAT = 39.8283;
    public static final double CENTER_LNG = -98.5795;
    public static final int ZOOM = 4;
    private GoogleMap map;
    private IMapPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
//        map.setMaxZoomPreference(5); // todo: get rid of zoom in???
//        map.getUiSettings().setZoomControlsEnabled(true);
//        map.getUiSettings().setZoomGesturesEnabled(true);
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

    }

    private void drawRoute(Route route) {
        LatLng first = new LatLng(route.getCity1().getLat(), route.getCity1().getLng());
        LatLng second = new LatLng(route.getCity2().getLat(), route.getCity2().getLng());
        Color color = route.getColor();
        int length = route.getLength();
        int colorValue = getColorValue(color);
        PolylineOptions polylineOptions = new PolylineOptions().add(first, second)
                .color(colorValue).width(20);
        map.addPolyline(polylineOptions);
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
        colorValues.put(Color.freightOrange, 0xFFA50000);
        colorValues.put(Color.cabooseGreen, GREEN);
        colorValues.put(Color.boxPurple, MAGENTA);
        colorValues.put(Color.coalRed, RED);
    }


}
