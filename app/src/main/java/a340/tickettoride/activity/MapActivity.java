package a340.tickettoride.activity;

import android.content.DialogInterface;
import android.graphics.*;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import static android.graphics.Color.*;

import java.util.*;

import a340.tickettoride.R;
import a340.tickettoride.adapter.*;
import a340.tickettoride.fragment.ResultsFragment;
import a340.tickettoride.presenter.DrawRoutesPresenter;
import a340.tickettoride.presenter.PlaceTrainsPresenter;
import a340.tickettoride.presenter.interfaces.IDrawRoutesPresenter;
import a340.tickettoride.presenter.interfaces.IMapPresenter;
import a340.tickettoride.presenter.MapPresenter;
import a340.tickettoride.presenter.interfaces.IPlaceTrainsPresenter;
import cs340.TicketToRide.utility.RouteColorOption;
import cs340.TicketToRide.model.game.*;
import cs340.TicketToRide.model.game.board.*;
import cs340.TicketToRide.model.game.card.DestinationCards;
import cs340.TicketToRide.model.game.card.TrainCard;
import cs340.TicketToRide.utility.*;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, MapPresenter.View {
    private static final int LINE_WIDTH = 15;
    private static final int LINE_BORDER_WIDTH = 17;
    private static final int CIRCLE_RADIUS = 35000;
    private static final int ORANGE = 0x7FFF9800;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int BLACK = 0x7F000000;
    public static final int BLUE = 0x7F0000FF;
    public static final int YELLOW = 0x7FFFFF00;
    public static final int GREEN = 0x7F00FF00;
    public static final int PURPLE = 0x7FFF00FF;
    public static final int RED = 0x7FFF0000;
    private static final double CENTER_LAT = 39.8283;
    private static final double CENTER_LNG = -94.5795;
    private static final float ZOOM = 3.65f;
    private static final float BASE_GAP = 17f;
    private static final float CITY_CODE_TEXT_SIZE = 30f;
    private static final int CITY_CODE_SHADOW_RADIUS = 5;
    private static final int CITY_CODE_STROKE_WIDTH = 2;
    private static final double CITY_NAME_LAT_OFFSET = 0.5;

    private Map<TrainCard.Color, Integer> trainColorValues = new HashMap<>();
    private Map<Player.Color, Integer> playerColorValues = new HashMap<>();
    private GoogleMap map;
    private IMapPresenter presenter;
    private SupportMapFragment mapFragment;
    private Set<Marker> cityMarkers;
    private Map<Route, List<Polyline>> lineRouteManager;
    private RecyclerView playerTurnRecycler;
    private DestCardAdapter destCardAdapter;
    private PlaceTrainsAdapter placeTrainsAdapter;
    private DrawerLayout drawerLayout;
    private Button routesBtn;
    private Button placeTrainBtn;
    private Button drawCardsBtn;
    private ImageButton viewBankBtn;
    private ImageButton viewSummaryBtn;
    private IDrawRoutesPresenter drawRoutesPresenter;
    private IPlaceTrainsPresenter placeTrainsPresenter;
    private ColorOptionsAdapter colorOptionsAdapter;
    private RecyclerView possibleRoutesRecycler;

    public MapActivity() {
        presenter = new MapPresenter(this);
        drawRoutesPresenter = new DrawRoutesPresenter(this);
        placeTrainsPresenter = new PlaceTrainsPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.startObserving();
        drawRoutesPresenter.startObserving();
        placeTrainsPresenter.startObserving();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopObserving();
        drawRoutesPresenter.startObserving();
        placeTrainsPresenter.stopObserving();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
        presenter.startPoller();
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
        trainColorValues = TrainCard.getColorMap();
        initPlayerColorValues();

        lineRouteManager = new HashMap<>();
        cityMarkers = new HashSet<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        initTurnTracker();
        initButtons();
    }

    private void initButtons() {
        routesBtn = findViewById(R.id.drawRoutesButton);
        placeTrainBtn = findViewById(R.id.placeTrainsButton);
        drawCardsBtn = findViewById(R.id.drawCardsButton);
        viewBankBtn = findViewById(R.id.viewBankBtn);
        viewSummaryBtn = findViewById(R.id.viewSummaryBtn);

        routesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickDrawDestCards();
            }
        });

        placeTrainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPlaceTrainDialog();
            }
        });

        drawCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(GravityCompat.START, false);
            }
        });

        viewBankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openDrawer(GravityCompat.START, false);
            }
        });
        viewSummaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDrawer(GravityCompat.END, false);
            }
        });
    }

    @Override
   public void openDrawer(int side, boolean lockDrawer) {
        drawerLayout.openDrawer(side);
        lockDrawer(lockDrawer);
    }

    @Override
    public void closeDrawer(int side) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, side);
        drawerLayout.closeDrawer(side);
    }

    @Override
    public void displayResults(Players players) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
                playerTurnRecycler.setVisibility(View.GONE);
                viewBankBtn.setVisibility(View.GONE);
                viewSummaryBtn.setVisibility(View.GONE);

                ResultsFragment results = new ResultsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.map, results);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    public void enableButtons() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawCardsBtn.setEnabled(true);
                placeTrainBtn.setEnabled(true);
                routesBtn.setEnabled(true);
            }
        });

    }

    public void disableButtons() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                drawCardsBtn.setEnabled(false);
                placeTrainBtn.setEnabled(false);
                routesBtn.setEnabled(false);
            }
        });

    }

    @Override
    public void chooseDestCard(final DestinationCards cards, final int minCardsToKeep) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initDestCardDialog(cards, minCardsToKeep);
            }
        });

    }

    @Override
    public void lockDrawer(boolean locked) {
        if (locked) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.START);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
        }
    }

    @Override
    public void initColorOptionsDialog(List<RouteColorOption> options) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_color_option, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.color_options_recycler);
        colorOptionsAdapter = new ColorOptionsAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(colorOptionsAdapter);

        final AlertDialog dialog = new AlertDialog.Builder(
                MapActivity.this)
                .setView(view)
                .setTitle("Color Options")
                .setMessage("Please choose an option to claim this route")
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteColorOption selectedOption = colorOptionsAdapter.getSelectedOption();
                        if (selectedOption != null) {
                            Route route = placeTrainsAdapter.getSelectedRoute();
                            placeTrainsPresenter.claimRoute(route, selectedOption);
                            dialog.dismiss();
                        } else {
                            displayText("You must select an option");
                        }
                    }
                });
            }
        });
    }

    private void initDestCardDialog(DestinationCards cards, final int minCardsToKeep) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_route, null, false);
        RecyclerView recyclerView = view.findViewById(R.id.dest_card_recycler);
        destCardAdapter = new DestCardAdapter(cards, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(destCardAdapter);

        final AlertDialog dialog = new AlertDialog.Builder(
                MapActivity.this)
                .setView(view)
                .setTitle("Destination Card")
                .setMessage("Select at least " + minCardsToKeep + " destination cards to keep")
                .setCancelable(false)
                .setPositiveButton("OK", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (destCardAdapter.getSelectedDestCards().size() >= minCardsToKeep) {
                            drawRoutesPresenter.discardDestCards();
                            dialog.dismiss();
                        }
                        else {
                            displayText("You must select at least " + minCardsToKeep + " cards.");
                        }
                    }
                });
            }
        });

        dialog.show();
    }

    private void initPlaceTrainDialog() {
        List<Route> routes = placeTrainsPresenter.getPossibleRoutesToClaim();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_place_trains, null, false);
        possibleRoutesRecycler = view.findViewById(R.id.place_trains_recycler);
        placeTrainsAdapter = new PlaceTrainsAdapter(routes, this);
        possibleRoutesRecycler.setLayoutManager(new LinearLayoutManager(this));
        possibleRoutesRecycler.setAdapter(placeTrainsAdapter);
        placeTrainsAdapter.notifyDataSetChanged();

        final AlertDialog dialog = new AlertDialog.Builder(
                MapActivity.this)
                .setView(view)
                .setTitle("Possible Paths")
                .setMessage("Select a path to claim")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        placeTrainsPresenter.onSelectRoute();
                    }
                });
            }
        });
        dialog.show();

        Window window = dialog.getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        window.setLayout((int) (width * 0.75), (int) (height * .90));
        window.setGravity(Gravity.CENTER);


        ViewGroup.LayoutParams layoutParams = possibleRoutesRecycler.getLayoutParams();
        layoutParams.height = (int) (layoutParams.height * .9);
        possibleRoutesRecycler.setLayoutParams(layoutParams);
    }

    private void initPlayerColorValues() {
        playerColorValues.put(Player.Color.black, this.getColor(R.color.Black));
        playerColorValues.put(Player.Color.blue, this.getColor(R.color.Blue));
        playerColorValues.put(Player.Color.red, this.getColor(R.color.Red));
        playerColorValues.put(Player.Color.green, this.getColor(R.color.Green));
        playerColorValues.put(Player.Color.yellow, this.getColor(R.color.Gold));
    }

    private Integer getColorValue(TrainCard.Color color) {
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
        Players players = presenter.getPlayers();
        TurnTrackerAdapter adapter = new TurnTrackerAdapter(players, this);
        playerTurnRecycler = findViewById(R.id.player_turn_recycler);
        playerTurnRecycler.setLayoutManager(new LinearLayoutManager(this));
        playerTurnRecycler.setAdapter(adapter);
    }

    public void displayPlayerTurn(final int playerIdx) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TurnTrackerAdapter adapter = (TurnTrackerAdapter) playerTurnRecycler.getAdapter();
                if (adapter == null) {
                    return;
                }
                adapter.setActivePlayer(playerIdx);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void displayCities() {
        Set<City> cities = presenter.getCities();
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
                .fillColor(Color.RED)
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
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setStrokeWidth(CITY_CODE_STROKE_WIDTH);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setFakeBoldText(true);
        textPaint.setShadowLayer(CITY_CODE_SHADOW_RADIUS, 0, 0, Color.BLACK);

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
        Set<Route> routes = presenter.getRoutes();
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

        //final PolylineOptions borderOptions = getBorderPolylineOptions(route, start, end, segmentSize);
        final PolylineOptions routeOptions = getPolylineOptions(route, start, end, segmentSize);
        //Polyline borderLine = map.addPolyline(borderOptions);
        Polyline routeLine = map.addPolyline(routeOptions);
        List<Polyline> lines = new ArrayList<>();
        //lines.add(borderLine);
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
        TrainCard.Color color = route.getColor();
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
                .color(Color.BLACK)
                .width(LINE_BORDER_WIDTH)
                .pattern(patterns);
    }

    private PolylineOptions getClaimedRoutePolylineOptions(LatLng start, LatLng end, int color) {
        return new PolylineOptions()
                .add(start, end)
                .color(color)
                .width(LINE_BORDER_WIDTH);
    }

    public void showRouteIsClaimed(final Route route) {
        ID playerId = route.getOccupierId();
        Player player = presenter.getPlayerById(playerId);
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

    @Override
    public DestinationCards getSelectedDestinationCards() {
        return destCardAdapter.getSelectedDestCards();
    }

    @Override
    public DestinationCards getRecentlyAddedDestCards() {
        return destCardAdapter.getAllDestCards();
    }

    @Override
    public Route getSelectedRoute() {
        return placeTrainsAdapter.getSelectedRoute();
    }

    private int convertPixelsToDp(float px) {
        return (int) (px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void displayText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
