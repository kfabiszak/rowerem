import com.google.maps.errors.ApiException;
import services.Bootstrap;
import services.google.maps.api.GoogleService;
import services.nextbike.api.NextBikeService;
import services.nextbike.api.structure.City;
import services.nextbike.api.structure.Place;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws InterruptedException, ApiException, IOException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handleRoute();

//        NextBikeService nextBikeService = new NextBikeService();
//        City city = nextBikeService.findCity("PL","Poznań");
//        Place place = new Place(52.420436599999995, 16.88854600000002);
//        System.out.println(nextBikeService.findClosest(city, place));

        System.out.println("");
    }
}
