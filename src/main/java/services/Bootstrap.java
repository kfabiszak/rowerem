package services;

import static spark.Spark.*;

import services.google.maps.api.GoogleService;
import services.nextbike.api.NextBikeService;
import services.nextbike.api.structure.City;
import services.nextbike.api.structure.Place;
import services.nextbike.api.structure.Station;
import spark.Request;
import spark.Response;
import travel.RouteFromClient;
import travel.RouteToClient;
import user.Registered;

import java.io.IOException;

/*
    Dokumentacja: http://sparkjava.com/
    Obsługa spark + MongoDb: https://blog.openshift.com/developing-single-page-web-applications-using-java-8-spark-mongodb-and-angularjs/

    Odbieranie parametrów:
     request.queryParams("param_name")
    Odbieranie wiadomości:
     Model_name element = jsonTransformer.fromJson(request.body(), Model_name.class);

    #TODO
    Klient przysyła informacje o trasie -> punkt startowy, docelowy, dodatkowe parametry
    !!!POTRZEBUJE kraj + miasto żeby wysyłał + jak to sprawdzić że mamy taki kraj i miasto - ja będę odpowiadał że nie ma
    Serwer znajduje optymalną trasę w NextBike Api
    Serwer wyznacza trasę w Google Map Api
    Serwer zwraca gotową trasę do klienta

    #TODO
    Obsługa logowania rejestracji z MongoDb
*/

/**
 * Manages client-server communication.
 */
public class Bootstrap {

    /**
     * Help to save Object to JSON and retrive Object from JSON.
     */
    private JSONTransformer jsonTransformer = new JSONTransformer();
    /**
     * Enables to get directions from Adress or Coords.
     */
    private GoogleService googleService = new GoogleService();

    /**
     * Handels receving and sending route information from and to client.
     */
    public void handleRoute() {
        post("/route", (Request request, Response response) -> {
            RouteFromClient route = jsonTransformer.fromJson(request.body(), RouteFromClient.class);
            System.out.println("jest odp");
            NextBikeService nextBikeService = null;
            try {
                nextBikeService = new NextBikeService();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nextBikeService != null) {
                nextBikeService.findStations(route);
            }
//            try {
//                route.setDirections(googleService.directionsFromAddress(route.getStartStation().getAddress(), route.getEndStation().getAddress(), true));
//            } catch (InterruptedException | ApiException | IOException e) {
//                e.printStackTrace();
//            }
            RouteToClient routeToClient = new RouteToClient(route.getOrigin(), route.getDestination(), route.getStartStation().getLat(), route.getStartStation().getLng()
            , route.getEndStation().getLat(), route.getEndStation().getLng());
            return jsonTransformer.toJson(routeToClient);
        });
        //TODO wysylasz mi lat,lng a ja odsylam stacje
        post("/station", (Request request, Response response) -> {
            Place place = jsonTransformer.fromJson(request.body(), Place.class);
            NextBikeService nextBikeService = null; //TODO inaczej rozwiązać tworzenie API
            try {
                nextBikeService = new NextBikeService();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nextBikeService != null) {
                City city = nextBikeService.findCity("PL","Poznań");
                Station closestStation = nextBikeService.findClosest(city, place);
                return jsonTransformer.toJson(closestStation);
            }
            return null; //TODO obsługa nulli (sprawdzanie czy nie null wszedzie)
        });
    }

    /**
     * Get login and pass to verification from client.
     */
    public void getLoginFromClient() {
        post("/login", (request, response) -> {
            Registered element = jsonTransformer.fromJson(request.body(), Registered.class);
            return element;
        });

    }
}