package travel;

/**
 * Represents the route passed to client.
 */
public class RouteToClient {

    /**
     * Address of start point.
     */
    private String origin;
    /**
     * Address of end point.
     */
    private String destination;
    /**
     * Latitude of origin Station.
     */
    private double startStationLat;
    /**
     * Longitude of origin station.
     */
    private double startStationLng;
    /**
     * Latitude of target Station.
     */
    private double endStationLat;
    /**
     * Longitude of target Station.
     */
    private double endStationLng;

    /**
     * Temporary constructor of object passed to client so that client can show the route on map.
     * @param origin Address of start point of user.
     * @param destination Address of end point of user.
     * @param startStationLat Latitude of start Station.
     * @param startStationLng Longitude of start Station.
     * @param endStationLat Latitude of end Station.
     * @param endStationLng Longitude of end Station.
     */
    public RouteToClient(String origin, String destination, double startStationLat, double startStationLng, double endStationLat, double endStationLng) {
        this.origin = origin;
        this.destination = destination;
        this.startStationLat = startStationLat;
        this.startStationLng = startStationLng;
        this.endStationLat = endStationLat;
        this.endStationLng = endStationLng;
    }

}
