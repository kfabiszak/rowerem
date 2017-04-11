import com.google.maps.errors.ApiException;
import services.Bootstrap;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws InterruptedException, ApiException, IOException {
//        RouteFromClient route = new RouteFromClient(loc.directionsFromAddress("Rolna 31 Poznań", "Piotrowo 4 Poznań")); //sprawdzic czy bootstrap tego nie nadpisze na nulla

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handleRoute();

        System.out.println("");
    }
}
