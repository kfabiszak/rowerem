package services.nextbike.api;

import com.google.gson.*;
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

public class NextBikeService {

    private String url = "https://nextbike.net/maps/nextbike-official.json";
    private Root root;

    public NextBikeService() throws IOException {

        JSONTransformer jsonTransformer = new JSONTransformer();

        InputStream is = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = jsonTransformer.readAll(rd);

        Gson gson = new GsonBuilder().serializeNulls().create();
        root = gson.fromJson(jsonText, Root.class);

    } //to chyba pójdzie do Route

    public LatLng findClosest(LatLng origin, City city) {


        return new LatLng(1.0, 1.0);
    }

}
