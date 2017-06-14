import com.google.maps.errors.ApiException;
import services.Bootstrap;

import java.io.IOException;

/**
 * Main class of rowerem application.
 */
public class Main {

    /**
     * Main loop of rowerem application.
     * @param args
     * @throws InterruptedException e
     * @throws ApiException e
     * @throws IOException e
     */
    public static void main(String [] args) throws InterruptedException, ApiException, IOException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handleRoute();

//        NextBikeService nextBikeService = new NextBikeService();
//        System.out.println();

        System.out.println("");
    }
}
