package services.nextbike.api;

import com.google.gson.*;
import services.JSONTransformer;
import travel.Root;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class NextBikeService {

    private String url = "https://nextbike.net/maps/nextbike-official.json";
    private JSONTransformer jsonTransformer = new JSONTransformer();
    private Root root;

    public NextBikeService() throws IOException {

        InputStream is = new URL(url).openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = jsonTransformer.readAll(rd);

        Gson gson = new GsonBuilder().serializeNulls().create();
        root = gson.fromJson(jsonText, Root.class);

    }

}
