package services.google.maps.api;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import java.io.IOException;

/**
 * Google GeoAPI proxy class. Enables to get directions from Adress or Coords. 
 */
public class GoogleService {

    /**
     * Context of GeoApi.
     */
    private GeoApiContext context;
    /**
     * Api key to GoogleMaps service.
     */
    private String key = "AIzaSyAUlf8MTcxeqfUMtIRlU4EFwNDhesHbzN4";

    /**
     * Construct a service with GeoAPI Context, provide the key.
     */
    public GoogleService() {
        context = new GeoApiContext().setApiKey(key);
    }

    /**
     * Gets directions from start point to end point (in String addresses).
     * @param startAddress start address.
     * @param endAddress end address.
     * @param byBike <code>true</code> if directions for bicycling, <code>false</code> for walking.
     * @return Google DirectionsAPI result from startAdress to endAdress.
     * @throws InterruptedException e
     * @throws ApiException e
     * @throws IOException Input-Output Exception
     */
    public DirectionsResult directionsFromAddress (String startAddress, String endAddress, boolean byBike) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startAddress)
                .destination(endAddress)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING)
                .await();
    }

    /**
     * Converts address string to LatLng coords.
     * @param address Address String.
     * @return LatLng coords of written address.
     * @throws InterruptedException e
     * @throws ApiException e
     * @throws IOException Input-Output Exception
     */
    public LatLng addressToCoords (String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results[0].geometry.location;
    }

    /**
     * Gets directions from start point to end point (in LatLng coords).
     * @param startCoords start coordinates.
     * @param endCoords end coordinates.
     * @param byBike <code>true</code> if directions for bicycling, <code>false</code> for walking.
     * @return DirectionsAPI result from startCoords to endCoords.
     * @throws InterruptedException e
     * @throws ApiException e
     * @throws IOException Input-Output Exception
     */
    public DirectionsResult directionsFromCoords (LatLng startCoords, LatLng endCoords, boolean byBike) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startCoords)
                .destination(endCoords)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING)
                .await();
    }

}
