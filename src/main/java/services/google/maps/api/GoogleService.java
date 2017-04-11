package services.google.maps.api;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import travel.Route;

import java.io.IOException;

/**
 * Handles GeoAPI. Enables to get directions from Adress or Coords. 
 */
public class GoogleService {

    private GeoApiContext context;

    /**
     * Construct a service with GeoAPI Context, provides the key.
     */
    public GoogleService() {
        context = new GeoApiContext().setApiKey("AIzaSyAUlf8MTcxeqfUMtIRlU4EFwNDhesHbzN4");
    }

    /**
     * Gets directions from start point to end point (in String addresses).
     * @param startAddress 
     * @param endAddress
     * @return directions from startAdress to endAdress
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public DirectionsResult directionsFromAddress (String startAddress, String endAddress) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startAddress)
                .destination(endAddress)
                .mode(TravelMode.BICYCLING)
                .await();
    } //TODO lepiej chyba wszystko wyznaczać ze współrzędnych a adresy zamieniac za pomocą addressToCoords

    /**
     * Converts address string to LatLng coords.
     * @param address
     * @return LatLng coords of written address
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public LatLng addressToCoords (String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results[0].geometry.location;
    } //TODO pomyśleć czy potrzebne w drugą stronę

    /**
     * Gets directions from start point to end point (in LatLng coords).
     * @param startCoords
     * @param endCoords
     * @param byBike <code>true</code> if directions for bicycling, <code>false</code> otherwise
     * @return directions from startCoords to endCoords
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    public DirectionsResult directionsFromCoords (LatLng startCoords, LatLng endCoords, boolean byBike) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startCoords)
                .destination(endCoords)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING) //bo musi dojsc do stacji wiec trzeba wyznaczac 3 trasy zawsze tak naprawdę
                .await();
    }

}
