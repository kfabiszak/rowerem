package travel;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import services.nextbike.api.structure.City;
import services.nextbike.api.structure.Station;

/**
 * Represent the route recevied from client.
 */
public class RouteFromClient {
    /**
     * Current address of user.
     */
    private String origin;
    /**
     * Current latitude of user.
     */
    private double originLat;
    /**
     * Current longitude of user.
     */
    private double originLng;
    /**
     * RouteFromClient destination.
     */
    private String destination; //gdzie chce dojechać
    /**
     * RouteFromClient destination latitude.
     */
    private double destinationLat;
    /**
     * RouteFromClient destination longitude.
     */
    private double destinationLng;
    /**
     * User country name.
     */
    private String country_name;
    /**
     * User city name.
     */
    private String city_name;
    /**
     * RouteFromClient start Station.
     */
    private Station startStation; //z jakiej stacji ma wziac rower
    /**
     * RouteFromClient end Station.
     */
    private Station endStation; //gdzie ma zostawić rower
    /**
     * City of user.
     */
    private City city;
    /**
     * Directions of the RouteFromClient.
     */
    private DirectionsResult directions;

    /**
     * No parameter RouteFromClient constructor.
     */
    public RouteFromClient() {}

    /**
     * construct RouteFromClient from Google Directions API results.
     * @param dir Google Directions API result. 
     */
    public RouteFromClient(DirectionsResult dir) {
        this.setDirections(dir);
    }

    public String getOrigin() {
        return origin;
    }

    public double getOriginLat() {
        return originLat;
    }

    public double getOriginLng() {
        return originLng;
    }

    public LatLng getOriginLatLng() {
        return new LatLng(originLat, originLng);
    }

    public String getDestination() {
        return destination;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public double getDestinationLng() {
        return destinationLng;
    }

    public LatLng getDestinationLatLng() {
        return new LatLng(destinationLat, destinationLng);
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public City getCity() {
        return city;
    }

    public DirectionsResult getDirections() {
        return directions;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public void setDirections(DirectionsResult directions) {
        this.directions = directions;
    }

    //TODO +opcja jezeli wybrał od razu stacje a nie jakis adres)
    //TODO Dodatkowo wyznaczam 2 trasy do stacji i ze stacji (googleAPI - tam jest opcja WALKING)

}
