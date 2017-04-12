import com.google.maps.errors.ApiException;
import services.Bootstrap;

import java.io.IOException;

public class Main {

    public static void main(String [] args) throws InterruptedException, ApiException, IOException {

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handleRoute();

        System.out.println("");
    }
}
