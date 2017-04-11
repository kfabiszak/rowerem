package services.nextbike.api;

import com.google.gson.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import services.JSONTransformer;
import services.nextbike.api.structure.City;
import services.nextbike.api.structure.Root;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import services.google.maps.api.GoogleService;
import services.nextbike.api.structure.Station;

/**
 * NextBike API proxy class.
 */
public class NextBikeService {

    /**
     * JSON data URL.
     */
    private String url = "https://nextbike.net/maps/nextbike-official.json";
    /**
     * Root of the data.
     */
    private Root root;

    /**
     * Construct NetBike proxy. Pass JSON data to Root object.
     *
     * @throws IOException
     */
    public NextBikeService() throws IOException {

        JSONTransformer jsonTransformer = new JSONTransformer();

        InputStream is = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = jsonTransformer.readAll(rd);

        Gson gson = new GsonBuilder().serializeNulls().create();
        root = gson.fromJson(jsonText, Root.class);

    } //to chyba pójdzie do Route

    /**
     * Find the Station closest to the User.
     *
     * @param origin User position
     * @param city User city
     * @return Coordinates of closest Station.
     * @throws java.lang.InterruptedException
     * @throws com.google.maps.errors.ApiException
     * @throws java.io.IOException
     */
    public LatLng findClosest(LatLng origin, City city) throws InterruptedException, ApiException, IOException {
        GoogleService gs = new GoogleService();

        int max = Integer.MAX_VALUE;
        Station dest = null;

        for (Station st : city.getStations()) {
            DirectionsResult dr = gs.directionsFromCoords(origin, st.getLatLng(), false);
            int sum = 0;
            for (DirectionsLeg leg : dr.routes[0].legs) {
                sum += leg.distance.inMeters;
            }
            if (sum < max) {
                dest = st;
            }

        }
        if(dest != null)
            return dest.getLatLng();
        else
            return null;
    }

}
