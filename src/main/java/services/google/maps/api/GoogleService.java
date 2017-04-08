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

public class GoogleService {

    private GeoApiContext context;

    public GoogleService() {
        context = new GeoApiContext().setApiKey("AIzaSyAUlf8MTcxeqfUMtIRlU4EFwNDhesHbzN4");
    }

    public void initialize() {
        try {
//            DirectionsResult results2 = DirectionsApi.newRequest(context)
//                    .origin("Rolna 31 Poznań")
//                    .destination("Piotrowo 4 Poznań")
//                    .mode(TravelMode.BICYCLING)
//                    .await();
//            System.out.println("");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public DirectionsResult directionsFromAddress (String startAddress, String endAddress) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startAddress)
                .destination(endAddress)
                .mode(TravelMode.BICYCLING)
                .await();
    } //TODO lepiej chyba wszystko wyznaczać ze współrzędnych a adresy zamieniac za pomocą addressToCoords

    public LatLng addressToCoords (String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        return results[0].geometry.location;
    } //TODO pomyśleć czy potrzebne w drugą stronę

    public DirectionsResult directionsFromCoords (LatLng startCoords, LatLng endCoords, boolean byBike) throws InterruptedException, ApiException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(startCoords)
                .destination(endCoords)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING) //bo musi dojsc do stacji wiec trzeba wyznaczac 3 trasy zawsze tak naprawdę
                .await();
    }

}
