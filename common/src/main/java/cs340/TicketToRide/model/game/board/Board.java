package cs340.TicketToRide.model.game.board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cs340.TicketToRide.model.game.card.TrainCard.Color;

import static cs340.TicketToRide.model.game.card.TrainCard.Color.boxPurple;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.cabooseGreen;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.coalRed;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.freightOrange;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.hopperBlack;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.passengerWhite;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.reeferYellow;
import static cs340.TicketToRide.model.game.card.TrainCard.Color.tankerBlue;

public class Board {
    private Set<City> cities;
    private Set<Route> routes;

    public Board() {
        cities = new HashSet<>();
        routes = new HashSet<>();
        initCitiesAndRoutes();
    }

    private void initCitiesAndRoutes() {
        City vancouver = new City("Vancouver", 49.2827,-123.1207);
        City seattle = new City("Seattle", 47.6062, -122.3321);
        City portland = new City("Portland", 45.5122, -122.6587);
        City sanFrancisco = new City("San Francisco", 37.7749, -122.4194);
        City losAngeles = new City("Los Angeles", 34.0522, -118.2437);
        City lasVegas = new City("Las Vegas", 36.1699, -115.1398);
        City phoenix = new City("Phoenix", 33.4484, -112.0740);
        City santaFe = new City("Santa Fe", 35.6870, -105.9378);
        City elPaso = new City("El Paso", 31.7619, -106.4850);
        City saltLakeCity = new City("Salt Lake City", 40.7608, -111.8910);
        City helena = new City("Helena", 46.5891, -112.0391);
        City calgary = new City("Calgary", 51.0486, -114.0708);
        City denver = new City("Denver", 39.7392, -104.9903);
        City winnipeg = new City("Winnipeg", 49.8951, -97.1384);
        City duluth = new City("Duluth", 46.7867, -92.1005);
        City omaha = new City("Omaha", 41.2565, -95.9345);
        City kansasCity = new City("Kansas City", 39.0997, -94.5786);
        City oklahomaCity = new City("Oklahoma City", 35.4676, -97.5164);
        City dallas = new City("Dallas", 32.7767, -96.7970);
        City houston = new City("Houston", 29.7604, -95.3698);
        City newOrleans = new City("New Orleans", 29.9511, -90.0715);
        City littleRock = new City("Little Rock", 34.7465, -92.2896);
        City chicago = new City("Chicago", 41.8781, -87.6298);
        City saintLouis = new City("Saint Louis", 38.6270, -90.1994);
        City saultStMarie = new City("Sault St. Marie", 46.4977, -84.3476);
        City toronto = new City("Toronto", 43.6532, -79.3832);
        City pittsburgh = new City("Pittsburgh", 40.4406, -79.9959);
        City nashville = new City("Nashville", 36.1627, -86.7816);
        City atlanta = new City("Atlanta", 33.7490, -84.3880);
        City miami = new City("Miami", 25.7617, -80.1918);
        City raleigh = new City("Raleigh", 35.7796, -78.6382);
        City charleston = new City("Charleston", 32.7765, -79.9311);
        City washington = new City("Washington", 38.9072, -77.0369);
        City newYork = new City("New York", 40.7128, -74.0060);
        City boston = new City("Boston", 42.3601, -71.0589);
        City montreal = new City("Montreal", 45.5017, -73.5673);

        City[] cities = {
                vancouver, seattle, portland, sanFrancisco, losAngeles, lasVegas, phoenix,
                santaFe, elPaso, saltLakeCity, helena, calgary, denver, winnipeg, duluth, omaha,
                kansasCity, oklahomaCity, dallas, houston, newOrleans, littleRock, chicago,
                chicago, saintLouis, saultStMarie, toronto, pittsburgh, nashville, atlanta, miami,
                raleigh, charleston, washington, newYork, boston, montreal
        };

        this.cities.addAll(Arrays.asList(cities));

        addRoute(vancouver, seattle, null, 1);
        addRoute(vancouver, seattle, null, 1);
        addRoute(vancouver, calgary, null, 3);
        addRoute(seattle, calgary, null, 4);
        addRoute(seattle, helena, reeferYellow, 6);
        addRoute(seattle, portland, null, 1);
        addRoute(seattle, portland, null, 1);
        addRoute(portland, saltLakeCity, tankerBlue, 6);
        addRoute(portland, sanFrancisco, boxPurple, 5);
        addRoute(portland, sanFrancisco, cabooseGreen, 5);
        addRoute(sanFrancisco, saltLakeCity, freightOrange, 5);
        addRoute(sanFrancisco, saltLakeCity, passengerWhite, 5);
        addRoute(sanFrancisco, losAngeles, boxPurple, 3);
        addRoute(sanFrancisco, losAngeles, reeferYellow, 3);
        addRoute(losAngeles, lasVegas, null, 2);
        addRoute(losAngeles, phoenix, null, 3);
        addRoute(losAngeles, elPaso, hopperBlack, 6);
        addRoute(calgary, helena, null, 4);
        addRoute(calgary, winnipeg, passengerWhite, 6);
        addRoute(helena, saltLakeCity, boxPurple, 3);
        addRoute(helena, denver, cabooseGreen, 4);
        addRoute(helena, omaha, coalRed, 5);
        addRoute(helena, winnipeg, tankerBlue, 4);
        addRoute(saltLakeCity, denver, coalRed, 3);
        addRoute(saltLakeCity, denver, reeferYellow, 3);
        addRoute(saltLakeCity, lasVegas, freightOrange, 3);
        addRoute(phoenix, denver, passengerWhite, 5);
        addRoute(phoenix, santaFe, null, 3);
        addRoute(phoenix, elPaso, null, 3);
        addRoute(denver, omaha, boxPurple, 4);
        addRoute(denver, kansasCity, hopperBlack, 4);
        addRoute(denver, kansasCity, freightOrange, 4);
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
        addRoute(duluth, omaha, null, 2);
        addRoute(duluth, omaha, null, 2);
        addRoute(omaha, chicago, tankerBlue, 4);
        addRoute(omaha, kansasCity, null, 1);
        addRoute(omaha, kansasCity, null, 1);
        addRoute(kansasCity, saintLouis, tankerBlue, 2);
        addRoute(kansasCity, saintLouis, boxPurple, 2);
        addRoute(kansasCity, oklahomaCity, null, 1);
        addRoute(kansasCity, oklahomaCity, null, 1);
        addRoute(oklahomaCity, littleRock, null, 2);
        addRoute(oklahomaCity, dallas, null, 2);
        addRoute(oklahomaCity, dallas, null, 2);
        addRoute(dallas, littleRock, null, 2);
        addRoute(dallas, houston, null, 1);
        addRoute(dallas, houston, null, 1);
        addRoute(houston, newOrleans, null, 2);
        addRoute(saultStMarie, montreal, hopperBlack, 5);
        addRoute(saultStMarie, toronto, null, 2);
        addRoute(chicago, toronto, passengerWhite, 4);
        addRoute(chicago, pittsburgh, freightOrange, 3);
        addRoute(chicago, pittsburgh, hopperBlack, 3);
        addRoute(chicago, saintLouis, passengerWhite, 2);
        addRoute(chicago, saintLouis, cabooseGreen, 2);
        addRoute(saintLouis, pittsburgh, cabooseGreen, 5);
        addRoute(saintLouis, nashville, null, 2);
        addRoute(saintLouis, littleRock, null, 2);
        addRoute(littleRock, nashville, passengerWhite, 3);
        addRoute(littleRock, newOrleans, cabooseGreen, 3);
        addRoute(newOrleans, atlanta, reeferYellow, 4);
        addRoute(newOrleans, atlanta, freightOrange, 4);
        addRoute(nashville, pittsburgh, reeferYellow, 4);
        addRoute(nashville, raleigh, hopperBlack, 3);
        addRoute(nashville, atlanta, null, 1);
        addRoute(atlanta, raleigh, null, 2);
        addRoute(atlanta, raleigh, null, 2);
        addRoute(atlanta, charleston, null, 2);
        addRoute(toronto, montreal, null, 3);
        addRoute(toronto, pittsburgh, null, 2);
        addRoute(pittsburgh, newYork, passengerWhite, 2);
        addRoute(pittsburgh, newYork, cabooseGreen, 2);
        addRoute(pittsburgh, washington, null, 2);
        addRoute(pittsburgh, raleigh, null, 2);
        addRoute(raleigh, washington, null, 2);
        addRoute(raleigh, washington, null, 2);
        addRoute(raleigh, charleston, null, 2);
        addRoute(charleston, miami, boxPurple, 4);
        addRoute(montreal, boston, null, 2);
        addRoute(montreal, boston, null, 2);
        addRoute(montreal, newYork, tankerBlue, 3);
        addRoute(newYork, boston, reeferYellow, 2);
        addRoute(newYork, boston, coalRed, 2);
        addRoute(newYork, washington, freightOrange, 2);
        addRoute(newYork, washington, hopperBlack, 2);
    }

    private void addRoute(City city1, City city2, Color color, int length) {
        Route route = new Route(city1, city2, color, length);
        routes.add(route);
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
