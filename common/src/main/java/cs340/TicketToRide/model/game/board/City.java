package cs340.TicketToRide.model.game.board;

public class City {
    private String name;
    private String code;
    private double lat;
    private double lng;

    public City(String name, String code, double lat, double lng) {
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
