package services.nextbike.api.structure;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represent the city.
 * @author monday
 */
public class City extends Place {

    /**
     * Unique ID.
     */
    private int uid;
    /**
     * Name of the city.
     */
    private String name;
    /**
     * Number of places within the city.
     */
    private String num_places; //to do inta
    /**
     * Refresh rate of the city.
     */
    private String refresh_rate; //to do inta
    //TODO bounds
    @SerializedName("places")
    /**
     * List of Stations within the city.
     */
    private List<Station> stations;


    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getNum_places() {
        return Integer.parseInt(num_places);
    }

    public int getRefresh_rate() {
        return Integer.parseInt(refresh_rate);
    }

    public List<Station> getStations() {
        return stations;
    }
}
