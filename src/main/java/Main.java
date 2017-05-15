import com.google.maps.errors.ApiException;
import services.Bootstrap;
import services.google.maps.api.GoogleService;
import services.nextbike.api.NextBikeService;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws InterruptedException, ApiException, IOException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handleRoute();

//        NextBikeService nextBikeService = new NextBikeService();
//        System.out.println();

        System.out.println("");
    }
}
