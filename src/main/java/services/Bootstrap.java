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
     * Enables to get locations and other information about bike stations.
     */
    private NextBikeService nextBikeService;

    public Bootstrap() {
        try {
            this.nextBikeService = new NextBikeService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshNextBike() {
        try {
            this.nextBikeService = new NextBikeService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handels receving and sending route information from and to client.
     */
    public void handleRoute() {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
        });

        post("/route", (Request request, Response response) -> {
            RouteFromClient route = jsonTransformer.fromJson(request.body(), RouteFromClient.class);
            System.out.println("jest odp");
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
            System.out.println("odp zwrotna");
            return jsonTransformer.toJson(routeToClient);
        });

        //TODO wysylasz mi lat,lng i PL i miasto a ja odsylam stacje
        post("/station", (Request request, Response response) -> {
            Place place = jsonTransformer.fromJson(request.body(), Place.class); //TODO tutaj city
            if (nextBikeService != null) {
                City city = nextBikeService.findCity("PL","Poznań");
                Station closestStation = nextBikeService.findClosest(city, place);
                return jsonTransformer.toJson(closestStation);
            }
            return null; //TODO obsługa nulli (sprawdzanie czy nie null wszedzie)
        });

        post("/all", (Request request, Response response) -> {
            //TODO odbieram miasto
            if (nextBikeService != null) {
                City city = nextBikeService.findCity("PL","Poznań");
                return jsonTransformer.toJson(city.getStations());
            }
            return null;
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