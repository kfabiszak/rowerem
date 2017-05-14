package services.nextbike.api.structure;

/**
 * Represent bike station.
 */
public class Station extends Place {

    /**
     * Unique ID.
     */
    private int uid;
    /**
     * Name of the station.
     */
    private String name;
    /**
     * Address of the station.
     */
    private String address;
    /**
     * Number of the station.
     */
    private int number;
    /**
     * Bikes currently available.
     */
    private int bikes;
    /**
     * Bike racks. 
     */
    private int bike_racks;
    /**
     * Bike racks currently available.
     */
    private int free_racks;
    /**
     * <code>true</code> if station is currently unavailable.
     */
    private boolean maintenance;
    /**
     * List of bikes.
     */
    private String bike_numbers;

    public Station(double lat, double lng) {
        super(lat, lng);
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNumber() {
        return number;
    }

    public int getBikes() {
        return bikes;
    }

    public int getBike_racks() {
        return bike_racks;
    }

    public int getFree_racks() {
        return free_racks;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public String getBike_numbers() {
        return bike_numbers; //TODO zwracać tablicę intów
    }
    //TODO biketypes

}
