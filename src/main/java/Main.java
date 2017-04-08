import com.google.maps.errors.ApiException;
import services.google.maps.api.GoogleService;
import travel.Route;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws InterruptedException, ApiException, IOException {
        GoogleService loc = new GoogleService();
        Route route = new Route(loc.directionsFromAddress("Rolna 31 Poznań", "Piotrowo 4 Poznań"));
        System.out.println("Hello World2!");
    }

}
