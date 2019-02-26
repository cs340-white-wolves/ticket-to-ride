package a340.tickettoride.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.android.ui.IconGenerator;

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
    private static final int LINE_WIDTH = 10;
    private static final int LINE_BORDER_WIDTH = 16;
    private static final int CIRCLE_RADIUS = 40000;
    private static final float CIRCLE_STROKE_WIDTH = 8f;
    private static final int ORANGE = 0xFFFF9800;
    private static final double CENTER_LAT = 39.8283;
    private static final double CENTER_LNG = -94.5795;
    private static final float ZOOM = 3.7f;
    public static final float BASE_GAP = 17f;

    private Map<Color, Integer> colorValues = new HashMap<>();
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
        map.getUiSettings().setZoomGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_options));
        map.moveCamera(CameraUpdateFactory.newLatLng(center));
        initColorValues();
        drawRoutes();
    }

    private void drawRoutes() {
        Board board = new Board();
        Set<City> cities = board.getCities();
        Set<Route> routes = board.getRoutes();

        for (Route route : routes) {
            drawRoute(route);
        }

        for (City city : cities) {
            addCityToMap(city);
        }
    }

    private void addCityToMap(City city) {
        LatLng latLng = new LatLng(city.getLat(), city.getLng());
        String name = city.getCode();
        CircleOptions options = new CircleOptions()
                .center(latLng)
                .strokeWidth(CIRCLE_STROKE_WIDTH)
                .strokeColor(BLACK)
                .fillColor(RED)
                .clickable(true)
                .radius(CIRCLE_RADIUS);

        map.addCircle(options);

        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });


        MarkerOptions options1 = new MarkerOptions();
        BitmapDescriptor d = createPureTextIcon(name);
        options1.icon(d).position(latLng);
        map.addMarker(options1);
//        map.addMarker(new MarkerOptions().position(latLng).title(name).visible(false));

//        IconGenerator iconGenerator = new IconGenerator(this);
//        iconGenerator.setTextAppearance(R.style.BlackText);
//        iconGenerator.setStyle(IconGenerator.STYLE_RED);
//        Bitmap iconBitmap = iconGenerator.makeIcon(name);
//        iconBitmap = Bitmap.createScaledBitmap(iconBitmap, 100, 100, false);
////        map.addMarker(new MarkerOptions()
////                .icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).position(latLng));
    }

    public BitmapDescriptor createPureTextIcon(String text) {

        Paint textPaint = new Paint(); // Adapt to your needs

        textPaint.setTextSize(50f);
        textPaint.setColor(WHITE);

//        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        textPaint.setStrokeWidth(2);
//        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setFakeBoldText(true);
        textPaint.setShadowLayer(5, 0, 0, BLACK);
//        textPaint.setTextScaleX(5);
        float textWidth = textPaint.measureText(text);
        float textHeight = textPaint.getTextSize();
        int width = (int) (textWidth);
        int height = (int) (textHeight);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        canvas.translate(0, height);

        // For development only:
        // Set a background in order to see the
        // full size and positioning of the bitmap.
        // Remove that for a fully transparent icon.
//        canvas.drawColor(BLACK);

        canvas.drawText(text, 0, 0, textPaint);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(image);
        return icon;
    }

    private void drawRouteSegments(Route route, float segmentSize) {
        LatLng first = new LatLng(route.getCity1Lat(), route.getCity1Lng());
        LatLng second = new LatLng(route.getCity2Lat(), route.getCity2Lng());

        if (route.isDoubleRoute()) {
            first = new LatLng(route.getCity1OffsetLat(), route.getCity1OffsetLng());
            second = new LatLng(route.getCity2OffsetLat(), route.getCity2OffsetLng());
        }

        final PolylineOptions borderPolyLineOptions = getBorderPolylineOptions(route, first, second, segmentSize);
        final PolylineOptions polylineOptions = getPolylineOptions(route, first, second, segmentSize);
        map.addPolyline(borderPolyLineOptions);
        map.addPolyline(polylineOptions);
    }

    private PolylineOptions getPolylineOptions(final Route route, final LatLng first,
                                               final LatLng second, final float segmentSize) {
        Color color = route.getColor();
        int colorValue = getColorValue(color);
        List<PatternItem> patterns = Arrays.asList(new Gap(getRouteGapSize(route)), new Dash(segmentSize));

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

    private void drawRoute(final Route route) {
        final LatLng latLng1 = new LatLng(route.getCity1Lat(), route.getCity1Lng());
        final LatLng latLng2 = new LatLng(route.getCity2Lat(), route.getCity2Lng());
        final View view = mapFragment.getView();

        if (view.getViewTreeObserver().isAlive()) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    final Point point1 = map.getProjection().toScreenLocation(latLng1);
                    final Point point2 = map.getProjection().toScreenLocation(latLng2);

//                    Point x1 = new Point(convertPixelsToDp(point1.x), convertPixelsToDp(point1.y));
//                    Point y1 = new Point(convertPixelsToDp(point2.x), convertPixelsToDp(point2.y));
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

    private float getRouteGapSize(Route route) {
        return BASE_GAP + route.getLength();
    }

    private int convertPixelsToDp(float px){
        return (int) (px / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
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
