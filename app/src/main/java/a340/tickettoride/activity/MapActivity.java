package a340.tickettoride.activity;

import android.content.DialogInterface;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import static android.graphics.Color.*;

import java.util.*;

import a340.tickettoride.R;
import a340.tickettoride.activity.adapter.DestCardAdapter;
import a340.tickettoride.activity.adapter.PlaceTrainsAdapter;
import a340.tickettoride.activity.adapter.TurnTrackerAdapter;
import a340.tickettoride.fragment.right.*;
import a340.tickettoride.presenter.IMapPresenter;
import a340.tickettoride.presenter.MapPresenter;
import cs340.TicketToRide.model.User;
import cs340.TicketToRide.model.game.ChatMessage;
import cs340.TicketToRide.model.game.Player;
import cs340.TicketToRide.model.game.board.*;
import cs340.TicketToRide.model.game.card.DestinationCard;
import cs340.TicketToRide.model.game.card.TrainCard.Color;
import cs340.TicketToRide.utility.Graph;
import cs340.TicketToRide.utility.ID;
import cs340.TicketToRide.utility.Password;
import cs340.TicketToRide.utility.Username;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        SummaryFragment.SummaryFragmentListener,
        ChatFragment.OnFragmentInteractionListener,
        HandFragment.OnFragmentInteractionListener,
        RoutesFragment.OnFragmentInteractionListener,
        AllPlayersFragment.OnFragmentInteractionListener,
        ChatListFragment.OnListFragmentInteractionListener {
    private static final int LINE_WIDTH = 15;
    private static final int LINE_BORDER_WIDTH = 17;
    private static final int CIRCLE_RADIUS = 35000;
    private static final int ORANGE = 0xFFFF9800;
    private static final double CENTER_LAT = 39.8283;
    private static final double CENTER_LNG = -94.5795;
    private static final float ZOOM = 3.65f;
    private static final float BASE_GAP = 17f;
    private static final float CITY_CODE_TEXT_SIZE = 50f;
    private static final int CITY_CODE_SHADOW_RADIUS = 5;
    private static final int CITY_CODE_STROKE_WIDTH = 2;
    private static final double CITY_NAME_LAT_OFFSET = 0.5;

    private Map<Color, Integer> trainColorValues = new HashMap<>();
    private Map<Player.Color, Integer> playerColorValues = new HashMap<>();
    private GoogleMap map;
    private IMapPresenter presenter;
    private SupportMapFragment mapFragment;
    private Set<Marker> cityMarkers;
    private Map<Route, List<Polyline>> lineRouteManager;
    private Board board;
    private RecyclerView playerTurnRecycler;

    private List<Player> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        final LatLng center = new LatLng(CENTER_LAT, CENTER_LNG);
        final LatLngBounds bounds = new LatLngBounds(center, center);
        map.setLatLngBoundsForCameraTarget(bounds);
        map.setMinZoomPreference(ZOOM);
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_options));
        map.moveCamera(CameraUpdateFactory.newLatLng(center));
        displayCities();
        drawRoutes();
    }

    private void init() {
        initTrainColorValues();
        initPlayerColorValues();
        lineRouteManager = new HashMap<>();
        cityMarkers = new HashSet<>();
        presenter = new MapPresenter();
        board = new Board();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initTurnTracker();
        initButtons();
    }

    private void initButtons() {
        Button routesBtn = findViewById(R.id.drawRoutesButton);
        routesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayersTurn();
                initDestCardDialog();
            }
        });

        Button placeTrainBtn = findViewById(R.id.placeTrainsButton);
        placeTrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayersTurn();
                for (Route route : lineRouteManager.keySet()) {
                    showRouteIsClaimed(route);
                    initPlaceTrainDialog();
                }
            }
        });
        Button drawCardsBtn = findViewById(R.id.drawCardsButton);
        drawCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPlayersTurn();
            }
        });
    }

    private void initDestCardDialog() {
        List<DestinationCard> cards = new ArrayList<>();

        cards.add(new DestinationCard(
                new City("SLC", "slc", 10, -10),
                new City("SLC", "slc", 10, -10),
                10
        ));
        cards.add(new DestinationCard(
                new City("dal", "slc", 10, -10),
                new City("dal", "slc", 10, -10),
                10
        ));

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_route, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.dest_card_recycler);
        DestCardAdapter adapter = new DestCardAdapter(cards, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(
                MapActivity.this)
                .setView(view)
                .setTitle("Destination Card")
                .setMessage("Select at least 2 destination cards to keep")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayText("Selected OK");
                    }
                })
                .create();
        dialog.show();
    }

    private void initPlaceTrainDialog() {
        List<Route> routes = new ArrayList<>();

        Route route = new Route(new City("Sandy", "sd", 20, 10), new City("Salt Lake", "sd", 20, 10), Color.coalRed, 0);
        routes.add(route);

        route = new Route(new City("Provo", "sd", 20, 10), new City("American Fork", "sd", 20, 10), Color.coalRed, 0);
        routes.add(route);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_place_trains, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.place_trains_recycler);
        PlaceTrainsAdapter adapter = new PlaceTrainsAdapter(routes, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(
                MapActivity.this)
                .setView(view)
                .setTitle("Possible Paths")
                .setMessage("Select a path to claim")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayText("Selected OK");
                    }
                })
                .create();
        dialog.show();
    }

    private void initTrainColorValues() {
        trainColorValues.put(Color.hopperBlack, BLACK);
        trainColorValues.put(Color.passengerWhite, WHITE);
        trainColorValues.put(Color.tankerBlue, BLUE);
        trainColorValues.put(Color.reeferYellow, YELLOW);
        trainColorValues.put(Color.freightOrange, ORANGE);
        trainColorValues.put(Color.cabooseGreen, GREEN);
        trainColorValues.put(Color.boxPurple, MAGENTA);
        trainColorValues.put(Color.coalRed, RED);
    }

    private void initPlayerColorValues() {
        playerColorValues.put(Player.Color.black, this.getColor(R.color.Black));
        playerColorValues.put(Player.Color.blue, this.getColor(R.color.Blue));
        playerColorValues.put(Player.Color.red, this.getColor(R.color.Red));
        playerColorValues.put(Player.Color.green, this.getColor(R.color.Green));
        playerColorValues.put(Player.Color.yellow, this.getColor(R.color.Gold));
    }

    private Integer getColorValue(Color color) {
        if (color == null) {
            return GRAY;
        }

        return trainColorValues.get(color);
    }

    public Integer getPlayerColorValue(Player.Color color) {
        if (color == null) {
            return GRAY;
        }
        return playerColorValues.get(color);
    }

    private void initTurnTracker() {
        players.add(new Player(new User(new Username("nate"), new Password("123"))));
        players.add(new Player(new User(new Username("jake"), new Password("123"))));
        players.add(new Player(new User(new Username("kate"), new Password("123"))));
        players.add(new Player(new User(new Username("sara"), new Password("123"))));
        players.add(new Player(new User(new Username("dave"), new Password("123"))));
        players.get(0).setColor(Player.Color.black);
        players.get(1).setColor(Player.Color.blue);
        players.get(2).setColor(Player.Color.red);
        players.get(3).setColor(Player.Color.green);
        players.get(4).setColor(Player.Color.yellow);
        TurnTrackerAdapter adapter = new TurnTrackerAdapter(players, this);
        playerTurnRecycler = findViewById(R.id.player_turn_recycler);
        playerTurnRecycler.setLayoutManager(new LinearLayoutManager(this));
        playerTurnRecycler.setAdapter(adapter);
    }

    private void nextPlayersTurn() {
        TurnTrackerAdapter adapter = (TurnTrackerAdapter) playerTurnRecycler.getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setNextActivePlayer();
        adapter.notifyDataSetChanged();
    }

    private void displayCities() {
        Set<City> cities = board.getCities();
        if (citiesDisplayed()) {
            return;
        }
        for (City city : cities) {
            addCityToMap(city);
        }
    }

    private void removeCities() {
        if (!citiesDisplayed()) {
            return;
        }
        for (Marker marker : cityMarkers) {
            marker.remove();
        }
        cityMarkers.clear();
    }

    private boolean citiesDisplayed() {
        return !cityMarkers.isEmpty();
    }

    private void addCityToMap(City city) {
        LatLng latLng = new LatLng(city.getLat(), city.getLng());
        String name = city.getCode();
        CircleOptions options = new CircleOptions()
                .center(latLng)
                .fillColor(RED)
                .radius(CIRCLE_RADIUS);

        latLng = new LatLng(latLng.latitude + CITY_NAME_LAT_OFFSET, latLng.longitude);
        map.addCircle(options);
        Marker marker = map.addMarker(new MarkerOptions()
                .icon(createPureTextIcon(name))
                .position(latLng));
        cityMarkers.add(marker);
    }

    public BitmapDescriptor createPureTextIcon(String text) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(CITY_CODE_TEXT_SIZE);
        textPaint.setColor(WHITE);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(CITY_CODE_STROKE_WIDTH);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setFakeBoldText(true);
        textPaint.setShadowLayer(CITY_CODE_SHADOW_RADIUS, 0, 0, BLACK);

        float textWidth = textPaint.measureText(text);
        float textHeight = textPaint.getTextSize();
        int width = (int) (textWidth) + CITY_CODE_SHADOW_RADIUS * 2;
        int height = (int) (textHeight) + CITY_CODE_SHADOW_RADIUS * 2;

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.translate(0, height - CITY_CODE_SHADOW_RADIUS * 2);
        canvas.drawText(text, CITY_CODE_SHADOW_RADIUS, CITY_CODE_SHADOW_RADIUS, textPaint);
        return BitmapDescriptorFactory.fromBitmap(image);
    }

    private void drawRoutes() {
        Set<Route> routes = board.getRoutes();
        for (Route route : routes) {
            drawRoute(route);
        }
     }

    private void drawRoute(final Route route) {
        final LatLng latLng1 = new LatLng(route.getCity1Lat(), route.getCity1Lng());
        final LatLng latLng2 = new LatLng(route.getCity2Lat(), route.getCity2Lng());
        final View view = mapFragment.getView();

        if (view.getViewTreeObserver().isAlive()) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (lineRouteManager.containsKey(route)) {
                        return;
                    }
                    final Point point1 = map.getProjection().toScreenLocation(latLng1);
                    final Point point2 = map.getProjection().toScreenLocation(latLng2);
                    final float segmentSize = calculateSegmentSize(point1, point2, route);
                    drawRouteSegments(route, segmentSize);
                }
            });
        }
    }

    private float calculateSegmentSize(Point point1, Point point2, Route route) {
        final int length = route.getLength();
        final int numGaps = length + 1;
        final Graph graph = new Graph();
        final double distance = graph.getDistance(point1.x, point2.x, point1.y, point2.y);
        final double distanceWithoutGaps = distance - (numGaps * getRouteGapSize(route));
        return (float) (distanceWithoutGaps / length);
    }

    private void drawRouteSegments(Route route, float segmentSize) {
        if (lineRouteManager.containsKey(route)) {
            return;
        }
        LatLng start = getRouteStartLocation(route);
        LatLng end = getRouteEndLocation(route);

        final PolylineOptions borderOptions = getBorderPolylineOptions(route, start, end, segmentSize);
        final PolylineOptions routeOptions = getPolylineOptions(route, start, end, segmentSize);
        Polyline borderLine = map.addPolyline(borderOptions);
        Polyline routeLine = map.addPolyline(routeOptions);
        List<Polyline> lines = new ArrayList<>();
        lines.add(borderLine);
        lines.add(routeLine);
        lineRouteManager.put(route, lines);
    }

    private LatLng getRouteStartLocation(Route route) {
        if (route.isDoubleRoute()) {
            return new LatLng(route.getCity1OffsetLat(), route.getCity1OffsetLng());
        }

        return new LatLng(route.getCity1Lat(), route.getCity1Lng());
    }

    private LatLng getRouteEndLocation(Route route) {
        if (route.isDoubleRoute()) {
            return new LatLng(route.getCity2OffsetLat(), route.getCity2OffsetLng());
        }

        return new LatLng(route.getCity2Lat(), route.getCity2Lng());
    }

    private float getRouteGapSize(Route route) {
        return BASE_GAP + route.getLength();
    }

    private PolylineOptions getPolylineOptions(final Route route, final LatLng first,
                                               final LatLng second, final float segmentSize) {
        Color color = route.getColor();
        int colorValue = getColorValue(color);
        List<PatternItem> patterns = Arrays.asList(new Gap(getRouteGapSize(route)),
                new Dash(segmentSize));

        return new PolylineOptions()
                .add(first, second)
                .color(colorValue)
                .width(LINE_WIDTH)
                .pattern(patterns);
    }

    private PolylineOptions getBorderPolylineOptions(Route route, final LatLng first,
                                                     final LatLng second, float segmentSize) {
        List<PatternItem> patterns = Arrays.asList(new Gap(getRouteGapSize(route)),
                new Dash(segmentSize));
        return new PolylineOptions()
                .add(first, second)
                .color(BLACK)
                .width(LINE_BORDER_WIDTH)
                .pattern(patterns);
    }

    private PolylineOptions getClaimedRoutePolylineOptions(LatLng start, LatLng end, int color) {
        return new PolylineOptions()
                .add(start, end)
                .color(color)
                .width(LINE_BORDER_WIDTH);
    }

    private void showRouteIsClaimed(final Route route) {
        Player player = players.get(0);
        route.occupy(player.getId()); // this is just for now
        ID playerId = route.getOccupierId();
        if (playerId == null) {
            return;
        }

        final List<Polyline> polylines = lineRouteManager.get(route);
        if (polylines == null) {
            return;
        }
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        LatLng start = getRouteStartLocation(route);
        LatLng end = getRouteEndLocation(route);
        Player.Color color = player.getColor();
        int colorValue = getPlayerColorValue(color);
        map.addPolyline(getClaimedRoutePolylineOptions(start, end, colorValue));
    }

    private int convertPixelsToDp(float px){
        return (int) (px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void displayText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(ChatMessage chatMessage) {

    }
}
