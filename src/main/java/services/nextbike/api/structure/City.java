package services.nextbike.api.structure;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City extends Place {

    private int uid;
    private String name;
    private String num_places; //to do inta
    private String refresh_rate; //to do inta
    //TODO bounds
    @SerializedName("places")
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
