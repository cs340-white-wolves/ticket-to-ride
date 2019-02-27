package cs340.TicketToRide.model.game.board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.model.game.card.TrainCard.Color;

import static cs340.TicketToRide.model.game.card.TrainCard.Color.*;

public class Board {
    private Set<City> cities;
    private Set<Route> routes;

    public Board() {
        cities = new HashSet<>();
        routes = new HashSet<>();
        initCitiesAndRoutes();
    }

    private void initCitiesAndRoutes() {
        City vancouver = new City("Vancouver", "VAN", 50.2827,-123.1207);
        City seattle = new City("Seattle", "SEA", 47.6062, -122.3321);
        City portland = new City("Portland", "POR", 45.0, -122.6587);
        City sanFrancisco = new City("San Francisco", "SFO", 37.7749, -122.4194);
        City losAngeles = new City("Los Angeles", "LA", 34.0522, -118.2437);
        City lasVegas = new City("Las Vegas", "LV", 36.1699, -115.1398);
        City phoenix = new City("Phoenix", "PHX", 35.1983, -111.6513);
        City santaFe = new City("Santa Fe", "SAF", 35.6870, -105.9378);
        City elPaso = new City("El Paso", "ELP", 31.7619, -106.4850);
        City saltLakeCity = new City("Salt Lake City", "SLC", 40.7608, -111.8910);
        City helena = new City("Helena", "HLN", 46.5891, -112.0391);
        City calgary = new City("Calgary", "CGY", 51.0486, -114.0708);
        City denver = new City("Denver", "DEN", 39.7392, -104.9903);
        City winnipeg = new City("Winnipeg", "WIN", 49.8951, -97.1384);
        City duluth = new City("Duluth", "DLH", 46.7867, -92.1005);
        City omaha = new City("Omaha", "OMA", 41.2565, -95.9345);
        City kansasCity = new City("Kansas City", "KC", 39.0997, -94.5786);
        City oklahomaCity = new City("Oklahoma City", "OKC", 35.4676, -97.5164);
        City dallas = new City("DFW", "DAL", 32.7767, -96.7970);
        City houston = new City("Houston", "HOU", 29.7604, -95.3698);
        City newOrleans = new City("New Orleans", "NOL", 29.9511, -90.0715);
        City littleRock = new City("Little Rock", "LIT", 34.7465, -92.2896);
        City chicago = new City("Chicago", "CHI", 41.8781, -87.6298);
        City saintLouis = new City("Saint Louis", "STL", 38.6270, -90.1994);
        City saultStMarie = new City("Sault St. Marie", "SSM", 46.4977, -84.3476);
        City toronto = new City("Toronto", "TOR", 43.6532, -79.3832);
        City pittsburgh = new City("Pittsburgh", "PIT", 40.4406, -79.9959);
        City nashville = new City("Nashville", "NAS", 36.1627, -86.7816);
        City atlanta = new City("Atlanta", "ATL", 33.7490, -84.3880);
        City miami = new City("Miami", "MIA", 25.7617, -80.1918);
        City raleigh = new City("Raleigh", "RAL", 35.7796, -78.6382);
        City charleston = new City("Charleston", "CHS", 32.7765, -79.9311);
        City washington = new City("Washington", "D.C.", 38.9072, -77.0369);
        City newYork = new City("New York", "NYC", 42.0848, -75.5);
        City boston = new City("Boston", "BOS", 42.3601, -71.0589);
        City montreal = new City("Montreal", "MON", 45.5017, -73.5673);

        City[] cities = {
                vancouver, seattle, portland, sanFrancisco, losAngeles, lasVegas, phoenix,
                santaFe, elPaso, saltLakeCity, helena, calgary, denver, winnipeg, duluth, omaha,
                kansasCity, oklahomaCity, dallas, houston, newOrleans, littleRock, chicago,
                chicago, saintLouis, saultStMarie, toronto, pittsburgh, nashville, atlanta, miami,
                raleigh, charleston, washington, newYork, boston, montreal
        };

        this.cities.addAll(Arrays.asList(cities));

        addDoubleRoute(vancouver, seattle, null, null, 1);
        addRoute(vancouver, calgary, null, 3);
        addRoute(seattle, calgary, null, 4);
        addRoute(seattle, helena, reeferYellow, 6);
        addDoubleRoute(seattle, portland, null, null, 1);
        addRoute(portland, saltLakeCity, tankerBlue, 6);
        addDoubleRoute(portland, sanFrancisco, boxPurple, cabooseGreen, 5);
        addDoubleRoute(sanFrancisco, saltLakeCity, freightOrange, passengerWhite, 5);
        addDoubleRoute(sanFrancisco, losAngeles, boxPurple, reeferYellow, 3);
        addRoute(losAngeles, lasVegas, null, 2);
        addRoute(losAngeles, phoenix, null, 3);
        addRoute(losAngeles, elPaso, hopperBlack, 6);
        addRoute(calgary, helena, null, 4);
        addRoute(calgary, winnipeg, passengerWhite, 6);
        addRoute(helena, saltLakeCity, boxPurple, 3);
        addRoute(helena, denver, cabooseGreen, 4);
        addRoute(helena, omaha, coalRed, 5);
        addRoute(helena, winnipeg, tankerBlue, 4);
        addDoubleRoute(saltLakeCity, denver, coalRed, reeferYellow, 3);
        addRoute(saltLakeCity, lasVegas, freightOrange, 3);
        addRoute(phoenix, denver, passengerWhite, 5);
        addRoute(phoenix, santaFe, null, 3);
        addRoute(phoenix, elPaso, null, 3);
        addRoute(denver, omaha, boxPurple, 4);
        addDoubleRoute(denver, kansasCity, hopperBlack, freightOrange, 4);
        addRoute(denver, oklahomaCity, coalRed, 4);
        addRoute(denver, santaFe, null, 2);
        addRoute(santaFe, oklahomaCity, tankerBlue, 3);
        addRoute(santaFe, elPaso, null, 2);
        addRoute(elPaso, oklahomaCity, reeferYellow, 5);
        addRoute(elPaso, dallas, coalRed, 4);
        addRoute(elPaso, houston, cabooseGreen, 6);
        addRoute(winnipeg, saultStMarie, null, 6);
        addRoute(winnipeg, duluth, hopperBlack, 4);
        addRoute(duluth, saultStMarie, null, 3);
        addRoute(duluth, toronto, boxPurple, 6);
        addRoute(duluth, chicago, coalRed, 3);
        addDoubleRoute(duluth, omaha, null,null, 2);
        addRoute(omaha, chicago, tankerBlue, 4);
        addDoubleRoute(omaha, kansasCity, null, null, 1);
        addDoubleRoute(kansasCity, saintLouis, tankerBlue, boxPurple, 2);
        addDoubleRoute(kansasCity, oklahomaCity, null, null, 2);
        addRoute(oklahomaCity, littleRock, null, 2);
        addDoubleRoute(oklahomaCity, dallas, null, null, 2);
        addRoute(dallas, littleRock, null, 2);
        addDoubleRoute(dallas, houston, null, null, 1);
        addRoute(houston, newOrleans, null, 2);
        addRoute(saultStMarie, montreal, hopperBlack, 5);
        addRoute(saultStMarie, toronto, null, 2);
        addRoute(chicago, toronto, passengerWhite, 4);
        addDoubleRoute(chicago, pittsburgh, freightOrange, hopperBlack, 3);
        addDoubleRoute(chicago, saintLouis, passengerWhite, cabooseGreen, 2);
        addRoute(saintLouis, pittsburgh, cabooseGreen, 5);
        addRoute(saintLouis, nashville, null, 2);
        addRoute(saintLouis, littleRock, null, 2);
        addRoute(littleRock, nashville, passengerWhite, 3);
        addRoute(littleRock, newOrleans, cabooseGreen, 3);
        addDoubleRoute(newOrleans, atlanta, reeferYellow, freightOrange, 4);
        addRoute(newOrleans, miami, coalRed, 6);
        addRoute(nashville, pittsburgh, reeferYellow, 4);
        addRoute(nashville, raleigh, hopperBlack, 3);
        addRoute(nashville, atlanta, null, 1);
        addDoubleRoute(atlanta, raleigh, null,null, 2);
        addRoute(atlanta, charleston, null, 2);
        addRoute(atlanta, miami, tankerBlue, 5);
        addRoute(toronto, montreal, null, 3);
        addRoute(toronto, pittsburgh, null, 2);
        addDoubleRoute(pittsburgh, newYork, passengerWhite, cabooseGreen, 2);
        addRoute(pittsburgh, washington, null, 2);
        addRoute(pittsburgh, raleigh, null, 2);
        addDoubleRoute(raleigh, washington, null, null, 2);
        addRoute(raleigh, charleston, null, 2);
        addRoute(charleston, miami, boxPurple, 4);
        addDoubleRoute(montreal, boston, null, null, 2);
        addRoute(montreal, newYork, tankerBlue, 3);
        addDoubleRoute(newYork, boston, reeferYellow, coalRed, 2);
        addDoubleRoute(newYork, washington, hopperBlack, hopperBlack, 2);
    }

    private void addRoute(City city1, City city2, Color color, int length) {
        Route route = new Route(city1, city2, color, length);
        routes.add(route);
    }

    private void addDoubleRoute(City city1, City city2, Color color1, Color color2, int length) {
        Route route1 = new Route(city1, city2, color1, length, Route.PRIMARY);
        Route route2 = new Route(city1, city2, color2, length, Route.SECONDARY);
        routes.add(route1);
        routes.add(route2);
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }
}
