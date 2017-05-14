package services.nextbike.api.structure;

import com.google.gson.annotations.SerializedName;
import com.google.maps.model.LatLng;

/**
 * Represent a place with coordinates.
 */
public class Place {

    @SerializedName("lat")
    /**
     * Latitude of the place.
     */
    private double lat;
    @SerializedName("lng")
    /**
     * Longitude of the place.
     */
    private double lng;

    public Place(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }
}
