/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author monday
 */
public class GoogleApiSingleton {
    private static GoogleApiSingleton instance = null;
    protected GoogleApiSingleton() {
        
    }
    
    public static GoogleApiSingleton getInstance() {
        if(instance == null) {
            instance = new GoogleApiSingleton();
        }
        return instance;
    }
    
    public DirectionsResult directionsNewRequestAddress(GeoApiContext context, String start, String end, boolean byBike) throws ApiException, InterruptedException, IOException {
        return DirectionsApi.newRequest(context)
                .origin(start)
                .destination(end)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING)
                .await();
    }
    
    public DirectionsResult directionsNewRequestCoords(GeoApiContext context, LatLng start, LatLng end, boolean byBike) throws ApiException, InterruptedException, IOException{
        return DirectionsApi.newRequest(context)
                .origin(start)
                .destination(end)
                .mode(byBike ? TravelMode.BICYCLING : TravelMode.WALKING)
                .await();
    }
    
    public GeocodingResult[] geocodingGeocode(GeoApiContext context, String address) throws ApiException, InterruptedException, IOException {
        return GeocodingApi.geocode(context, address).await();
    }
}
