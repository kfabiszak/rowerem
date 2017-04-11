package travel;

import com.google.gson.annotations.SerializedName;
import com.google.maps.model.LatLng;

public class Place {

    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;

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
